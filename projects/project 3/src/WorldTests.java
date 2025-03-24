import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WorldTests {
    public static String makeSave(int rows, int cols, String... entities) {
        StringBuilder sb = new StringBuilder(String.format("Rows: %d%nCols: %d%n", rows, cols));
        for (String entity : entities) {
            sb.append("Entity: ").append(entity).append(System.lineSeparator());
        }
        return sb.toString();
    }

    public static int countImages(String filePath, String key) {
        int lineCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(key)) {
                    lineCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineCount;
    }

    @Test
    public void testParsing() {
        String sav = """
                Rows: 3
                Cols: 5
                Background: grass grass grass grass grass
                Background: grass  grass  grass
                Background: grass grass grass grass grass grass
                Entities:
                """;

        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 1);

        assertEquals(0, entities.size());

        assertThrows(IllegalArgumentException.class, () ->
                VirtualWorld.headlessMain(new String[]{"-string", "Entity:"}, 1)
        );

        assertThrows(IllegalArgumentException.class, () ->
                VirtualWorld.headlessMain(new String[]{"-string", "Background:"}, 1)
        );

        assertDoesNotThrow(() -> VirtualWorld.headlessMain(new String[]{"-string", "Rows: 1\nCols: 1\n"}, 1));
    }

    @Test
    public void testTryAddEntity() {
        String sav = makeSave(1, 1, "stump  0 0", "stump  0 0");

        assertThrows(IllegalArgumentException.class, () ->
                VirtualWorld.headlessMain(new String[]{"-string", sav}, 1)
        );
    }

    @Test
    public void testWithinBounds() {
        String sav = makeSave(9, 4, "house  4 9");

        assertThrows(IllegalArgumentException.class, () ->
                VirtualWorld.headlessMain(new String[]{"-string", sav}, 1)
        );
    }

    @Test
    public void testDudeAnimation() {
        String sav = makeSave(1, 1, "dude test 0 0 0.1 1.0 1");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 10);

        assertEquals(1, entities.size());
        assertEquals("test 0 0 100", entities.get(0));
    }

    @Test
    public void testDudeLimit() {
        String sav = makeSave(1, 6,
                "dude test 0 0 100.0 1.0 4", "tree  1 0 1.0 0.1 1", "tree  2 0 1.0 0.1 1", "tree  3 0 1.0 0.1 1", "tree  4 0 1.0 0.1 1", "tree  5 0 1.0 0.1 1");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 10);

        assertTrue(entities.stream().anyMatch("test 3 0 0"::equals));
    }

    @Test
    public void testDudePathing() {
        String sav = makeSave(5, 4,
                "dude test 0 0 100.0 1.0 1",
                "water  2 0",
                "water  0 2",
                "water  1 2",
                "water  1 3",
                "water  1 4", "stump stump 2 1",
                "tree tree 2 2 100.0 0.1 1",
                "stump stump 2 3",
                "water  3 3",
                "house  2 4");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 7);

        assertEquals(1, entities.size());
        assertEquals("test 2 3 0", entities.get(0));
    }

    @Test
    public void testDudeFullPathing() {
        String sav = makeSave(4, 4,
                "dude test 0 0 100.0 1.0 1", "water  2 0",
                "stump stump 2 1",
                "water  0 2", "water  1 2",
                "water  1 3", "tree  2 3 1.0 0.1 1"
        );
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 4);

        assertEquals(1, entities.size());
        assertEquals("test 2 2 0", entities.get(0));
    }

    @Test
    public void testDudeFullTrample() {
        String sav = makeSave(1, 5, "dude  0 0 1.0 1.0 0", "stump test 2 0", "house  4 0");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 3);
        assertEquals(0, entities.size());
    }

    @Test
    public void testDudeNotFullTrample() {
        String sav = makeSave(1, 5, "dude  0 0 1.0 1.0 1", "stump test 2 0", "tree  4 0 1.0 1.0 1");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 3);

        assertEquals(0, entities.size());
    }

    @Test
    public void testDudeFullTransform() {
        String sav = makeSave(2, 4, "tree  1 0 100.0 0.1 2", "water  2 0", "house  0 1", "dude test 1 1 100.0 1.0 2", "tree  3 1 100.0 0.1 100");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 5.0);

        assertTrue(entities.stream().anyMatch("test 2 1 0"::equals));
    }

    @Test
    public void testDudeNotFullTransform() {
        String sav = makeSave(1, 4,  "tree  0 0 100.0 0.1 1", "dude test 1 0 100.0 1.0 1", "house  3 0");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 5.0);

        assertEquals(2, entities.size());
        assertTrue(entities.stream().anyMatch("test 2 0 0"::equals));
    }

    @Test
    public void testFairyAnimation() {
        String sav = makeSave(1, 1, "fairy test 0 0 0.1 1.0");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 10);

        assertEquals(1, entities.size());
        assertEquals("test 0 0 100", entities.get(0));
    }

    @Test
    public void testFairyPathing() {
        String sav = makeSave(5, 4, "fairy test 0 0 100.0 1.0", "water  2 0", "water  0 2", "water  1 2", "water  1 3", "water  1 4", "water  3 3", "stump  2 4");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 7);

        assertEquals(2, entities.size());
        assertTrue(entities.stream().anyMatch("test 2 3 0"::equals));
    }

    @Test
    public void testMushroomBehavior() {
        String sav = """
                Rows: 3
                Cols: 3
                Background: grass  grass
                Background: grass grass grass
                Background: grass  grass
                Entity: mushroom mushroom 1 1 1.0
                """;

        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 20.0);

        assertEquals(7, entities.size());
    }

    @Test
    public void testSaplingImageIndex() {
        String sav = makeSave(1, 2, "dude  0 0 100.0 3.0 100", "sapling test 1 0");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 11.0);

        int images = countImages("imagelist", "sapling");
        int expectedIndex = (int) (images * 0.4);

        assertEquals(1, entities.size());
        assertEquals("test 1 0 " + expectedIndex, entities.get(0));
    }

    @Test
    public void testSaplingTransform() {
        String sav = makeSave(1, 1, "sapling test 0 0");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 10.0);

        assertEquals(1, entities.size());
        assertEquals("tree_test 0 0 0", entities.get(0));
    }

    @Test
    public void testStumpIntoSaplingIntoTree() {
        String sav = makeSave(2, 2, "stump test 0 0", "fairy  1 0 1.0 1.0");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 20.0);

        assertEquals(1, entities.size());
        assertEquals("tree_sapling_test", entities.get(0).split(" ", 2)[0]);
    }

    @Test
    public void testTreeAnimation() {
        String sav = makeSave(1, 1, "tree test 0 0 0.1 1.0 1");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 10.0);

        assertEquals(1, entities.size());
        assertEquals("test 0 0 100", entities.get(0));
    }

    @Test
    public void testSaplingIntoStump() {
        String sav = makeSave(2, 2, "sapling test 0 0", "dude  1 0 100.0 0.1 1");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 10.0);

        assertEquals(1, entities.size());
        assertEquals("stump_test 0 0 0", entities.get(0));
    }

    @Test
    public void testStumpIntoSapling() {
        String sav = makeSave(2, 2, "fairy  1 0 100.0 0.100", "stump mystump 0 0");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 1.0);

        assertEquals(1, entities.size());
        assertEquals("sapling_mystump", entities.get(0).split(" ", 2)[0]);
    }

    @Test
    public void testTreeIntoStump() {
        String sav = makeSave(2, 1, "tree test 0 0 1.0 1.0 0");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 1.0);

        assertEquals(1, entities.size());
        assertEquals("stump_test 0 0 0", entities.get(0));
    }
}