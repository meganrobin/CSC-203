# Project 4

## Course Information

- **Course Name:** CSC 203
- **Instructor:** Vanessa Rivera
- **Term:** 2023-24 Spring Quarter

## Overview

In this project, you will add entirely new functionality to the virtual world in the form of a "world-changing event" that creates brand-new entities to interact and change the world.

## Learning Objectives

In completing this assessment, you will be able to:

- Create and integrate new classes as extensions to an existing class hierarchy.
- Utilize code that interacts with external non-text, image data files.
- Write a function that performs real-time functionality through a user's mouse press.

## Optional Partner

You may, but are not require to, work on this assignment with up to one other partner.
Your partner must be enrolled in one of ***my*** CSC 203 sections this quarter.

If working with a partner, you must choose which of your repositories to base your project 4 work off of.
That person will submit work to both GitHub and Canvas.
If you would like simultaneous access to the GitHub repository, please come see me during lab so that I may grant access to the same repository for you both.

> [!WARNING]
> 
> By working with a partner, you are agreeing to not share code any code until you have both submitted all previous projects.
> 
> Submission of similar code for any assignment (except Project 4) will result in an academic integrity violation for both parties involved. 

## Instructions

**Important:** When accepting the repository invitation, you will be asked for a team name. 
If you are working alone, simply enter a name in the following format: `LASTNAME-FIRSTNAME`.

If you are working as a team, **the person who's base code is being should create the team**.
In this case, name your repository using the following format: `CREATORSLASTNAME-CREATORSFIRSTNAME-PARTNERLASTNAME-PARTNERFIRSTNAME`.

Once accepted, copy over your Project 3 code to the Project 4 repository, then complete the following tasks.
If you are working on a team, you can both push and pull updates. Be sure to use the "rebase" feature whenever possible to ensure changes are able to be pulled.

### Task 1: New Entities

- **Description:** Design and implement *at least* two new **concrete** subtypes that extend your `Entity` class hierarchy.
  Some examples include a green "dude" that travels the world, randomly planting stumps, a "zombie" that seeks out and "zombifies" dudes and fairies through transformations, or a "dragon" that travels the land transforming Houses into "fire" entities and is attacked by Fairies.
- **Requirements:**
  - You must have already divided the Entity class into subclasses. You may not use the base Project 2 Entity class for this project.
  - Both entities must use entirely new graphics. You may not use any given image files.
    - Note: You must commit and push any new image files for your program to be properly graded.
  - Both entities must schedule and perform behaviors.
  - At least one entity must perform movement and this movement must be unique.
    For example, you should not make a new entity that simply moves toward trees and saplings like a dude or toward a stump like a fairy.
  - At least one entity should utilize a new instance variable in its behaviors, unique from any previous instance variables in existing project.
  - Both entities must schedule and perform animations, having at least 2 image frames of animation.
- **Exclusions:**
  - Divide existing entity subclass into further subclasses does not count. For example, creating "DudeFull" and "DudeNotFull" entities.

### Task 2: World-Changing Event

- **Description:** You will add your entities to the world via a "world-changing event".
  This world-changing event will lead to your task 1 entities being created, background tiles changing, and at least one entity transformation.
  Clicking in the virtual world window will cause this event to occur, localized at the position of the mouse click.
  You will implement your world-changing event by modifying logic within the `VirtualWorld.mousePressed()` method.
- **Requirements:**
  - A world-changing event must occur with respect to the cursor position when you click inside the virtual world window.
  - Your world-changing event must (eventually) create one of your new entities from task 1 within the world.
  - When the world-changing event occurs, at least 9 background tiles must be changed to at least one entirely new graphic.
    This change can be done around the mouse click area when the event occurs or can happen over time, through the newly created entity for example.
    You may **not** use any image files given in this, or other versions of, the project.
    - Note: You must commit and push any new image files for your program to be properly graded.
  - An entity must be "transformed" into another as a result of the world-changing event.
    You may change some entities when the mouse is clicked or your new entity may transform other entities as part of its behavior.
    Existing entities may be transformed, e.g. a dude can be transformed into either a fairy or one of your new entities.
  - Your second entity must be created either when the world-changing event occurs or as a result of it. 
    Your first entity may create or transform other entities into your second entity, for example.
  - Both entities must have their actions scheduled.

### Task 3: Event Description and Instructions

- **Description:** In your Canvas submission, write a short description of what your world-changing event does and any instructions on how to perform it (e.g., click near a Dude, etc.).
  If I cannot determine how to properly execute your world-changing event, you may not receive full credit for your submission.

### (Optional) Task 4: Extra Credit

- **Description:** You may receive up to four points of extra credit on this assignment by exceeding the above requirements in complexity and content.
  Examples include:
  - Adding more than the entities required.
  - Complex and unique entity pathing or logic.
  - Creating graphics by hand.
  - Modifying the existing virtual world by altering the world save file or existing graphics and logic.
  - Adding entirely new functionality (for example, like music and sound effects)
- **Requirements:**
  - As part of your Canvas submission, you must indicate what work you've done that constitutes extra credit.
  - You must also provide a reasonable estimate for how much extra credit you believe you deserve, citing your submitted work as evidience.
    Use the following criteria for reference:
    - No Extra Credit: Meets the above requirements but does not exceed them.
    - Full Extra Credit: A significant amount (4 - 6) of new entities were added with substantially complex logic, each with hand-crafted graphics, and entirely new features such as audio or interactivity have been added to the program.

## Resources

- [The GNU Image Manipulation Program](https://www.gimp.org/): Can be used to modify and create new graphics.
- [OpenGameArt.org](https://opengameart.org/): Contains free, open source graphics intended for video games.
- [PImage Documentation](https://processing.org/reference/PImage.html): Documentation for the PImage class used in the virtual world program.
- [mousePressed() Documentation](https://processing.org/reference/mousePressed_.html): Documentation for the `mousePressed()` method used in the virtual world program.

## Additional Notes

### The `imagelist` File

The `imagelist` file specifies key/value pairs of.

`VirtualWorld`'s `ImageLibrary imageLibrary` instance variable (which is passed to entities throughout the program).

For example, calling `imageLibrary.get("dude")` will return a `List` of images associated with the key "dude" based on the following lines of the imagelist file:

```
dude images/dude1.png
dude images/dude2.png
dude images/dude3.png
dude images/dude4.png
```

A resultant list is typically passed directly to Entity constructors. This is where.

To add new images to the program, you may introduce new lines with new keys that point to new images in the `images` folder.
For example, if you were adding a two-frame animation for a "penguin" entity, you might add these lines (assuming the corresponding image files were also added `images`):

```
penguin images/penguin1.png
penguin images/penguin2.png
```

For best results, the images you use should match the size of the `VirtualWorld` grid tiles: 32 pixels by 32 pixels.


### The `Background` Class

The `World` class represents its background (the grass, flowers, etc.) as a 2D grid of `Background` objects.
Each `Background` object is associated with an image list from an `ImageLibrary`, similar to entities as described above.
This list is passed in when creating a new background object.

For example, creating a new `Background` object using, for example, `imageLibrary.get("grass")` will create a background object that uses the following image from `imagelist`:

```
grass images/grass.png
```

You can add new images for backgrounds, just like for entities.
Note that backgrounds will only require a single image; they are not animated.

Be sure to look at `World.setBackgroundCell()`, which takes a `Background` object as a parameter.
This method is used to change the background grid using a `Background` object.

### Mouse Click Behavior

You will implement mouse click behavior by modifying the `VirtualWorld.mousePressed()` method.
As is, this method converts the mouse location to a world position as a `Point` object, and then prints information for any entities at that location.
You will change this functionality to interact with the world according to your design.
Within this method, you have access to the virtual world's world model and data through the `VirtualWorld` object's instance variables, e.g. `public World world`.

To see examples of adding new entities to the world, look at your existing functionalities.
Specifically, the "transform" method and "parse" methods provide examples.
Note that actions must be scheduled for newly created entities, or they will remain inactive.

You must also perform validation to ensure that the mouse press produces no errors.
Properly removing any present entities, ensuring positions are within bounds, etc., for example.

## Submission

### Final Submission

For completion of this assignment, please complete the following:

1. Commit and push your updated code to your version of this assignment's GitHub repository.
   - **Important:** Ensure all of your new classes, images, and any other additional resources, have been added and committed to GitHub. Files that have not been added show up as <span style="color: red;">red</span> within IntelliJ.
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