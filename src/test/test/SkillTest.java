package test;

import model.AbilityScore;
import model.AbilityType;
import model.Skill;
import model.SkillType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SkillTest {

    private Skill stealthSkill;
    private AbilityScore dexterityScore;

    @BeforeEach
    void runBefore() {
        dexterityScore = new AbilityScore(AbilityType.DEXTERITY, 14);  // Modifier of +2
        stealthSkill = new Skill(SkillType.STEALTH, dexterityScore, true);
    }

    @Test
    public void testConstructor() {
        assertEquals(SkillType.STEALTH, stealthSkill.getType());
        assertEquals(dexterityScore, stealthSkill.getAssociatedAbility());
        assertTrue(stealthSkill.getIsProficient());
    }

    @Test
    public void testSetProficiency() {
        stealthSkill.setProficiency(false);
        assertFalse(stealthSkill.getIsProficient());
    }

    @Test
    public void testGetTotalSkillModifierWithProficiency() {
        assertEquals(4, stealthSkill.getTotalSkillModifier());  // +2 (modifier) + 2 (proficiency)
    }

    @Test
    public void testGetTotalSkillModifierWithoutProficiency() {
        stealthSkill.setProficiency(false);
        assertEquals(2, stealthSkill.getTotalSkillModifier());  // +2 (modifier) without proficiency bonus
    }

    @Test
    public void testDescribeSkill() {
        String expectedDescription = "STEALTH (Associated with DEXTERITY): Proficient";
        assertEquals(expectedDescription, stealthSkill.describeSkill());
    }
}
