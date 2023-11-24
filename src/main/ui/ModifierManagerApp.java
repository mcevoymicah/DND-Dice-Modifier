package ui;

import model.*;
import persistence.*;


import java.util.List;
import java.util.Map;
import java.util.Scanner;

import java.io.*;

// This class represents

public class ModifierManagerApp {
    private GameCharacter character;
    private static final String JSON_STORE = "./data/character.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: runs the D&D Modifier Manager application
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

    // Buffs/Debuffs

    // MODIFIES: this, character
    // EFFECTS: Receives the details of a new buff or debuff and
    // adds it to the character's active buffs/debuffs list.
    public void addBuffDebuff(String name, AbilityType effectAbility, int effectMagnitude, int duration) {
        BuffDebuff newBuffDebuff = new BuffDebuff(name, effectAbility, effectMagnitude, duration);
        character.addBuffDebuff(newBuffDebuff);
    }

    // Skills & Proficiencies

    // Roll for Checks

    // Save/Loading


}
