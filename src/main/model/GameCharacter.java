package model;

import java.util.ArrayList;
import java.util.List;

// Represents a player's character in D&D 5E.
// Contains all the details of a character that are essential for modifier calculations

public class GameCharacter {
    private final String name;
    private final List<AbilityScore> abilityScores;       // A list of the six main ability scores.
    private final List<BuffDebuff> activeBuffsDebuffs;    // A list of currently active buffs and debuffs.
    private final List<Skill> skills;                     // A list of skills the character is proficient in.
    private final RollHistory rollHistory;                // A record of all rolls made by the character.

    // EFFECTS: constructs a character with a name and initializes default lists for attributes.
    public GameCharacter(String name) {
        this.name = name;
        this.abilityScores = new ArrayList<>();
        this.activeBuffsDebuffs = new ArrayList<>();
        this.skills = new ArrayList<>();
        this.rollHistory = new RollHistory();

        // Initialize all ability scores with default values (e.g., 10)
        for (AbilityType type : AbilityType.values()) {
            this.abilityScores.add(new AbilityScore(type, 10));
        }
    }

    // Getters

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
        return this.skills;
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
    public void updateAbilityScore(AbilityType abilityType, int newScore) {
        for (AbilityScore ability : abilityScores) {
            if (ability.getType() == abilityType) {
                ability.setScore(newScore);
                break;
            }
        }
    }


    // Buffs and Debuffs

    // REQUIRES: buffOrDebuff to not be null
    // MODIFIES: this
    // EFFECTS:  Adds the provided buff or debuff to the character's active list.
    public void addBuffDebuff(BuffDebuff buffOrDebuff) {
        activeBuffsDebuffs.add(buffOrDebuff);
    }

    // REQUIRES: buffOrDebuff to be in the activeBuffsDebuffs list
    // MODIFIES: this
    // EFFECTS:  Removes the specified buff or debuff from the character's active list.
    public void removeBuffDebuff(BuffDebuff buffOrDebuff) {
        activeBuffsDebuffs.remove(buffOrDebuff);
    }

    // MODIFIES: this
    // EFFECTS:  Clears all active buffs and debuffs from the character.
    public void clearBuffsDebuffs() {
        activeBuffsDebuffs.clear();
    }


    // Skills

    // REQUIRES: skill to not be null and not already in the skills list
    // MODIFIES: this
    // EFFECTS:  Adds the provided skill to the character's list of skills.
    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    // REQUIRES: skill to be in the skills list
    // MODIFIES: this
    // EFFECTS:  Removes the specified skill from the character's list.
    public void removeSkill(Skill skill) {
        skills.remove(skill);
    }

    // REQUIRES: skillName to correspond to a known skill
    // EFFECTS:  Returns true if the character is proficient in the specified skill, false otherwise.
    public boolean isProficientInSkill(SkillType skillType) {
        for (Skill skill : skills) {
            if (skill.getType() == skillType && skill.getIsProficient()) {
                return true;
            }
        }
        return false;
    }


    // Roll History

    // REQUIRES: roll to not be null
    // MODIFIES: this
    // EFFECTS:  Adds the provided roll to the character's roll history.
    public void addRoll(Roll roll) {
        rollHistory.addRoll(roll);
    }


    // Other

    // EFFECTS:  Calculates and returns the total modifier for the specified skill,
    //           considering ability scores, buffs, debuffs, and proficiencies.
    public int calculateTotalModifierForSkill(SkillType skillType) {
        for (Skill skill : skills) {
            if (skill.getType() == skillType) {
                // If the character doesn't have the associated ability score,
                // return 0 regardless of proficiency status
                if (!this.hasAbility(skill.getAssociatedAbility().getType())) {
                    return 0;
                }

                return skill.getTotalSkillModifier();
            }
        }
        return 0;
    }

    // EFFECTS: Checks if the character has the given ability
    public boolean hasAbility(AbilityType abilityType) {
        for (AbilityScore ability : abilityScores) {
            if (ability.getType() == abilityType) {
                return true;
            }
        }
        return false;
    }

}

