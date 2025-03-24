# Lab 6

## Course Information

- **Course:** CSC 203
- **Instructor:** Professor Vanessa Rivera
- **Term:** 2023-24 Spring Quarter

## Overview

This lab will have you gain experience with the `PathingStrategy` interface used in Project 3 by using it in a smaller program.

## Learning Objectives

In completing this assessment, you will be able to:

- Understand the structure of a non-trivial Java program. (🛠️)
- Utilize a class that implements an interface according to the strategy design principle. (🤝)
- Write a complex function call involving streams and lambda functions. (🔢)

## Instructions

As given, `PathingMain` utilizes the `GoRightPathingStrategy`.
This strategy simply traverses points exactly one space to the right, recursively.
Though, as given, a path to the goal will never be generated, this strategy attempts to find one.
You can see a success state if you move the goal into the path by changing the values of the `goalPosition` variable.

> [!Note]
>
> When running `PathingMain` you can interact with the program in the following ways:
> 
> - **Space Key:** Generate a path.
> - **"C" Key:** Delete a generated path.
> - **"P" Key:** Hide/Reveal the generated path.
> - **Left or Right Mouse Buttons:** Add/Remove an obstacle from a position

Your goal for this lab is to modify the `PathingMain.searchForGoal()` method to utilize either the included `SingleStepPathingStrategy` or your completed `AStarPathingStrategy`.
This will allow the program to generate a path from the penguin (the start) to the fish (the goal).

The strategy should be implemented such that obstacles are avoided and only positions within the screen are checked.
Take a moment to understand the methods in `PathingMain` and its `GridValues` instance variable.

You will need to perform the following modifications to calling `computePath()`:

- Change the pathing strategy from the `GoRightPathingStrategy`.
- Change the `canPassThrough` lambda function to consider the grid bounds and obstacles.
- Change the `potentialNeighbors` lambda function to include all neighboring points, not just the neighbor to the right.

## Submission

For completion of this assignment, please complete the following:

1. Commit and push your updated code to your version of this assignment's GitHub repository.
    - **Note:** The GitHub checkmark will not appear on this assignment.
2. In a submission to this assignment's Canvas page, include the following:
    1. A screenshot of your repository on GitHub.com, including your repository name and number of commits.
    2. A screenshot of the program displaying a successful pathfinding search to the goal using either `SingleStepPathingStrategy` or `AStarPathingStrategy`.
       Please adhere to the following requirements for your screenshot:
       - Your path must not be a straight line.
       - Your path must be generated after altering the path by adding/removing obstacles with the left or right mouse buttons.

***_You are strongly encouraged to verify the correctness of your program with an instructor._***

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
>
> Please contact me or see our course syllabus for clarification or further details.

## Due Date

Please refer to Canvas for due-date information, if applicable.

## Grading

Please refer to Canvas for additional grading information, if applicable.