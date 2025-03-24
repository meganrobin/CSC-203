import processing.core.PImage;

import java.util.List;

public class Rose implements Actor, Waterable {
    public static final String ROSE_KEY = "rose";
    public static final double ROSE_TO_NYMPH_UPDATE_PERIOD = 0.125;
    public static final int ROSE_WATER_LIMIT = 9;
    /** An x/y position in the world. */
    private Point position;
    /** Inanimate (singular) or animation (multiple) image graphics. */
    private List<PImage> images;
    /** Index of the element from 'images' used to draw the actor. */
    private int imageIndex;
    /** Positive (non-zero) time delay between the actor's updates. */
    private double updatePeriod;
    /** Current water level. */
    private int water;

    /**
     * Returns a new 'Rose' type actor.
     * Constructor arguments provide hints to data necessary for a subtype.
     *
     * @param position      The actor's x/y position in the world.
     * @param images        A list of images that represent an actor and its possible animation.
     * @param updatePeriod  The time between when an actor's update is scheduled and executed.
     *
     * @return A new Actor object configured as a(n) 'Rose'.
     */
    public Rose(Point position, List<PImage> images, double updatePeriod) {
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.updatePeriod = updatePeriod;
        water = 0;
    }

    /** Schedules the next update for the actor. */
    public void scheduleUpdate(World world, ImageLibrary imageLibrary, EventScheduler eventScheduler) {
        eventScheduler.scheduleEvent(this, new Update(this, world, imageLibrary), updatePeriod);
    }

    /** Rose update logic. */
    public void executeUpdate(World world, ImageLibrary imageLibrary, EventScheduler eventScheduler) {
        imageIndex = images.size() * water / ROSE_WATER_LIMIT;
        if (water >= ROSE_WATER_LIMIT) {
            Actor nymph = new Nymph(position, imageLibrary.get(Nymph.NYMPH_KEY), ROSE_TO_NYMPH_UPDATE_PERIOD);

            world.removeEntity(eventScheduler, this);

            world.addEntity(nymph);
            nymph.scheduleUpdate(world, imageLibrary, eventScheduler);
        } else {
            scheduleUpdate(world, imageLibrary, eventScheduler);
        }
    }

    public int getImageIndex() {return imageIndex;}
    public List<PImage> getImages() {return images;}
    public Point getPosition() {return position;}
    public void setPosition(Point position) {this.position = position;}
    public int getWater() {return water;}
    public void setWater(int water) {this.water = water;}
}
