package ui;

import model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ModifierManagerConsoleApp {
    private GameCharacter character;
    private final Scanner scanner;

    // EFFECTS: Constructs a ModifierManagerConsoleApp that deals with data persistence
    public ModifierManagerConsoleApp() {
        scanner = new Scanner(System.in);
    }

    // EFFECTS: Runs the main menu of the application
    public void run() {
        boolean running = true;

        while (running) {
            System.out.println("\nWelcome to D&D 5E Modifier Manager");
            System.out.println("1. Create New Character");
            System.out.println("2. Quit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createCharacter();
                    manageCharacter();
                    break;
                case "2":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // EFFECTS: Manages character options after creation or loading
    private void manageCharacter() {
        boolean managing = true;

        while (managing) {
            System.out.println("\nCharacter Management");
            System.out.println("1. Add Buffs/Debuffs");
            System.out.println("2. Define Skills and Proficiencies");
            System.out.println("3. View Character Details");
            System.out.println("4. Roll for Checks");
            System.out.println("5. Quit to Main Menu");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addBuffDebuff();
                    break;
                case "2":
                    defineSkillsAndProficiencies();
                    break;
                case "3":
                    System.out.println(viewCharacterDetails());
                    break;
                case "4":
                    rollForChecks();
                    break;
                case "5":
                    managing = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates a new character with user input
    private void createCharacter() {
        System.out.print("Enter character name: ");
        String name = scanner.nextLine();

        System.out.print("Enter character level: ");
        int level = Integer.parseInt(scanner.nextLine());

        // Initialize abilities
        Map<AbilityType, Integer> abilities = new HashMap<>();
        for (AbilityType ability : AbilityType.values()) {
            System.out.print("Enter score for " + ability + ": ");
            int score = Integer.parseInt(scanner.nextLine());
            abilities.put(ability, score);
        }

        // Create character with the provided name, level, and ability scores
        character = new GameCharacter(name, level);
        for (Map.Entry<AbilityType, Integer> entry : abilities.entrySet()) {
            character.updateAbilityScore(entry.getKey(), entry.getValue());
        }

        System.out.println("Character created successfully!");
    }


    // MODIFIES: this
    // EFFECTS: Adds a buff or debuff to the character
    private void addBuffDebuff() {
        String name = getBuffDebuffName();
        AbilityType abilityType = getAbilityTypeFromUser();
        int magnitude = getEffectMagnitude();
        int duration = getEffectDuration();

        BuffDebuff buffDebuff = new BuffDebuff(name, abilityType, magnitude, duration);
        character.addBuffDebuff(buffDebuff);
        System.out.println("Buff/Debuff added successfully!");
    }

    // EFFECTS: Prompts/returns the name of the buff/debuff
    private String getBuffDebuffName() {
        System.out.print("Enter buff/debuff name: ");
        return scanner.nextLine();
    }

    // EFFECTS: Prompts/returns the chosen AbilityType
    private AbilityType getAbilityTypeFromUser() {
        AbilityType[] abilities = AbilityType.values();
        int abilityIndex = -1;

        while (abilityIndex < 0 || abilityIndex >= abilities.length) {
            System.out.println("Choose an ability:");
            for (int i = 0; i < abilities.length; i++) {
                System.out.println((i + 1) + ". " + abilities[i]);
            }

            System.out.print("Enter the number of the ability: ");
            try {
                abilityIndex = Integer.parseInt(scanner.nextLine()) - 1;
                if (abilityIndex < 0 || abilityIndex >= abilities.length) {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        return abilities[abilityIndex];
    }

    // EFFECTS: Prompts/returns the effect magnitude
    private int getEffectMagnitude() {
        int magnitude = 0;
        while (magnitude == 0) {
            System.out.print("Enter effect magnitude (e.g., +2 or -3): ");
            try {
                magnitude = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return magnitude;
    }

    // EFFECTS: Prompts/returns the duration of the buff/debuff
    private int getEffectDuration() {
        int duration = -1;
        while (duration < 0) {
            System.out.print("Enter duration (in turns): ");
            try {
                duration = Integer.parseInt(scanner.nextLine());
                if (duration < 0) {
                    System.out.println("Duration cannot be negative. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return duration;
    }

    // MODIFIES: this
    // EFFECTS: Defines skills and proficiencies for the character
    private void defineSkillsAndProficiencies() {
        SkillType chosenSkill = chooseSkill();
        boolean isProficient = askProficiency();
        AbilityType associatedAbilityType = Skill.getAssociatedAbilityBySkill(chosenSkill);
        AbilityScore associatedAbility = character.getAbilityScoreByType(associatedAbilityType);

        if (associatedAbility == null) {
            System.out.println("Error: Associated ability not found for this skill.");
            return;
        }

        // Create and add the skill
        Skill skill = new Skill(chosenSkill, associatedAbility, isProficient);
        character.addSkill(skill);

        // Give feedback when a skill is successfully added
        int proficiencyBonus = 1 + (character.getLevel() + 3) / 4;
        System.out.println("Skill added successfully!");
        System.out.println(skill.describeSkill() + " (Proficiency Bonus: " + proficiencyBonus + ")");
    }

    // EFFECTS: Displays available skills, returns the chosen SkillType
    private SkillType chooseSkill() {
        SkillType[] skills = SkillType.values();
        int skillIndex = -1;

        while (skillIndex < 0 || skillIndex >= skills.length) {
            System.out.println("Available Skills:");
            for (int i = 0; i < skills.length; i++) {
                System.out.println((i + 1) + ". " + skills[i]);
            }

            System.out.print("Choose a skill by number: ");
            try {
                skillIndex = Integer.parseInt(scanner.nextLine()) - 1;
                if (skillIndex < 0 || skillIndex >= skills.length) {
                    System.out.println("Invalid choice. Please select a valid skill number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        return skills[skillIndex];
    }

    /// EFFECTS: Confirms if the character is proficient in the skill; returns true if proficient
    private boolean askProficiency() {
        while (true) {
            System.out.print("Is the character proficient in this skill? (yes/no): ");
            String proficiencyInput = scanner.nextLine().trim().toLowerCase();
            if (proficiencyInput.equals("yes")) {
                return true;
            } else if (proficiencyInput.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }


    // EFFECTS: Displays character details
    private String viewCharacterDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Name: ").append(character.getName()).append("\n");
        details.append("Level: ").append(character.getLevel()).append("\n");

        details.append("\nAbility Scores:\n");
        for (AbilityScore ability : character.getAbilityScores()) {
            details.append(ability.getType()).append(": ").append(ability.getScore()).append("\n");
        }

        details.append("\nActive Buffs/Debuffs:\n");
        for (BuffDebuff buff : character.getActiveBuffsDebuffs()) {
            details.append(buff.getDescription()).append("\n");
        }

        details.append("\nSkills:\n");
        for (Skill skill : character.getSkills()) {
            details.append(skill.describeSkill()).append("\n");
        }

        details.append("\nRoll History:\n");
        List<Roll> rolls = character.getRollHistory().getRollList();
        if (rolls.isEmpty()) {
            details.append("No rolls made yet.\n");
        } else {
            for (Roll roll : rolls) {
                details.append(roll.rollDescription()).append("\n");
            }
        }

        return details.toString();
    }


    // EFFECTS: Handles rolling for skill or ability checks
    private void rollForChecks() {
        System.out.println("Do you want to roll for:");
        System.out.println("1. Skill");
        System.out.println("2. Ability");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                rollForSkill();
                break;
            case "2":
                rollForAbility();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    // EFFECTS: Rolls a 20-sided die and returns the result
    private int rollDice() {
        return (int) (Math.random() * 19) + 1;
    }


    // EFFECTS: Calculates the total buff/debuff modifier for the given ability
    private int calculateBuffDebuffModifier(AbilityType abilityType) {
        int buffDebuffModifier = 0;
        for (BuffDebuff buffDebuff : character.getActiveBuffsDebuffs()) {
            if (buffDebuff.getEffectAbility() == abilityType) {
                buffDebuffModifier += buffDebuff.getEffectMagnitude();
            }
        }
        return buffDebuffModifier;
    }

    // EFFECTS: Rolls for a skill
    private void rollForSkill() {
        SkillType chosenSkillType = promptSkillSelection();
        if (chosenSkillType == null) {
            return;
        }

        AbilityType associatedAbilityType = Skill.getAssociatedAbilityBySkill(chosenSkillType);
        AbilityScore associatedAbility = character.getAbilityScoreByType(associatedAbilityType);

        int proficiencyBonus = calculateProficiencyBonus(chosenSkillType);
        int buffDebuffModifier = calculateBuffDebuffModifier(associatedAbilityType);
        int rollValue = rollDice();
        int totalModifier = associatedAbility.getModifier() + proficiencyBonus + buffDebuffModifier;
        Roll roll = new Roll(chosenSkillType + " check", rollValue, totalModifier);

        displaySkillRollDetails(
                chosenSkillType, rollValue, associatedAbility, proficiencyBonus, buffDebuffModifier, totalModifier);

        character.addRoll(roll);
        character.updateBuffsDebuffsDuration();
    }

    // EFFECTS: Returns the chosen SkillType or null if invalid input
    private SkillType promptSkillSelection() {
        System.out.println("Available Skills:");
        SkillType[] skillTypes = SkillType.values();
        for (int i = 0; i < skillTypes.length; i++) {
            System.out.println((i + 1) + ". " + skillTypes[i]);
        }

        System.out.print("Choose a skill by number: ");
        try {
            int skillIndex = Integer.parseInt(scanner.nextLine()) - 1;
            if (skillIndex >= 0 && skillIndex < skillTypes.length) {
                return skillTypes[skillIndex];
            } else {
                System.out.println("Invalid skill choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }

        return null;
    }

    // EFFECTS: Returns proficiency bonus for skill based on the character's level
    private int calculateProficiencyBonus(SkillType skillType) {
        boolean isProficient = character.isProficientInSkill(skillType);
        int proficiencyBonus = isProficient ? 1 + (character.getLevel() + 3) / 4 : 0;
        return proficiencyBonus;
    }

    // EFFECTS: Displays the details of the roll
    private void displaySkillRollDetails(SkillType skill, int rollValue, AbilityScore ability, int proficiencyBonus,
                                    int buffDebuffModifier, int totalResult) {
        System.out.println("\nRoll Result:");
        System.out.println("Skill: " + skill);
        System.out.println("Base Roll: " + rollValue);
        System.out.println("Ability Modifier: " + ability.getModifier());
        System.out.println("Proficiency Bonus: " + proficiencyBonus);
        System.out.println("Buff/Debuff Modifier: " + buffDebuffModifier);
        System.out.println("Total Result: " + totalResult);
    }


    // EFFECTS: Rolls for an ability
    private void rollForAbility() {
        AbilityType chosenAbility = promptAbilitySelection();
        if (chosenAbility == null) {
            return;
        }

        AbilityScore abilityScore = character.getAbilityScoreByType(chosenAbility);

        int buffDebuffModifier = calculateBuffDebuffModifier(chosenAbility);
        int rollValue = rollDice();
        int totalModifier = abilityScore.getModifier() + buffDebuffModifier;
        Roll roll = new Roll(chosenAbility.toString() + " check", rollValue, totalModifier);

        displayAbilityRollDetails(
                chosenAbility, rollValue, abilityScore, buffDebuffModifier, totalModifier);

        character.addRoll(roll);
        character.updateBuffsDebuffsDuration();
    }

    // EFFECTS: Prompts the user to select an ability and returns the chosen AbilityType, or null if invalid input
    private AbilityType promptAbilitySelection() {
        System.out.println("Available Abilities:");
        AbilityType[] abilities = AbilityType.values();
        for (int i = 0; i < abilities.length; i++) {
            System.out.println((i + 1) + ". " + abilities[i]);
        }

        System.out.print("Choose an ability by number: ");
        try {
            int abilityIndex = Integer.parseInt(scanner.nextLine()) - 1;
            if (abilityIndex >= 0 && abilityIndex < abilities.length) {
                return abilities[abilityIndex];
            } else {
                System.out.println("Invalid ability choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }

        return null;
    }

    // EFFECTS: Displays the details of the roll for the ability
    private void displayAbilityRollDetails(AbilityType ability, int rollValue, AbilityScore abilityScore,
                                           int buffDebuffModifier, int totalResult) {
        System.out.println("\nRoll Result:");
        System.out.println("Ability: " + ability);
        System.out.println("Base Roll: " + rollValue);
        System.out.println("Ability Modifier: " + abilityScore.getModifier());
        System.out.println("Buff/Debuff Modifier: " + buffDebuffModifier);
        System.out.println("Total Result: " + totalResult);
    }

}
