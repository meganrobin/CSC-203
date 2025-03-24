import processing.core.PImage;

import java.util.List;

public class Restaurant extends Entity {
    public static final String RESTAURANT_KEY = "restaurant";

    public static final int RESTAURANT_PARSE_PROPERTY_COUNT = 0;
    private int chanterelleAmount = 0;

    Restaurant(String id, Point position, List<PImage> images) {
        super(id, position, images);
    }

    public void setChanterelleAmount(int chanterelleAmount) {
        this.chanterelleAmount = chanterelleAmount;
    }

    public int getChanterelleAmount() {
        return chanterelleAmount;
    }
}
