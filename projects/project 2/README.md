# Project 2

## Course Information

- **Course Name:** CSC 203
- **Instructor:** Professor Vanessa Rivera
- **Term:** 2023-24 Spring Quarter

## Overview

In this assignment, you will be introduced to a large, object-oriented codebase in the form of a video game-like virtual world.
This project is similar to lab assignments 3 and 4 but **contains significantly different code and resources**.

As given, classes in this program lack **cohesion**: they perform a variety of tasks from overly general to very specific.
You will divide the present `Entity` and `Action` classes into multiple classes that exhibit high cohesion, that is each will serve a singular, well-defined purpose.
In doing so, you will create class and/or interface hierarchies for both "families": `Action` and `Entity`.

By the end of this project, the virtual world program will be representative of a fully functioning and well-designed object-oriented program.

## Learning Objectives

In completing this assessment, you will be able to:

- Create a UML diagram containing inheritance and interface hierarchies. (🎨)
- Write extending and implementing classes using Java syntax. (🧬, 🤝, ☕)
- Refactor a large, non-trivial code base using modern tools. (🛠️)

## Instructions

### Checkpoint 1

#### Task 1: Acquaint Yourself with the Program

**🎯Task Goal:** Complete the Checkpoint 1 Quiz.

1. **Run "VirtualWorld":** Run `VirtualWorld` and examine the program's execution.
   - The arrow keys can be used to move your "viewport" of the world.
   - You can click on certain entities to see some "log" information.
   - If you provide one of the following command-line arguments  you can change the speed of the simulation:
     - `-fast`: Two times faster simulation speed
     - `-faster`: Four times faster simulation speed
     - `-fastest`: Sixteen times faster simulation speed
2. **Read Accompanying Documentation:** Read both `class_descriptions.md` and `entity_descriptions.md` to get a brief overview of program components.
3. **Examine the Code:** Look at all the classes in the `src` directory, taking special care to understand `Entity.Java` and `Action.java`.
   Try to understand how the classes interact using the documentation as a guide before moving on to the next task.
4. **Study Prior Work:** Some questions will ask about the best way to refactor parts of the code.
   Use completed labs 3 and 4 as examples.
5. **Complete the Canvas Quiz:** A quiz that asks questions about these instructions is listed under the Project 1 module. Complete this quiz to earn credit for this task.
   - **Note: The quiz has a 30-minute timer**.
     You may attempt this quiz every 8 hours before its due date.

> [!Note]
>
> You will be asked to make a very small modification to the program during the quiz.
> Have the program open and ready in IntelliJ IDEA before starting.

### Checkpoint 2

#### Task 2: Create a Refactored Design

**🎯Task Goal:** Create UML diagrams for the `Action` and `Entity` classes.

1. **Create Two Class Hierarchies in UML:** Create a diagram for `Action` and `Entity`, including their entire class/interface hierarchies.
2. **Guidelines:** Below are guidelines to help you get started:
   - You must use **extends** and/or **implements** relationship arrows in your diagram.
   - You must indicate whether a specific class diagram represents an interface or abstract class and must indicate all modifiers such as `public`, `abstract`, `static`, etc.
   - You do not need to submit diagrams for any other classes, e.g. `World`, `Event`, etc.
   - Your overall goal is to reduce redundancy and improve cohesion.
   - You may choose to divide your classes either using subclasses, interfaces, or a combination of both; there is no optimal solution, but each has advantages and disadvantages.
   - Use inheritance (e.g., `extends`) to propagate data and or functionality requirements.
   - Use contracts (e.g., `implements`) for shared functionality requirements, especially in unrelated classes.
   - Use intermediate abstract classes to propagate shared data and functionality to concrete classes.
   - You will want each "EntityKind" and "ActionKind" to be represented by a class and, based on your design.
   - Iterate: after you’ve refactored the class, perform the process again, checking for remaining redundancies.
   - No class should implement an interface method without code: i.e., a "{}" implementation.
   - If a class does not make use of an instance variable defined within it or that it has inherited, you may need to redesign your hierarchy or remove that instance variable.
   - Follow standard data hiding rules: make instance variables private whenever possible.
   - You will eventually remove
3. **Restrictions:** You may not automatically generate your UML diagram.
   You are allowed to **handwrite** the diagrams, physically or digitally, or use the "diagrams.net" application (see the resource section)/"diagrams.net" IntelliJ Plugin.
   **No other formats will be accepted.**
4. **Future Modifications:** After submission, you are allowed to make modifications to your design as you complete your final submission.
   In doing so, you must submit a new UML diagram that reflects your final submission code and descriptions of such changes with your final submission.
5. **Submission:** For completion, you will submit a photo or image of a UML diagram to Canvas.
   Your UML diagram must be legible and include your name.

> [!Note]
>
> You are not expected to write any code for this task.

### Final Submission

#### Task 3: Implement the Refactored Design

**🎯Task Goal:** Refactor `Action` and `Entity` into hierarchies in Java code.

1. **Refactor the Two Hierarchies:** Refactor the codebase in IntelliJ IDEA to mirror the work done for your UML design.
   Your refactoring must not add or remove any functionality; the resulting program must work exactly as before and all tests within `WorldTests.java` must pass.
2. **Common Errors and Tips:** Below is a list of potential tips and common errors that typically cause the program to fail:
   - Use IntelliJ IDEA refactoring features wherever possible for moving members between classes.
   - Refactor classes one direct subclass at a time, as in your lab assignments.
   - Ensure that no `animationPeriod` or `behaviorPeriod` is assigned a value of zero.
   - `VirtualWorld.scheduleActions` will require modification due to dependency on `Entity`.
      Here, you are recommended to use `instanceof` and type casting.
   - `World.findNearest` will require modification to use `Class<?>` instead of `EntityKind`.
   - Repeatedly run `VirtualWorld` to visually inspect errors.
   - Commit and push often when the program is in a runnable state.
   - **❗Important:** Ensure that animation and behavior periods are passed in the correct order for every call in which they appear.
     You can ensure this by "hovering" over any call with a mouse and visually inspecting that the argument order matches the method signature.
   - **❗Important:** Do not assign any of the "INDEX" constant values to any instance variable.
3. **Testing and Verification:** All unit tests included `WorldTests` must pass.
   Each of these tests creates a small version of the virtual world to test specific functionality scenarios.
   You **do not** need to write any of your own tests.
4. **UML Updates:** If your code implementation deviates from your UML diagram, you must submit a new diagram.
   Additionally, **you must include a written description of and reasoning for all changes** between your original diagram to your final diagram
   Submit a photo or image of UML diagram to Canvas alongside your final submission.
   Your UML diagram must be legible and include your name.
   If your implementation did not change from your design, you do not need to resubmit a UML diagram.
5. **Submission:** For completion, you must commit and push your new files and changes to GitHub.
   You must submit a screenshot showing proof of your GitHub submission to Canvas.

## Resources
- [draw.io](https://app.diagrams.net/): A web-based application for creating UML diagrams.

## Submission

### Checkpoint 1

For completion of this checkpoint, please complete the Canvas quiz described above.

### Checkpoint 2

For completion of this checkpoint, please complete the following:

1. Create a UML diagram per the above instructions.
2. Submit a photo or image of your UML diagram to this assignment's Checkpoint 2 submission Canvas page.

### Final

For completion of this assignment, please complete the following:

1. Commit and push your updated code to your version of this assignment's GitHub repository.
2. In a submission to this assignment's final submission Canvas page, include the following:
    1. A screenshot of your repository on GitHub.com, including your repository name and number of commits.
        - **Note:** No checkmark will be present.
    2. Your updated UML diagram (if necessary).

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
