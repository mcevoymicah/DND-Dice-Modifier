package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.*;

class GamePanel extends JPanel {
    private final ModifierManagerApp managerApp;
    private ModifierManagerGUI managerUI;


    private final Color backgroundColor = new Color(198, 172, 143);
    private final Color buttonColor = new Color(234, 224, 213);
    private final Color textColor = new Color(38, 35, 31);


    public GamePanel(ModifierManagerApp app, ModifierManagerGUI gui) {
        this.managerApp = app;
        this.managerUI = gui;


    }


    // MODIFIES: this, managerApp
    // EFFECTS: Sets up panel and adds all components necessary for buff/debuff input.
    public void addBuffDebuffsPanel() {
        initializePanel(); // Set up the panel layout and background

        JTextField nameField = addNameField(); // Add name input
        JComboBox<AbilityType> abilityComboBox = addAbilityComboBox(); // Add ability selection
        JSpinner magnitudeSpinner = addMagnitudeSpinner(); // Add magnitude input
        JSpinner durationSpinner = addDurationSpinner(); // Add duration input

        addButtons(nameField, abilityComboBox, magnitudeSpinner, durationSpinner);
        // Add the Add and Return buttons

        this.revalidate();
        this.repaint();
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds the name input field to the panel.
    public JTextField addNameField() {
        JTextField nameField = new JTextField(20);

        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));
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

        this.add(new JLabel("Ability:"));
        this.add(abilityComboBox);
        this.add(Box.createVerticalStrut(10)); // Add space after the component
        return abilityComboBox; // Return the created JComboBox
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds the magnitude spinner to the panel.
    public JSpinner addMagnitudeSpinner() {
        JSpinner magnitudeSpinner = new JSpinner(new SpinnerNumberModel(
                0, -10, 10, 1));
        magnitudeSpinner.setMaximumSize(new Dimension(Integer.MAX_VALUE, magnitudeSpinner.getPreferredSize().height));
        magnitudeSpinner.setBackground(buttonColor);
        magnitudeSpinner.setForeground(textColor);

        this.add(new JLabel("Magnitude:"));
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

        this.add(new JLabel("Duration:"));
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
        initializePanel(); // Set up the panel layout and background

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
        this.add(new JLabel("Select Skill:"));
        this.add(skillTypeComboBox);
        this.add(Box.createVerticalStrut(10));
        this.add(new JLabel("Proficiency:"));
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

    public void viewDetails() {
        initializePanel();

        this.add(new JLabel("Character Name: " + managerApp.getCharacterName()));
        this.add(new JLabel("Character Level: " + managerApp.getCharacterLevel()));
        this.add(returnButton());
        this.add(createTextAreaForSection(managerApp.getAbilityScoresText()));
        this.add(createTextAreaForSection(managerApp.getBuffsDebuffsText()));
        this.add(createTextAreaForSection(managerApp.getSkillsProficiencyText()));
        this.add(createTextAreaForSection(managerApp.getRecentRollsText()));

    }

    // Dice Rolls

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

    private void performSkillCheck() {
        SkillType chosenSkill = getUserSelectedSkill();
        managerApp.rollForSkillCheck(chosenSkill);
    }

    private void performAbilityCheck() {
        AbilityType chosenAbility = getUserSelectedAbility();
        managerApp.rollForAbilityCheck(chosenAbility);
    }

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

    // MODIFIES: this
    // EFFECTS:
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

        return returnButton;
    }

}
