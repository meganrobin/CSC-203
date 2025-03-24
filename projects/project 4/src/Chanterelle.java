import processing.core.PImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chanterelle extends Health implements Transformable {
    public static final String CHANTERELLE_KEY = "chanterelle";

    // variables for making a Chanterelle from the "world" file
    public static final int CHANTERELLE_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 0;
    public static final int CHANTERELLE_PARSE_BEHAVIOR_PERIOD_INDEX = 0;
    public static final int CHANTERELLE_PARSE_PROPERTY_COUNT = 1;

    // random creation variables
    public static final double CHANTERELLE_RANDOM_ANIMATION_PERIOD_MIN = 3.0;
    public static final double CHANTERELLE_RANDOM_ANIMATION_PERIOD_MAX = 6.0;
    public static final double CHANTERELLE_RANDOM_BEHAVIOR_PERIOD_MIN = 3.0;
    public static final double CHANTERELLE_RANDOM_BEHAVIOR_PERIOD_MAX = 8.0;
    public static final int CHANTERELLE_RANDOM_HEALTH_MIN = 3;
    public static final int CHANTERELLE_RANDOM_HEALTH_MAX = 5;

    Chanterelle(String id, Point position, List<PImage> images, double animationPeriod, double behaviorPeriod, int health) {
        super(id, position, images, animationPeriod, behaviorPeriod, health);
    }

    /** Executes Chanterelle specific Logic. */
    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        if (!transform(world, scheduler, imageLibrary)) {
            scheduleBehavior(scheduler, world, imageLibrary);
        }
        List<Point> neighbors = PathingStrategy.CARDINAL_NEIGHBORS.apply(getPosition()).toList();
        ArrayList<Point> neighborsArrayList = new ArrayList<>(neighbors);
        Collections.shuffle(neighborsArrayList);

        List<Point> chanterelleBackgroundPositions = new ArrayList<>(); // List of adjacent tiles w/ normal grass
        List<Point> chanterelleEntityPositions = new ArrayList<>(); // List of adjacent tiles w/ Chanterelle grass
        for (Point adjacentPosition : neighborsArrayList) {
            if (world.inBounds(adjacentPosition) && !world.isOccupied(adjacentPosition) && world.hasBackground(adjacentPosition)) {
                Background bg = world.getBackgroundCell(adjacentPosition);
                if (bg.getId().equals("grass") || bg.getId().equals("grass_flowers") || bg.getId().equals("grass_mushroom")) {
                    chanterelleBackgroundPositions.add(adjacentPosition);
                } else if (bg.getId().equals("grass_chanterelle")) {
                    chanterelleEntityPositions.add(adjacentPosition);
                }
            }
        }

        if (!chanterelleBackgroundPositions.isEmpty()) {
            Point backgroundPosition = chanterelleBackgroundPositions.get(0);

            Background background = new Background("grass_chanterelle", imageLibrary.get("grass_chanterelle"), 0);
            world.setBackgroundCell(backgroundPosition, background);
        } else if (!chanterelleEntityPositions.isEmpty()) {
            Point position = chanterelleEntityPositions.get(0);

            Chanterelle chanterelle = new Chanterelle(CHANTERELLE_KEY, position, imageLibrary.get(CHANTERELLE_KEY), NumberUtil.getRandomDouble(CHANTERELLE_RANDOM_ANIMATION_PERIOD_MIN, CHANTERELLE_RANDOM_ANIMATION_PERIOD_MAX), getBehaviorPeriod() * 2.0, NumberUtil.getRandomInt(CHANTERELLE_RANDOM_HEALTH_MIN, CHANTERELLE_RANDOM_HEALTH_MAX));

            world.addEntity(chanterelle);
            chanterelle.schedule(scheduler, world, imageLibrary); // start the new Chanterelle's animations and behavior
        }

        scheduleBehavior(scheduler, world, imageLibrary);
    }
    /** Checks the Chanterelle's health and transforms accordingly, returning true if successful. */
    public boolean transform(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (getHealth() <= 0) {
            world.removeEntity(scheduler, this);

            return true;
        }

        return false;
    }

}