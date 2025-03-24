import processing.core.PImage;

import java.util.List;

public abstract class Animatable extends Behaviorable {
    /** Positive (non-zero) time delay between the entity's animations. */
    private double animationPeriod;
    Animatable(String id, Point position, List<PImage> images, double animationPeriod, double behaviorPeriod) {
        super(id, position, images, behaviorPeriod);
        this.animationPeriod = animationPeriod;
    }

    /** Begins all animation updates for the entity. */
    public void schedule(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduler.scheduleEvent(this, new Animation(this, 0), animationPeriod);
        super.scheduleBehavior(scheduler, world, imageLibrary);
    }
    public void updateImage() {
        setImageIndex(getImageIndex() + 1);
    }

    public double getAnimationPeriod() {
        return animationPeriod;
    }
}