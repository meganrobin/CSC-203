import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AStarPathingStrategy implements PathingStrategy {

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
        // A list of “seen” nodes to process, sorted by f-score.
        // The current node is selected as the node from the Open Set with the best f-score.
        List<Point> openSet = new ArrayList<>();
        // A list of already processed nodes.
        // Ensure that nodes are added to this after being processed.
        List<Point> closedSet = new ArrayList<>();
        HashMap<Point, Integer> gScores = new HashMap<>();
        HashMap<Point, Integer> fScores = new HashMap<>();
        HashMap<Point, Point> previousMappings = new HashMap<>();

        // set g-score of the start point to 0, add it to the open set
        gScores.put(start, 0);
        fScores.put(start, start.manhattanDistanceTo(end));
        previousMappings.put(start, null);
        openSet.add(start);

        Point current = start;

        while (!openSet.isEmpty()) {
            Point lowestF = openSet.getFirst();
            for (Point point : openSet) {
                if (fScores.getOrDefault(point, Integer.MAX_VALUE) <= fScores.getOrDefault(lowestF, Integer.MAX_VALUE)) {
                    lowestF = point;
                }
            }
            // the node from the open set w/ the best F-Value is the new current node
            current = lowestF;
            // move current point from open set to closed set
            openSet.remove(current);
            closedSet.add(current);

            // if the current point is within reach of the end point, reconstruct and return the path using the previous mappings
            if (withinReach.test(current, end)) {
                List<Point> path = new ArrayList<>();
                // while the current node's previous mapping is NOT null, add the current node to the path
                while (previousMappings.get(current) != null) {
                    path.add(current);
                    current = previousMappings.get(current);
                }
                path = path.reversed();
                return path;
            }
            // for each traversable neighbor of the current node that's NOT in the closed set
            List<Point> neighbors = potentialNeighbors.apply(current).filter(canPassThrough).filter(point -> !closedSet.contains(point)).toList();
            for (Point point : neighbors) {
                // if the neighbor is not already in the open set, add it there
                if (!openSet.contains(point)) {
                    openSet.add(point);
                }
                gScores.put(point, gScores.get(current) + 1);
                fScores.put(point, point.manhattanDistanceTo(end) + gScores.get(point));
                previousMappings.put(point, current);
            }
        }
        // return empty list if no path is found
        return List.of();
    }
}
