import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Used to test your code using the included 'FileProcessor' class.
 * <strong>DO NOT</strong> modify this file.
 *
 * @Author Vanessa Rivera
 */
public class FileProcessorTests {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        outContent.reset();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testLittleNumber1() {
        FileProcessor.main(new String[] {"input1.txt", "little"});

        String expected = """
                          1 + 2 = 3
                          3 * 4 = 12
                          5 ^ 6 = 15625
                          """
                .replace("\n", System.lineSeparator());

        String actual = outContent.toString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testLittleNumber2() {
        FileProcessor.main(new String[] {"input2.txt", "little"});

        String expected = """
                          1 + 2 = 3
                          3 * 4 = 12
                          5 ^ 6 = 15625
                          2 ^ 24 = 16777216
                          99 * 99 = 9801
                          123 + 456 = 579
                          """
                .replace("\n", System.lineSeparator());

        String actual = outContent.toString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testBigNumber1() {
        FileProcessor.main(new String[] {"input1.txt", "big"});

        String expected = """
                          1 + 2 = 3
                          3 * 4 = 12
                          5 ^ 6 = 15625
                          """
                .replace("\n", System.lineSeparator());

        String actual = outContent.toString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testBigNumber2() {
        FileProcessor.main(new String[] {"input2.txt", "big"});

        String expected = """
                          1 + 2 = 3
                          3 * 4 = 12
                          5 ^ 6 = 15625
                          2 ^ 24 = 16777216
                          99 * 99 = 9801
                          123 + 456 = 579
                          """
                .replace("\n", System.lineSeparator());

        String actual = outContent.toString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testBigNumber3() {
        FileProcessor.main(new String[] {"input3.txt", "big"});

        String expected = """
                          1 + 99999999999 = 100000000000
                          1234567812345678 + 8765432187654321 = 9999999999999999
                          1 + 9 = 10
                          22 + 88 = 110
                          333 + 777 = 1110
                          """
                .replace("\n", System.lineSeparator());

        String actual = outContent.toString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testBigNumber4() {
        FileProcessor.main(new String[] {"input4.txt", "big"});

        String expected = """
                          123456789 * 123456789 = 15241578750190521
                          10 * 9999999999 = 99999999990
                          9999999999 * 9999999999 = 99999999980000000001
                          """
                .replace("\n", System.lineSeparator());

        String actual = outContent.toString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testBigNumber5() {
        FileProcessor.main(new String[] {"input5.txt", "big"});

        String expected = """
                          9999999999 ^ 3 = 999999999700000000029999999999
                          2 ^ 40 = 1099511627776
                          """
                .replace("\n", System.lineSeparator());

        String actual = outContent.toString();

        Assertions.assertEquals(expected, actual);
    }
}
