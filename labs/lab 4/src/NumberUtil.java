/** Defines numeric helper functions. */
public class NumberUtil {
    /** Limits a value to the given  range. */
    public static int clamp(int value, int low, int high) {
        return Math.min(high, Math.max(value, low));
    }
}
