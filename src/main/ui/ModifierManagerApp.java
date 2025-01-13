package ui;

import model.*;
import persistence.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import java.io.*;

// ModifierManagerApp handles the creation, management, and persistence of GameCharacter data.
// This class initializes characters, validates input data, manages character abilities, buffs,
// debuffs, skills, and proficiencies, supports rolling for checks and calculating modifiers
// based on character abilities and active effects.



public class ModifierManagerApp {
    private GameCharacter character;
    private static final String JSON_STORE = "./data/character.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: constructs a ModifierManagerApp that deals with data persistence
    public ModifierManagerApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // Main Menu

    // REQUIRES: name is non-null and non-empty, level is positive
    // MODIFIES: this
    // EFFECTS: Initializes the character with the given name, level, and ability scores.
    public void initCharacter(String name, int level, Map<AbilityType, Integer> abilities) {
        character = new GameCharacter(name, level);
        for (AbilityType ability : abilities.keySet()) {
            character.updateAbilityScore(ability, abilities.get(ability));
        }
    }

    // Validates the provided character name.
    // REQUIRES: name is a non-null String
    // EFFECTS: Throws IllegalArgumentException if name is empty, otherwise returns the name.
    public String validateName(String name) throws IllegalArgumentException {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name field cannot be empty.");
        }
        return name;
    }

    // Validates the provided level input.
    // REQUIRES: levelStr is a non-null String
    // EFFECTS: Throws IllegalArgumentException if levelStr is not a valid integer,
    //          otherwise returns the parsed integer level.
    public int validateLevel(String levelStr) throws IllegalArgumentException {
        if (levelStr.isEmpty() || !levelStr.matches("\\d+")) {
            throw new IllegalArgumentException("Level must be a numeric value.");
        }
        return Integer.parseInt(levelStr);
    }

    // Validates the ability scores from the provided map.
    // REQUIRES: abilityScores is a non-null Map with AbilityType as keys and String as values
    // EFFECTS: Throws IllegalArgumentException if any value in abilityScores is not a valid integer,
    //          otherwise returns a Map with parsed integer values.
    public Map<AbilityType, Integer> validateAbilities(Map<AbilityType, String> abilityScores)
            throws IllegalArgumentException {
        Map<AbilityType, Integer> validatedAbilities = new HashMap<>();
        for (AbilityType ability : AbilityType.values()) {
            String scoreStr = abilityScores.get(ability);
            if (scoreStr.isEmpty() || !scoreStr.matches("\\d+")) {
                throw new IllegalArgumentException(ability.toString() + " must be a numeric value.");
            }
            validatedAbilities.put(ability, Integer.parseInt(scoreStr));
        }
        return validatedAbilities;
    }

    // Buffs/Debuffs

    // MODIFIES: this, character
    // EFFECTS: Receives the details of a new buff or debuff and
    //          adds it to the character's active buffs/debuffs list.
    public void addBuffDebuff(String name, AbilityType effectAbility, int effectMagnitude, int duration) {
        if (character == null) {
            throw new IllegalArgumentException("Character is not initialized.");
        } else {
            BuffDebuff newBuffDebuff = new BuffDebuff(name, effectAbility, effectMagnitude, duration);
            character.addBuffDebuff(newBuffDebuff);
        }

    }


    // Skills & Proficiencies

    // MODIFIES: character
    // EFFECTS: Tries to add a new skill with proficiency to the character. Returns true if successful, false otherwise.
    public boolean addSkill(JComboBox<SkillType> skillTypeComboBox, JCheckBox proficiencyCheckBox) {
        SkillType chosenSkillType = (SkillType) skillTypeComboBox.getSelectedItem();
        AbilityScore associatedAbility = findAssociatedAbility(chosenSkillType);

        if (associatedAbility == null) {
            return false;
        }

        boolean isProficient = proficiencyCheckBox.isSelected();
        Skill newSkill = new Skill(chosenSkillType, associatedAbility, isProficient);
        character.addSkill(newSkill);

        return true;
    }

    // EFFECTS: Finds and returns the associated ability with the skill.
    private AbilityScore findAssociatedAbility(SkillType chosenSkillType) {
        AbilityType associatedAbilityType = Skill.getAssociatedAbilityBySkill(chosenSkillType);
        for (AbilityScore ability : character.getAbilityScores()) {
            if (ability.getType() == associatedAbilityType) {
                return ability;
            }
        }
        return null;
    }

    // Character Details

    // EFFECTS: Returns a character's name
    public String getCharacterName() {
        return character.getName();
    }

    // EFFECTS: Returns a character's level
    public int getCharacterLevel() {
        return character.getLevel();
    }

    // EFFECTS: Returns a string representation of the character's ability scores.
    public String getAbilityScoresText() {
        StringBuilder text = new StringBuilder("\nAbility Scores:\n");
        for (AbilityScore score : character.getAbilityScores()) {
            text.append(score.getType()).append(": ").append(score.getScore()).append("\n");
        }
        return text.toString();
    }

    // EFFECTS: Returns a string representation of the character's active buffs and debuffs.
    public String getBuffsDebuffsText() {
        StringBuilder text = new StringBuilder("\nActive Buffs/Debuffs:\n");
        if (character.getActiveBuffsDebuffs().isEmpty()) {
            text.append("None\n");
        } else {
            for (BuffDebuff buffDebuff : character.getActiveBuffsDebuffs()) {
                text.append(buffDebuff.getDescription()).append("\n");
            }
        }
        return text.toString();
    }

    // EFFECTS: Returns a string representation of the character's skills and their proficiency.
    public String getSkillsProficiencyText() {
        StringBuilder text = new StringBuilder("\nSkills Proficiency:\n");
        for (Skill skill : character.getSkills()) {
            text.append(skill.describeSkill()).append("\n");
        }
        return text.toString();
    }

    // EFFECTS: Returns a string representation of the character's recent roll history.
    public String getRecentRollsText() {
        StringBuilder text = new StringBuilder("\nRecent Roll History:\n");
        List<Roll> rolls = character.getRollHistory().getRollList();
        if (rolls.isEmpty()) {
            text.append("No rolls made yet.\n");
        } else {
            for (int i = rolls.size() - 1; i >= Math.max(0, rolls.size() - 5); i--) {
                text.append(rolls.get(i).rollDescription()).append("\n");
            }
        }
        return text.toString();
    }

    // Roll for Checks

    // EFFECTS:  Rolls the dice for the chosen skill, calculates the skill modifier,
    //           and prints the original dice roll, applied modifiers, and the total result.
    // EFFECTS: Performs a skill check using the specified skill type and dice roll.
    public void rollForSkillCheck(SkillType chosenSkill, int diceRoll) {
        int modifier = calculateSkillModifier(chosenSkill);
        int totalResult = diceRoll + modifier;

        JOptionPane.showMessageDialog(null, "Original Roll: " + diceRoll
                + "\nApplied Modifiers: " + modifier
                + "\nTotal Result: " + totalResult);

        Roll newRoll = new Roll(chosenSkill.name() + " check", diceRoll, modifier);
        character.getRollHistory().addRoll(newRoll);

        // Log this event
        EventLog.getInstance().logEvent(new Event("Skill check rolled for "
                + chosenSkill + ": " + totalResult));

        character.updateBuffsDebuffsDuration();

    }


    // EFFECTS: Performs an ability check using the specified ability type and dice roll.
    public void rollForAbilityCheck(AbilityType chosenAbility, int diceRoll) {
        int modifier = calculateAbilityModifier(chosenAbility);
        int totalResult = diceRoll + modifier;

        JOptionPane.showMessageDialog(null, "Original Roll: " + diceRoll
                + "\nApplied Modifiers: " + modifier
                + "\nTotal Result: " + totalResult);

        Roll newRoll = new Roll(chosenAbility.name() + " check", diceRoll, modifier);
        character.getRollHistory().addRoll(newRoll);

        EventLog.getInstance().logEvent(new Event(chosenAbility
                + " check rolled: " + totalResult));

        character.updateBuffsDebuffsDuration();
    }


    // EFFECTS:  If the user chooses automatic roll, it simulates a roll of a 20-sided dice.
    //           If the user chooses manual input, it prompts the user to input their own dice roll.
    //           Returns the dice roll value.
    public int rollDice(boolean isAutomatic) {
        if (isAutomatic) {
            return (int) (Math.random() * 20) + 1; // 20 sided dice roll
        } else {
            String rollInput = JOptionPane.showInputDialog("Input your roll:");
            return Integer.parseInt(rollInput);
        }
    }

    // EFFECTS:  Returns the total modifier for a given skill, considering both the associated
    //           ability score's modifier and a proficiency bonus if the character is proficient in the skill.
    //           Also takes into account active buffs and debuffs on the character.
    public int calculateSkillModifier(SkillType skill) {
        AbilityType associatedAbility = Skill.getAssociatedAbilityBySkill(skill);
        int abilityModifier = getModifierForAbility(associatedAbility);

        boolean isProficient = character.isProficientInSkill(skill);
        int bonus = 1 + (character.getLevel() + 3) / 4;
        int proficiencyBonus = isProficient ? bonus : 0;

        int buffDebuffModifier = calculateBuffDebuffModifier(associatedAbility);

        return abilityModifier + proficiencyBonus + buffDebuffModifier;
    }

    // EFFECTS:  Returns the modifier associated with the provided ability type.
    //           Also takes into account active buffs and debuffs on the character.
    public int calculateAbilityModifier(AbilityType ability) {
        int abilityModifier = getModifierForAbility(ability);

        int buffDebuffModifier = calculateBuffDebuffModifier(ability);

        return abilityModifier + buffDebuffModifier;
    }

    // EFFECTS:  Returns the cumulative modifier for the specified ability type based on active buffs and debuffs.
    public int calculateBuffDebuffModifier(AbilityType ability) {
        int totalModifier = 0;

        for (BuffDebuff bd : character.getActiveBuffsDebuffs()) {
            if (bd.getEffectAbility() == ability) {
                totalModifier += bd.getEffectMagnitude();
            }
        }

        return totalModifier;
    }


    // EFFECTS:  Returns the modifier for the specified ability type.
    //           If the ability is not found among the character's ability scores, returns 0.
    public int getModifierForAbility(AbilityType ability) {
        for (AbilityScore score : character.getAbilityScores()) {
            if (score.getType() == ability) {
                return score.getModifier();
            }
        }
        return 0;
    }

    // Save/Loading

    public void saveCharacter() {
        try {
            jsonWriter.open();
            jsonWriter.write(character);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null,
                    "Saved " + character.getName() + " to " + JSON_STORE);

            EventLog.getInstance().logEvent(new Event("Character saved: "
                    + character.getName()));

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to write to file: " + JSON_STORE);

            EventLog.getInstance().logEvent(new Event("Failed to save character: "
                    + character.getName()));
        }
    }

    public boolean loadCharacter() {
        try {
            character = jsonReader.read();
            JOptionPane.showMessageDialog(null, "Loaded " + character.getName() + " from "
                    + JSON_STORE);

            // Log the character load event
            EventLog.getInstance().logEvent(new Event("Character loaded: " + character.getName()));

            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE);

            // Log the character load error
            EventLog.getInstance().logEvent(new Event("Failed to load character from file."));

            return false;
        }
    }



}
