import processing.core.PImage;

import java.util.List;

public class Stump extends Entity {
    public static final String STUMP_KEY = "stump";

    public static final int STUMP_PARSE_PROPERTY_COUNT = 0;

    Stump(String id, Point position, List<PImage> images) {
        super(id, position, images);
    }
}
