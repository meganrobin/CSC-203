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

    /** Entity grid. */
    private final Entity[][] occupancy;

    /** Entity set. Must be synchronized with the 'occupancy' grid. */
    private final Set<Entity> entities;

    public World(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Entity[numRows][numCols];
        this.entities = new HashSet<>();
    }

    /** Logging used for testing. Do not move or modify this method. */
    public List<String> log(){
        return entities.stream()
                .map(Entity::log)
                .filter(Objects::nonNull)
                .toList();
    }


    /** Returns 'true' if the given point is within the world. */
    public boolean inBounds(Point position) {
        return position.y >= 0 && position.y < numRows && position.x >= 0 && position.x < numCols;
    }

    /** Returns 'true' if the given point contains an entity. */
    public boolean isOccupied(Point position) {
        return occupancy[position.y][position.x] != null;
    }

    /** Returns the (optional) entity at the given point. */
    public Optional<Entity> getOccupant(Point position) {
        if (inBounds(position) && isOccupied(position)) {
            return Optional.of(occupancy[position.y][position.x]);
        } else {
            return Optional.empty();
        }
    }

    /** Returns the nearest given entity to the given point. */
    public Optional<Entity> nearestEntity(List<Entity> entities, Point position) {
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            Entity nearest = entities.get(0);
            double nearestDistance = nearest.getPosition().manhattanDistanceTo(position);

            for (Entity other : entities) {
                double otherDistance = other.getPosition().manhattanDistanceTo(position);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

    /** Returns the (optional) nearest world entity of the given kind(s) to the point.*/
    public Optional<Entity> findNearest(Point position, List<Class<?>> kinds) {
        List<Entity> ofType = new LinkedList<>();
        for (Class<?> kind : kinds) {
            for (Entity entity : this.entities) {
                if (kind.isInstance(entity)) {
                    ofType.add(entity);
                }
            }
        }

        return nearestEntity(ofType, position);
    }

    /** Attempts to add an entity to the world. */
    public void addEntity(Entity entity) {
        if (!inBounds(entity.getPosition())) {
            throw new IllegalArgumentException(String.format(
                    "World position %s out of bounds", entity.getPosition()
            ));
        }
        if (isOccupied(entity.getPosition())) {
            throw new IllegalArgumentException(String.format(
                    "World already occupied at position %s", entity.getPosition()
            ));
        }

        setOccupancyCell(entity.getPosition(), entity);
        entities.add(entity);
    }

    /** Moves an entity in the world, updating data structures as necessary. */
    public void moveEntity(EventScheduler scheduler, Entity entity, Point position) {
        Point oldPos = entity.getPosition();
        if (inBounds(position) && !position.equals(oldPos)) {
            setOccupancyCell(oldPos, null);
            Optional<Entity> occupant = getOccupant(position);
            occupant.ifPresent(target -> removeEntity(scheduler, target));
            setOccupancyCell(position, entity);
            entity.setPosition(position);
        }
    }

    /** Removes the given entity from the world and unschedules its events. */
    public void removeEntity(EventScheduler scheduler, Entity entity) {
        scheduler.unscheduleAllEvents(entity);
        removeEntityAt(entity.getPosition());
    }

    /** Removes an entity from a given position in the world. **Does not** unschedule its events. */
    public void removeEntityAt(Point position) {
        Optional<Entity> potentialEntity = getOccupant(position);
        if (potentialEntity.isPresent()) {
            Entity entity = potentialEntity.get();

            // Moves the entity just outside the grid for debugging purposes.
            entity.setPosition(new Point(-1, -1));
            entities.remove(entity);
            setOccupancyCell(position, null);
        }
    }

    /** Updates the entity occupancy grid at the given point. */
    public void setOccupancyCell(Point position, Entity entity) {
        occupancy[position.y][position.x] = entity;
    }

    /** Updates the background tile grid at the given point. */
    public void setBackgroundCell(Point position, Background background) {
        this.background[position.y][position.x] = background;
    }

    /** Returns 'true' if the given point contains a background tile. */
    public boolean hasBackground(Point position) {
        return background[position.y][position.x] != null;
    }

    /** Returns a background tile at the given point or null if one doesn't exist. */
    public Background getBackgroundCell(Point position) {
        return background[position.y][position.x];
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

    public Set<Entity> getEntities() {
        return entities;
    }

}
