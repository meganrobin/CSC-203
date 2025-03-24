import processing.core.PImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mushroom extends Behaviorable {
    public static final String MUSHROOM_KEY = "mushroom";

    public static final int MUSHROOM_PARSE_BEHAVIOR_PERIOD_INDEX = 0;
    public static final int MUSHROOM_PARSE_PROPERTY_COUNT = 1;

    Mushroom(String id, Point position, List<PImage> images, double behaviorPeriod) {
        super(id, position, images, behaviorPeriod);
    }

    /** Executes Mushroom specific Logic. */
    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        List<Point> adjacentPositions = new ArrayList<>(List.of(
                new Point(getPosition().x - 1, getPosition().y),
                new Point(getPosition().x + 1, getPosition().y),
                new Point(getPosition().x, getPosition().y - 1),
                new Point(getPosition().x, getPosition().y + 1)
        ));
        Collections.shuffle(adjacentPositions);

        List<Point> mushroomBackgroundPositions = new ArrayList<>();
        List<Point> mushroomEntityPositions = new ArrayList<>();
        for (Point adjacentPosition : adjacentPositions) {
            if (world.inBounds(adjacentPosition) && !world.isOccupied(adjacentPosition) && world.hasBackground(adjacentPosition)) {
                Background bg = world.getBackgroundCell(adjacentPosition);
                if (bg.getId().equals("grass")) {
                    mushroomBackgroundPositions.add(adjacentPosition);
                } else if (bg.getId().equals("grass_mushrooms")) {
                    mushroomEntityPositions.add(adjacentPosition);
                }
            }
        }

        if (!mushroomBackgroundPositions.isEmpty()) {
            Point backgroundPosition = mushroomBackgroundPositions.get(0);

            Background background = new Background("grass_mushrooms", imageLibrary.get("grass_mushrooms"), 0);
            world.setBackgroundCell(backgroundPosition, background);
        } else if (!mushroomEntityPositions.isEmpty()) {
            Point position = mushroomEntityPositions.get(0);

            Mushroom mushroom = new Mushroom(MUSHROOM_KEY, position, imageLibrary.get(MUSHROOM_KEY), getBehaviorPeriod() * 4.0);

            world.addEntity(mushroom);
            mushroom.scheduleBehavior(scheduler, world, imageLibrary);
        }

        scheduleBehavior(scheduler, world, imageLibrary);
    }
}
