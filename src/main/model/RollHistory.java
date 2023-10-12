package model;

import java.awt.*;

public class RollHistory {

    private List<Roll> rolls;            // A list of all rolls made.

    // EFFECTS: constructs a list of all rolls made

    public RollHistory(List<Roll> rolls) {
        this.rolls = rolls;
    }

    // getters

    public String getRollList() {
        return this.rolls;
    }
}
