import processing.core.*;

public final class LittleWorld extends PApplet {
    private static String[] ARGS;

    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;
    public static final int VIEW_WIDTH = 288;
    public static final int VIEW_HEIGHT = 288;
    public static final int VIEW_SCALE = 2;
    public static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
    public static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
    public static final String IMAGE_LIST_FILE_NAME = "imagelist";
    public static final int DEFAULT_IMAGE_COLOR = 0x808080;
    public static final String FAST_FLAG = "-fast";
    public static final String FASTER_FLAG = "-faster";
    public static final String FASTEST_FLAG = "-fastest";
    public static final String WORLD_STRING_FLAG = "-string";
    public static final double FAST_SCALE = 0.5;
    public static final double FASTER_SCALE = 0.25;
    public static final double FASTEST_SCALE = 0.10;
    public String worldString = "world";
    public boolean worldStringIsFilePath = true;
    public long startTimeMillis = 0;
    public double timeScale = 1.0;

    public ImageLibrary imageLibrary;
    public World world;
    public WorldView view;
    public EventScheduler scheduler;

    /** Settings for pixelated graphics */
    public void settings() {
        noSmooth();
        size(VIEW_WIDTH * VIEW_SCALE, VIEW_HEIGHT * VIEW_SCALE);
    }

    /** Entrypoint that runs the Processing applet. */
    public static void main(String[] args) {
        LittleWorld.ARGS = args;
        PApplet.main(LittleWorld.class);
    }

    /** Processing entry point for "sketch" setup. */
    public void setup() {
        parseCommandLine(ARGS);

        loadImageLibrary(IMAGE_LIST_FILE_NAME);
        loadWorld(worldString, imageLibrary);

        view = new WorldView(VIEW_ROWS, VIEW_COLS, this, VIEW_SCALE, world, TILE_WIDTH, TILE_HEIGHT);
        scheduler = new EventScheduler();
        startTimeMillis = System.currentTimeMillis();

        scheduleActions(world, scheduler, imageLibrary);
    }

    /** Handles command line arguments. */
    public void parseCommandLine(String[] args) {
        for (String arg : args) {
            switch (arg) {
                case FAST_FLAG -> timeScale = Math.min(FAST_SCALE, timeScale);
                case FASTER_FLAG -> timeScale = Math.min(FASTER_SCALE, timeScale);
                case FASTEST_FLAG -> timeScale = Math.min(FASTEST_SCALE, timeScale);
                case WORLD_STRING_FLAG -> worldStringIsFilePath = false;
                default -> worldString = arg;
            }
        }
    }

    /** Loads the image library. */
    public void loadImageLibrary(String filename) {
        imageLibrary = new ImageLibrary(ImageLibrary.createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
        imageLibrary.loadFromFile(filename, this);
    }

    /** Loads the world. */
    public void loadWorld(String loadString, ImageLibrary imageLibrary) {
        if (worldStringIsFilePath) {
            world = WorldParser.createFromFile(loadString, imageLibrary);
        } else {
            world = WorldParser.createFromString(loadString, imageLibrary);
        }
    }

    /** Called to start all actor's actions and behaviors when the program starts. */
    public void scheduleActions(World world, EventScheduler eventScheduler, ImageLibrary imageLibrary) {
        for (Actor actor : world.getEntities()) {
            actor.scheduleUpdate(world, imageLibrary, eventScheduler);
        }
    }

    /** Called multiple times automatically per second. */
    public void draw() {
        double appTime = (System.currentTimeMillis() - startTimeMillis) * 0.001;
        double frameTime = appTime / timeScale - scheduler.getCurrentTime();
        update(frameTime);
        view.drawViewport();
    }

    /** Performs update logic. */
    public void update(double frameTime){
        scheduler.updateOnTime(frameTime);
    }

}
