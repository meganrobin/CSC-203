# Entity Descriptions

Entities carry out two kinds of actions:
1. Behaviors
2. Animations

Behaviors are the logic carried out specific to the Entity. For example, Dudes will move toward Trees to gather resources.

Entities have a list of graphics (`images`) to cycle through.
Animations update the Entity's currently displayed image from this list.
Note that it's possible for the image list to be of size 1, indicating only one static graphic.

Some entities can move, determining a movement path in the process.
Outside of Dude, entities cannot typically path "through" other entities.
That is, entities must normally move around other entities.

## Dude ![Dude Graphic](images/dude1.png) ![Dude Graphic (Carrying)](images/dude_carry1.png)
- If they are not at their resource capacity, they seek the nearest Tree or Sapling and then move toward it to gather resources.
- If they are at their resource capacity, they seek the nearest House and then move toward it to deposit their resources.
- When achieving maximum capacity or depositing resources, they "transform" themselves into a new, equivalent instance with different graphics.
- When pathing, they ignore Stumps and destroy them if passing through them.

## Fairy ![Fairy Graphic](images/fairy1.png)
- They seek the nearest Stump and then move toward it to transform the Stump into a Sapling.

## House ![House Graphic](images/house.png)
- Dudes seek out Houses to deposit resources.
- Otherwise, does nothing.

## Mushroom ![Mushroom Graphic](images/mushroom.png)
- Randomly chooses an adjacent (cardinally) and unoccupied position with a "grass" background to convert the background into a "mushroom" background.
- When all adjacent "grass" background tiles are "mushroom" background tiles, it chooses one of the tiles at random to place a new Mushroom entity.
- The Mushrooms created by a Mushroom behave progressively slower.

## Sapling ![Sapling Graphic](images/sapling3.png)
- Dudes seek out Saplings to gather resources, reducing the Sapling's "health" in the process.
- Periodically gains "health" until reaching a maximum value, upon which it transforms into a Tree with random properties.
- Periodically checks if its "health" reaches zero. If so, it transforms into a Stump.
- It does not animate as normal: it has constant animation and behavior periods and "animates" so that its graphic communicates its "health" level.

## Stump ![Stump Graphic](images/stump.png)
- Dudes ignore Stumps when pathing, destroying the Stump if "trampling" over them.
- Fairies seek out Stumps to transform them into Saplings.
- Otherwise, does nothing.

## Tree ![Sapling Graphic](images/tree1.png)
- Dudes seek out Trees to gather resources, reducing the Tree's "health" in the process.
- Periodically checks if its "health" reaches zero. If so, it transforms into a Stump.

## Water ![Water Graphic](images/water_masked.png)
- Acts as an obstacle to entity movement.
- The single graphic is "masked" through transparency to reveal the top of its background tile.
  This allows for "water's edge" background tiles to be visible.
- Otherwise, does nothing.


