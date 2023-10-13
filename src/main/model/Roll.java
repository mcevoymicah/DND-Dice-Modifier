package model;

public class Roll {

    private String type;                     // The type of roll (e.g., "Strength check", "Stealth check")
    private int baseResult;                  // The result of the dice roll before any modifiers
    private int appliedModifier;             // The total modifier applied to the roll
    private int finalOutcome;                // The result after applying the modifier

    // EFFECTS: constructs a roll with type, base result, applied modifier, and final outcome

    public Roll(String type, int baseResult, int appliedModifier) {
        this.type = type;
        this.baseResult = baseResult;
        this.appliedModifier = appliedModifier;
        computeOutcome();
    }

    // Getters

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

    // Setters

    // REQUIRES: baseResult to be a positive integer
    // MODIFIES: this
    // EFFECTS: modifies the base result of the roll and recalculates the outcome
    public void setBaseResult(int baseResult) {
        this.baseResult = baseResult;
        computeOutcome();
    }

    // REQUIRES: appliedModifier to be an integer
    // MODIFIES: this
    // EFFECTS: modifies the applied modifier and recalculates the outcome
    public void setAppliedModifier(int appliedModifier) {
        this.appliedModifier = appliedModifier;
        computeOutcome();
    }

    // MODIFIES: this
    // EFFECTS: computes the final outcome based on the base result and applied modifier
    private void computeOutcome() {
        this.finalOutcome = this.baseResult + this.appliedModifier;
    }

    // EFFECTS: returns a string description of the roll
    public String rollDescription() {
        return type + ": Rolled a " + baseResult + " with a modifier of "
                + appliedModifier + ". Final result: " + finalOutcome;
    }

}
