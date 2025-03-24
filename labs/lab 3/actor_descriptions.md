# Actor Descriptions

## Gardener ![Gardener Graphic](images/gardener1.png)

- If they have water, they move toward Roses, transferring their water until empty.
- If they are out of water, they move toward a Well to refill it.

## Nymph ![Nymph Graphic](images/nymph1.png)

- May potentially move in a random horizontal or vertical direction.

## Rose ![Rose Graphic](images/rose3.png)

- Begins at zero water level.
- Receives water from a Gardener
- If it has reached its maximum water level, it will become a Nymph.

## Well ![Well Graphic](images/well1.png)

- Gardeners use it to refill their water.
- Otherwise, does nothing.