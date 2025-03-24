import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Gardener implements Actor, Waterable, Repositionable {
    public static final String GARDENER_KEY = "gardener";
    public static final int GARDENER_WATER_LIMIT = 3;
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
     * Returns a new 'Gardener' type actor.
     * Constructor arguments provide hints to data necessary for a subtype.
     *
     * @param position      The actor's x/y position in the world.
     * @param images        A list of images that represent an actor and its possible animation.
     * @param updatePeriod  The time between when an actor's update is scheduled and executed.
     *
     * @return A new Actor object configured as a(n) 'Gardener'.
     */
    public Gardener(Point position, List<PImage> images, double updatePeriod) {
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.updatePeriod = updatePeriod;
        this.water = GARDENER_WATER_LIMIT;
    }

    /** Schedules the next update for the actor. */
    public void scheduleUpdate(World world, ImageLibrary imageLibrary, EventScheduler eventScheduler) {
        eventScheduler.scheduleEvent(this, new Update(this, world, imageLibrary), updatePeriod);
    }

    /** Gardener update logic. */
    public void executeUpdate(World world, ImageLibrary imageLibrary, EventScheduler eventScheduler) {
        imageIndex += 1;
        if (water > 0) {
            Optional<Actor> target = Actor.findByKind(world, Rose.class);

            if (target.isPresent()) {
                Rose rose = (Rose)target.get();

                if (!position.adjacentTo(rose.getPosition())) {
                    reposition(world, eventScheduler);
                } else {
                    rose.setWater(rose.getWater() + 1);
                    water = water - 1;
                }
            }
        } else {
            Optional<Actor> target = Actor.findByKind(world, Well.class);

            if (target.isPresent()) {
                Actor well = target.get();

                if (!position.adjacentTo(well.getPosition())) {
                    reposition(world, eventScheduler);
                } else {
                    water = GARDENER_WATER_LIMIT;
                }
            }
        }
        scheduleUpdate(world, imageLibrary, eventScheduler);
    }

    /** Gardener motion logic. */
    public void reposition(World world, EventScheduler eventScheduler) {
        Optional<Actor> target;
        if (water > 0) {
            target = Actor.findByKind(world, Rose.class);
        } else {
            target = Actor.findByKind(world, Well.class);
        }

        int nextX = position.x;
        int nextY = position.y;

        if (target.isPresent()) {
            Actor actor = target.get();

            if (position.x < actor.getPosition().x) nextX += 1;
            else if (position.x > actor.getPosition().x) nextX -= 1;
            else if (position.y < actor.getPosition().y) nextY += 1;
            else if (position.y > actor.getPosition().y) nextY -= 1;
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
    public int getWater() {return water;}
    public void setWater(int water) {this.water = water;}
}
