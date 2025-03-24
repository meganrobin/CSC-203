import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Chef extends Animatable implements Transformable, Movable {
    public static final String CHEF_KEY = "chef";

    // random creation variables
    public static final double CHEF_RANDOM_ANIMATION_PERIOD_MIN = 0.333;
    public static final double CHEF_RANDOM_ANIMATION_PERIOD_MAX = 0.667;
    public static final double CHEF_RANDOM_BEHAVIOR_PERIOD_MIN = 1.0;
    public static final double CHEF_RANDOM_BEHAVIOR_PERIOD_MAX = 2.0;
    public static final int CHEF_RANDOM_RESOURCE_LIMIT_MIN = 3;
    public static final int CHEF_RANDOM_RESOURCE_LIMIT_MAX = 5;

    /** Number of resources collected by the entity. */
    private int resourceCount;
    /** Total number of resources the entity may hold. */
    private int resourceLimit;
    /** True only when Chef is carrying soup. */
    private boolean soup;

    Chef(String id, Point position, List<PImage> images, double animationPeriod, double behaviorPeriod, int resourceCount, int resourceLimit, boolean soup) {
        super(id, position, images, animationPeriod, behaviorPeriod);
        this.resourceCount = resourceCount;
        this.resourceLimit = resourceLimit;
        this.soup = soup;
    }

    /** Executes Chef specific Logic. */
    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        Optional<Entity> chefTarget = findChefTarget(world);
        System.out.print(this);
        System.out.print(" has target: ");
        System.out.println(chefTarget);
        // scheduleBehavior if: there's no available target || the Chef's target is NOT adjacent to the Chef || when Chef adds 1 resource, but that +1 still doesn't make the Chef reach max resource limit
        if (chefTarget.isEmpty() || !moveTo(world, chefTarget.get(), scheduler, imageLibrary) || !transform(world, scheduler, imageLibrary)) {
            scheduleBehavior(scheduler, world, imageLibrary);
        }
    }

    /** Returns the (optional) entity a Chef will path toward. */
    public Optional<Entity> findChefTarget(World world) {
        List<Class<?>> potentialTargets;

        if (soup) { // if the Chef is carrying soup
            potentialTargets = List.of(Dude.class);
        }
        else if (resourceCount == resourceLimit) { // if Chef needs to go to a Restaurant
            potentialTargets = List.of(Restaurant.class);
        } else {
            potentialTargets = List.of(Chanterelle.class);
        }
        return world.findNearest(getPosition(), potentialTargets);
    }

    /** Attempts to move the Chef toward a target, returning True if already adjacent to it. */
    public boolean moveTo(World world, Entity target, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (getPosition().adjacentTo(target.getPosition())) { // if Chef is adjacent to the target, and if the target is a Chanterelle
            if (target instanceof Chanterelle) {
                ((Health) target).setHealth(((Health) target).getHealth() - 1);
            }
            else if (target instanceof Restaurant) {
                if (((Restaurant) target).getChanterelleAmount() + resourceCount >= 6) { // if the restaurant has 6 or more chanterelles
                    ((Restaurant) target).setChanterelleAmount(((Restaurant) target).getChanterelleAmount() + resourceCount - 6);
                    soup = true;
                    resourceCount = 0;
                } else {
                    ((Restaurant) target).setChanterelleAmount(((Restaurant) target).getChanterelleAmount() + resourceCount);
                }
            }
            else if (target instanceof Dude) {
                soup = false;
                Chef chef = new Chef(getID(), getPosition(), imageLibrary.get(CHEF_KEY), getAnimationPeriod(), getBehaviorPeriod(), 0, resourceLimit, false);

                world.removeEntity(scheduler, this);

                world.addEntity(chef);
                chef.schedule(scheduler, world, imageLibrary);

                Chef chefTransformed = new Chef(getID(), target.getPosition(), imageLibrary.get(CHEF_KEY), getAnimationPeriod(), getBehaviorPeriod(), 0, resourceLimit, false);

                world.removeEntity(scheduler, target);

                world.addEntity(chefTransformed);
                chefTransformed.schedule(scheduler, world, imageLibrary);
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
     * Determines a Chef's next position when moving.
     */
    public Point nextPosition(World world, Point destination) {
        // Initialize the pathing strategy
        PathingStrategy pathingStrategy = new AStarPathingStrategy();

        // A lambda function that determines if a grid point is traversable
        // True if either the grid point isn't occupied and the grid point is in bounds
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
            System.out.println("Empty path");
            return getPosition();
        } else {
            // Logic if there is a path
            System.out.println("Path found");
            return path.getFirst();
        }

    }

    /** Changes the Chef's graphics. */
    public boolean transform(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (soup) { // when resourceCount is greater than or equal to resourceLimit, change chef_carry animations to chef_soup animations
            Chef chef = new Chef(getID(), getPosition(), imageLibrary.get(CHEF_KEY + "_soup"), getAnimationPeriod(), getBehaviorPeriod(), 0, resourceLimit, true);

            world.removeEntity(scheduler, this);

            world.addEntity(chef);
            chef.schedule(scheduler, world, imageLibrary);

            return true;
        } else if (resourceCount < resourceLimit) { // if resourceCount isn't at limit, change chef animations to chef_carry animations
            resourceCount += 1;
            if (resourceCount == resourceLimit) {
                Chef chef = new Chef(getID(), getPosition(), imageLibrary.get(CHEF_KEY + "_carry"), getAnimationPeriod(), getBehaviorPeriod(), resourceCount, resourceLimit, false);

                world.removeEntity(scheduler, this);

                world.addEntity(chef);
                chef.schedule(scheduler, world, imageLibrary);

                return true;
            }
        } else { // when resourceCount is greater than or equal to resourceLimit, change chef_soup animations to chef animations
            Chef chef = new Chef(getID(), getPosition(), imageLibrary.get(CHEF_KEY), getAnimationPeriod(), getBehaviorPeriod(), 0, resourceLimit, false);

            world.removeEntity(scheduler, this);

            world.addEntity(chef);
            chef.schedule(scheduler, world, imageLibrary);

            return true;
        }

        return false; // returns false when Chef adds 1 resource, but that +1 still doesn't make the Chef reach max resource limit
    }
}