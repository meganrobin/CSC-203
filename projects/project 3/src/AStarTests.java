import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AStarTests {

    @Test
    public void testDudeFullPathing() {
        String sav = WorldTests.makeSave(2, 3, "tree tree 0 0 0.1 1.0 1", "stump stump 1 0", "dude test 0 1 100.0 1.0 1", "water  1 1", "house  2 1");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 5);

        assertEquals(1, entities.size());
        assertEquals("test 2 0 0", entities.get(0));
    }

    @Test
    public void testDudeNotFullPathing() {
        String sav = WorldTests.makeSave(2, 3, "dude test 0 1 100.0 1.0 100", "house  1 1", "tree  2 1 100.0 0.1 100");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 5);

        assertEquals(1, entities.size());
        assertEquals("test 2 0 0", entities.get(0));
    }

    @Test
    public void testFairyPathing() {
        String sav = WorldTests.makeSave(2, 3, "fairy test 0 1 100.0 1.0", "water  1 1", "stump  2 1");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 3);

        assertEquals(1, entities.size());
        assertEquals("test 2 0 0", entities.get(0));
    }

    @Test
    public void testDudeFullBlocked() {
        String sav = WorldTests.makeSave(4, 3, "dude test 0 0 100.0 1.0 1", "tree tree 1 0 0.1 0.1 1", "water  1 2", "water  1 3", "water  2 1", "house  2 3");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 10);

        assertEquals(2, entities.size());
        assertTrue(entities.stream().anyMatch("test 0 0 0"::equals));
    }

    @Test
    public void testDudeNotFullBlocked() {
        String sav = WorldTests.makeSave(4, 3, "dude test 0 0 100.0 1.0 1", "water  1 2", "water  1 3", "water  2 1", "tree tree 2 3 1.0 1.0 1");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 10);

        assertEquals(2, entities.size());
        assertTrue(entities.stream().anyMatch("test 0 0 0"::equals));
    }

    @Test
    public void testFairyBlocked() {
        String sav = WorldTests.makeSave(4, 3, "fairy test 0 0 100.0 1.0", "water  1 2", "water  1 3", "water  2 1", "stump stump 2 3");
        List<String> entities = VirtualWorld.headlessMain(new String[]{"-string", sav}, 10);

        assertEquals(2, entities.size());
        assertTrue(entities.stream().anyMatch("test 0 0 0"::equals));
    }
}