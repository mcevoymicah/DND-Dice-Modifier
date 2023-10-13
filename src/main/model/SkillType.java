package model;

public enum SkillType {
    ATHLETICS(AbilityType.STRENGTH),
    ACROBATICS(AbilityType.DEXTERITY),
    SLEIGHT_OF_HAND(AbilityType.DEXTERITY),
    STEALTH(AbilityType.DEXTERITY),
    ARCANA(AbilityType.INTELLIGENCE),
    HISTORY(AbilityType.INTELLIGENCE),
    INVESTIGATION(AbilityType.INTELLIGENCE),
    NATURE(AbilityType.INTELLIGENCE),
    RELIGION(AbilityType.INTELLIGENCE),
    ANIMAL_HANDLING(AbilityType.WISDOM),
    INSIGHT(AbilityType.WISDOM),
    MEDICINE(AbilityType.WISDOM),
    PERCEPTION(AbilityType.WISDOM),
    SURVIVAL(AbilityType.WISDOM),
    DECEPTION(AbilityType.CHARISMA),
    INTIMIDATION(AbilityType.CHARISMA),
    PERFORMANCE(AbilityType.CHARISMA),
    PERSUASION(AbilityType.CHARISMA);

    private final AbilityType associatedAbility;

    SkillType(AbilityType associatedAbility) {
        this.associatedAbility = associatedAbility;
    }

    public AbilityType getAssociatedAbility() {
        return associatedAbility;
    }
}

