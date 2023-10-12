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

    // getters

    public String getName() {
        return this.name;
    }

    public int getEffect() {
        return this.effect;
    }

    public int getDuration() {
        return this.duration;
    }
}
