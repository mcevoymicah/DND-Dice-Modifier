package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.*;

class GamePanel extends JPanel {
    private ModifierManagerApp managerApp;
    private ModifierManagerGUI managerUI;


    public GamePanel(ModifierManagerApp app) {
        this.managerApp = app;
    }

    // MODIFIES: this, managerApp
    // EFFECTS: Clears the current GamePanel and adds components for buff/debuff input
    //          On user input and button click, captures the data and calls
    //           managerApp.addBuffDebuff to add the buff/debuff. Updates the GamePanel
    //           to reflect the new state.

    public void addBuffDebuffsPanel() {
        this.removeAll(); // Clear existing components
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField nameField = new JTextField(20);
        JComboBox<AbilityType> abilityComboBox = new JComboBox<>(AbilityType.values());
        JSpinner magnitudeSpinner = new JSpinner(new SpinnerNumberModel(0, -10, 10, 1));
        JSpinner durationSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JButton addButton = new JButton("Add Buff/Debuff");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                AbilityType effectAbility = (AbilityType) abilityComboBox.getSelectedItem();
                int effectMagnitude = (Integer) magnitudeSpinner.getValue();
                int duration = (Integer) durationSpinner.getValue();

                // Call a method in ModifierManagerApp to handle the addition
                managerApp.addBuffDebuff(name, effectAbility, effectMagnitude, duration);
            }
        });

        // Add components to the panel
        this.add(new JLabel("Name:"));
        this.add(nameField);
        this.add(new JLabel("Ability:"));
        this.add(abilityComboBox);
        this.add(new JLabel("Magnitude:"));
        this.add(magnitudeSpinner);
        this.add(new JLabel("Duration:"));
        this.add(durationSpinner);
        this.add(addButton);

        this.revalidate();
        this.repaint();
    }



    public void defineSkills() {

    }

    public void viewDetails() {

    }

    public void rollChecks() {

    }

    public void quitApp() {

    }
}
