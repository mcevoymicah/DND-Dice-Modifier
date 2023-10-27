package model;

import org.json.JSONObject;

// Represents a specific buff or debuff that can be applied to a character.
// Contains name, effect, and duration of the buff/debuff.

public class BuffDebuff {
    private final String name;           // Name of the buff/debuff
    private AbilityType effectAbility;   // The ability affected by the buff/debuff
    private int effectMagnitude;         // The magnitude of the effect (e.g., +2 or -3)
    private int duration;                // How many rounds the buff/debuff lasts

    // EFFECTS: constructs a buff or debuff with name, effect ability, magnitude, and duration.
    public BuffDebuff(String name, AbilityType effectAbility, int effectMagnitude, int duration) {
        this.name = name;
        this.effectAbility = effectAbility;
        this.effectMagnitude = effectMagnitude;
        this.duration = duration;
    }

    // Getters

    public String getName() {
        return this.name;
    }

    public AbilityType getEffectAbility() {
        return this.effectAbility;
    }

    public int getEffectMagnitude() {
        return this.effectMagnitude;
    }

    public int getDuration() {
        return this.duration;
    }

    public String getDescription() {
        return name + " (" + effectAbility + ", " + effectMagnitude + ", Duration: " + duration + " rounds)";
    }


    // Setters

    // MODIFIES: this
    // EFFECTS: sets the ability affected by the buff/debuff
    public void setEffectAbility(AbilityType effectAbility) {
        this.effectAbility = effectAbility;
    }

    // MODIFIES: this
    // EFFECTS: sets the magnitude of the effect of the buff/debuff
    public void setEffectMagnitude(int effectMagnitude) {
        this.effectMagnitude = effectMagnitude;
    }

    // REQUIRES: duration to be a non-negative integer
    // MODIFIES: this
    // EFFECTS: sets the buff/debuff duration
    public void setDuration(int duration) {
        this.duration = duration;
    }

    // Other

    // REQUIRES: rounds to be a non-negative integer
    // MODIFIES: this
    // EFFECTS: decreases the buff/debuff duration by the specified number of rounds
    public void decrementDuration(int rounds) {
        this.duration -= rounds;
        if (this.duration < 0) {
            this.duration = 0; // Ensure it doesn't go into negative values
        }
    }

    // REQUIRES: rounds to be a non-negative integer
    // MODIFIES: this
    // EFFECTS: increases the buff/debuff duration by the specified number of rounds
    public void incrementDuration(int rounds) {
        this.duration += rounds;
    }

    // Code influence by the JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("effectAbility", effectAbility.toString());
        json.put("effectMagnitude", effectMagnitude);
        json.put("duration", duration);
        return json;
    }

}