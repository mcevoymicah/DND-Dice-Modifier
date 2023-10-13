package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbilityScoreTest {

    private AbilityScore abilityScore;

    @BeforeEach
    void runBefore() {
        abilityScore = new AbilityScore(AbilityType.STRENGTH, 10);
    }

    @Test
    public void testConstructor() {
        assertEquals(AbilityType.STRENGTH, abilityScore.getName());
        assertEquals(10, abilityScore.getScore());
        assertEquals(0, abilityScore.getModifier()); // As per D&D 5E rules
    }

    @Test
    public void testConstructorEdgeCases() {
        abilityScore = new AbilityScore(AbilityType.STRENGTH, 1);
        assertEquals(-5, abilityScore.getModifier()); // As per D&D 5E rules

        abilityScore = new AbilityScore(AbilityType.STRENGTH, 30);
        assertEquals(10, abilityScore.getModifier()); // As per D&D 5E rules
    }

    @Test
    public void testSetScoreNormal() {
        abilityScore.setScore(12);
        assertEquals(12, abilityScore.getScore());
        assertEquals(1, abilityScore.getModifier()); // Score of 12 should have a modifier of +1.
    }

    @Test
    public void testSetScoreEdgeCases() {
        abilityScore.setScore(3);
        assertEquals(3, abilityScore.getScore());
        assertEquals(-4, abilityScore.getModifier()); // Score of 3 should have a modifier of -4.
    }

    @Test
    public void testSetModifier() {
        abilityScore.setModifier(-1);
        assertEquals(-1, abilityScore.getModifier());
    }

    @Test
    public void testCalculateModifierNormal() {
        abilityScore.setScore(14);
        assertEquals(2, abilityScore.getModifier()); // Score of 14 should have a modifier of +2.
    }

    @Test
    public void testCalculateModifierEdgeCases() {
        abilityScore.setScore(29);
        assertEquals(9, abilityScore.getModifier()); // Score of 29 should have a modifier of +9.
    }

    @Test
    public void testDisplayAsString() {
        assertEquals("STRENGTH: 10 (Modifier: 0)", abilityScore.displayAsString());
    }
}
