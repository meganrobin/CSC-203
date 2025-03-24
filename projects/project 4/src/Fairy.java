import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

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

            if (moveTo(world, fairyTarget.get(), scheduler, imageLibrary)) {
                Sapling sapling = new Sapling(Sapling.SAPLING_KEY + "_" + fairyTarget.get().getID(), tgtPos, imageLibrary.get(Sapling.SAPLING_KEY));

                world.addEntity(sapling);
                sapling.schedule(scheduler, world, imageLibrary);
            }
        }

        scheduleBehavior(scheduler, world, imageLibrary);
    }

    /** Attempts to move the Fairy toward a target, returning True if already adjacent to it. */
    public boolean moveTo(World world, Entity target, EventScheduler scheduler, ImageLibrary imageLibrary) {
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
        // Initialize the pathing strategy
        PathingStrategy pathingStrategy = new AStarPathingStrategy();

        // A lambda function that determines if a grid point is traversable
        Predicate<Point> canPassThrough = point -> world.inBounds(point) && !world.isOccupied(point);

        // A lambda function that determines if two points are adjacent to each another
        // Can be simplified if you examine the Point class
        BiPredicate<Point, Point> withinReach = (p1, p2) -> p1.manhattanDistanceTo(p2) == 1;

        // A lambda function that produces a stream of neighboring points for a given point
        // Take a look at the type of PathingStrategy.CARDINAL_NEIGHBORS
        Function<Point, Stream<Point>> potentialNeighbors = point -> pathingStrategy.CARDINAL_NEIGHBORS.apply(point);

        // Call the pathing strategy's computePath
        List<Point> path = pathingStrategy.computePath(getPosition(), destination, canPassThrough, withinReach, potentialNeighbors);

        if (path.isEmpty()) {
            // Logic if there is no path or at the destination
            return getPosition();
        } else {
            // Logic if there is a path
            return path.getFirst();
        }

//        // Differences between the destination and current position along each axis
//        int deltaX = destination.x - getPosition().x;
//        int deltaY = destination.y - getPosition().y;
//
//        // Positions one step toward the destination along each axis
//        Point horizontalPosition = new Point(getPosition().x + Integer.signum(deltaX), getPosition().y);
//        Point verticalPosition = new Point(getPosition().x, getPosition().y + Integer.signum(deltaY));
//
//        // Assumes all destinations are within bounds of the world
//        // If this is not the case, also check 'World.withinBounds()'
//        boolean horizontalOccupied = world.isOccupied(horizontalPosition);
//        boolean verticalOccupied = world.isOccupied(verticalPosition);
//
//        // Move along the farther direction, preferring horizontal
//        if (Math.abs(deltaX) >= Math.abs(deltaY)) {
//            if (!horizontalOccupied) {
//                return horizontalPosition;
//            } else if (!verticalOccupied) {
//                return verticalPosition;
//            }
//        } else {
//            if (!verticalOccupied) {
//                return verticalPosition;
//            } else if (!horizontalOccupied) {
//                return horizontalPosition;
//            }
//        }
//
//        return getPosition();
    }
}