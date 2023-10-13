package model;

public class BuffDebuff {
    private String name;            // Name of the buff/debuff
    private String effect;          // Effect of the buff/debuff (e.g., "+2 to Strength checks")
    private int duration;           // How many rounds the buff/debuff lasts

    // EFFECTS: constructs a buff or debuff with name, effect, and duration.

    public BuffDebuff(String name, String effect, int duration) {
        this.name = name;
        this.effect = effect;
        this.duration = duration;
    }

    // Getters

    public String getName() {
        return this.name;
    }

    public String getEffect() {
        return this.effect;
    }

    public int getDuration() {
        return this.duration;
    }


    // Setters

    // REQUIRES: effect to be a non-empty string
    // MODIFIES: this
    // EFFECTS: sets the buff/debuff effect description
    public void setEffect(String effect) {
        this.effect = effect;
    }

    // REQUIRES: duration to be a non-negative integer
    // MODIFIES: this
    // EFFECTS: sets the buff/debuff duration
    public void setDuration(int duration) {
        this.duration = duration;
    }


    // Other

    // REQUIRES: rounds to be a positive integer
    // MODIFIES: this
    // EFFECTS: decreases the buff/debuff duration by the specified number of rounds
    public void decrementDuration(int rounds) {
        this.duration -= rounds;
        if (this.duration < 0) {
            this.duration = 0; // Ensure it doesn't go into negative values
        }
    }

    // REQUIRES: rounds to be a positive integer
    // MODIFIES: this
    // EFFECTS: increases the buff/debuff duration by the specified number of rounds
    public void incrementDuration(int rounds) {
        this.duration += rounds;
    }
}
