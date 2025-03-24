import java.util.List;

import processing.core.PImage;

/** Represents a background for the 2D world. */
public final class Background {
    /** A string identifier for the background tile */
    private final String id;

    /** The list of image graphics used by the background tile. */
    private final List<PImage> images;

    /** The index of the image used to draw the background tile. */
    private final int imageIndex;

    public Background(String id, List<PImage> images, int imageIndex) {
        this.id = id;
        this.images = images;
        this.imageIndex = imageIndex;
    }

    public PImage getImage() {
        return images.get(imageIndex);
    }

    public String getId() {
        return id;
    }
}
