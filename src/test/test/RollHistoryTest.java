package test;

import model.Roll;
import model.RollHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RollHistoryTest {

    private RollHistory rollHistory;
    private Roll testRoll1;
    private Roll testRoll2;

    @BeforeEach
    void runBefore() {
        List<Roll> rolls = new ArrayList<>();
        rollHistory = new RollHistory(rolls);

        testRoll1 = new Roll("Strength check", 10, 2);
        testRoll2 = new Roll("Dexterity check", 8, 1);
    }

    @Test
    public void testAddRoll() {
        rollHistory.addRoll(testRoll1);
        assertEquals(1, rollHistory.getRollList().size());
        assertEquals(testRoll1, rollHistory.getRollList().get(0));
    }

    @Test
    public void testGetLastRoll() {
        assertNull(rollHistory.getLastRoll()); // No rolls added yet

        rollHistory.addRoll(testRoll1);
        assertEquals(testRoll1, rollHistory.getLastRoll());

        rollHistory.addRoll(testRoll2);
        assertEquals(testRoll2, rollHistory.getLastRoll());
    }

    @Test
    public void testClearRollHistory() {
        rollHistory.addRoll(testRoll1);
        rollHistory.addRoll(testRoll2);
        assertEquals(2, rollHistory.getRollList().size());

        rollHistory.clearRollHistory();
        assertEquals(0, rollHistory.getRollList().size());
    }

    @Test
    public void testRemoveRoll() {
        rollHistory.addRoll(testRoll1);
        rollHistory.addRoll(testRoll2);
        assertEquals(2, rollHistory.getRollList().size());

        rollHistory.removeRoll(testRoll1);
        assertEquals(1, rollHistory.getRollList().size());
        assertFalse(rollHistory.getRollList().contains(testRoll1));
    }
}
