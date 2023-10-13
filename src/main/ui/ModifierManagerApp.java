package ui;

import model.*;

import java.util.List;
import java.util.Scanner;


// D&D 5E Modifier Manager Application
public class ModifierManagerApp {
    private GameCharacter character;
    private final Scanner input;

    // EFFECTS: runs the D&D Modifier Manager application
    public ModifierManagerApp() {
        input = new Scanner(System.in);
        runManager();
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
                return false;
            default:
                System.out.println("Selection not valid...");
                return true;
        }
    }


    // MODIFIES: this
    // EFFECTS: Initializes the character by prompting the user for ability scores
    private void initCharacter() {
        System.out.println("Enter character's name:");
        String name = input.next();

        character = new GameCharacter(name);

        System.out.println("Please create your character profile by setting your ability scores.");
        System.out.println("Enter STRENGTH score:");
        int strength = input.nextInt();
        character.updateAbilityScore(AbilityType.STRENGTH, strength);

        System.out.println("Enter DEXTERITY score:");
        int dexterity = input.nextInt();
        character.updateAbilityScore(AbilityType.DEXTERITY, dexterity);

        System.out.println("Enter CONSTITUTION score:");
        int constitution = input.nextInt();
        character.updateAbilityScore(AbilityType.CONSTITUTION, constitution);

        System.out.println("Enter INTELLIGENCE score:");
        int intelligence = input.nextInt();
        character.updateAbilityScore(AbilityType.INTELLIGENCE, intelligence);

        System.out.println("Enter WISDOM score:");
        int wisdom = input.nextInt();
        character.updateAbilityScore(AbilityType.WISDOM, wisdom);

        System.out.println("Enter CHARISMA score:");
        int charisma = input.nextInt();
        character.updateAbilityScore(AbilityType.CHARISMA, charisma);

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

    // MODIFIES: character
    // EFFECTS: Prompts the user for the details of a new buff or debuff (name, effect, duration).
    //          Creates a new BuffDebuff object based on the user's input.
    //          Adds the new BuffDebuff object to the character's list of active buffs and debuffs.
    private void addBuffsDebuffs() {
        // Prompt for buff/debuff name
        System.out.println("Enter the name of the buff/debuff:");
        String name = input.next();

        // Prompt for the affected ability
        AbilityType effectAbility = null;
        while (effectAbility == null) {
            System.out.println("Enter the ability affected by the buff/debuff (e.g., STRENGTH, DEXTERITY, ...):");
            String abilityInput = input.next().toUpperCase();
            try {
                effectAbility = AbilityType.valueOf(abilityInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid ability! Please enter a valid ability.");
            }
        }

        // Prompt for effect magnitude
        System.out.println("Enter the magnitude of the effect (e.g., +2 or -3):");
        int effectMagnitude = input.nextInt();

        // Prompt for duration
        System.out.println("Enter the duration (number of rounds) of the buff/debuff:");
        int duration = input.nextInt();

        // Create BuffDebuff object and add to character's list
        BuffDebuff newBuffDebuff = new BuffDebuff(name, effectAbility, effectMagnitude, duration);
        character.addBuffDebuff(newBuffDebuff);

        System.out.println(name + " has been added to " + character.getName() + " with a " + effectMagnitude
                + " effect on " + effectAbility + " for " + duration + " rounds.");
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
    public int calculateSkillModifier(SkillType skill) {
        AbilityType associatedAbility = Skill.getAssociatedAbilityBySkill(skill);
        int abilityModifier = getModifierForAbility(associatedAbility);

        boolean isProficient = character.isProficientInSkill(skill);
        int proficiencyBonus = isProficient ? 2 : 0; // Assuming proficiency bonus is 2 for simplicity

        return abilityModifier + proficiencyBonus;
    }

    // EFFECTS:  Returns the modifier associated with the provided ability type.
    public int calculateAbilityModifier(AbilityType ability) {
        return getModifierForAbility(ability);
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

}