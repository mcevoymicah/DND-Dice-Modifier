package model;

public class AbilityScore {

    private String name;                    // Name of the ability score (eg. Strength)
    private int score;                      // The actual score
    private int modifier;                   // The calculated modifier based on the score

    // EFFECTS: constructs an ability with name, score, and modifier.

    public AbilityScore(String name, int score) {
        this.name = name;
        this.score = score;
        this.modifier = calculateModifier();;
    }

    // getters

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    public int getModifier() {
        return this.modifier;
    }


    // Setters

    // REQUIRES: newScore to be a positive integer
    // MODIFIES: this
    // EFFECTS:  sets the ability score to newScore and recalculates the modifier based on newScore
    public void setScore(int newScore) {
        this.score = newScore;
        this.modifier = calculateModifier();  // Recalculate the modifier whenever the score changes
    }

    // REQUIRES: modifier to be an integer (can be negative)
    // MODIFIES: this
    // EFFECTS:  sets the ability modifier to the specified value without recalculating it from the score
    public void setModifier(int modifier) {
        this.modifier = modifier;
    }


    // Other

    // EFFECTS:  calculates and returns the ability modifier based on the standard D&D 5E formula
    private int calculateModifier() {
        return Math.floorDiv(this.score - 10, 2);
    }

    // EFFECTS:  returns a string representation of the ability, including its name, score, and modifier
    public String displayAsString() {
        return name + ": " + score + " (Modifier: " + modifier + ")";
    }

}


