import processing.core.PImage;

import java.util.List;

public abstract class Health extends Animatable {
    /** Entity's current health level. */
    private int health;
    Health(String id, Point position, List<PImage> images, double animationPeriod, double behaviorPeriod, int health) {
        super(id, position, images, animationPeriod, behaviorPeriod);
        this.health = health;
    }

    public void setHealth(int health) { this.health = health; }

    public int getHealth() { return health; }
}
