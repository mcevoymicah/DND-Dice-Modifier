package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import model.*;

// GamePanel extends JPanel and serves as the main content area in the ModifierManagerGUI,
// It provides interactive panels for tasks like adding buffs/debuffs, managing skills, viewing character
// details, and performing dice rolls. The class coordinates with ModifierManagerApp for
// character data operations and manages UI components and user interactions.

class GamePanel extends JPanel {
    private final ModifierManagerApp managerApp;
    private ModifierManagerGUI managerUI;


    private final Color backgroundColor = new Color(217,220,214);
    private final Color buttonColor = new Color(129,195,215);
    private final Color textColor = new Color(22,66,91);

    // EFFECTS: constructs a GamePanel with ModifierManagerApp and ModifierManagerGUI

    public GamePanel(ModifierManagerApp app, ModifierManagerGUI gui) {
        this.managerApp = app;
        this.managerUI = gui;
    }

    // MODIFIES: this, managerApp
    // EFFECTS: Sets up panel and adds all components necessary for buff/debuff input.
    public void addBuffDebuffsPanel() {
        initializePanel(); // Set up the panel layout and background

        this.add(Box.createVerticalGlue());

        JTextField nameField = addNameField(); // Add name input
        JComboBox<AbilityType> abilityComboBox = addAbilityComboBox(); // Add ability selection
        JSpinner magnitudeSpinner = addMagnitudeSpinner(); // Add magnitude input
        JSpinner durationSpinner = addDurationSpinner(); // Add duration input


        addButtons(nameField, abilityComboBox, magnitudeSpinner, durationSpinner);
        // Add the Add and Return buttons

        this.add(Box.createVerticalGlue());

        this.revalidate();
        this.repaint();
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds the name input field to the panel.
    public JTextField addNameField() {
        JTextField nameField = new JTextField(20);

        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setBackground(buttonColor);
        nameField.setForeground(textColor);

        this.add(new JLabel("Name:"));
        this.add(nameField);
        this.add(Box.createVerticalStrut(10));
        return nameField;
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds the ability combo box to the panel.
    public JComboBox<AbilityType> addAbilityComboBox() {
        JComboBox<AbilityType> abilityComboBox = new JComboBox<>(AbilityType.values());

        abilityComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, abilityComboBox.getPreferredSize().height));
        abilityComboBox.setBackground(buttonColor);
        abilityComboBox.setForeground(textColor);

        this.add(new JLabel("Ability:", SwingConstants.CENTER));
        this.add(abilityComboBox);
        this.add(Box.createVerticalStrut(10));
        return abilityComboBox;
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds the magnitude spinner to the panel.
    public JSpinner addMagnitudeSpinner() {
        JSpinner magnitudeSpinner = new JSpinner(new SpinnerNumberModel(
                0, -10, 10, 1));
        magnitudeSpinner.setMaximumSize(new Dimension(Integer.MAX_VALUE, magnitudeSpinner.getPreferredSize().height));
        magnitudeSpinner.setBackground(buttonColor);
        magnitudeSpinner.setForeground(textColor);

        this.add(new JLabel("Magnitude:", SwingConstants.CENTER));
        this.add(magnitudeSpinner);
        this.add(Box.createVerticalStrut(10));

        return magnitudeSpinner;
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds the duration spinner to the panel.
    public JSpinner addDurationSpinner() {
        JSpinner durationSpinner = new JSpinner(new SpinnerNumberModel(
                1, 1, 100, 1));
        durationSpinner.setMaximumSize(new Dimension(Integer.MAX_VALUE, durationSpinner.getPreferredSize().height));
        durationSpinner.setBackground(buttonColor);
        durationSpinner.setForeground(textColor);

        this.add(new JLabel("Duration:", SwingConstants.CENTER));
        this.add(durationSpinner);
        this.add(Box.createVerticalStrut(10));

        return durationSpinner;
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds the add button and return button to the panel,
    //          and sets their action listeners.
    public void addButtons(JTextField nameField, JComboBox<AbilityType> abilityComboBox,
                           JSpinner magnitudeSpinner, JSpinner durationSpinner) {
        JButton addButton = createButton("Add Buff/Debuff");

        addButton.addActionListener(e -> handleAddBuffDebuff(
                nameField, abilityComboBox, magnitudeSpinner, durationSpinner));

        this.add(addButton);
        this.add(Box.createVerticalStrut(10));
        this.add(returnButton());
    }

    // MODIFIES: managerApp
    // EFFECTS: Captures the user input and calls managerApp.addBuffDebuff to add the buff/debuff,
    //          then switches to the actions panel.
    public void handleAddBuffDebuff(JTextField nameField, JComboBox<AbilityType> abilityComboBox,
                                    JSpinner magnitudeSpinner, JSpinner durationSpinner) {
        String name = nameField.getText();
        AbilityType effectAbility = (AbilityType) abilityComboBox.getSelectedItem();
        int effectMagnitude = (Integer) magnitudeSpinner.getValue();
        int duration = (Integer) durationSpinner.getValue();

        managerApp.addBuffDebuff(name, effectAbility, effectMagnitude, duration);
        managerUI.switchToActionsPanel();
    }


    // Skills

    // MODIFIES: this, character
    // EFFECTS: Sets up the panel for defining skills and proficiencies.
    public void setupSkillsProficienciesPanel() {
        initializeSkillsProficienciesComponents();
        managerUI.switchToActionsPanel();
        this.revalidate();
        this.repaint();
    }

    // MODIFIES: this
    // EFFECTS: Initializes components for the skills and proficiencies panel.
    private void initializeSkillsProficienciesComponents() {
        initializePanel();

        JComboBox<SkillType> skillTypeComboBox = new JComboBox<>(SkillType.values());
        skillTypeComboBox.setMaximumSize(new Dimension(
                Integer.MAX_VALUE, skillTypeComboBox.getPreferredSize().height));
        skillTypeComboBox.setBackground(buttonColor);
        skillTypeComboBox.setForeground(textColor);

        JCheckBox proficiencyCheckBox = new JCheckBox("Proficient");
        proficiencyCheckBox.setBackground(buttonColor);
        proficiencyCheckBox.setForeground(textColor);

        JButton addSkillButton = createButton("Add Skill");
        setupAddSkillButtonAction(addSkillButton, skillTypeComboBox, proficiencyCheckBox);

        // Add components to the panel
        this.add(Box.createVerticalGlue());
        this.add(new JLabel("Select Skill:", SwingConstants.CENTER));
        this.add(skillTypeComboBox);
        this.add(Box.createVerticalStrut(10));
        this.add(new JLabel("Proficiency:", SwingConstants.CENTER));
        this.add(proficiencyCheckBox);
        this.add(Box.createVerticalStrut(10));
        this.add(addSkillButton);
        this.add(Box.createVerticalStrut(10));
        this.add(returnButton());
        this.add(Box.createVerticalGlue());
    }


    // MODIFIES: this
    // EFFECTS: Sets up the action listener for the "Add Skill" button.
    private void setupAddSkillButtonAction(JButton addSkillButton, JComboBox<SkillType> skillTypeComboBox,
                                           JCheckBox proficiencyCheckBox) {
        addSkillButton.addActionListener(e -> {
            boolean success = managerApp.addSkill(skillTypeComboBox, proficiencyCheckBox);
            if (success) {
                JOptionPane.showMessageDialog(null, "Skill added successfully!");
            } else {
                JOptionPane.showMessageDialog(null,
                        "Error: Could not find associated ability for the chosen skill.");
            }
        });
    }

    // View Details

    // MODIFIES: this
    // EFFECTS: Displays the character's basic details.
    public void viewDetails() {
        initializePanel();

        JLabel name = new JLabel("Character Name: " + managerApp.getCharacterName());
        name.setHorizontalAlignment(JLabel.CENTER);

        JLabel level = new JLabel("Character Level: " + managerApp.getCharacterLevel());
        level.setHorizontalAlignment(JLabel.CENTER);

        this.add(Box.createVerticalGlue());

        this.add(name);
        this.add(level);

        this.add(Box.createVerticalStrut(10));

        this.add(returnButton());

        this.add(Box.createVerticalStrut(10));

        this.add(createTextAreaForSection(managerApp.getAbilityScoresText()));
        this.add(createTextAreaForSection(managerApp.getBuffsDebuffsText()));
        this.add(createTextAreaForSection(managerApp.getSkillsProficiencyText()));
        this.add(createTextAreaForSection(managerApp.getRecentRollsText()));

        this.add(Box.createVerticalGlue());

    }

    // Dice Rolls

    // MODIFIES: this
    // EFFECTS: Sets up a panel with buttons to initiate skill checks and ability checks.
    //           Clicking these buttons will prompt the user to select a skill or ability
    //           and perform the corresponding check.
    public void rollChecks() {
        initializePanel();

        JButton skillCheckButton = createButton("Roll for Skill Check");
        skillCheckButton.addActionListener(e -> performSkillCheck());


        JButton abilityCheckButton = createButton("Roll for Ability Check");
        abilityCheckButton.addActionListener(e -> performAbilityCheck());

        this.add(Box.createVerticalGlue());

        this.add(skillCheckButton);
        this.add(Box.createVerticalStrut(10));
        this.add(abilityCheckButton);
        this.add(Box.createVerticalStrut(10));
        this.add(returnButton());

        this.add(Box.createVerticalGlue());
    }

    // MODIFIES: this
    // EFFECTS: Opens a dialog for the user to select a skill.
    //           Once a skill is selected, performs a skill check using managerApp.
    private void performSkillCheck() {
        SkillType chosenSkill = getUserSelectedSkill();
        boolean isAutomaticRoll = getUserRollChoice();
        int rollResult = managerApp.rollDice(isAutomaticRoll);
        managerApp.rollForSkillCheck(chosenSkill, rollResult);
    }

    // MODIFIES: this
    // EFFECTS: Opens a dialog for the user to select an ability.
    //           Once an ability is selected, performs an ability check using managerApp.
    private void performAbilityCheck() {
        AbilityType chosenAbility = getUserSelectedAbility();
        boolean isAutomaticRoll = getUserRollChoice();
        int rollResult = managerApp.rollDice(isAutomaticRoll);
        managerApp.rollForAbilityCheck(chosenAbility, rollResult);
    }

    // EFFECTS: Prompts user if they want the system to roll for them
    private boolean getUserRollChoice() {
        int response = JOptionPane.showConfirmDialog(null,
                "Do you want the system to roll for you?",
                "Roll Dice", JOptionPane.YES_NO_OPTION);
        return response == JOptionPane.YES_OPTION;
    }


    // EFFECTS: Opens a dialog for the user to select a skill from the SkillType enum.
    //          Returns the selected SkillType or null if the selection is canceled.
    private SkillType getUserSelectedSkill() {
        JComboBox<SkillType> skillComboBox = new JComboBox<>(SkillType.values());
        int result = JOptionPane.showConfirmDialog(null, skillComboBox,
                "Select a Skill:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            return (SkillType) skillComboBox.getSelectedItem();
        } else {
            return null;
        }
    }

    // EFFECTS: Opens a dialog for the user to select an ability from the AbilityType enum.
    //          Returns the selected AbilityType or null if the selection is canceled.
    private AbilityType getUserSelectedAbility() {
        JComboBox<AbilityType> abilityComboBox = new JComboBox<>(AbilityType.values());
        int result = JOptionPane.showConfirmDialog(null, abilityComboBox,
                "Select an Ability:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            return (AbilityType) abilityComboBox.getSelectedItem();
        } else {
            return null;
        }
    }


    // Helpers

    // MODIFIES: this
    // EFFECTS: Clears the current GamePanel and initializes the layout and style.
    public void initializePanel() {
        this.removeAll(); // Clear existing components
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(backgroundColor);
        this.setForeground(textColor);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }


    // EFFECTS: Creates a text area for section
    private JTextArea createTextAreaForSection(String sectionText) {
        JTextArea textArea = new JTextArea(sectionText);
        textArea.setEditable(false);
        return textArea;
    }


    // EFFECTS: Creates and returns a new JButton with the specified text, styled
    //          with center alignment, maximum size, background color, text color, border,
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        button.setBackground(buttonColor);
        button.setForeground(textColor);

        // Border
        Border lineBorder = BorderFactory.createLineBorder(textColor, 2);
        button.setBorder(lineBorder);

        button.setFocusPainted(false);

        return button;
    }

    // EFFECTS: Creates and returns a new JButton that takes user back to the actions panel
    private JButton returnButton() {

        JButton returnButton = createButton("Return");
        returnButton.addActionListener(e -> managerUI.switchToActionsPanel());

        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        return returnButton;
    }

}
