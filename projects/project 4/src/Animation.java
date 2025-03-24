public class Animation implements Action {
    private final Animatable animatableEntity;
    private int repeatCount;

    Animation(Animatable animatableEntity, int repeatCount) {
        this.animatableEntity = animatableEntity;
        this.repeatCount = repeatCount;
    }
    /** Performs 'Animation' specific execute logic. */
    public void execute(EventScheduler scheduler) {
        animatableEntity.updateImage();

        if (repeatCount != 1) {
            scheduler.scheduleEvent(animatableEntity, new Animation(animatableEntity, Math.max(this.repeatCount - 1, 0)), animatableEntity.getAnimationPeriod());
        }
    }
}