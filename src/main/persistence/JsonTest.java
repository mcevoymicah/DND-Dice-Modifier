package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    // Helper method to validate AbilityScore contents
    protected void checkAbilityScore(AbilityType type, int score, AbilityScore abilityScore) {
        assertEquals(type, abilityScore.getType());
        assertEquals(score, abilityScore.getScore());
    }

    // Helper method to validate Skill contents
    protected void checkSkill(SkillType type, boolean isProficient, Skill skill) {
        assertEquals(type, skill.getType());
        assertEquals(isProficient, skill.getIsProficient());
    }

    // Helper method to validate GameCharacter contents
    protected void checkGameCharacter(String name, GameCharacter expected, GameCharacter actual) {
        assertEquals(name, actual.getName());
        assertEquals(expected.getAbilityScores().size(), actual.getAbilityScores().size());
        assertEquals(expected.getSkills().size(), actual.getSkills().size());
        assertEquals(expected.getActiveBuffsDebuffs().size(), actual.getActiveBuffsDebuffs().size());
    }

    // Helper method to validate RollHistory contents
    protected void checkRoll(String type, int baseResult, int appliedModifier, int finalOutcome, Roll roll) {
        assertEquals(type, roll.getType());
        assertEquals(baseResult, roll.getBaseResult());
        assertEquals(appliedModifier, roll.getAppliedModifier());
        assertEquals(finalOutcome, roll.getFinalOutcome());
    }
}
