import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Nymph extends Actor{

    public static final String NYMPH_KEY = "nymph";

    public Nymph(Point position, List<PImage> images, double updatePeriod) {
        super(position, images, updatePeriod);
    }

    /** Nymph update logic. */
    @Override
    public void executeUpdate(World world, ImageLibrary imageLibrary, EventScheduler eventScheduler) {
        setImageIndex(getImageIndex() + 1);
        reposition(world, eventScheduler);
        scheduleUpdate(world, imageLibrary, eventScheduler);
    }

    /** Nymph motion logic. */
    public void reposition(World world, EventScheduler eventScheduler) {
        int nextX = getPosition().x;
        int nextY = getPosition().y;

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
}
