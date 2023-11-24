package ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import model.*;

import java.util.*;


public class ModifierManagerGUI extends JFrame {
    private final ModifierManagerApp managerApp;
    private GamePanel gamePanel;
    private JPanel initialPanel;
    private JPanel actionsPanel;
    private JPanel characterCreationPanel;
    private JPanel loadCharacterPanel;

    private JTextField nameField;
    private JTextField levelField;
    private Map<AbilityType, JTextField> abilityFields;


    private final Color backgroundColor = new Color(198, 172, 143);
    private final Color buttonColor = new Color(234, 224, 213);
    private final Color textColor = new Color(38, 35, 31);

    public ModifierManagerGUI(ModifierManagerApp app) {
        super("D&D 5E Modifier Manager");
        this.managerApp = app;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);

        nameField = new JTextField();
        levelField = new JTextField();
        abilityFields = new HashMap<>();

        setupInitialPanel();
        setupActionsPanel();
        setupCharacterCreationPanel();
        setuploadCharacterPanel();

        getContentPane().add(initialPanel, BorderLayout.CENTER); // Start with the initial panel
        setVisible(true);
    }

    // Initial Panel

    // REQUIRES: This method does not have specific requirements.
    // MODIFIES: this
    // EFFECTS: Initializes and configures the initialPanel with a BoxLayout, background color, and border.
    //           Creates, adds, and sets up the "New Character" button, and "Load Character"
    //           button to the initialPanel.
    private void setupInitialPanel() {
        initialPanel = new JPanel();
        initialPanel.setLayout(new BoxLayout(initialPanel, BoxLayout.Y_AXIS));
        initialPanel.setBackground(backgroundColor);
        initialPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("D&D 5E Modifier Manager", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBackground(backgroundColor);
        titleLabel.setForeground(textColor);


        JButton newCharacterButton = createButton("New Character");
        JButton loadCharacterButton = createButton("Load Character");


        initialPanel.add(Box.createVerticalGlue());
        initialPanel.add(titleLabel);
        initialPanel.add(Box.createVerticalStrut(20));
        initialPanel.add(newCharacterButton);
        initialPanel.add(Box.createVerticalStrut(10));
        initialPanel.add(loadCharacterButton);
        initialPanel.add(Box.createVerticalGlue());

        newCharacterButton.addActionListener(e -> switchToCharacterCreation());
        loadCharacterButton.addActionListener(e -> switchToLoadCharacter());
    }

    // MODIFIES: this
    // EFFECTS: Switches from the initialPanel to the characterCreationPanel
    private void switchToCharacterCreation() {
        getContentPane().remove(initialPanel);
        getContentPane().add(characterCreationPanel, BorderLayout.CENTER);
        characterCreationPanel.setVisible(true);
        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: Switches from the initialPanel to the loadCharacterPanel
    private void switchToLoadCharacter() {
        getContentPane().remove(initialPanel);
        getContentPane().add(loadCharacterPanel, BorderLayout.CENTER);
        loadCharacterPanel.setVisible(true);
        revalidate();
        repaint();
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

    private JPanel setupLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(backgroundColor);
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding

        Dimension textFieldSize = new Dimension(500, 80); // Preferred size for text fields
        nameField.setMaximumSize(textFieldSize);
        nameField.setBackground(backgroundColor);
        nameField.setForeground(textColor);

        levelField.setMaximumSize(textFieldSize);
        levelField.setBackground(backgroundColor);
        levelField.setForeground(textColor);

        leftPanel.add(Box.createVerticalGlue()); // Pushes components to the center
        leftPanel.add(createLabel("Name:"));
        leftPanel.add(nameField);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer between Name and Level
        leftPanel.add(createLabel("Level:"));
        leftPanel.add(levelField);
        leftPanel.add(Box.createVerticalGlue()); // Pushes components to the center

        return leftPanel;
    }

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

    private JPanel setupButtonPanel() {
        JButton submitButton = createButton("Create Character");

        submitButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                if (name.isEmpty()) {
                    throw new IllegalArgumentException("Name field cannot be empty.");
                }

                int level;
                if (levelField.getText().isEmpty() || !levelField.getText().matches("\\d+")) {
                    throw new IllegalArgumentException("Level must be a numeric value.");
                } else {
                    level = Integer.parseInt(levelField.getText());
                }

                Map<AbilityType, Integer> abilities = new HashMap<>();
                for (AbilityType ability : AbilityType.values()) {
                    JTextField abilityField = abilityFields.get(ability);
                    if (abilityField.getText().isEmpty() || !abilityField.getText().matches("\\d+")) {
                        throw new IllegalArgumentException(ability.toString() + " must be a numeric value.");
                    } else {
                        int score = Integer.parseInt(abilityField.getText());
                        abilities.put(ability, score);
                    }
                }

                managerApp.initCharacter(name, level, abilities);
                switchToActionsPanelFromCreation();
            } catch (IllegalArgumentException ex) {
                // Show an error dialog or update an error message label
                JOptionPane.showMessageDialog(characterCreationPanel, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(submitButton, BorderLayout.PAGE_END);
        return buttonPanel;
    }


    // MODIFIES: this
    // EFFECTS: Removes the characterPanel and adds the actionsPanel
    private void switchToActionsPanelFromCreation() {
        getContentPane().remove(characterCreationPanel);
        getContentPane().add(actionsPanel, BorderLayout.CENTER);
        actionsPanel.setVisible(true);
        revalidate();
        repaint();
    }

    // Load Character Panel

    // MODIFIES:
    // EFFECTS: Loads a character
    private void setuploadCharacterPanel() {
        loadCharacterPanel = new JPanel();
        loadCharacterPanel.setLayout(new BoxLayout(initialPanel, BoxLayout.Y_AXIS));
        loadCharacterPanel.setBackground(backgroundColor);
        loadCharacterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    // MODIFIES: this
    // EFFECTS: Removes the characterPanel and adds the actionsPanel
    private void switchToActionsPanelFromLoad() {
        getContentPane().remove(loadCharacterPanel);
        getContentPane().add(actionsPanel, BorderLayout.CENTER);
        actionsPanel.setVisible(true);
        revalidate();
        repaint();
    }

    // Action Panel

    // MODIFIES: this
    // EFFECTS: Initializes and configures the actionsPanel with buttons for different actions (adding buffs/debuffs,
    //         defining skills, etc.) and sets up their action listeners.

    private void setupActionsPanel() {
        actionsPanel = new JPanel();
        actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.Y_AXIS));
        actionsPanel.setBackground(backgroundColor);
        actionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
        actionsPanel.add(Box.createVerticalStrut(10));

        actionsPanel.add(Box.createVerticalGlue());

        addBuffsButton.addActionListener(e -> gamePanel.addBuffDebuffsPanel());
        addBuffsButton.addActionListener(e -> gamePanel.defineSkills());
        addBuffsButton.addActionListener(e -> gamePanel.viewDetails());
        addBuffsButton.addActionListener(e -> gamePanel.rollChecks());
        addBuffsButton.addActionListener(e -> quitApp());


        actionsPanel.setVisible(false);
    }

    // Save Character Panel

    private void saveCharacter() {
        // Implementation to save the current character
    }

    // Quit Panel

    private void quitApp() {

    }


    // Constants

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
