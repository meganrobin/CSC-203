/** Represents a rectangle around the currently visible world. */
public final class Viewport {
    /** The height of the view. */
    private final int numRows;

    /** The width of the view. */
    private final int numCols;

    /** The top position of the view. */
    private int row;

    /** The left position of the view. */
    private int col;

    public Viewport(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    /** Moves the view. */
    public void shift(int col, int row) {
        this.col = col;
        this.row = row;
    }

    /** Returns 'true' if the given point is within the view. */
    public boolean contains(Point point) {
        return point.y >= this.row && point.y < this.row + this.numRows
                && point.x >= this.col && point.x < this.col + this.numCols;
    }

    /** Returns the world position relative to the view position. */
    public Point viewportToWorld(int col, int row) {
        return new Point(col + this.col, row + this.row);
    }

    /** Returns the view position relative to the world position. */
    public Point worldToViewport(int col, int row) {
        return new Point(col - this.col, row - this.row);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }
}
