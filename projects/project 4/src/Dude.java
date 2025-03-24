import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Dude extends Animatable implements Transformable, Movable {
    public static final String DUDE_KEY = "dude";

    public static final int DUDE_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 0;
    public static final int DUDE_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX = 1;
    public static final int DUDE_PARSE_PROPERTY_RESOURCE_LIMIT_INDEX = 2;
    public static final int DUDE_PARSE_PROPERTY_COUNT = 3;

    /** Number of resources collected by the entity. */
    private int resourceCount;
    /** Total number of resources the entity may hold. */
    private int resourceLimit;

    Dude(String id, Point position, List<PImage> images, double animationPeriod, double behaviorPeriod, int resourceCount, int resourceLimit) {
        super(id, position, images, animationPeriod, behaviorPeriod);
        this.resourceCount = resourceCount;
        this.resourceLimit = resourceLimit;
    }

    /** Executes Dude specific Logic. */
    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        Optional<Entity> dudeTarget = findDudeTarget(world);
        if (dudeTarget.isEmpty() || !moveTo(world, dudeTarget.get(), scheduler, imageLibrary) || !transform(world, scheduler, imageLibrary)) {
            scheduleBehavior(scheduler, world, imageLibrary);
        }
    }

    /** Returns the (optional) entity a Dude will path toward. */
    public Optional<Entity> findDudeTarget(World world) {
        List<Class<?>> potentialTargets;

        if (resourceCount == resourceLimit) {
            potentialTargets = List.of(House.class);
        } else {
            potentialTargets = List.of(Tree.class, Sapling.class);
        }

        return world.findNearest(getPosition(), potentialTargets);
    }

    /** Attempts to move the Dude toward a target, returning True if already adjacent to it. */
    public boolean moveTo(World world, Entity target, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (getPosition().adjacentTo(target.getPosition())) {
            if (target instanceof Tree || target instanceof Sapling) {
                ((Health) target).setHealth(((Health) target).getHealth() - 1);
            }
            return true;
        } else {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }

            return false;
        }
    }

    /**
     * Determines a Dude's next position when moving.
     */
    public Point nextPosition(World world, Point destination) {
        // Initialize the pathing strategy
        PathingStrategy pathingStrategy = new AStarPathingStrategy();

        // A lambda function that determines if a grid point is traversable
        // True if either the grid point isn't occupied or it's occupied by a stump, and the grid point is in bounds
        Predicate<Point> canPassThrough = point -> world.inBounds(point) && (!world.isOccupied(point) || world.getOccupant(point).get() instanceof Stump);

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
//        // If this is not the case, also check 'World.inBounds()'
//        boolean horizontalOccupied = world.isOccupied(horizontalPosition) && !(world.getOccupant(horizontalPosition).get() instanceof Stump);
//        boolean verticalOccupied = world.isOccupied(verticalPosition) && !(world.getOccupant(verticalPosition).get() instanceof Stump);
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

    /** Changes the Dude's graphics. */
    public boolean transform(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (resourceCount < resourceLimit) {
            resourceCount += 1;
            if (resourceCount == resourceLimit) {
                Dude dude = new Dude(getID(), getPosition(), imageLibrary.get(DUDE_KEY + "_carry"), getAnimationPeriod(), getBehaviorPeriod(), resourceCount, resourceLimit);

                world.removeEntity(scheduler, this);

                world.addEntity(dude);
                dude.schedule(scheduler, world, imageLibrary);

                return true;
            }
        } else { // when resourceCount >= resourceLimit
            Dude dude = new Dude(getID(), getPosition(), imageLibrary.get(DUDE_KEY), getAnimationPeriod(), getBehaviorPeriod(), 0, resourceLimit);

            world.removeEntity(scheduler, this);

            world.addEntity(dude);
            dude.schedule(scheduler, world, imageLibrary);

            return true;
        }

        return false; // returns false when Dude adds 1 resource, but that +1 still doesn't make the Dude reach max resource limit
    }
}