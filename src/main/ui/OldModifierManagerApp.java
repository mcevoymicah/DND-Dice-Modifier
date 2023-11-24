package ui;

import model.*;
import persistence.*;

import java.util.List;
import java.util.Scanner;

import java.io.*;

// This class represents the main UI for managing character modifiers in a Dungeons & Dragons 5th Edition game.

// The application allows users to:
//         Initialize a new game character with ability scores
//         Add buffs and debuffs to the character
//         Define character's skills and proficiencies
//         View the character's details including ability scores, active buffs/debuffs, skills, and recent roll history
//         Make skill and ability checks, simulating dice rolls with the option of manual input or automatic dice roll
//         Save a character and load a previous character


public class OldModifierManagerApp {
    private GameCharacter character;
    private final Scanner input;
    private static final String JSON_STORE = "./data/character.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: runs the D&D Modifier Manager application
    public OldModifierManagerApp() {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        offerCharacterLoad();
    }


    // MODIFIES: this
    // EFFECTS: processes user input, displays menu, gets user command,
    //          manages the program loop, and handles welcome and farewell messages
    private void runManager() {
        System.out.println("Welcome to D&D 5E Modifier Manager");
        initCharacter();

        boolean keepGoing = true;
        while (keepGoing) {
            displayMenu();
            String command = input.next().toLowerCase();
            keepGoing = executeMenuCommand(command);
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: executes the menu command given by the user;
    //          returns false if the user chooses to quit, true otherwise
    private boolean executeMenuCommand(String command) {
        switch (command) {
            case "a":
                addBuffsDebuffs();
                return true;
            case "s":
                defineSkillsProficiencies();
                return true;
            case "v":
                viewCharacterDetails();
                return true;
            case "r":
                rollForChecks();
                return true;
            case "q":
                offerCharacterSave();
                return false;

            default:
                System.out.println("Selection not valid...");
                return true;
        }
    }


    // MODIFIES: this
    // EFFECTS: Initializes the character with the given name, level, and ability scores.
    private void initCharacter() {
        System.out.println("Enter character's name:");
        String name = input.next();

        System.out.println("Enter character's level:");
        int level  = input.nextInt();

        character = new GameCharacter(name, level);

        System.out.println("Please create your character profile by setting your ability scores.");

        AbilityType[] abilities = AbilityType.values();
        for (AbilityType ability : abilities) {
            System.out.println("Enter " + ability + " score:");
            int score = input.nextInt();
            character.updateAbilityScore(ability, score);
        }
    }


    // EFFECTS: Displays the main menu
    private void displayMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("\ta -> Add Buffs/Debuffs");
        System.out.println("\ts -> Define Skills & Proficiencies");
        System.out.println("\tv -> View Ability Scores, Modifiers, Skills, Buffs/Debuffs & Roll History");
        System.out.println("\tr -> Roll for Checks");
        System.out.println("\tq -> Quit");
    }

    // Buffs/Debuffs


    // MODIFIES: this, character
    // EFFECTS: Prompts the user for the details of a new buff or debuff and
    //          adds it to the character's active buffs/debuffs list.
    private void addBuffsDebuffs() {
        String name = promptForName();
        AbilityType effectAbility = promptForAbility();
        int effectMagnitude = promptForEffectMagnitude();
        int duration = promptForDuration();

        BuffDebuff newBuffDebuff = new BuffDebuff(name, effectAbility, effectMagnitude, duration);
        character.addBuffDebuff(newBuffDebuff);

        System.out.println(name + " has been added to " + character.getName() + " with a " + effectMagnitude
                + " effect on " + effectAbility + " for " + duration + " rounds.");
    }

    // EFFECTS: Prompts user for the name of the buff/debuff and returns it.
    private String promptForName() {
        System.out.println("Enter the name of the buff/debuff:");
        String name = input.next();
        input.nextLine();
        return name;
    }

    //  EFFECTS: Prompts user for an ability affected by the buff/debuff and returns it.
    private AbilityType promptForAbility() {
        AbilityType effectAbility = null;
        while (effectAbility == null) {
            System.out.println("Select an ability from the following list affected by the buff/debuff:");
            for (AbilityType ability : AbilityType.values()) {
                System.out.println(ability);
            }

            String abilityInput = input.nextLine().toUpperCase();
            try {
                effectAbility = AbilityType.valueOf(abilityInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid ability! Please enter a valid ability.");
            }
        }
        return effectAbility;
    }

    // EFFECTS: Prompts user for the magnitude of the effect and returns it.
    private int promptForEffectMagnitude() {
        System.out.println("Enter the magnitude of the effect (e.g., +2 or -3):");
        return input.nextInt();
    }

    // EFFECTS: Prompts user for the duration of the buff/debuff and returns it.
    private int promptForDuration() {
        System.out.println("Enter the duration (number of rounds) of the buff/debuff:");
        return input.nextInt();
    }



    // Skills & Proficiencies

    // MODIFIES: character
    // EFFECTS: Prompts the user for a skill name and updates the character's skills list based on user input.
    private void defineSkillsProficiencies() {
        SkillType chosenSkillType = getChosenSkillType();
        AbilityScore associatedAbility = findAssociatedAbility(chosenSkillType);

        if (associatedAbility == null) {
            System.out.println("Error: Could not find associated ability for the chosen skill.");
            return;
        }

        boolean isProficient = getProficiencyInput(chosenSkillType);

        // Update the character's skills list based on the user's input
        Skill newSkill = new Skill(chosenSkillType, associatedAbility, isProficient);
        character.addSkill(newSkill);

        System.out.println(chosenSkillType + " has been added to " + character.getName()
                + ". Proficiency: " + (isProficient ? "Yes" : "No"));
    }

    // EFFECTS: Prompts the user to choose a skill from the list and returns the chosen skill type.
    private SkillType getChosenSkillType() {
        System.out.println("Enter the name of the skill from the following list:");
        for (SkillType skill : SkillType.values()) {
            System.out.println(skill);
        }

        String skillInput = input.nextLine().toUpperCase();
        SkillType chosenSkillType = null;
        while (chosenSkillType == null) {
            try {
                chosenSkillType = SkillType.valueOf(skillInput);
            } catch (IllegalArgumentException e) {
                skillInput = input.next().toUpperCase();
            }
        }

        return chosenSkillType;
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

    // EFFECTS: Asks if the character is proficient in the specified skill
    //          returns true if proficient, false otherwise.
    private boolean getProficiencyInput(SkillType chosenSkillType) {
        System.out.println("Is the character proficient in " + chosenSkillType + "? (yes/no):");
        while (true) {
            String proficiencyInput = input.next().toLowerCase();
            if (proficiencyInput.equals("yes")) {
                return true;
            } else if (proficiencyInput.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid input! Please answer with 'yes' or 'no'.");
            }
        }
    }

    // Character details

    // EFFECTS: Displays the character's basic details.
    private void viewCharacterDetails() {
        System.out.println("Character Name: " + character.getName());
        System.out.println("Character Level: " + character.getLevel());
        displayAbilityScores();
        displayBuffsDebuffs();
        displaySkillsProficiency();
        displayRecentRolls();
    }

    // EFFECTS: Displays the character's ability scores.
    private void displayAbilityScores() {
        System.out.println("\nAbility Scores:");
        for (AbilityScore score : character.getAbilityScores()) {
            System.out.println(score.getType() + ": " + score.getScore());
        }
    }

    // EFFECTS: Displays the character's active buffs and debuffs.
    private void displayBuffsDebuffs() {
        System.out.println("\nActive Buffs/Debuffs:");
        if (character.getActiveBuffsDebuffs().isEmpty()) {
            System.out.println("None");
        } else {
            for (BuffDebuff buffDebuff : character.getActiveBuffsDebuffs()) {
                System.out.println(buffDebuff.getDescription());
            }
        }
    }

    // EFFECTS: Displays the character's skills and their proficiencies.
    private void displaySkillsProficiency() {
        System.out.println("\nSkills Proficiency:");
        for (Skill skill : character.getSkills()) {
            System.out.println(skill.describeSkill());
        }
    }

    // EFFECTS: Displays the character's recent roll history.
    private void displayRecentRolls() {
        System.out.println("\nRecent Roll History:");
        List<Roll> rolls = character.getRollHistory().getRollList();

        if (rolls.isEmpty()) {
            System.out.println("No rolls made yet.");
        } else {
            for (int i = rolls.size() - 1; i >= Math.max(0, rolls.size() - 5); i--) {
                System.out.println(rolls.get(i).rollDescription());
            }
        }
    }


    // Roll for Checks

    // EFFECTS:  Asks the user if they are making a skill check. If "yes", proceeds with a skill check.
    //           Otherwise, proceeds with an ability check.
    public void rollForChecks() {
        Scanner input = new Scanner(System.in);

        System.out.println("Is this a skill check? (yes/no):");
        String response = input.nextLine().toLowerCase();

        if (response.equals("yes")) {
            rollForSkillCheck();
        } else {
            rollForAbilityCheck();
        }

        character.updateBuffsDebuffsDuration();
    }

    // EFFECTS:  Prompts the user to select a skill from the available SkillType options.
    //           Rolls the dice for the chosen skill, calculates the skill modifier,
    //           and prints the original dice roll, applied modifiers, and the total result.

    public void rollForSkillCheck() {
        Scanner input = new Scanner(System.in);

        System.out.println("Select a skill from the following list:");
        for (SkillType skill : SkillType.values()) {
            System.out.println(skill);
        }

        String chosenSkillName = input.nextLine().toUpperCase();
        SkillType chosenSkill = SkillType.valueOf(chosenSkillName);

        int originalRoll = rollDice();
        int modifier = calculateSkillModifier(chosenSkill);

        System.out.println("Original Roll: " + originalRoll);
        System.out.println("Applied Modifiers: " + modifier);
        System.out.println("Total Result: " + (originalRoll + modifier));

        Roll newRoll = new Roll(chosenSkillName + " check", originalRoll, modifier);
        character.getRollHistory().addRoll(newRoll);

    }

    // EFFECTS:  Prompts the user to select an ability from the available AbilityType options.
    //           Rolls the dice for the chosen ability, calculates the ability modifier,
    //           and prints the original dice roll, applied modifiers, and the total result.
    public void rollForAbilityCheck() {
        Scanner input = new Scanner(System.in);

        System.out.println("Select an ability from the following list:");
        for (AbilityType ability : AbilityType.values()) {
            System.out.println(ability);
        }

        String chosenAbilityName = input.nextLine().toUpperCase();
        AbilityType chosenAbility = AbilityType.valueOf(chosenAbilityName);

        int originalRoll = rollDice(); // null because we don't have a skill in context
        int modifier = calculateAbilityModifier(chosenAbility);

        System.out.println("Original Roll: " + originalRoll);
        System.out.println("Applied Modifiers: " + modifier);
        System.out.println("Total Result: " + (originalRoll + modifier));

        Roll newRoll = new Roll(chosenAbilityName + " check", originalRoll, modifier);
        character.getRollHistory().addRoll(newRoll);

    }

    // EFFECTS:  Asks the user if they want the system to automatically roll a die for them.
    //           If the user chooses automatic roll, it simulates a roll of a 20-sided dice.
    //           If the user chooses manual input, it prompts the user to input their own dice roll.
    //           Returns the dice roll value.
    public int rollDice() {
        Scanner input = new Scanner(System.in);

        System.out.println("Do you want the system to roll for you? (yes/no):");
        String response = input.nextLine().toLowerCase();

        if (response.equals("yes")) {
            return (int) (Math.random() * 20) + 1; // 20 sided dice roll
        } else {
            System.out.println("Input your roll:");
            return input.nextInt();
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

    // EFFECTS: Asks the user if they would like to save their character before quitting.
    //          If user responds with "yes", the character is saved to file. Otherwise, does nothing.
    private void offerCharacterSave() {
        System.out.println("Would you like to save your character before quitting? (yes/no)");
        String response = input.next().toLowerCase();
        if (response.equals("yes")) {
            saveCharacter();
        }
    }

    // MODIFIES: This, the file specified by JSON_STORE
    // EFFECTS: Attempts to save character to file. If successful, a message indicating success is printed.
    private void saveCharacter() {
        try {
            jsonWriter.open();
            jsonWriter.write(character);
            jsonWriter.close();
            System.out.println("Saved " + character.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: Asks the user if they would like to load a character from a file.
    //          If user responds with "yes", attempts to load a character from file.
    //          Otherwise, initializes a new character.
    private void offerCharacterLoad() {
        System.out.println("Would you like to load a character? (yes/no)");
        String response = input.next().toLowerCase();
        if (response.equals("yes")) {
            loadCharacter();
        } else {
            runManager();
        }
    }

    // MODIFIES: This
    // EFFECTS: Attempts to load character from file. If successful, a message indicating success is printed.
    //          If unsuccessful, a message indicating failure is printed, and a new character is initialized.
    private void loadCharacter() {
        try {
            character = jsonReader.read();
            System.out.println("Loaded " + character.getName() + " from " + JSON_STORE);

            boolean keepGoing = true;
            while (keepGoing) {
                displayMenu();
                String command = input.next().toLowerCase();
                keepGoing = executeMenuCommand(command);
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            initCharacter();
        }
    }

}
