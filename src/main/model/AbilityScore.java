package model;

// Represents one of the six main ability scores in D&D 5E.
// Contains details about the ability's type, its base value,
// and provides methods to calculate the modifier based on the score.

public class AbilityScore {

    private final AbilityType type;   // Type of the ability (e.g., STRENGTH)
    private int score;                // The actual score
    private int modifier;             // The calculated modifier based on the score

    // EFFECTS: constructs an ability with type and score.

    public AbilityScore(AbilityType type, int score) {
        this.type = type;
        setScore(score);  // Automatically sets the modifier based on the score
    }

    // Getters

    public AbilityType getType() {
        return this.type;
    }

    public int getScore() {
        return this.score;
    }

    public int getModifier() {
        return this.modifier;
    }

    // Setters

    // MODIFIES: this
    // EFFECTS:  sets the ability score and recalculates the modifier based on the score
    public void setScore(int score) {
        this.score = score;
        this.modifier = calculateModifier();  // Recalculate the modifier whenever the score changes
    }

    // EFFECTS:  calculates and returns the ability modifier based on the standard D&D 5E formula
    private int calculateModifier() {
        return Math.floorDiv(this.score - 10, 2);
    }

    // EFFECTS:  returns a string representation of the ability, including its type, score, and modifier
    public String displayAsString() {
        return type + ": " + score + " (Modifier: " + modifier + ")";
    }

}
