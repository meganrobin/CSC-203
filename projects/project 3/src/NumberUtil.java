import java.util.Random;

/** Defines numeric helper functions. */
public class NumberUtil {
    /** Returns a random integer from min (inclusive) to max (exclusive) */
    public static int getRandomInt(int min, int max) {
        Random random = new Random();
        return min + random.nextInt(max - min);
    }

    /** Returns a random double from min (inclusive) to max (exclusive) */
    public static double getRandomDouble(double min, double max) {
        Random random = new Random();
        return min + random.nextDouble() * (max - min);
    }

    /** Limits a value to the given  range. */
    public static int clamp(int value, int low, int high) {
        return Math.min(high, Math.max(value, low));
    }
}
