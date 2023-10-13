package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuffDebuffTest {

    private BuffDebuff buffDebuff;

    @BeforeEach
    public void setUp() {
        buffDebuff = new BuffDebuff("Strength Boost", "+2 to Strength checks", 5);
    }

    @Test
    public void testConstructor() {
        assertEquals("Strength Boost", buffDebuff.getName());
        assertEquals("+2 to Strength checks", buffDebuff.getEffect());
        assertEquals(5, buffDebuff.getDuration());
    }

    @Test
    public void testSetEffect() {
        buffDebuff.setEffect("+3 to Strength checks");
        assertEquals("+3 to Strength checks", buffDebuff.getEffect());
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
