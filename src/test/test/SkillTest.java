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

    @Test
    public void testDescribeSkillNotProficient() {
        stealthSkill.setProficiency(false);
        String expectedDescription = "STEALTH (Associated with DEXTERITY): Not proficient";
        assertEquals(expectedDescription, stealthSkill.describeSkill());
    }

    @Test
    public void testGetAssociatedAbilityBySkill() {
        // Test for Strength-associated skills
        assertEquals(AbilityType.STRENGTH, Skill.getAssociatedAbilityBySkill(SkillType.ATHLETICS));

        // Test for Dexterity-associated skills
        assertEquals(AbilityType.DEXTERITY, Skill.getAssociatedAbilityBySkill(SkillType.ACROBATICS));
        assertEquals(AbilityType.DEXTERITY, Skill.getAssociatedAbilityBySkill(SkillType.SLEIGHT_OF_HAND));
        assertEquals(AbilityType.DEXTERITY, Skill.getAssociatedAbilityBySkill(SkillType.STEALTH));

        // Test for Intelligence-associated skills
        assertEquals(AbilityType.INTELLIGENCE, Skill.getAssociatedAbilityBySkill(SkillType.ARCANA));
        assertEquals(AbilityType.INTELLIGENCE, Skill.getAssociatedAbilityBySkill(SkillType.HISTORY));
        assertEquals(AbilityType.INTELLIGENCE, Skill.getAssociatedAbilityBySkill(SkillType.INVESTIGATION));
        assertEquals(AbilityType.INTELLIGENCE, Skill.getAssociatedAbilityBySkill(SkillType.NATURE));
        assertEquals(AbilityType.INTELLIGENCE, Skill.getAssociatedAbilityBySkill(SkillType.RELIGION));

        // Test for Wisdom-associated skills
        assertEquals(AbilityType.WISDOM, Skill.getAssociatedAbilityBySkill(SkillType.ANIMAL_HANDLING));
        assertEquals(AbilityType.WISDOM, Skill.getAssociatedAbilityBySkill(SkillType.INSIGHT));
        assertEquals(AbilityType.WISDOM, Skill.getAssociatedAbilityBySkill(SkillType.MEDICINE));
        assertEquals(AbilityType.WISDOM, Skill.getAssociatedAbilityBySkill(SkillType.PERCEPTION));
        assertEquals(AbilityType.WISDOM, Skill.getAssociatedAbilityBySkill(SkillType.SURVIVAL));

        // Test for Charisma-associated skills
        assertEquals(AbilityType.CHARISMA, Skill.getAssociatedAbilityBySkill(SkillType.DECEPTION));
        assertEquals(AbilityType.CHARISMA, Skill.getAssociatedAbilityBySkill(SkillType.INTIMIDATION));
        assertEquals(AbilityType.CHARISMA, Skill.getAssociatedAbilityBySkill(SkillType.PERFORMANCE));
        assertEquals(AbilityType.CHARISMA, Skill.getAssociatedAbilityBySkill(SkillType.PERSUASION));
    }


}
