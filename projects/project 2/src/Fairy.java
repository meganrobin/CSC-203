import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Fairy extends Animatable implements Movable {
    public static final String FAIRY_KEY = "fairy";

    public static final int FAIRY_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 0;
    public static final int FAIRY_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX = 1;
    public static final int FAIRY_PARSE_PROPERTY_COUNT = 2;

    Fairy(String id, Point position, List<PImage> images, double animationPeriod, double behaviorPeriod) {
        super(id, position, images, animationPeriod, behaviorPeriod);
    }

    /** Executes Fairy specific Logic. */
    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        Optional<Entity> fairyTarget = world.findNearest(getPosition(), new ArrayList<>(List.of(Stump.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (moveTo(world, fairyTarget.get(), scheduler)) {
                Sapling sapling = new Sapling(Sapling.SAPLING_KEY + "_" + fairyTarget.get().getID(), tgtPos, imageLibrary.get(Sapling.SAPLING_KEY));

                world.addEntity(sapling);
                sapling.schedule(scheduler, world, imageLibrary);
            }
        }

        scheduleBehavior(scheduler, world, imageLibrary);
    }

    /** Attempts to move the Fairy toward a target, returning True if already adjacent to it. */
    public boolean moveTo(World world, Entity target, EventScheduler scheduler) {
        if (getPosition().adjacentTo(target.getPosition())) {
            world.removeEntity(scheduler, target);
            return true;
        } else {
            Point nextPos = nextPosition(world, target.getPosition());
            if (!getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    /** Determines a Fairy's next position when moving. */
    public Point nextPosition(World world, Point destination) {
        // Differences between the destination and current position along each axis
        int deltaX = destination.x - getPosition().x;
        int deltaY = destination.y - getPosition().y;

        // Positions one step toward the destination along each axis
        Point horizontalPosition = new Point(getPosition().x + Integer.signum(deltaX), getPosition().y);
        Point verticalPosition = new Point(getPosition().x, getPosition().y + Integer.signum(deltaY));

        // Assumes all destinations are within bounds of the world
        // If this is not the case, also check 'World.withinBounds()'
        boolean horizontalOccupied = world.isOccupied(horizontalPosition);
        boolean verticalOccupied = world.isOccupied(verticalPosition);

        // Move along the farther direction, preferring horizontal
        if (Math.abs(deltaX) >= Math.abs(deltaY)) {
            if (!horizontalOccupied) {
                return horizontalPosition;
            } else if (!verticalOccupied) {
                return verticalPosition;
            }
        } else {
            if (!verticalOccupied) {
                return verticalPosition;
            } else if (!horizontalOccupied) {
                return horizontalPosition;
            }
        }

        return getPosition();
    }
}