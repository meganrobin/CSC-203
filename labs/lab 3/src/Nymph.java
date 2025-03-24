import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Nymph implements Actor, Repositionable {
    public static final String NYMPH_KEY = "nymph";
    /** An x/y position in the world. */
    private Point position;
    /** Inanimate (singular) or animation (multiple) image graphics. */
    private List<PImage> images;
    /** Index of the element from 'images' used to draw the actor. */
    private int imageIndex;
    /** Positive (non-zero) time delay between the actor's updates. */
    private double updatePeriod;

    /**
     * Returns a new 'Nymph' type actor.
     * Constructor arguments provide hints to data necessary for a subtype.
     *
     * @param position      The actor's x/y position in the world.
     * @param images        A list of images that represent an actor and its possible animation.
     * @param updatePeriod  The time between when an actor's update is scheduled and executed.
     *
     * @return A new Actor object configured as a(n) 'Nymph'.
     */
    public Nymph(Point position, List<PImage> images, double updatePeriod) {
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.updatePeriod = updatePeriod;
    }

    /** Schedules the next update for the actor. */
    public void scheduleUpdate(World world, ImageLibrary imageLibrary, EventScheduler eventScheduler) {
        eventScheduler.scheduleEvent(this, new Update(this, world, imageLibrary), updatePeriod);
    }


    /** Nymph update logic. */
    public void executeUpdate(World world, ImageLibrary imageLibrary, EventScheduler eventScheduler) {
        imageIndex += 1;
        reposition(world, eventScheduler);
        scheduleUpdate(world, imageLibrary, eventScheduler);
    }

    /** Nymph motion logic. */
    public void reposition(World world, EventScheduler eventScheduler) {
        int nextX = position.x;
        int nextY = position.y;

        Random rand = new Random();
        if (rand.nextDouble() < 0.25) {
            if (rand.nextDouble() < 0.50) {
                if (rand.nextDouble() < 0.50) {
                    nextX += 1;
                } else {
                    nextX -= 1;
                }
            } else {
                if (rand.nextDouble() < 0.50) {
                    nextY += 1;
                } else {
                    nextY -= 1;
                }
            }
        }

        Point destination = new Point(nextX, nextY);
        if (world.inBounds(destination) && !world.isOccupied(destination)) {
            world.moveEntity(eventScheduler, this, destination);
        }
    }

    public int getImageIndex() {return imageIndex;}
    public List<PImage> getImages() {return images;}
    public Point getPosition() {return position;}
    public void setPosition(Point position) {this.position = position;}
}
