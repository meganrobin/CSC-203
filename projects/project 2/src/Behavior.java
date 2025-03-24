public class Behavior implements Action {
    private final Behaviorable behaviorableEntity;
    private final World world;
    private final ImageLibrary imageLibrary;

    Behavior(Behaviorable behaviorableEntity, World world, ImageLibrary imageLibrary) {
        this.behaviorableEntity = behaviorableEntity;
        this.world = world;
        this.imageLibrary = imageLibrary;
    }
    /** Performs 'Behavior' specific execute logic. */
    public void execute(EventScheduler scheduler) {
        behaviorableEntity.executeBehavior(world, imageLibrary, scheduler);
    }
}