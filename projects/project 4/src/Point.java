/** Represents a location in 2D space.*/
public class Point {
    /** The horizontal component. */
    public final int x;

    /** The vertical component. Larger values are lower on the screen. */
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int manhattanDistanceTo(Point point) {
        int deltaX = x - point.x;
        int deltaY = y - point.y;

        return Math.abs(deltaX) + Math.abs(deltaY);
    }

    public double euclideanDistanceTo(Point point) {
        int deltaX = x - point.x;
        int deltaY = y - point.y;

        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public boolean adjacentTo(Point point) {
        return manhattanDistanceTo(point) == 1;
    }

    @Override
    public final String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public final boolean equals(Object other) {
        if (other instanceof Point point) {
            return x == point.x && y == point.y;
        }
        return false;
    }

    @Override
    public final int hashCode() {
        int hash = 1;
        hash = hash * 31 + x;
        hash = hash * 31 + y;
        return hash;
    }
}
