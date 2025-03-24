import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Gardener extends WaterActor {
    public static final String GARDENER_KEY = "gardener";
    public static final int GARDENER_WATER_LIMIT = 3;

    public Gardener(Point position, List<PImage> images, double updatePeriod) {
        super(position, images, updatePeriod, GARDENER_WATER_LIMIT);
    }

    /** Gardener update logic. */
    @Override
    public void executeUpdate(World world, ImageLibrary imageLibrary, EventScheduler eventScheduler) {
        setImageIndex(getImageIndex() + 1);
        if (this.getWater() > 0) {
            Optional<Actor> target = findByKind(world, Rose.class);

            if (target.isPresent()) {
                Actor rose = target.get();

                if (!getPosition().adjacentTo(rose.getPosition())) {
                    reposition(world, eventScheduler);
                } else {
                    WaterActor waterActorRose = (WaterActor) rose;
                    waterActorRose.setWater(waterActorRose.getWater() + 1);
                    setWater(getWater() - 1);
                }
            }
        } else {
            Optional<Actor> target = findByKind(world, Well.class);

            if (target.isPresent()) {
                Actor well = target.get();

                if (!getPosition().adjacentTo(well.getPosition())) {
                    reposition(world, eventScheduler);
                } else {
                    setWater(GARDENER_WATER_LIMIT);
                }
            }
        }
        scheduleUpdate(world, imageLibrary, eventScheduler);
    }

    /** Gardener motion logic. */
    public void reposition(World world, EventScheduler eventScheduler) {
        Optional<Actor> target;
        if (getWater() > 0) {
            target = findByKind(world, Rose.class);
        } else {
            target = findByKind(world, Well.class);
        }

        int nextX = getPosition().x;
        int nextY = getPosition().y;

        if (target.isPresent()) {
            Actor actor = target.get();

            if (getPosition().x < actor.getPosition().x) nextX += 1;
            else if (getPosition().x > actor.getPosition().x) nextX -= 1;
            else if (getPosition().y < actor.getPosition().y) nextY += 1;
            else if (getPosition().y > actor.getPosition().y) nextY -= 1;
        }

        Point destination = new Point(nextX, nextY);
        if (world.inBounds(destination) && !world.isOccupied(destination)) {
            world.moveEntity(eventScheduler, this, destination);
        }
    }
}
