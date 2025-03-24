/**
 * Demonstrates encapsulation of functionality as a model for your linked list class.
 *
 * @author Vanessa Rivera
 */
public class LittleNumber {
    /** The integer value the LittleNumber represents. */
    private final int value; // Final because LittleNumbers are immutable

    /**
     * Constructs a LittleNumber to represent the given value.
     *
     * @param value
     */
    public LittleNumber(int value) {
        this.value = value;
    }

    /**
     * Instance method version of addition.
     *
     * @param addend The other number
     * @return A LittleNumber representing the sum
     */
    public LittleNumber add(LittleNumber addend) {
        int sum = value + addend.value;
        return new LittleNumber(sum);
    }

    /**
     * Instance method version of multiplication.
     *
     * @param multiplicand The other number
     * @return A LittleNumber representing the product
     */
    public LittleNumber multiply(LittleNumber multiplicand) {
        int product = value * multiplicand.value;
        return new LittleNumber(product);
    }

    /**
     * Instance method version of exponentiation.
     * Note: this method accepts a regular integer as an exponent, not a LittleNumber.
     *
     * @param exponent The exponent to raise to
     * @return A LittleNumber representing the power
     */
    public LittleNumber exponentiate(int exponent) {
        int power = 1;
        for (int n = 0; n < exponent; n++) {
            power *= value;
        }
        return new LittleNumber(power);
    }


    /**
     * Static version of addition.
     *
     * @param a A number to add
     * @param b The other number to add
     * @return A LittleNumber representing the sum
     */
    public static LittleNumber add(LittleNumber a, LittleNumber b) {
        int sum = a.value + b.value;
        return new LittleNumber(sum);
    }


    /**
     * Static version of multiplication.
     *
     * @param a A number to multiply
     * @param b The other number to multiply
     * @return A LittleNumber representing the product
     */
    public static LittleNumber multiply(LittleNumber a, LittleNumber b) {
        int product = a.value * b.value;
        return new LittleNumber(product);
    }

    /**
     * Static version of exponentiation.
     * Note: this method accepts a regular integer as an exponent, not a LittleNumber.
     *
     * @param base The base value to raise
     * @param exponent The exponent to raise to
     * @return A LittleNumber representing the power
     */
    public static LittleNumber exponentiate(LittleNumber base, int exponent) {
        int power = 1;
        for (int n = 0; n < exponent; n++) {
            power *= base.value;
        }
        return new LittleNumber(power);
    }

    /**
     * Constructs a LittleNumber object from a string.
     *
     * @param string The string representing the little number
     * @return The LittleNumber object representing the string
     */
    public static LittleNumber fromString(String string) {
        int value = Integer.parseInt(string);
        return new LittleNumber(value);
    }

    /**
     * Constructs a string from the LittleNumber object.
     *
     * @return The string representation of the LittleNumber
     */
    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
