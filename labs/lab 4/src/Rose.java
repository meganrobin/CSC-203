import processing.core.PImage;

import java.util.List;

public class Rose extends WaterActor {
    public static final String ROSE_KEY = "rose";
    public static final double ROSE_TO_NYMPH_UPDATE_PERIOD = 0.125;
    public static final int ROSE_WATER_LIMIT = 9;

    public Rose(Point position, List<PImage> images, double updatePeriod) {
        super(position, images, updatePeriod, 0);
    }

    /** Rose update logic. */
    public void executeUpdate(World world, ImageLibrary imageLibrary, EventScheduler eventScheduler) {
        setImageIndex(getImages().size() * getWater() / ROSE_WATER_LIMIT);
        if (getWater() >= ROSE_WATER_LIMIT) {
            Actor nymph = new Nymph(getPosition(), imageLibrary.get(Nymph.NYMPH_KEY), ROSE_TO_NYMPH_UPDATE_PERIOD);

            world.removeEntity(eventScheduler, this);

            world.addEntity(nymph);
            nymph.scheduleUpdate(world, imageLibrary, eventScheduler);
        } else {
            scheduleUpdate(world, imageLibrary, eventScheduler);
        }
    }
}
