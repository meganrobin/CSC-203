import processing.core.PImage;

import java.util.List;

public class Water extends Entity {
    public static final String WATER_KEY = "water";

    public static final int WATER_PARSE_PROPERTY_COUNT = 0;

    Water(String id, Point position, List<PImage> images) {
        super(id, position, images);
    }
}
