import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Contains file processing behavior for the project.
 * You will modify only the following methods:
 * - parseLineUsingLittleNumber()
 * - parseLineUsingBigNumber()
 * You do not need to run this class directly.
 *
 * @author Vanessa Rivera
 */
public class FileProcessor {
    /**
     * Automatically called multiple times when parsing an input text file, once for each line.
     * The included tests control whether the LittleNumber or BigNumber version is used.
     *
     * @param line The line from the input file, including any newline characters
     */
    public static void parseLineUsingLittleNumber(String line) {
        String[] lineArray = line.split("\\s+");
        if (lineArray.length != 3) {
            return;
        }

        LittleNumber firstLittleNumber = LittleNumber.fromString(lineArray[0]);
        LittleNumber secondLittleNumber = LittleNumber.fromString(lineArray[2]);

        LittleNumber result = new LittleNumber(0);
        switch (lineArray[1]) {
            case "+" -> result = firstLittleNumber.add(secondLittleNumber);
            case "*" -> result = firstLittleNumber.multiply(secondLittleNumber);
            case "^" -> result = firstLittleNumber.exponentiate(Integer.parseInt(secondLittleNumber.toString()));
        }

        System.out.printf("%s %s %s = %s%n", firstLittleNumber.toString(), lineArray[1], secondLittleNumber.toString(), result.toString());
    }

    /**
     * Automatically called multiple times when parsing an input text file, once for each line.
     * The included tests control whether the LittleNumber or BigNumber version is used.
     *
     * @param line The line from the input file, including any newline characters
     */
    public static void parseLineUsingBigNumber(String line) {
        String[] lineArray = line.split("\\s+");
        if (lineArray.length != 3) {
            return;
        }

        BigNumber firstBigNumber = BigNumber.fromString(lineArray[0]);
        BigNumber secondBigNumber = BigNumber.fromString(lineArray[2]);

        BigNumber result = new BigNumber();
        switch (lineArray[1]) {
            case "+" -> result = firstBigNumber.add(secondBigNumber);
            case "*" -> result = firstBigNumber.multiply(secondBigNumber);
            case "^" -> result = firstBigNumber.exponentiate(Integer.parseInt(secondBigNumber.toString()));
        }

        System.out.printf("%s %s %s = %s%n", firstBigNumber, lineArray[1], secondBigNumber, result);
    }

    /**
     * Called by the included test file. You do not need to run this method directly.
     * <strong>Do not</strong> modify this method.
     *
     * @param args Command-line arguments
     *             0: The input file path. (Required)
     *             1: Option to use "little" or "big" number parsing. (Required)
     */
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    switch (args[1]) {
                        case "little":
                            parseLineUsingLittleNumber(line);
                            break;
                        case "big":
                            parseLineUsingBigNumber(line);
                            break;
                        default:
                            throw new IllegalArgumentException(String.format("Option %s is invalid, must be 'little' or 'big'.%n", args[1]));
                    }
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.printf("Expected 2 command-line arguments, got %d.%n", args.length);
        } catch (IOException e) {
            System.out.printf("Failed to read file %s.%n", args[0]);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
