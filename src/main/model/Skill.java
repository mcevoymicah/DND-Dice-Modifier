package model;

import org.json.JSONObject;

// Represents a specific skill in the D&D 5E universe.
// Contains skill's type, associated ability, and character's proficiency in that skill.

public class Skill {

    private final SkillType type;                       // Name of skill (eg. Stealth)
    private final AbilityScore associatedAbility;       // The associated ability score (e.g., Dexterity for Stealth)
    private boolean isProficient;                       //  Whether the character is proficient in the skill.

    // EFFECTS: constructs a skill with name, associated ability, and if the character is proficient or not

    public Skill(SkillType name, AbilityScore associatedAbility, boolean isProficient) {
        this.type = name;
        this.associatedAbility = associatedAbility;
        this.isProficient = isProficient;
    }



    // Getters

    public SkillType getType() {
        return this.type;
    }

    public AbilityScore getAssociatedAbility() {
        return this.associatedAbility;
    }

    public boolean getIsProficient() {
        return this.isProficient;
    }


    // Setters

    // MODIFIES: this
    // EFFECTS: sets the proficiency status for this skill
    public void setProficiency(boolean proficiency) {
        this.isProficient = proficiency;
    }


    // Other

    // EFFECTS: returns the total skill modifier (ability modifier + proficiency bonus if proficient)
    public int getTotalSkillModifier() {
        int proficiencyBonus = (isProficient) ? 2 : 0;
        return associatedAbility.getModifier() + proficiencyBonus;
    }

    // EFFECTS: returns a string description of the skill, its associated ability, and if it's proficient
    public String describeSkill() {
        String proficiencyStatus = isProficient ? "Proficient" : "Not proficient";
        return type.toString() + " (Associated with "
                + associatedAbility.getType() + "): " + proficiencyStatus;
    }


    // Code influence by the JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    // EFFECTS: Returns the AbilityType associated with the given SkillType.
    public static AbilityType getAssociatedAbilityBySkill(SkillType skill) {
        return skill.getAssociatedAbility();
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", type.toString());
        json.put("associatedAbility", associatedAbility.toJson());
        json.put("isProficient", isProficient);
        return json;
    }

}

