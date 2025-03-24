import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class GoRightStrategy implements PathingStrategy {
    /**
     * Return a list containing an adjacent x + 1 point from the start.
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

        // Recursively add adjacent right points

        // Get a neighboring point to the right, if it exists
        Optional<Point> adjacentRight = potentialNeighbors.apply(start)
                    .filter(p -> p.y == start.y && p.x == start.x + 1)
                    .filter(canPassThrough)
                    .findFirst();

        // Return a list containing it, if it does
        if (adjacentRight.isPresent()) {
            Point neighbor = adjacentRight.get();

            // Return list
            List<Point> path = new ArrayList<>();
            path.add(neighbor);

            // Recursively add more points
            path.addAll(computePath(neighbor, end, canPassThrough, withinReach, potentialNeighbors));

            // Return the path
            return path;
        }

        // No valid positions
        return new ArrayList<>();
    }
}
