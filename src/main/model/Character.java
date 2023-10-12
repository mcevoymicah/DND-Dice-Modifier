package model;

import java.util.List;

public class Character {
    private String name;
    private List<AbilityScore> abilityScores;       // A list of the six main ability scores.
    private List<BuffDebuff> activeBuffsDebuffs;    // A list of currently active buffs and debuffs.
    private List<Skill> skills;                     // A list of skills in the character is proficient in.
    private RollHistory rollHistory;                // A record of all rolls made by the character.

    // EFFECTS: constructs a character with name, ability scores, active buffs/debuffs,
    //          skills and roll history

    public Character(String name, List<AbilityScore> abilityScores, List<BuffDebuff> activeBuffsDebuffs,
                     List<Skill> skills, RollHistory rollHistory) {
        this.name = name;
        this.abilityScores = abilityScores;
        this.activeBuffsDebuffs = activeBuffsDebuffs;
        this.skills = skills;
        this.rollHistory = rollHistory;
    }

    // getters

    public String getName() {
        return this.name;
    }

    public List<AbilityScore> getAbilityScores() {
        return this.abilityScores;
    }

    public List<BuffDebuff> getActiveBuffsDebuffs() {
        return this.activeBuffsDebuffs;
    }

    public List<Skill> getSkills() {
        return null;
    }

    public RollHistory getRollHistory() {
        return this.rollHistory;
    }


    // Ability Scores

    // REQUIRES: abilityName to be one of the standard D&D 5E ability names,
    //           and newValue to be in the range typically allowed for ability scores (1-20)
    // MODIFIES: this
    // EFFECTS:  Updates the specified ability score to the new value
    //           and returns true if the update was successful; false otherwise.
    public boolean updateAbilityScore(String abilityName, int newValue) {
        // stub
        return false;
    }


    // Buffs and Debuffs

    // REQUIRES: buffOrDebuff to not be null
    // MODIFIES: this
    // EFFECTS:  Adds the provided buff or debuff to the character's active list.
    public void addBuffDebuff(BuffDebuff buffOrDebuff) {
        // stub
    }

    // REQUIRES: buffOrDebuff to be in the activeBuffsDebuffs list
    // MODIFIES: this
    // EFFECTS:  Removes the specified buff or debuff from the character's active list.
    public void removeBuffDebuff(BuffDebuff buffOrDebuff) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS:  Clears all active buffs and debuffs from the character.
    public void clearBuffsDebuffs() {
        // stub
    }


    // Skills

    // REQUIRES: skill to not be null and not already in the skills list
    // MODIFIES: this
    // EFFECTS:  Adds the provided skill to the character's list of skills.
    public void addSkill(Skill skill) {
        // stub
    }

    // REQUIRES: skill to be in the skills list
    // MODIFIES: this
    // EFFECTS:  Removes the specified skill from the character's list.
    public void removeSkill(Skill skill) {
        // stub
    }

    // REQUIRES: skillName to correspond to a known skill
    // EFFECTS:  Returns true if the character is proficient in the specified skill, false otherwise.
    public boolean isProficientInSkill(String skillName) {
        // stub
        return false;
    }


    // Roll History

    // REQUIRES: roll to not be null
    // MODIFIES: this
    // EFFECTS:  Adds the provided roll to the character's roll history.
    public void addRoll(Roll roll) {
        // stub
    }

    // EFFECTS:  Returns the most recent roll from the character's roll history.
    public Roll getLastRoll() {
        // stub
        return new Roll();
    }


    // Other

    // REQUIRES: skillName to correspond to a known skill
    // EFFECTS:  Calculates and returns the total modifier for the specified skill,
    //           considering ability scores, buffs, debuffs, and proficiencies.
    public int calculateTotalModifierForSkill(String skillName) {
        // stub
        return 0;
    }
}

