# Class Descriptions

## `Action`
- A class you will refactor into its own class hierarchy.
- Represents one of the two kinds actions that affect entities and their relationship to the world:
  1. Animation
  2. Behavior

## `Entity`
- A class you will refactor into its own class hierarchy.
- Represents one of the eight kinds of entities:
  1. Dude
  2. Fairy
  3. House
  4. Mushroom
  5. Sapling
  6. Stump
  7. Tree
  8. Water
- Schedules `Action` objects using `EventScheduler`.
- Interacts with the `World`.
- Uses images from the `ImageLibrary`.

## `Background`
- Represents a background tile image.
- The `World` contains a grid of these objects.

## `Event`
- An event to be performed at a scheduled time.
- Each event has an associated `Action` and `Entity`.

## `EventScheduler`
- Manages the scheduling of and executes all `Events`.

## `ImageLibrary`
- Used to store image data as a `Map` of `String`/`List` pairs.
- Reads the `imagelist` file to initialize.
- Note: The `imagelist` file references files in the included `images` folder.

## `NumberUtil`
- Contains method(s) for manipulating numbers and producing random values.

## `Point`
- Represents a 2d-coordinate.

## `Update`
- Used to call an `Entity`'s update.
- Whenever created, it is associated with a scheduled `Event`.

## `Viewport`
- Represents the current rectangular view of the `World`.

## `VirtualWorld`
- The application itself.
- Run `VirtualWorld` to begin the simulation.

## `World`
- Manages the model of the world.
- Contains a set of the current `Entity`s in the world.
- Contains the background information.
- The world is created from methods in `WorldParser`

## `WorldParser`
- Contains methods for reading the `World` save file.
- Is dependent on `Entity` functionality and data due to `WorldParser.parseEntity`.
- Note: the `World` save file is contained in the `world` text file.

## `WorldView`
- Draws the current state of the `World` through a given `ViewPort`.