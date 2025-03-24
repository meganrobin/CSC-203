# Project 3

## Course Information

- **Course Name:** CSC 203
- **Instructor:** Professor Vanessa Rivera
- **Term:** 2023-24 Spring Quarter

## Overview

In this assignment, you will improve the pathing behavior of entities within your virtual world program.
As you've likely witnessed, dudes' and fairies' movements are very simplistic and often cause the entity to be stuck on an obstacle or other entity.

Pathing algorithms are a field of study in and of themselves.
Here, you will implement the widely used A* pathfinding algorithm after unifying the movement functionality of your entities through the use of an interface.
This `PathingStrategy` interface leverages functional programming features such as streams and lambda functions for you to practice working with higher-order coding concepts.

## Instructions

### Checkpoint

#### Task 1: Acquire the Project and Copy Your Project 2 Code

**🎯 Task Goal:** Obtain the project code.

The Project 3 repository contains identical classes to the initial Project 2 repository with several exceptions:

- The `PathingStrategy` interface has been included.
- The `SingleStepPathingStrategy` and (the unimplemented) `AStarPathingStrategy` classes are included.
- A new test file `AStarTests` has been included.

To begin, you must copy over your `Entity` and `Action` hierarchies from Project 2.
You can do so by opening both projects, selecting your classes from Project 2's `src` directory, copying, and then pasting them into your Project 3 `src` directory.

> [!Warning]
>
> Do **not** delete or overwrite the included `PathingStrategy.java`, `SingleStepPathingStrategy.java`, or `AStarPathingStrategy.java` files.
> If you accidentally overwrite one of these files, you can right-click the file, e.g., `SingleStepPathingStrategy.java` and select `Git > Rollback`.

You should also copy over any changes you made to your non-`Entity` and non-`Action` classes, such as `World.findNearest()` and `VirtualWorld.scheduleActions()`, so that the project runs correctly.

#### Task 2: Acquaint Yourself with the Project

**🎯 Task Goal:** Complete the Checkpoint 1 Quiz.

1. **Examine the Code:** Look at the rest of the project instructions and take time to understand the new classes and interface within the `src` directory.
2. **Understand The Algorithm:** Look at and understand the module 6 learning materials regarding A*.
   You will be asked questions about the algorithm in the quiz.
3. **Submission:** For completion, complete the quiz on Canvas.
   - Note: The quiz has a 30-minute timer, but it may be repeated every 8 hours until its due date. Your highest score is recorded on repeat attempts.

### Final Submission

#### Task 3: The Strategy Design Pattern

**🎯 Task Goal:** Modify the program so that your moving entities utilize a `PathingStrategy` implementing class.

##### Design Patterns

Design patterns represent solutions to common problems in software design.
They are like templates that can be applied to real-world programming scenarios.
Patterns help you build reliable and maintainable code by following proven principles and methodologies.
One such pattern we will explore in this project is the "Strategy" pattern.

##### The Strategy Pattern

The Strategy pattern is a behavioral design pattern that defines a family of algorithms, encapsulates each one of them, and makes them interchangeable.
The pattern lets the algorithm vary independently of *clients* that use it.
In other words, it enables an object to change its behavior at runtime by switching out one strategy for another.
In this project, we will focus on implementing a single strategy and using that throughout the codebase.

##### The PathingStrategy Interface

In the context of our pathfinding project, `PathingStrategy` is an interface representing the strategy for navigating through the virtual world.
Different strategies (implementations) may specify alternate ways for entities in your virtual world program to calculate paths.

This interface declares the `computePath` method that any of these pathfinding strategies must implement.
It takes a starting point and a goal point and returns a list of points that represent a path from start to end.
The method uses functional interfaces (`Predicate`, `BiPredicate`, and `Function`) to encapsulate the logic needed for the pathfinding operation.

```java
// PathingStrategy's computePath method
List<Point> computePath(
        Point start,
        Point end,
        Predicate<Point> canPassThrough,
        BiPredicate<Point, Point> withinReach,
        Function<Point, Stream<Point>> potentialNeighbors
);
```

Included in Project 3 are two pathing strategies (classes) that implement `PathingStrategy`:

1. `SingleStepPathingStrategy`: This is a simple implementation that calculates the path as a series of next steps towards the goal. It provides behavior identical to the existing virtual world movement. If the starting point is within reach of the end, it returns an empty list.
2. `AStarPathingStrategy` (you’ll implement this): This will be a more complex strategy using the A* search algorithm to find the best path to the goal.

##### Refactoring to Use PathingStrategy

You must refactor your project to use the `PathingStrategy` for entity movement.

Begin by examining the "nextPosition" methods in your entities.
Understand how these methods currently determine the next move.
Look for patterns and repeated logic that could be encapsulated by the pathfinding interface.
Recognizing this will help you abstract the pathfinding logic away from your entities and into to `PathingStrategy` implementations.

Once you've isolated the movement logic, you will refactor code that determines a singular next `Point` movement position to use a list of points produced by `PathingStrategy.computePath` instead.
To do so, you will need to instantiate a `PathingStrategy` object within your code to call `computePath`.
You may do so as an instance variable or locally, immediately before constructing the path.
This object will be responsible for computing the path.

You should start with the `SingleStepPathingStrategy` to test your refactoring before moving on to the `AStarPathingStrategy`.

```java
// A pathing strategy instantiation
PathingStrategy pathingStrategy = new SingleStepPathingStrategy();
```

To call the `computePath()` method, you need to pass it lambda functions as arguments.
These functions will determine which points can be passed through, which meet a "goal" criteria, and which neighboring positions for any specific point are considered, described as follows:

- `canPassThrough` should represent points that are not occupied and within the bounds of the world. You will use `World` methods to create this predicate.
- `withinReach` defines when the goal has been reached. In our world, this is as simple as checking if two points are adjacent.
- `potentialNeighbors` should use the provided `CARDINAL_NEIGHBORS` function or a custom one if needed.

Note that different entities may have different lambda function arguments.
For example, "dudes" can "trample" stumps whereas fairies cannot.

```java
// An basic example of a "canPassThrough" predicate.
// Note that each entity may have a different predicate.
Predicate<Point> canPassThrough = point -> !world.isOccupied(point) && world.withinBounds(point);
```

The list returned by `computePath` is a `List` of points that represent the calculated path.
The first point in this list, if it exists, is the **next** step your entity should take towards its destination.
However, make sure to understand the requirements of this list.
For example, it should **not** include the starting point or the destination point.

> [!IMPORTANT]
>
> This can potentially be a problem when working on your A* implementation, as **you** must enforce these requirements on the resultant list.

After calling `computePath`, your "nextPosition" methods should analyze the list and decide which point to return as the next move.
If the list is empty, the entity is either at the destination or there is no valid path.
Otherwise, extract the next step from the list and return it as the new position.

```java
List<Point> path = pathingStrategy.computePath(...);

if (path.isEmpty()) {
    // Logic if there is no path or at the destination
} else {
    // Logic if there is a path
}
```

You are **strongly encouraged** to start by refactoring your code to use `SingleStepPathingStrategy`.
Upon successful refactoring, **all** of your regular `WorldTests` should pass.
After confirming that `SingleStepPathingStrategy` works correctly, you can begin working on the `AStarPathingStrategy`.
Further details A* are provided in the next task.

#### Task 4: Implementing the AStarPathingStrategy

**🎯 Task Goal:** Implement the A* algorithm using the `PathingStrategy` interface and integrate it into your moving entities' code.

The `AStarPathingStrategy` class will be your implementation of the A* pathfinding algorithm.

Start by deciding which data will be required and how to store it.
For example, the open set, a "came from" map, g-scores, f-scores, etc.
It is perfectly fine to opt for using easily understandable data structures, `List` for example, directly within the `computePath` method.

```java
// Some possible example structures
List<Point> openSet;
Map<Point, Point> cameFrom;
Map<Point, Integer> gscore;
```

**Note:** Do **not** modify the existing `Point` class to contain path finding information, as not all classes that utilize it will perform pathfinding.
If you'd like to store these properties in a point-like object (e.g., a `Node` class), you ***must*** do so by extending the existing `Point` class.

For sorting or prioritizing, as is needed for the open set, you can perform two approaches:

1. You can simply iterate over a list and find to find a minimum value (recall, lower f-scores are better).
2. You can sort a data structure using a `comparator`.

Additionally, a way to obtain a "default" value from a `Map` (e.g., in the case that a position hasn't been considered yet) is:

```java
gscore.getOrDefault(point, Integer.MAX_VALUE); // Returns a very large number if the point isn't in the map
```

After initializing data structures, you must populate them with initial values where appropriate, such as setting the g-score of the starting point to 0 and adding it to the open set.

Here are some guidelines to refer to as you implement the standard steps of the A* algorithm:

- **Heuristic:** Recall that common heuristics, which estimate the cost from a point to the goal, for a grid are the (preferably) Manhattan distance and Euclidean distance.
- **Neighbors:** Utilize the `potentialNeighbors` lambda to iterate over the neighbors of the "current" node. Remember to filter the neighbors using the `canPassThrough` predicate.
- **Scores:** For each neighbor, calculate the tentative g-score, check if this path to the neighbor is better than any previously known path, and update the relevant data structures accordingly.
- **Termination:** Implement the correct logic for when the goal has been reached or when there is no possible path (i.e., the open set is empty).
- **Path Reconstruction:** Once the goal is reached, you should return the reconstructed path. You will likely do so by iterating over your "came from" data. Be sure to **not** include the starting point or the node, and ensure the list is properly ordered.
- **Impossible Paths:** If a path to the goal does not exist, `computePath` should return an empty list and the entity should not move.

After completion, you can change references in your code from using `SingleStepPathingStrategy` to `AStarPathingStrategy`.
For example, if you instantiated a `SingleStepPathingStrategy` as below:

```java
// A pathing strategy instantiation
PathingStrategy pathingStrategy = new SingleStepPathingStrategy();
```

you would refactor your code like so:

```java
// A pathing strategy instantiation
PathingStrategy pathingStrategy = new AStarPathingStrategy();
```

You should run all the included `WorldTests` and `AStarTests` to see if they pass and the virtual world itself with the `-fastest` flag to ensure that it continues to run indefinitely.

## Submission

### Checkpoint 1

For completion of this checkpoint, please complete the Canvas quiz described above.

### Final Submission

For completion of this assignment, please complete the following:

1. Commit and push your updated code to your version of this assignment's GitHub repository.
2. In a submission to this assignment's final submission Canvas page, include a screenshot of your repository on GitHub.com, including your repository name and number of commits.
   - **Note:** No checkmark will be present.

> [!Warning]
>
> It is your responsibility to ensure proper submission of all assignment components according to the assignment instructions before the due date.
>
> Improperly submitted lab assignments will receive a grade of zero.
>
> You are encouraged to verify submission with your instructor if you are ever unsure.

## Academic Integrity

> [!Warning]
>
> Submitting this assignment confirms that you did not use solutions or code from external, AI-generated, or peer sources.
>
> You also agree to have your code checked by standard plagiarism detection software.
>
> Violation will result in a grade of zero, a report to the University, and further potential action.

## Due Date

Please refer to Canvas for due-date information, if applicable.

## Grading

Please refer to Canvas for additional grading information, if applicable.
