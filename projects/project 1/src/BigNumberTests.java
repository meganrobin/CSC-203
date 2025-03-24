import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * An empty class for unit tests of your new classes.
 */
public class BigNumberTests {
    @Test
    public void testCreateBigNumber() {
        BigNumber testNumber = BigNumber.fromString("8888");
        Assertions.assertEquals("8888", testNumber.toString());
    }
    @Test
    public void testAddBigNumber() {
        BigNumber testNumber1 = BigNumber.fromString("88");
        BigNumber testNumber2 = BigNumber.fromString("22");
        BigNumber sum = testNumber1.add(testNumber2);
        Assertions.assertEquals("110", sum.toString());
    }

    @Test
    public void testMultiplyBigNumber() {
        BigNumber testNumber1 = BigNumber.fromString("6");
        BigNumber testNumber2 = BigNumber.fromString("2");
        BigNumber product1 = testNumber1.multiply(testNumber2);
        Assertions.assertEquals("12", product1.toString());
    }
    @Test
    public void testMultiplyBigNumber2() {
        BigNumber testNumber3 = BigNumber.fromString("55");
        BigNumber testNumber4 = BigNumber.fromString("4");
        BigNumber product2 = testNumber3.multiply(testNumber4);
        Assertions.assertEquals("220", product2.toString());
    }

    @Test
    public void testExponentiateBigNumber1() {
        BigNumber testNumber1 = BigNumber.fromString("8");
        int power1 = 2;
        BigNumber actual = testNumber1.exponentiate(power1);
        Assertions.assertEquals("64", actual.toString());
    }

    @Test
    public void testExponentiateBigNumber2() {
        BigNumber testNumber2 = BigNumber.fromString("20");
        int power2 = 3;
        BigNumber actual = testNumber2.exponentiate(power2);
        Assertions.assertEquals("8000", actual.toString());
    }

    @Test
    public void testExponentiateBigNumber3() {
        BigNumber testNumber3 = BigNumber.fromString("33");
        int power3 = 5;
        BigNumber actual = testNumber3.exponentiate(power3);
        Assertions.assertEquals("39135393", actual.toString());
    }

    @Test
    public void testExponentiateBigNumber4() {
        BigNumber testNumber4 = BigNumber.fromString("40");
        int power4 = 6;
        BigNumber actual = testNumber4.exponentiate(power4);
        Assertions.assertEquals("4096000000", actual.toString());
    }
}
