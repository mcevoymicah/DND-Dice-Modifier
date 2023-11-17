package test;

import model.AbilityType;
import model.BuffDebuff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.json.JSONObject;


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
    public void testDecrementDuration() {
        buffDebuff.decrementDuration();
        assertEquals(4, buffDebuff.getDuration());
    }

    @Test
    public void testDecrementDurationToZero() {
        for (int i = 0; i < 5; i++) {
            buffDebuff.decrementDuration();
        }
        assertEquals(0, buffDebuff.getDuration());
    }

    @Test
    public void testDecrementDurationBeyondZero() {
        for (int i = 0; i < 6; i++) {
            buffDebuff.decrementDuration();
        }
        assertEquals(0, buffDebuff.getDuration());
    }

    @Test
    public void testGetDescription() {
        String expectedDescription = "Strength Boost (STRENGTH, 2, Duration: 5 rounds)";
        assertEquals(expectedDescription, buffDebuff.getDescription());
    }

    @Test
    public void testToJson() {
        JSONObject json = buffDebuff.toJson();

        // Check each field in the JSON object
        assertEquals("Strength Boost", json.getString("name"));
        assertEquals("STRENGTH", json.getString("effectAbility")); // Assuming that AbilityType.STRENGTH.toString() returns "STRENGTH"
        assertEquals(2, json.getInt("effectMagnitude"));
        assertEquals(5, json.getInt("duration"));
    }

}
