# Class Descriptions

## `Actor`

- The class you will refactor.
- Represents one of the four kinds of actors:
  1. Gardener
  2. Nymph
  3. Rose
  4. Well
- Schedules `Update` objects using `EventScheduler`.
- Interacts with the `World`.
- Uses images from the `ImageLibrary`.

## `Background`

- Represents a background tile image.
- The `World` contains a grid of these objects.

## `Event`

- An event to be performed at a scheduled time.
- Each event has an associated `Update` and `Entity`.

## `EventScheduler`

- Manages the scheduling of and executes all `Events`.

## `ImageLibrary`

- Used to store image data as a `Map` of `String`/`List` pairs.
- Reads the `imagelist` file to initialize.
- Note: The `imagelist` file references files in the included `images` folder.

## `LittleWorld`

- The application itself.
- Run `src/LittleWorld` to begin the simulation.

## `NumberUtil`

- Contains method(s) for manipulating numbers.

## `Point`

- Represents a 2d-coordinate.

## `Update`

- Used to call an `Actor`'s update.
- Whenever created, it is associated with a scheduled `Event`.

## `Viewport`

- Represents the current rectangular view of the `World`.

## `World`

- Manages the model of the world.
- Contains a set of the current `Actor`s in the world.
- Contains the background information.
- The world is created from methods in `WorldParser`

## `WorldParser`

- Contains methods for reading the `World` save file.
- Is dependent on `Actor` functionality and data due to `WorldParser.parseActor`.
- Note: the `World` save file is contained in the `world` text file.

## `WorldView`

- Draws the current state of the `World` through a given `ViewPort`.