import java.util.*;
import java.util.List;

/**
 * Represents the 2D World in which this simulation is running.
 * Keeps track of the size of the world, the background image for each
 * location in the world, and the entities that populate the world.
 */
public final class World {
    /** World height. */
    private final int numRows;

    /** World width. */
    private final int numCols;

    /** Background tile grid. */
    private final Background[][] background;

    /** Actor grid. */
    private final Actor[][] occupancy;

    /** Actor set. Must be synchronized with the 'occupancy' grid. */
    private final Set<Actor> entities;

    public World(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Actor[numRows][numCols];
        this.entities = new HashSet<>();
    }

    /** Returns 'true' if the given point is within the world. */
    public boolean inBounds(Point point) {
        return point.y >= 0 && point.y < numRows
                && point.x >= 0 && point.x < numCols;
    }

    /** Returns 'true' if the given point contains an actor. */
    public boolean isOccupied(Point position) {
        return occupancy[position.y][position.x] != null;
    }

    /** Returns the (optional) actor at the given point. */
    public Optional<Actor> getOccupant(Point position) {
        if (inBounds(position) && isOccupied(position)) {
            return Optional.of(occupancy[position.y][position.x]);
        } else {
            return Optional.empty();
        }
    }

    /** Attempts to add an actor to the world. */
    public void addEntity(Actor actor) {
        if (!inBounds(actor.getPosition())) {
            throw new IllegalArgumentException(String.format(
                    "World position %s out of bounds", actor.getPosition()
            ));
        }
        if (isOccupied(actor.getPosition())) {
            throw new IllegalArgumentException(String.format(
                    "World already occupied at position %s", actor.getPosition()
            ));
        }

        setOccupancyCell(actor.getPosition(), actor);
        entities.add(actor);
    }

    /** Moves an actor in the world, updating data structures as necessary. */
    public void moveEntity(EventScheduler scheduler, Actor actor, Point position) {
        Point oldPos = actor.getPosition();
        if (inBounds(position) && !position.equals(oldPos)) {
            setOccupancyCell(oldPos, null);
            Optional<Actor> occupant = getOccupant(position);
            occupant.ifPresent(target -> removeEntity(scheduler, target));
            setOccupancyCell(position, actor);
            actor.setPosition(position);
        }
    }

    /** Removes the given actor from the world, unscheduling its events. */
    public void removeEntity(EventScheduler scheduler, Actor actor) {
        scheduler.unscheduleAllEvents(actor);
        removeEntityAt(actor.getPosition());
    }

    /** Removes an actor at a given position in the world, assuming its events are unscheduled. */
    private void removeEntityAt(Point position) {
        Optional<Actor> potentialEntity = getOccupant(position);
        if (potentialEntity.isPresent()) {
            Actor actor = potentialEntity.get();

            // Moves the entity just outside the grid for debugging purposes.
            actor.setPosition(new Point(-1, -1));
            entities.remove(actor);
            setOccupancyCell(position, null);
        }
    }

    /** Updates the actor occupancy grid at the given point. */
    public void setOccupancyCell(Point point, Actor actor) {
        occupancy[point.y][point.x] = actor;
    }

    /** Updates the background tile grid at the given point. */
    public void setBackgroundCell(Point point, Background background) {
        this.background[point.y][point.x] = background;
    }

    /** Returns 'true' if the given point contains a background tile. */
    public boolean hasBackground(Point point) {
        return background[point.y][point.x] != null;
    }

    /** Returns a background tile at the given point or null if one doesn't exist. */
    public Background getBackgroundCell(Point point) {
        return background[point.y][point.x];
    }

    /** Returns the (optional) background tile at the given point. */
    public Optional<Background> getBackground(Point position) {
        if (inBounds(position) && hasBackground(position)) {
            return Optional.of(background[position.y][position.x]);
        } else {
            return Optional.empty();
        }
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public Set<Actor> getEntities() {
        return entities;
    }

}
