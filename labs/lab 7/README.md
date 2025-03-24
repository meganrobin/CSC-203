# Lab 7

## Course Information

- **Course:** CSC 203
- **Instructor:** Professor Vanessa Rivera
- **Term:** 2023-24 Spring Quarter

## Overview

This lab will have you practice tracing code that utilizes try-catch blocks for exception handling.

## Learning Objectives

In completing this assessment, you will be able to:

- Trace and understand Java code that utilizes exception handling. (🛠️)

## Instructions

First, examine the given `Cat` and `Main` classes.

When ran, `Main.main()` will attempt to call `Main.exceptional()` three times.
This method accepts a `Cat` object as a single parameter and then attempts multiple error-producing operations utilizing it.
Specifically, the `Cat` instances assigned to `Main`'s static `CAT_A`, `CAT_B`, and `CAT_C` variables are passed into `exceptional`.
As given, however, the assignment statements for these variables are incomplete.

Your goal for this lab is to trace the program in order to identify the values needed so that particular branches of the program are executed.
When executed, your `Main.main()` should print the following text, exactly:
 
```
Please don't divide any cat by zero.
Who would name their cat that?
We found a stray cat.
The cat is 0 many years old.

```

To accomplish this, you must change the assignment statements for the static `CAT_A`, `CAT_B` and `CAT_C` variables in the `Main` class.

For example, you might change the assignment statement for `CAT_A` like so:

```
public static Cat CAT_A = new Cat("Harvest", 4);
```

Importantly, **do not modify any other lines of code within the project**.

## Submission

For completion of this assignment, please complete the following:

1. Commit and push your updated code to your version of this assignment's GitHub repository.
   - **Note:** The GitHub checkmark will not appear on this assignment.
2. In a submission to this assignment's Canvas page, include the following:
   - A screenshot of your repository on GitHub.com, including your repository name and number of commits.

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