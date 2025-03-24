import java.util.*;
import processing.core.PImage;

/** Represents an actor that exists in the virtual world. */
public abstract class Actor {
    // TODO: You will remove enum and the 'kind' instance variable during refactoring.
    /**
     * Enumerated type defining different kinds of actors in the world.
     * Specific values are assigned to the actor's 'kind' instance variable at initialization.
     * Each kind represents a distinct kind of actor with unique properties and behaviors.
     */

    // Constant save file column positions for properties required by actors.
    public static final int ACTOR_PROPERTY_KEY_INDEX = 0;
    public static final int ACTOR_PROPERTY_POSITION_X_INDEX = 1;
    public static final int ACTOR_PROPERTY_POSITION_Y_INDEX = 2;
    public static final int ACTOR_PROPERTY_UPDATE_PERIOD_INDEX = 3;
    public static final int ACTOR_PROPERTY_COUNT = 4;

    // Constant string identifiers for the corresponding actor type.
    // Used to identify actors in the save file and retrieve image information.
    // TODO: Move to relevant subtypes.

    // Constant limits and default values for specific actor types.
    // TODO: Move into relevant subtypes.

    // TODO: Move/modify access to these instance variables in your hierarchy.

    /** An x/y position in the world. */
    private Point position;
    /** Inanimate (singular) or animation (multiple) image graphics. */
    private List<PImage> images;
    /** Index of the element from 'images' used to draw the actor. */
    private int imageIndex;
    /** Positive (non-zero) time delay between the actor's updates. */
    private double updatePeriod;

    // TODO: Remove (interfaces) or refactor with super (superclasses).
    /**
     * Constructs an Actor with specified characteristics.
     * In the base program, this is not called directly.
     * Instead, the encapsulated 'create' methods are used to create specific types of actors.
     *
     * @param position        The actor's x/y position in the world.
     * @param images          The actor's inanimate (singular) or animation (multiple) images.
     * @param updatePeriod    The positive (non-zero) time delay between the actor's updates.
     */
    public Actor(Point position, List<PImage> images, double updatePeriod) {
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.updatePeriod = updatePeriod;
    }

    // TODO: Replace usage of 'create' methods with relevant subtype constructors.

    // Regular Methods

    /** Searches the world for the first entity of a given kind.*/
    public static Optional<Actor> findByKind(World world, Class<?> actorKind) {
        // TODO: Modify usage of ActorKind with Class<?>

        for (int y = 0; y < world.getNumRows(); y++) {
            for (int x = 0; x < world.getNumCols(); x++) {
                Point point = new Point(x, y);

                Optional<Actor> potentialOccupant = world.getOccupant(point);
                if (potentialOccupant.isPresent()) {
                    Actor occupant = potentialOccupant.get();

                    // TODO: Modify to use actorKind.isInstance()
                    if (actorKind.isInstance(occupant)) {
                        return potentialOccupant;
                    }
                }
            }
        }

        return Optional.empty();
    }

    /** Schedules the next update for the actor. */
    public void scheduleUpdate(World world, ImageLibrary imageLibrary, EventScheduler eventScheduler) {
        eventScheduler.scheduleEvent(this, new Update(this, world, imageLibrary), updatePeriod);
    }

    /** Calls the actor specific update. */
    public abstract void executeUpdate(World world, ImageLibrary imageLibrary, EventScheduler eventScheduler);

    // Actor specific logic
    // TODO: Modify or move as necessary.

    // Getters and Setters
    // TODO: Add, move, and delete, only as necessary, to access private data in the hierarchy.

    public int getImageIndex() {
        return imageIndex;
    }

    public List<PImage> getImages() {return images;}

    public Point getPosition() {
        return position;
    }

    public void setImageIndex(int imageIndex) { this.imageIndex = imageIndex;}

    public void setPosition(Point position) {
        this.position = position;
    }
}
