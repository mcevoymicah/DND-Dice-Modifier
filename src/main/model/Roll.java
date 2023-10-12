package model;

public class Roll {

    private String type;                     // The type of roll (e.g., "Strength check", "Stealth check")
    private int baseResult;                  // The result of the dice roll before any modifiers
    private int appliedModifier;             // The total modifier applied to the roll
    private int finalOutcome;                // The result after applying the modifier

    // EFFECTS: constructs a roll with type, base result, applied modifier, and final outcome

    public Score(String type, int baseResult, int appliedModifier, int finalOutcome) {
        this.type = type;
        this.baseResult = baseResult;
        this.appliedModifier = appliedModifier;
        this.finalOutcome = finalOutcome;
    }

    // getters

    public String getType() {
        return this.type;
    }

    public int getBaseResult() {
        return this.baseResult;
    }

    public int getAppliedModifier() {
        return this.appliedModifier;
    }

    public int getFinalOutcome() {
        return this.finalOutcome;
    }

}
