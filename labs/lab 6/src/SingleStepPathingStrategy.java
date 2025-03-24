import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SingleStepPathingStrategy implements PathingStrategy {
    /**
     * Return a list containing a single point representing the next step toward a goal
     * If the start is within reach of the goal, the returned list is empty.
     *
     * @param start the point to begin the search from
     * @param end the point to search for a point within reach of
     * @param canPassThrough a function that returns true if the given point is traversable
     * @param withinReach a function that returns true if both points are within reach of each other
     * @param potentialNeighbors a function that returns the neighbors of a given point, as a stream
     */
    public List<Point> computePath(
            Point start,
            Point end,
            Predicate<Point> canPassThrough,
            BiPredicate<Point, Point> withinReach,
            Function<Point, Stream<Point>> potentialNeighbors
    ) {
        // If already within reach of the goal, return an empty list
        if (withinReach.test(start, end)) {
            return new ArrayList<>();
        }

        // Horizontal Check
        Optional<Point> horizontalNext = potentialNeighbors.apply(start)
                .filter(p -> Math.abs(end.x - p.x) < Math.abs(end.x - start.x))
                .filter(canPassThrough)
                .min((p1, p2) -> Math.abs(end.x - p1.x) - Math.abs(end.x - p2.x));

        // Return a list containing the point if it exists
        if (horizontalNext.isPresent()) {
            Point neighbor = horizontalNext.get();

            // Return list
            List<Point> path = new ArrayList<>();
            path.add(neighbor);

            // Recursively add more points
            path.addAll(computePath(neighbor, end, canPassThrough, withinReach, potentialNeighbors));

            // Return the path
            return path;
        }

        // Vertical Check
        Optional<Point> verticalNext = potentialNeighbors.apply(start)
                .filter(p -> Math.abs(end.y - p.y) < Math.abs(end.y - start.y))
                .filter(canPassThrough)
                .min((p1, p2) -> Math.abs(end.y - p1.y) - Math.abs(end.y - p2.y));

        // Return a list containing the point if it exists
        if (verticalNext.isPresent()) {
            Point neighbor = verticalNext.get();

            // Return list
            List<Point> path = new ArrayList<>();
            path.add(neighbor);

            // Recursively add more points
            path.addAll(computePath(neighbor, end, canPassThrough, withinReach, potentialNeighbors));

            // Return the path
            return path;
        }

        // No valid positions
        return new ArrayList<>(); // Assumed empty
    }
}