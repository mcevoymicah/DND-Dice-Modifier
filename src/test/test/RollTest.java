package test;

import model.Roll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RollTest {

    private Roll roll;

    @BeforeEach
    public void setUp() {
        roll = new Roll("Strength check", 10, 5);
    }

    @Test
    public void testConstructorNormal() {
        assertEquals("Strength check", roll.getType());
        assertEquals(10, roll.getBaseResult());
        assertEquals(5, roll.getAppliedModifier());
        assertEquals(15, roll.getFinalOutcome()); // 10 (base) + 5 (modifier) = 15
    }

    @Test
    public void testSetBaseResult() {
        roll.setBaseResult(12);
        assertEquals(12, roll.getBaseResult());
        assertEquals(17, roll.getFinalOutcome()); // 12 (base) + 5 (modifier) = 17
    }

    @Test
    public void testSetAppliedModifier() {
        roll.setAppliedModifier(3);
        assertEquals(3, roll.getAppliedModifier());
        assertEquals(13, roll.getFinalOutcome()); // 10 (base) + 3 (modifier) = 13
    }

    @Test
    public void testRollDescription() {
        String expected = "Strength check: Rolled a 10 with a modifier of 5. Final result: 15";
        assertEquals(expected, roll.rollDescription());
    }

}
