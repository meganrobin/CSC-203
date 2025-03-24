import java.io.*;
import java.util.*;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

/** Stores sequences of image data to be used by the program. */
public class ImageLibrary {
    /** Key-retrievable image sequences */
    private final HashMap<String, List<PImage>> imagesMap;

    /** The list of images associated with invalid keys */
    private final List<PImage> defaultImages;

    public ImageLibrary(PImage defaultImage) {
        imagesMap = new HashMap<>();
        defaultImages = List.of(defaultImage);
    }

    /** Creates a solid color rectangle image. */
    public static PImage createImageColored(int width, int height, int color) {
        PImage img = new PImage(width, height, PConstants.RGB);
        img.loadPixels();
        Arrays.fill(img.pixels, color);
        img.updatePixels();
        return img;
    }

    /** Returns a specific list of images from the library. */
    public List<PImage> get(String key) {
        return imagesMap.getOrDefault(key, defaultImages);
    }

    /** Returns a specific image from the library. */
    public PImage get(String key, int index) {
        List<PImage> images = imagesMap.getOrDefault(key, defaultImages);
        return images.get(index % images.size());
    }

    /** Initializes the library from a text file */
    public void loadFromFile(String filePath, PApplet screen) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.ready()) {
                parseLoadLine(reader.readLine(), screen);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    /** Parses a single line of text from a text file. */
    private void parseLoadLine(String line, PApplet screen) {
        // Cleanup and Format Line
        String[] args = line.strip().split("\\s");

        // Parse Line
        if (args.length == 2) {
            String key = args[0];
            PImage image = screen.loadImage(args[1]);

            // Store properly loaded image
            if (image != null && image.width != -1) {

                // Initialize the list if it doesn't exist
                if (!imagesMap.containsKey(key)) {
                    imagesMap.put(key, new ArrayList<>());
                }

                // Add the image to the list
                get(key).add(image);
            }
        }
    }
}
