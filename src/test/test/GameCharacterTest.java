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

    @BeforeEach
    public void setup() {
        testAbility = new AbilityScore(AbilityType.STRENGTH, 10);
        testBuff = new BuffDebuff("Strength Boost", AbilityType.STRENGTH, 2, 5);
        testSkill = new Skill(SkillType.ACROBATICS, testAbility, true);
        testRoll = new Roll("Test Roll", 20, 5);
        RollHistory testRollHistory = new RollHistory(new ArrayList<>());

        character = new GameCharacter("TestChar");


    }

    @Test
    public void testGetName() {
        assertEquals("TestChar", character.getName());
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
    public void testUpdateNonExistingAbilityScore() {
        character.getAbilityScores().clear();
        character.updateAbilityScore(AbilityType.STRENGTH, 15);

        int updatedStrengthScore = -1;
        for (AbilityScore ability : character.getAbilityScores()) {
            if (ability.getType() == AbilityType.STRENGTH) {
                updatedStrengthScore = ability.getScore();
                break;
            }
        }

        assertEquals(-1, updatedStrengthScore);  // As there's no such score in the list
    }

    @Test
    public void testUpdateAbilityScoreMultipleNonMatchingAbilities() {
        AbilityScore dexterity = new AbilityScore(AbilityType.DEXTERITY, 12);
        AbilityScore intelligence = new AbilityScore(AbilityType.INTELLIGENCE, 14);

        character.getAbilityScores().add(dexterity);
        character.getAbilityScores().add(intelligence);

        character.updateAbilityScore(AbilityType.CHARISMA, 16);

        int updatedCharismaScore = -1;
        for (AbilityScore ability : character.getAbilityScores()) {
            if (ability.getType() == AbilityType.CHARISMA) {
                updatedCharismaScore = ability.getScore();
                break;
            }
        }

        assertEquals(16, updatedCharismaScore); // As Charisma is not in the list
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
    public void testIsProficientInSkillWithEmptySkills() {
        character.getSkills().clear();
        assertFalse(character.isProficientInSkill(SkillType.ACROBATICS));
    }

    @Test
    public void testIsProficientInSkillMultipleNonMatchingSkills() {
        Skill athletics = new Skill(SkillType.ATHLETICS, testAbility, false);
        Skill stealth = new Skill(SkillType.STEALTH, testAbility, false);

        character.getSkills().add(athletics);
        character.getSkills().add(stealth);

        assertFalse(character.isProficientInSkill(SkillType.INVESTIGATION));
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

    @Test
    public void testCalculateTotalModifierForSkillWithoutRelatedAbility() {
        character.getAbilityScores().clear();  // Removing all ability scores
        character.addSkill(testSkill);
        int expectedModifier = 0; // Should return 0 since there's no related ability score
        assertEquals(expectedModifier, character.calculateTotalModifierForSkill(SkillType.ACROBATICS));
    }

    @Test
    public void testCalculateTotalModifierForSkillNonExistentSkill() {
        character.getSkills().clear();
        int modifier = character.calculateTotalModifierForSkill(SkillType.ACROBATICS);
        assertEquals(0, modifier);
    }


    @Test
    public void testCalculateTotalModifierForSkillWithNonExistingSkill() {
        character.getSkills().clear();
        assertEquals(0, character.calculateTotalModifierForSkill(SkillType.ACROBATICS));
    }


    @Test
    public void testHasAbilityWithExistingAbility() {
        character.getAbilityScores().add(testAbility);
        assertTrue(character.hasAbility(AbilityType.STRENGTH));
    }

    @Test
    public void testHasAbilityWithNonExistingAbility() {
        character.getAbilityScores().clear();
        assertFalse(character.hasAbility(AbilityType.STRENGTH));
    }

    @Test
    public void testHasAbilityWithMultipleAbilities() {
        AbilityScore dexterity = new AbilityScore(AbilityType.DEXTERITY, 12);
        AbilityScore intelligence = new AbilityScore(AbilityType.INTELLIGENCE, 14);

        character.getAbilityScores().add(testAbility);
        character.getAbilityScores().add(dexterity);
        character.getAbilityScores().add(intelligence);

        assertTrue(character.hasAbility(AbilityType.STRENGTH));
        assertTrue(character.hasAbility(AbilityType.DEXTERITY));
        assertTrue(character.hasAbility(AbilityType.INTELLIGENCE));
    }

    @Test
    public void testHasAbilityWithEmptyAbilities() {
        character.getAbilityScores().clear();
        assertFalse(character.hasAbility(AbilityType.STRENGTH));
    }

}

