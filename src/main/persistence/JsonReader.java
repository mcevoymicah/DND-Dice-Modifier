package persistence;

import model.*;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Code influence by the JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents a reader that reads a GameCharacter from JSON data stored in file
public class JsonReader {
    private final String source;

    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: Reads the JSON data from the source file and returns the constructed GameCharacter
    public GameCharacter read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameCharacter(jsonObject);
    }

    // EFFECTS: Returns the content of the source file as a string
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: Returns a GameCharacter parsed from the provided JSON object
    private GameCharacter parseGameCharacter(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int level = jsonObject.getInt("level");

        GameCharacter character = new GameCharacter(name, level);
        addAbilityScores(character, jsonObject);
        addSkills(character, jsonObject);
        addBuffsDebuffs(character, jsonObject);

        // Populating roll history
        if (jsonObject.has("rollHistory")) {
            JSONObject rollHistoryJsonObject = jsonObject.getJSONObject("rollHistory");
            JSONArray rollArray = rollHistoryJsonObject.getJSONArray("rolls");
            List<Roll> rolls = readRolls(rollArray);
            for (Roll roll : rolls) {
                character.addRoll(roll);
            }
        }

        return character;
    }


    // MODIFIES: character
    // EFFECTS: Updates the ability scores of the character from the JSON data
    private void addAbilityScores(GameCharacter character, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("abilityScores");
        for (Object json : jsonArray) {
            JSONObject nextAbility = (JSONObject) json;
            updateAbilityScore(character, nextAbility);
        }
    }

    // MODIFIES: character
    // EFFECTS: Updates a single ability score of the character from the JSON data
    private void updateAbilityScore(GameCharacter character, JSONObject jsonObject) {
        AbilityType type = AbilityType.valueOf(jsonObject.getString("type"));
        int score = jsonObject.getInt("score");
        character.updateAbilityScore(type, score);
    }

    // MODIFIES: character
    // EFFECTS: Adds skills to the character from the JSON data
    private void addSkills(GameCharacter character, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("skills");
        for (Object json : jsonArray) {
            JSONObject nextSkill = (JSONObject) json;
            addSkill(character, nextSkill);
        }
    }

    // MODIFIES: character
    // EFFECTS: Adds a single skill to the character from the JSON data
    private void addSkill(GameCharacter character, JSONObject jsonObject) {
        SkillType type = SkillType.valueOf(jsonObject.getString("type"));

        JSONObject associatedAbilityJson = jsonObject.getJSONObject("associatedAbility");
        AbilityType abilityType = AbilityType.valueOf(associatedAbilityJson.getString("type"));
        AbilityScore associatedAbility = character.getAbilityScoreByType(abilityType);

        boolean isProficient = jsonObject.getBoolean("isProficient");

        Skill skill = new Skill(type, associatedAbility, isProficient);
        character.addSkill(skill);
    }


    // MODIFIES: character
    // EFFECTS: Adds active buffs/debuffs to the character from the JSON data
    private void addBuffsDebuffs(GameCharacter character, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("activeBuffsDebuffs");
        for (Object json : jsonArray) {
            JSONObject nextBuffDebuff = (JSONObject) json;
            addBuffDebuff(character, nextBuffDebuff);
        }
    }

    // MODIFIES: character
    // EFFECTS: Adds a single buff/debuff to the character from the JSON data
    private void addBuffDebuff(GameCharacter character, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        AbilityType effectAbility = AbilityType.valueOf(jsonObject.getString("effectAbility"));
        int effectMagnitude = jsonObject.getInt("effectMagnitude");
        int duration = jsonObject.getInt("duration");

        BuffDebuff buffDebuff = new BuffDebuff(name, effectAbility, effectMagnitude, duration);
        character.addBuffDebuff(buffDebuff);
    }

    // EFFECTS: Returns a list of Roll objects parsed from the provided JSON array
    private List<Roll> readRolls(JSONArray jsonArray) {
        List<Roll> rolls = new ArrayList<>();

        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            Roll roll = readRoll(jsonObject);
            rolls.add(roll);
        }

        return rolls;
    }

    // EFFECTS: Returns a Roll object parsed from the provided JSON object
    private Roll readRoll(JSONObject jsonObject) {
        String type = jsonObject.getString("type");
        int baseResult = jsonObject.getInt("baseResult");
        int appliedModifier = jsonObject.getInt("appliedModifier");

        return new Roll(type, baseResult, appliedModifier);
    }
}
