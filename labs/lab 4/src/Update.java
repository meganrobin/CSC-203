/** A scheduled update to be carried out by a specific actor. */
public class Update {
    /** The actor enacting the update. */
    private final Actor actor;

    /** World in which the update occurs. */
    private final World world;

    /** Image data that may be used by the update. */
    private final ImageLibrary imageLibrary;

    /** Constructs an Update object. */
    public Update(Actor actor, World world, ImageLibrary imageLibrary) {
        this.actor = actor;
        this.world = world;
        this.imageLibrary = imageLibrary;
    }

    /** Called when the update's scheduled time occurs. */
    public void execute(EventScheduler scheduler) {
        actor.executeUpdate(world, imageLibrary, scheduler);
    }
}
