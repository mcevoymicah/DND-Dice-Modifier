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
                    System.out.println("Exiting... Goodbye!");
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
        System.out.print("Enter buff/debuff name: ");
        String name = scanner.nextLine();

        // Display available abilities
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

        // Get effect magnitude
        int magnitude = 0;
        while (magnitude == 0) {
            System.out.print("Enter effect magnitude (e.g., +2 or -3): ");
            try {
                magnitude = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // Get duration
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

        // Create and add the buff/debuff
        BuffDebuff buffDebuff = new BuffDebuff(name, abilities[abilityIndex], magnitude, duration);
        character.addBuffDebuff(buffDebuff);
        System.out.println("Buff/Debuff added successfully!");

        // Display all active buffs
        System.out.println("\nActive Buffs/Debuffs:");
        for (BuffDebuff buff : character.getActiveBuffsDebuffs()) {
            System.out.println("- " + buff.getDescription());
        }
    }


    // MODIFIES: this
    // EFFECTS: Defines skills and proficiencies for the character
    private void defineSkillsAndProficiencies() {
        SkillType[] skills = SkillType.values();
        int skillIndex = -1;

        // Display available skills
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

        // Ask about proficiency
        boolean isProficient = false;
        while (true) {
            System.out.print("Is the character proficient in this skill? (yes/no): ");
            String proficiencyInput = scanner.nextLine().trim().toLowerCase();
            if (proficiencyInput.equals("yes")) {
                isProficient = true;
                break;
            } else if (proficiencyInput.equals("no")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }

        // Get the chosen skill and associated ability
        SkillType chosenSkill = skills[skillIndex];
        AbilityType associatedAbilityType = Skill.getAssociatedAbilityBySkill(chosenSkill);
        AbilityScore associatedAbility = character.getAbilityScoreByType(associatedAbilityType);

        // Calculate proficiency bonus based on character level
        int proficiencyBonus = 1 + (character.getLevel() + 3) / 4;

        // Add skill to character
        Skill skill = new Skill(chosenSkill, associatedAbility, isProficient);
        character.addSkill(skill);

        // Provide feedback
        System.out.println("Skill added successfully!");
        System.out.println(skill.describeSkill() + " (Proficiency Bonus: " + proficiencyBonus + ")");
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

        if (choice.equals("1")) {
            rollForSkill();
        } else if (choice.equals("2")) {
            rollForAbility();
        } else {
            System.out.println("Invalid option.");
        }
    }



    // EFFECTS: Rolls for a skill, whether the character is proficient or not
    private void rollForSkill() {
        System.out.println("Available Skills:");
        SkillType[] skillTypes = SkillType.values();
        for (int i = 0; i < skillTypes.length; i++) {
            System.out.println((i + 1) + ". " + skillTypes[i]);
        }

        System.out.print("Choose a skill by number: ");
        int skillIndex;
        try {
            skillIndex = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return;
        }

        if (skillIndex < 0 || skillIndex >= skillTypes.length) {
            System.out.println("Invalid skill choice. Please try again.");
            return;
        }

        SkillType chosenSkillType = skillTypes[skillIndex];
        AbilityType associatedAbilityType = Skill.getAssociatedAbilityBySkill(chosenSkillType);
        AbilityScore associatedAbility = character.getAbilityScoreByType(associatedAbilityType);

        // Check proficiency
        boolean isProficient = character.isProficientInSkill(chosenSkillType);
        int proficiencyBonus = isProficient ? 1 + (character.getLevel() + 3) / 4 : 0;

        // Calculate buffs/debuffs modifier
        int buffDebuffModifier = 0;
        for (BuffDebuff buffDebuff : character.getActiveBuffsDebuffs()) {
            if (buffDebuff.getEffectAbility() == associatedAbilityType) {
                buffDebuffModifier += buffDebuff.getEffectMagnitude();
            }
        }

        // Roll a 20-sided die
        int rollValue = (int) (Math.random() * 20) + 1;

        // Calculate the total modifier
        int totalModifier = associatedAbility.getModifier() + proficiencyBonus + buffDebuffModifier;

        // Create a Roll object
        Roll roll = new Roll(chosenSkillType + " check", rollValue, totalModifier);

        // Display the roll details
        System.out.println("\nRoll Result:");
        System.out.println("Skill: " + chosenSkillType);
        System.out.println("Base Roll: " + rollValue);
        System.out.println("Ability Modifier: " + associatedAbility.getModifier());
        System.out.println("Proficiency Bonus: " + proficiencyBonus);
        System.out.println("Buff/Debuff Modifier: " + buffDebuffModifier);
        System.out.println("Total Result: " + roll.getFinalOutcome());

        // Add the roll to the character's roll history
        character.addRoll(roll);

        // Update buffs and debuffs
        character.updateBuffsDebuffsDuration();
    }


    // EFFECTS: Rolls for an ability and displays the result
    private void rollForAbility() {
        System.out.println("Available Abilities:");
        AbilityType[] abilities = AbilityType.values();
        for (int i = 0; i < abilities.length; i++) {
            System.out.println((i + 1) + ". " + abilities[i]);
        }

        System.out.print("Choose an ability by number: ");
        int abilityIndex;
        try {
            abilityIndex = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        if (abilityIndex < 0 || abilityIndex >= abilities.length) {
            System.out.println("Invalid ability choice. Please try again.");
            return;
        }

        AbilityType chosenAbility = abilities[abilityIndex];

        // Fetch ability score
        AbilityScore abilityScore = character.getAbilityScoreByType(chosenAbility);
        if (abilityScore == null) {
            System.out.println("Error: Ability score for " + chosenAbility + " not found.");
            return;
        }

        // Calculate buffs/debuffs modifier
        int buffDebuffModifier = 0;
        for (BuffDebuff buffDebuff : character.getActiveBuffsDebuffs()) {
            if (buffDebuff.getEffectAbility() == chosenAbility) {
                buffDebuffModifier += buffDebuff.getEffectMagnitude();
            }
        }

        // Roll a 20-sided die
        int rollValue = (int) (Math.random() * 20) + 1;

        // Calculate the total modifier
        int totalModifier = abilityScore.getModifier() + buffDebuffModifier;

        // Create a Roll object
        Roll roll = new Roll(chosenAbility.toString() + " check", rollValue, totalModifier);

        // Display the roll details
        System.out.println("\nRoll Result:");
        System.out.println("Ability: " + chosenAbility);
        System.out.println("Base Roll: " + rollValue);
        System.out.println("Ability Modifier: " + abilityScore.getModifier());
        System.out.println("Buff/Debuff Modifier: " + buffDebuffModifier);
        System.out.println("Total Result: " + roll.getFinalOutcome());

        // Add the roll to the character's roll history
        character.addRoll(roll);

        // Update buffs and debuffs
        character.updateBuffsDebuffsDuration();
    }
}
