package model;

// Represents a specific skill in the D&D 5E universe.
// Contains skill's type, associated ability, and character's proficiency in that skill.

public class Skill {

    private final SkillType type;                       // Name of skill (eg. Stealth)
    private final AbilityScore associatedAbility;       // The associated ability score (e.g., Dexterity for Stealth)
    private boolean isProficient;                 //  Whether the character is proficient in the skill.

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


    // EFFECTS: Returns the AbilityType associated with the given SkillType.
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public static AbilityType getAssociatedAbilityBySkill(SkillType skill) {
        switch (skill) {
            case ATHLETICS:
                return AbilityType.STRENGTH;
            case ACROBATICS:
            case SLEIGHT_OF_HAND:
            case STEALTH:
                return AbilityType.DEXTERITY;
            case ARCANA:
            case HISTORY:
            case INVESTIGATION:
            case NATURE:
            case RELIGION:
                return AbilityType.INTELLIGENCE;
            case ANIMAL_HANDLING:
            case INSIGHT:
            case MEDICINE:
            case PERCEPTION:
            case SURVIVAL:
                return AbilityType.WISDOM;
            case DECEPTION:
            case INTIMIDATION:
            case PERFORMANCE:
            case PERSUASION:
                return AbilityType.CHARISMA;
            default:
                return null;
        }
    }
}

