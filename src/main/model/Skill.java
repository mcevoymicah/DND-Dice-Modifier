package model;

public class Skill {

    private String name;                          // Name of skill (eg. Stealth)
    private AbilityScore associatedAbility;       // The associated ability score (e.g., Dexterity for Stealth)
    private boolean isProficient;                //  Whether the character is proficient in the skill.

    // EFFECTS: constructs a skill with name, associated ability, and if the character is proficient or not

    public Skill(String name, AbilityScore associatedAbility, boolean isProficient) {
        this.name = name;
        this.associatedAbility = associatedAbility;
        this.isProficient = isProficient;
    }

}

    // getters

//    public String getName() {
//        return this.name;
//    }

//    public int getAssociatedAbility() {
//        return this.associatedAbility;
//    }

//    public int getIsProficient() {
//        return this.isProficient;
//    }
//}
