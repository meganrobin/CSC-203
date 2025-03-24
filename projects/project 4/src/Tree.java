import processing.core.PImage;

import java.util.List;

public class Tree extends Health implements Transformable {
    public static final String TREE_KEY = "tree";

    public static final int TREE_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 0;
    public static final int TREE_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX = 1;
    public static final int TREE_PARSE_PROPERTY_HEALTH_INDEX = 2;
    public static final int TREE_PARSE_PROPERTY_COUNT = 3;

    Tree(String id, Point position, List<PImage> images, double animationPeriod, double behaviorPeriod, int health){
        super(id, position, images, animationPeriod, behaviorPeriod, health);
    }

    /** Executes Tree specific Logic. */
    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        if (!transform(world, scheduler, imageLibrary)) {
            scheduleBehavior(scheduler, world, imageLibrary);
        }
    }

    /** Checks the Tree's health and transforms accordingly, returning true if successful. */
    public boolean transform(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (getHealth() <= 0) {
            Stump stump = new Stump(Stump.STUMP_KEY + "_" + getID(), getPosition(), imageLibrary.get(Stump.STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return true;
        }

        return false;
    }
}
