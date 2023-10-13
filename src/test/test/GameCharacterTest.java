package test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameCharacterTest {

    private GameCharacter character;
    private AbilityScore testAbility;
    private BuffDebuff testBuff;
    private Skill testSkill;
    private Roll testRoll;
    private RollHistory testRollHistory;

    @BeforeEach
    public void setup() {
        testAbility = new AbilityScore(AbilityType.STRENGTH, 10);
        testBuff = new BuffDebuff("Strength Boost", AbilityType.STRENGTH, 2, 5);
        testSkill = new Skill(SkillType.ACROBATICS, testAbility, true);
        testRoll = new Roll("Test Roll", 20, 5);
        testRollHistory = new RollHistory(new ArrayList<>());

        character = new GameCharacter("TestChar");


    }

    @Test
    public void testUpdateAbilityScoreValid() {
        character.updateAbilityScore(AbilityType.STRENGTH, 15);

        int updatedStrengthScore = 10;  // default value
        for (AbilityScore ability : character.getAbilityScores()) {
            if (ability.getType() == AbilityType.STRENGTH) {
                updatedStrengthScore = ability.getScore();
                break;
            }
        }

        assertEquals(15, updatedStrengthScore);
    }


    @Test
    public void testAddBuffDebuff() {
        character.addBuffDebuff(testBuff);
        assertTrue(character.getActiveBuffsDebuffs().contains(testBuff));
    }

    @Test
    public void testRemoveBuffDebuff() {
        character.addBuffDebuff(testBuff);
        character.removeBuffDebuff(testBuff);
        assertFalse(character.getActiveBuffsDebuffs().contains(testBuff));
    }

    @Test
    public void testClearBuffsDebuffs() {
        character.addBuffDebuff(testBuff);
        character.clearBuffsDebuffs();
        assertTrue(character.getActiveBuffsDebuffs().isEmpty());
    }

    @Test
    public void testAddSkill() {
        character.addSkill(testSkill);
        assertTrue(character.getSkills().contains(testSkill));
    }

    @Test
    public void testRemoveSkill() {
        character.addSkill(testSkill);
        character.removeSkill(testSkill);
        assertFalse(character.getSkills().contains(testSkill));
    }

    @Test
    public void testIsProficientInSkillTrue() {
        character.addSkill(testSkill);
        assertTrue(character.isProficientInSkill(SkillType.ACROBATICS));
    }

    @Test
    public void testIsProficientInSkillFalse() {
        assertFalse(character.isProficientInSkill(SkillType.ACROBATICS));
    }

    @Test
    public void testAddRoll() {
        character.addRoll(testRoll);
        assertTrue(character.getRollHistory().getRollList().contains(testRoll));
    }

    @Test
    public void testCalculateTotalModifierForSkill() {
        character.addSkill(testSkill);
        character.getAbilityScores().add(testAbility);
        int expectedModifier = testSkill.getTotalSkillModifier() + testAbility.getModifier();
        assertEquals(expectedModifier, character.calculateTotalModifierForSkill(SkillType.ACROBATICS));
    }
}

