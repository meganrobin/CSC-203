import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public final class VirtualWorld extends PApplet {
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;
    public static final int VIEW_WIDTH = 512;
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
    public static final double FASTEST_SCALE = 0.0625;
    private static String[] ARGS;
    public String worldString = "world";
    public boolean worldStringIsFilePath = true;
    public long startTimeMillis = 0;
    public double timeScale = 1.0;

    public ImageLibrary imageLibrary;
    public World world;
    public WorldView view;
    public EventScheduler scheduler;

    /** Entrypoint that runs the Processing applet. */
    public static void main(String[] args) {
        VirtualWorld.ARGS = args;
        PApplet.main(VirtualWorld.class);
    }

    /** Performs an entire VirtualWorld simulation for testing. */
    public static List<String> headlessMain(String[] args, double lifetime){
        VirtualWorld.ARGS = args;

        VirtualWorld virtualWorld = new VirtualWorld();
        virtualWorld.setup();
        virtualWorld.update(lifetime);

        return virtualWorld.world.log();
    }

    /** Settings for pixelated graphics */
    public void settings() {
        noSmooth();
        size(VIEW_WIDTH * VIEW_SCALE, VIEW_HEIGHT * VIEW_SCALE);
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

    /** Called to start all entity's actions and behaviors when the program starts. */
    public void scheduleActions(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Animatable) {
                ((Animatable) entity).schedule(scheduler, world, imageLibrary);
            }
            else if (entity instanceof Behaviorable) {
                ((Behaviorable) entity).scheduleBehavior(scheduler, world, imageLibrary);
            }
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

    /** Mouse press input handling. */
    public void mousePressed() {
        Point pressed = mouseToPoint();
        System.out.println("Click Location (" + pressed.x + ", " + pressed.y + ")");

        Optional<Entity> entityOptional = world.getOccupant(pressed);
        if (entityOptional.isPresent()) {
            Entity entity = entityOptional.get();

            if (entity.log() != null) {
                System.out.println(entity.log());
            }
        }
        // Add new Restaurant, Chef, and 5 Chanterelle spores if there's 3 or more empty spaces available
        List<Point> neighbors = PathingStrategy.CARD_AND_INTERCARD_NEIGHBORS.apply(pressed).filter(point -> world.inBounds(point) && !world.isOccupied(point)).toList();
        ArrayList<Point> neighborsArrayList = new ArrayList<>(neighbors);
        Collections.shuffle(neighborsArrayList);

        if (!world.isOccupied(pressed) && neighborsArrayList.size() >= 4) {
            // add Restaurant
            Restaurant restaurant = new Restaurant(
                    Restaurant.RESTAURANT_KEY,
                    pressed,
                    imageLibrary.get(Restaurant.RESTAURANT_KEY)
            );
            world.addEntity(restaurant);

            // add first Chanterelle
            Point chanterellePoint = neighborsArrayList.removeFirst();
            Chanterelle chanterelle = new Chanterelle(
                    Chanterelle.CHANTERELLE_KEY,
                    chanterellePoint,
                    imageLibrary.get(Chanterelle.CHANTERELLE_KEY),
                    NumberUtil.getRandomDouble(Chanterelle.CHANTERELLE_RANDOM_ANIMATION_PERIOD_MIN, Chanterelle.CHANTERELLE_RANDOM_ANIMATION_PERIOD_MAX),
                    NumberUtil.getRandomDouble(Chanterelle.CHANTERELLE_RANDOM_BEHAVIOR_PERIOD_MIN, Chanterelle.CHANTERELLE_RANDOM_BEHAVIOR_PERIOD_MAX),
                    NumberUtil.getRandomInt(Chanterelle.CHANTERELLE_RANDOM_HEALTH_MIN, Chanterelle.CHANTERELLE_RANDOM_HEALTH_MAX)
            );
            world.addEntity(chanterelle);
            chanterelle.schedule(scheduler, world, imageLibrary); // start the new Chanterelle's animations and behavior

            // add second Chanterelle
            Point chanterellePoint2 = neighborsArrayList.removeFirst();
            Chanterelle chanterelle2 = new Chanterelle(
                    Chanterelle.CHANTERELLE_KEY,
                    chanterellePoint2,
                    imageLibrary.get(Chanterelle.CHANTERELLE_KEY),
                    NumberUtil.getRandomDouble(Chanterelle.CHANTERELLE_RANDOM_ANIMATION_PERIOD_MIN, Chanterelle.CHANTERELLE_RANDOM_ANIMATION_PERIOD_MAX),
                    NumberUtil.getRandomDouble(Chanterelle.CHANTERELLE_RANDOM_BEHAVIOR_PERIOD_MIN, Chanterelle.CHANTERELLE_RANDOM_BEHAVIOR_PERIOD_MAX),
                    NumberUtil.getRandomInt(Chanterelle.CHANTERELLE_RANDOM_HEALTH_MIN, Chanterelle.CHANTERELLE_RANDOM_HEALTH_MAX)
            );
            world.addEntity(chanterelle2);
            chanterelle2.schedule(scheduler, world, imageLibrary); // start the new Chanterelle's animations and behavior

            // add Chef
            Point chefPoint = neighborsArrayList.removeFirst();
            Chef chef = new Chef(
                    Chef.CHEF_KEY,
                    chefPoint,
                    imageLibrary.get(Chef.CHEF_KEY),
                    NumberUtil.getRandomDouble(Chef.CHEF_RANDOM_ANIMATION_PERIOD_MIN, Chef.CHEF_RANDOM_ANIMATION_PERIOD_MAX),
                    NumberUtil.getRandomDouble(Chef.CHEF_RANDOM_BEHAVIOR_PERIOD_MIN, Chef.CHEF_RANDOM_BEHAVIOR_PERIOD_MAX),
                    0,
                    NumberUtil.getRandomInt(Chef.CHEF_RANDOM_RESOURCE_LIMIT_MIN, Chef.CHEF_RANDOM_RESOURCE_LIMIT_MAX),
                    false
            );
            world.addEntity(chef);
            chef.schedule(scheduler, world, imageLibrary); // start the new Chanterelle's animations and behavior

            neighborsArrayList.removeFirst(); // remove the first point in the list to make sure there's not too many Chanterelle spores
            for (Point point : neighborsArrayList) {
                Background bg = world.getBackgroundCell(point);
                if (bg.getId().equals("grass") || bg.getId().equals("grass_chanterelle") || bg.getId().equals("grass_flowers") || bg.getId().equals("grass_mushrooms")) { // add a Chanterelle spore background for every unoccupied point adjacent to the new restaurant
                    Background background = new Background("grass_chanterelle", imageLibrary.get("grass_chanterelle"), 0);
                    world.setBackgroundCell(point, background);
                }
            }

        }
    }

    /** Converts mouse position to world position. */
    private Point mouseToPoint() {
        return view.getViewport().viewportToWorld(mouseX / TILE_WIDTH / VIEW_SCALE, mouseY / TILE_HEIGHT / VIEW_SCALE);
    }

    /** Keyboard input handling. */
    public void keyPressed() {
        if (key == CODED) {
            int dx = 0;
            int dy = 0;

            switch (keyCode) {
                case UP -> dy -= 1;
                case DOWN -> dy += 1;
                case LEFT -> dx -= 1;
                case RIGHT -> dx += 1;
            }

            view.shiftView(dx, dy);
        }
    }

}
