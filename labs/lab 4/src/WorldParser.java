import java.io.*;
import java.util.*;
import java.util.stream.Stream;

/** Contains functionality for loading a world from a text file. */
public class WorldParser {

    /** Used to store a dimensions for instantiating a world. */
    private static class WorldDimensions {
        int numRows;
        int numCols;
    }

    /** Creates a 'World' from a text file. */
    public static World createFromFile(String filePath, ImageLibrary imageLibrary) {
        World world = null;

        try (FileReader reader = new FileReader(filePath)) {
            world = load(reader, imageLibrary);
        } catch (IOException e) {
            System.err.printf(
                    "Unable to load world from file '%s'%n",
                    filePath
            );
        }

        return world;
    }

    /** Creates a 'World' from a string. */
    public static World createFromString(String worldString, ImageLibrary imageLibrary) {
        return load(new StringReader(worldString), imageLibrary);
    }

    /**
     * Loads a world by parsing the given source, erasing any current state.
     *
     * @param reader The data source.
     * @param imageLibrary Image data to use for world entities.
     */
    private static World load(Reader reader, ImageLibrary imageLibrary){
        // Initialize Temporary Data Structures
        WorldDimensions worldDimensions = new WorldDimensions(); // x: numCols, y: numRows
        List<Background[]> backgroundRows = new LinkedList<>();
        Set<Actor> potentialEntities = new HashSet<>();

        // Parse the file
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                    parseLoadLine(line, imageLibrary, worldDimensions, backgroundRows, potentialEntities);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        // Instantiate the world
        World world;
        if (worldDimensions.numRows > 0 && worldDimensions.numCols > 0) {
            world = new World(worldDimensions.numRows, worldDimensions.numCols);
        } else {
            throw new IllegalArgumentException("World dimension is non-positive");
        }

        // Construct the background grid
        for (int y = 0; y < backgroundRows.size(); y++) {
            Background[] backgroundRow = backgroundRows.get(y);
            for (int x = 0; x < backgroundRow.length; x++) {
                world.setBackgroundCell(new Point(x, y), backgroundRow[x]);
            }
        }

        // Construct the occupancy grid and entity set
        for (Actor actor : potentialEntities) {
            Point position = actor.getPosition();
            world.addEntity(actor);
        }

        // Return the successfully constructed world
        return world;
    }

    /** Called to parse each line in the save file. */
    private static void parseLoadLine(String line, ImageLibrary imageLibrary, WorldDimensions worldDimensions, List<Background[]> backgroundRows, Set<Actor> entities) {
        // Clean Up the line
        line = line.strip();

        // Handle Comments
        if (line.startsWith("#")) return;

        // Format the line
        String[] args = line.strip().split(":\\s+");
        if (args.length > 0) {
            switch(args[0]) {
                case "Rows":
                    worldDimensions.numRows = parseRows(args[1]);
                    break;
                case "Cols":
                    worldDimensions.numCols = parseCols(args[1]);
                    break;
                case "Background":
                    backgroundRows.add(parseBackground(args[1], imageLibrary, worldDimensions.numCols));
                    break;
                case "Actor":
                    entities.add(parseActor(args[1], imageLibrary));
                    break;
            }
        }
    }

    /** Parses lines containing world row size information. */
    private static int parseRows(String parameters) {
        return Integer.parseInt(parameters.strip());
    }

    /** Parses lines containing world column size information. */
    private static int parseCols(String parameters) {
        return Integer.parseInt(parameters.strip());
    }

    /** Parses lines containing background information. */
    private static Background[] parseBackground(String parameters, ImageLibrary imageLibrary, int numCols) {
        // Clean and format parameters
        String[] args = parameters.strip().split("\\s");

        // Parse the parameters
        return Stream.of(args)
                .map(key -> key.isBlank() ? null : new Background(key, imageLibrary.get(key), 0))
                .limit(numCols)
                .toArray(Background[]::new);
    }

    /** Parses lines containing actor information. */
    public static Actor parseActor(String parameters, ImageLibrary imageLibrary) {
        String[] args = parameters.strip().split("\\s");

        if (args.length >= Actor.ACTOR_PROPERTY_COUNT) {
            // Get general properties
            String[] properties = Arrays.copyOfRange(
                    args,
                    0,
                    Actor.ACTOR_PROPERTY_COUNT
            );

            // Parse properties
            String key = properties[Actor.ACTOR_PROPERTY_KEY_INDEX];
            Point position = new Point(
                    Integer.parseInt(properties[Actor.ACTOR_PROPERTY_POSITION_X_INDEX]),
                    Integer.parseInt(properties[Actor.ACTOR_PROPERTY_POSITION_Y_INDEX])
            );
            double updatePeriod = Double.parseDouble(properties[Actor.ACTOR_PROPERTY_UPDATE_PERIOD_INDEX]);

            // Parse specific properties
            // TODO: Modify to use subtype constructors
            return switch (key) {
                case Gardener.GARDENER_KEY -> new Gardener(position, imageLibrary.get(Gardener.GARDENER_KEY), updatePeriod);
                case Nymph.NYMPH_KEY -> new Nymph(position, imageLibrary.get(Nymph.NYMPH_KEY), updatePeriod);
                case Rose.ROSE_KEY -> new Rose(position, imageLibrary.get(Rose.ROSE_KEY), updatePeriod);
                case Well.WELL_KEY -> new Well(position, imageLibrary.get(Well.WELL_KEY), updatePeriod);
                default -> throw new IllegalArgumentException(String.format("Unexpected entity key: %s", key));
            };
        } else {
            throw new IllegalArgumentException("Entity command parameters must be formatted as: [key] [id] [x] [y] ...");
        }
    }
}
