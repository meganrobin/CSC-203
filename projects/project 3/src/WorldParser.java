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
        Set<Entity> potentialEntities = new HashSet<>();

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
        for (Entity entity : potentialEntities) {
            Point position = entity.getPosition();
            world.addEntity(entity);
        }

        // Return the successfully constructed world
        return world;
    }


    /** Called to parse each line in the save file. */
    private static void parseLoadLine(String line, ImageLibrary imageLibrary, WorldDimensions worldDimensions, List<Background[]> backgroundRows, Set<Entity> entities) {
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
                case "Entity":
                    entities.add(parseEntity(args[1], imageLibrary));
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

    /** Parses lines containing entity information. */
    private static Entity parseEntity(String parameters, ImageLibrary imageLibrary) {
        String[] args = parameters.strip().split("\\s");

        if (args.length >= Entity.ENTITY_PROPERTY_COLUMN_COUNT) {
            // Get general properties
            String[] properties = Arrays.copyOfRange(
                    args,
                    0,
                    Entity.ENTITY_PROPERTY_COLUMN_COUNT
            );

            // Parse general properties
            String key = properties[Entity.ENTITY_PROPERTY_KEY_INDEX];
            String id = properties[Entity.ENTITY_PROPERTY_ID_INDEX];
            Point position = new Point(
                    Integer.parseInt(properties[Entity.ENTITY_PROPERTY_POSITION_X_INDEX]),
                    Integer.parseInt(properties[Entity.ENTITY_PROPERTY_POSITION_Y_INDEX])
            );

            // Get specific properties
            String[] specificProperties = Arrays.copyOfRange(
                    args,
                    Entity.ENTITY_PROPERTY_COLUMN_COUNT,
                    args.length
            );

            // Parse specific properties
            return switch (key) {
                case Dude.DUDE_KEY -> parseDude(specificProperties, id, position, imageLibrary);
                case Fairy.FAIRY_KEY -> parseFairy(specificProperties, id, position, imageLibrary);
                case House.HOUSE_KEY -> parseHouse(specificProperties, id, position, imageLibrary);
                case Mushroom.MUSHROOM_KEY -> parseMushroom(specificProperties, id, position, imageLibrary);
                case Water.WATER_KEY -> parseWater(specificProperties, id, position, imageLibrary);
                case Sapling.SAPLING_KEY -> parseSapling(specificProperties, id, position, imageLibrary);
                case Stump.STUMP_KEY -> parseStump(specificProperties, position, id, imageLibrary);
                case Tree.TREE_KEY -> parseTree(specificProperties, id, position, imageLibrary);
                default -> throw new IllegalArgumentException(String.format("Unexpected entity key: %s", key));
            };
        } else {
            throw new IllegalArgumentException("Entity command parameters must be formatted as: [key] [id] [x] [y] ...");
        }
    }

    /** Parses a line of Dude data. */
    private static Dude parseDude(String[] properties, String id, Point position, ImageLibrary imageLibrary) {
        if (properties.length == Dude.DUDE_PARSE_PROPERTY_COUNT) {

            // Modify to use a constructor from your class hierarchy
            // Ensure the order of all passed arguments match the desired parameters
            return new Dude(
                    id,
                    position,
                    imageLibrary.get(Dude.DUDE_KEY),
                    Double.parseDouble(properties[Dude.DUDE_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX]),
                    Double.parseDouble(properties[Dude.DUDE_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX]),
                    0,
                    Integer.parseInt(properties[Dude.DUDE_PARSE_PROPERTY_RESOURCE_LIMIT_INDEX])
            );

        } else {
            throw new IllegalArgumentException(String.format(
                    "%s requires %d properties when parsing, got %d",
                    Dude.DUDE_KEY,
                    Dude.DUDE_PARSE_PROPERTY_COUNT,
                    properties.length
            ));
        }
    }

    /** Parses a line of Fairy data. */
    private static Fairy parseFairy(String[] properties, String id, Point position, ImageLibrary imageLibrary) {
        if (properties.length == Fairy.FAIRY_PARSE_PROPERTY_COUNT) {

            // Modify to use a constructor from your class hierarchy
            // Ensure the order of all passed arguments match the desired parameters
            return new Fairy(
                    id,
                    position,
                    imageLibrary.get(Fairy.FAIRY_KEY),
                    Double.parseDouble(properties[Fairy.FAIRY_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX]),
                    Double.parseDouble(properties[Fairy.FAIRY_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX])
            );

        } else {
            throw new IllegalArgumentException(String.format(
                    "%s requires %d properties when parsing, got %d",
                    Fairy.FAIRY_KEY,
                    Fairy.FAIRY_PARSE_PROPERTY_COUNT,
                    properties.length
            ));
        }
    }

    /** Parses a line of Mushroom data. */
    private static Mushroom parseMushroom(String[] properties, String id, Point position, ImageLibrary imageLibrary) {
        if (properties.length == Mushroom.MUSHROOM_PARSE_PROPERTY_COUNT) {

            // Modify to use a constructor from your class hierarchy
            // Ensure the order of all passed arguments match the desired parameters
            return new Mushroom(
                    id,
                    position,
                    imageLibrary.get(Mushroom.MUSHROOM_KEY),
                    Double.parseDouble(properties[Mushroom.MUSHROOM_PARSE_BEHAVIOR_PERIOD_INDEX])
            );

        } else {
            throw new IllegalArgumentException(String.format(
                    "%s requires %d properties when parsing, got %d",
                    Mushroom.MUSHROOM_KEY,
                    Mushroom.MUSHROOM_PARSE_PROPERTY_COUNT,
                    properties.length
            ));
        }
    }

    /** Parses a line of House data. */
    private static House parseHouse(String[] properties, String id, Point position, ImageLibrary imageLibrary) {
        if (properties.length == House.HOUSE_PARSE_PROPERTY_COUNT) {

            // Modify to use a constructor from your class hierarchy
            // Ensure the order of all passed arguments match the desired parameters
            return new House(
                    id,
                    position,
                    imageLibrary.get(House.HOUSE_KEY)
            );

        } else {
            throw new IllegalArgumentException(String.format(
                    "%s requires %d properties when parsing, got %d",
                    House.HOUSE_KEY,
                    House.HOUSE_PARSE_PROPERTY_COUNT,
                    properties.length
            ));
        }
    }

    private static Sapling parseSapling(String[] properties, String id, Point position, ImageLibrary imageLibrary) {
        if (properties.length == Sapling.SAPLING_PARSE_PROPERTY_COUNT) {

            // Modify this to use a constructor from your class hierarchy
            // Ensure the order of all passed arguments match the desired parameters
            return new Sapling(
                    id,
                    position,
                    imageLibrary.get(Sapling.SAPLING_KEY)
            );

        } else {
            throw new IllegalArgumentException(String.format(
                    "%s requires %d properties when parsing, got %d",
                    Sapling.SAPLING_KEY,
                    Sapling.SAPLING_PARSE_PROPERTY_COUNT,
                    properties.length
            ));
        }
    }

    private static Stump parseStump(String[] properties, Point pt, String id, ImageLibrary imageLibrary) {
        if (properties.length == Stump.STUMP_PARSE_PROPERTY_COUNT) {

            // Modify this to use a constructor from your class hierarchy
            // Ensure the order of all passed arguments match the desired parameters
            return new Stump(
                    id,
                    pt,
                    imageLibrary.get(Stump.STUMP_KEY)
            );

        } else {
            throw new IllegalArgumentException(String.format(
                    "%s requires %d properties when parsing, got %d",
                    Stump.STUMP_KEY,
                    Stump.STUMP_PARSE_PROPERTY_COUNT,
                    properties.length
            ));
        }
    }

    private static Tree parseTree(String[] properties, String id, Point position, ImageLibrary imageLibrary) {
        if (properties.length == Tree.TREE_PARSE_PROPERTY_COUNT) {

            // Modify to use a constructor from your class hierarchy
            // Ensure the order of all passed arguments match the desired parameters
            return new Tree(
                    id,
                    position,
                    imageLibrary.get(Tree.TREE_KEY),
                    Double.parseDouble(properties[Tree.TREE_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX]),
                    Double.parseDouble(properties[Tree.TREE_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX]),
                    Integer.parseInt(properties[Tree.TREE_PARSE_PROPERTY_HEALTH_INDEX])
            );

        } else {
            throw new IllegalArgumentException(String.format(
                    "%s requires %d properties when parsing, got %d",
                    Tree.TREE_KEY,
                    Tree.TREE_PARSE_PROPERTY_COUNT,
                    properties.length
            ));
        }
    }

    private static Water parseWater(String[] properties, String id, Point position, ImageLibrary imageLibrary) {
        if (properties.length == Water.WATER_PARSE_PROPERTY_COUNT) {

            // Modify to use a constructor from your class hierarchy
            // Ensure the order of all passed arguments match the desired parameters
            return new Water(
                    id,
                    position,
                    imageLibrary.get(Water.WATER_KEY)
            );

        } else {
            throw new IllegalArgumentException(String.format(
                    "%s requires %d properties when parsing, got %d",
                    Water.WATER_KEY,
                    Water.WATER_PARSE_PROPERTY_COUNT,
                    properties.length
            ));
        }
    }
}
