package model;

import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

// Represents a collection of rolls made during the game.
// Can add, remove, and view roll history.

public class RollHistory {

    private final List<Roll> rolls;            // A list of all rolls made.

    // EFFECTS: constructs an empty list of rolls made
    public RollHistory() {
        this.rolls = new ArrayList<>();
    }

    // EFFECTS: constructs a list of rolls made with the provided rolls
    public RollHistory(List<Roll> rolls) {
        this.rolls = rolls;
    }


    // Getters

    public List<Roll> getRollList() {
        return this.rolls;
    }


    // Other

    // MODIFIES: this
    // EFFECTS: adds the given roll to the roll history
    public void addRoll(Roll roll) {
        rolls.add(roll);
    }

    // EFFECTS: returns the last roll from the roll history; returns null if the roll history is empty
    public Roll getLastRoll() {
        if (!rolls.isEmpty()) {
            return rolls.get(rolls.size() - 1);
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: clears the roll history
    public void clearRollHistory() {
        rolls.clear();
    }

    // REQUIRES: roll to be in the roll history
    // MODIFIES: this
    // EFFECTS: removes the given roll from the roll history
    public void removeRoll(Roll roll) {
        rolls.remove(roll);
    }

    // Code influence by the JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("rolls", rollsToJson());
        return json;
    }

    // EFFECTS: returns rolls in this history as a JSON array
    private JSONArray rollsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Roll r : rolls) {
            jsonArray.put(r.toJson());
        }

        return jsonArray;
    }
}

