package ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import model.*;

import java.util.*;

// This class represents the main GUI for managing character modifiers in a Dungeons & Dragons 5th Edition game.

// The application allows users to:
//         Initialize a new game character with ability scores
//         Add buffs and debuffs to the character
//         Define character's skills and proficiencies
//         View the character's details including ability scores, active buffs/debuffs, skills, and recent roll history
//         Make skill and ability checks, simulating dice rolls with the option of manual input or automatic dice roll
//         Save a character and load a previous character



public class ModifierManagerGUI extends JFrame {
    private final ModifierManagerApp managerApp;
    private final GamePanel gamePanel;
    private JPanel initialPanel;
    private JPanel actionsPanel;
    private JPanel characterCreationPanel;
    private JPanel postLoadPanel;

    private final JTextField nameField;
    private final JTextField levelField;
    private final Map<AbilityType, JTextField> abilityFields;


    private final Color backgroundColor = new Color(217,220,214);
    private final Color buttonColor = new Color(129,195,215);
    private final Color textColor = new Color(22,66,91);

    public ModifierManagerGUI(ModifierManagerApp app) {
        super("D&D 5E Modifier Manager");
        this.managerApp = app;
        this.gamePanel = new GamePanel(managerApp, this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 600);

        nameField = new JTextField();
        levelField = new JTextField();
        abilityFields = new HashMap<>();
        postLoadPanel = new JPanel();

        setupInitialPanel();
        setupActionsPanel();
        setupCharacterCreationPanel();

        getContentPane().add(initialPanel, BorderLayout.CENTER); // Start with the initial panel
        setVisible(true);
    }

    // Initial Panel


    // MODIFIES: this
    // EFFECTS: Initializes and configures the initialPanel with a BoxLayout,
    //           background color, border, title label, and buttons.
    private void setupInitialPanel() {
        initialPanel = new JPanel();
        initialPanel.setLayout(new BoxLayout(initialPanel, BoxLayout.Y_AXIS));
        initialPanel.setBackground(backgroundColor);
        initialPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addTitleAndButtonsToInitialPanel();
    }

    // EFFECTS: Adds title label and buttons to the initialPanel.
    private void addTitleAndButtonsToInitialPanel() {
        JLabel titleLabel = new JLabel("D&D 5E Modifier Manager", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBackground(backgroundColor);
        titleLabel.setForeground(textColor);

        JButton newCharacterButton = createButton("New Character");
        newCharacterButton.addActionListener(e -> switchToCharacterCreation());

        JButton loadCharacterButton = createButton("Load Character");
        loadCharacterButton.addActionListener(e -> {
            boolean loadSuccess = managerApp.loadCharacter();
            if (loadSuccess) {
                switchPanel(initialPanel, actionsPanel);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to load character.");
            }
        });

        initialPanel.add(Box.createVerticalGlue());
        initialPanel.add(titleLabel);
        initialPanel.add(Box.createVerticalStrut(20));
        initialPanel.add(newCharacterButton);
        initialPanel.add(Box.createVerticalStrut(10));
        initialPanel.add(loadCharacterButton);
        initialPanel.add(Box.createVerticalGlue());
    }

    // Character Creation Panel

    // MODIFIES: this
    // EFFECTS: Initializes and configures the main character creation panel
    //           with a specific layout and background color.
    private void setupCharacterCreationPanel() {
        characterCreationPanel = new JPanel();
        characterCreationPanel.setLayout(new BoxLayout(characterCreationPanel, BoxLayout.X_AXIS));
        characterCreationPanel.setBackground(backgroundColor);

        JPanel leftPanel = setupLeftPanel();
        JPanel abilityPanel = setupAbilityPanel();
        JPanel buttonPanel = setupButtonPanel();


        // Add leftPanel at the top, dragonPanel at the center, and buttonPanel at the bottom
        JPanel westPanel = new JPanel(new BorderLayout());
        westPanel.add(leftPanel, BorderLayout.CENTER);
        westPanel.add(buttonPanel, BorderLayout.SOUTH);

        characterCreationPanel.add(westPanel, BorderLayout.WEST);
        characterCreationPanel.add(abilityPanel, BorderLayout.CENTER);
        characterCreationPanel.setVisible(false);
    }

    // MODIFIES: this, nameField, levelField
    // EFFECTS: Creates and returns a JPanel (leftPanel) with a vertical BoxLayout.
    //          This panel contains two labeled text fields: one for the character's name and one for their level.
    private JPanel setupLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(backgroundColor);
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding


        nameField.setMaximumSize(new Dimension(500, 80));
        nameField.setBackground(backgroundColor);
        nameField.setForeground(textColor);

        levelField.setMaximumSize(new Dimension(500, 80));
        levelField.setBackground(backgroundColor);
        levelField.setForeground(textColor);

        ImageIcon imageIcon = new ImageIcon("images/pixilart-drawing.png");

        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(createLabel("Name:"));
        leftPanel.add(nameField);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(createLabel("Level:"));
        leftPanel.add(levelField);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(imageLabel); // Add the image label here
        leftPanel.add(Box.createVerticalGlue());

        return leftPanel;
    }


    // MODIFIES: this, abilityFields
    // EFFECTS: Creates and returns a JPanel (abilityPanel) with a GridBagLayout.
    //          The panel contains a set of labeled text fields, one for each AbilityType.
    private JPanel setupAbilityPanel() {
        JPanel abilityPanel = new JPanel();
        abilityPanel.setLayout(new GridBagLayout()); // For better control over component layout
        abilityPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(textColor), "Ability Scores"));
        abilityPanel.setBackground(backgroundColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        for (AbilityType ability : AbilityType.values()) {
            gbc.weighty = 1;
            abilityPanel.add(createLabel(ability.toString() + ":"), gbc);
            JTextField scoreField = new JTextField();
            scoreField.setBackground(backgroundColor);
            scoreField.setForeground(textColor);
            abilityFields.put(ability, scoreField);
            abilityPanel.add(scoreField, gbc);
        }

        return abilityPanel;
    }

    // EFFECTS: Sets up the panel containing the submit button for character creation.
    private JPanel setupButtonPanel() {
        JButton submitButton = createButton("Create Character");
        submitButton.addActionListener(e -> handleSubmitButtonAction());

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(submitButton, BorderLayout.PAGE_END);
        return buttonPanel;
    }

    // EFFECTS: Handles the action of the submit button, which involves validating input,
    //          creating a character, and switching panels.
    private void handleSubmitButtonAction() {
        try {
            String name = managerApp.validateName(nameField.getText());
            int level = managerApp.validateLevel(levelField.getText());


            Map<AbilityType, String> abilityScoresStr = new HashMap<>();
            for (AbilityType abilityType : AbilityType.values()) {
                abilityScoresStr.put(abilityType, abilityFields.get(abilityType).getText());
            }
            Map<AbilityType, Integer> abilities = managerApp.validateAbilities(abilityScoresStr);


            managerApp.initCharacter(name, level, abilities);


            switchPanel(characterCreationPanel, actionsPanel);

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(characterCreationPanel, ex.getMessage(),
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Action Panel

    // MODIFIES: this
    // EFFECTS: Initializes and configures the actionsPanel with buttons for different actions (adding buffs/debuffs,
    //         defining skills, etc.) and sets up their action listeners.
    private void setupActionsPanel() {

        initializeActionPanel();

        JButton addBuffsButton = createButton("Add Buffs/Debuffs");
        JButton defineSkillsButton = createButton("Define Skills & Proficiencies");
        JButton viewDetailsButton = createButton("View Character Details");
        JButton rollChecksButton = createButton("Roll for Checks");
        JButton quitButton = createButton("Quit");

        actionsPanel.add(Box.createVerticalGlue());

        actionsPanel.add(addBuffsButton);
        actionsPanel.add(Box.createVerticalStrut(10));
        actionsPanel.add(defineSkillsButton);
        actionsPanel.add(Box.createVerticalStrut(10));
        actionsPanel.add(viewDetailsButton);
        actionsPanel.add(Box.createVerticalStrut(10));
        actionsPanel.add(rollChecksButton);
        actionsPanel.add(Box.createVerticalStrut(10));
        actionsPanel.add(quitButton);

        actionsPanel.add(Box.createVerticalGlue());

        addBuffsButton.addActionListener(e -> switchToBuffDebuff(gamePanel));
        defineSkillsButton.addActionListener(e -> switchToSkills(gamePanel));
        viewDetailsButton.addActionListener(e -> switchToViewDetails(gamePanel));
        rollChecksButton.addActionListener(e -> switchToRoll(gamePanel));
        quitButton.addActionListener(e -> saveCharacter());


        actionsPanel.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: Clears the current GamePanel and initializes the layout and style.
    public void initializeActionPanel() {
        actionsPanel = new JPanel();
        actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.Y_AXIS));
        actionsPanel.setBackground(backgroundColor);
        actionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    // Save Character Panel

    // EFFECTS: Asks the user if they would like to save their character before quitting.
    //          If user responds with "yes", the character is saved to file. Otherwise, does nothing.
    private void saveCharacter() {
        int response = JOptionPane.showConfirmDialog(null,
                "Would you like to save your character before quitting?",
                "Save Character", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            managerApp.saveCharacter();
        }
        System.exit(0);
    }

    // EFFECTS: Removes the current panel and adds the specified panel.
    private void switchPanel(JPanel panelToRemove, JPanel panelToAdd) {
        getContentPane().remove(panelToRemove);
        getContentPane().add(panelToAdd, BorderLayout.CENTER);
        panelToAdd.setVisible(true);

        revalidate();
        repaint();
    }


    // Helpers

    // EFFECTS: Switches from the initialPanel to the characterCreationPanel
    private void switchToCharacterCreation() {
        switchPanel(initialPanel, characterCreationPanel);
    }

    // EFFECTS: Switches back to the actions panel from the gamePanel.
    public void switchToActionsPanel() {
        switchPanel(gamePanel, actionsPanel);
    }

    // EFFECTS: Switches from the postLoadPanel to the actionsPanel
    public void switchToMainMenu() {
        switchPanel(postLoadPanel, actionsPanel);
    }

    // EFFECTS: Sets up the gamePanel with the buff/debuff panel and switches to it.
    private void switchToBuffDebuff(JPanel panelToAdd) {
        gamePanel.addBuffDebuffsPanel();
        switchPanel(actionsPanel, panelToAdd);
    }

    // EFFECTS: Sets up the gamePanel with the skill panel and switches to it.
    private void switchToSkills(JPanel panelToAdd) {
        gamePanel.setupSkillsProficienciesPanel();
        switchPanel(actionsPanel, panelToAdd);
    }

    // EFFECTS: Sets up the gamePanel with the details panel and switches to it.
    private void switchToViewDetails(JPanel panelToAdd) {
        gamePanel.viewDetails();
        switchPanel(actionsPanel, panelToAdd);
    }

    // EFFECTS: Sets up the gamePanel with the roll panel and switches to it.
    private void switchToRoll(JPanel panelToAdd) {
        gamePanel.rollChecks();
        switchPanel(actionsPanel, panelToAdd);
    }


    // EFFECTS: Creates and returns a new JButton with the specified text, styled
    //          with center alignment, maximum size, background color, text color, border,
    //
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

    // EFFECTS: Creates and returns a new JLabel with the specified text, styled with a background color.
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setBackground(buttonColor);

        return label;
    }

}
