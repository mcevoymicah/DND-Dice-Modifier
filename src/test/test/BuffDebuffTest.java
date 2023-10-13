package test;

import model.AbilityType;
import model.BuffDebuff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BuffDebuffTest {

    private BuffDebuff buffDebuff;

    @BeforeEach
    public void setUp() {
        buffDebuff = new BuffDebuff("Strength Boost", AbilityType.STRENGTH, 2, 5);
    }

    @Test
    public void testConstructor() {
        assertEquals("Strength Boost", buffDebuff.getName());
        assertEquals(AbilityType.STRENGTH, buffDebuff.getEffectAbility());
        assertEquals(2, buffDebuff.getEffectMagnitude());
        assertEquals(5, buffDebuff.getDuration());
    }

    @Test
    public void testSetEffectAbility() {
        buffDebuff.setEffectAbility(AbilityType.STRENGTH);
        assertEquals(AbilityType.STRENGTH, buffDebuff.getEffectAbility());
    }

    @Test
    public void testSetEffectMagnitude() {
        buffDebuff.setEffectMagnitude(3);
        assertEquals(3, buffDebuff.getEffectMagnitude());
    }

    @Test
    public void testSetDuration() {
        buffDebuff.setDuration(10);
        assertEquals(10, buffDebuff.getDuration());
    }

    @Test
    public void testIncrementDuration() {
        buffDebuff.incrementDuration(3);
        assertEquals(8, buffDebuff.getDuration());
    }

    @Test
    public void testDecrementDurationToZero() {
        buffDebuff.decrementDuration(5);
        assertEquals(0, buffDebuff.getDuration());
    }

    @Test
    public void testDecrementDurationBeyondZero() {
        buffDebuff.decrementDuration(10);
        assertEquals(0, buffDebuff.getDuration());
    }
}
