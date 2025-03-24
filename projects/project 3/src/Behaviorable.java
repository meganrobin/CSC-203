import processing.core.PImage;

import java.util.List;

public abstract class Behaviorable extends Entity {
    /** Positive (non-zero) time delay between the entity's behaviors. */
    private double behaviorPeriod;

    Behaviorable(String id, Point position, List<PImage> images, double behaviorPeriod) {
        super(id, position, images);
        this.behaviorPeriod = behaviorPeriod;
    }

    public void scheduleBehavior(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduler.scheduleEvent(this, new Behavior(this, world, imageLibrary), behaviorPeriod);
    }
    public abstract void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler);

    public double getBehaviorPeriod() { return behaviorPeriod; }
}
