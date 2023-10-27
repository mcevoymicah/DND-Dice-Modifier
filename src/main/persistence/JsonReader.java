package persistence;

import model.*;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

// Code influence by the JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    public GameCharacter read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameCharacter(jsonObject);
    }

    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private GameCharacter parseGameCharacter(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        GameCharacter character = new GameCharacter(name);
        addAbilityScores(character, jsonObject);
        addSkills(character, jsonObject);
        addBuffsDebuffs(character, jsonObject);

        return character;
    }

    private void addAbilityScores(GameCharacter character, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("abilityScores");
        for (Object json : jsonArray) {
            JSONObject nextAbility = (JSONObject) json;
            updateAbilityScore(character, nextAbility);
        }
    }

    private void updateAbilityScore(GameCharacter character, JSONObject jsonObject) {
        AbilityType type = AbilityType.valueOf(jsonObject.getString("type"));
        int score = jsonObject.getInt("score");
        character.updateAbilityScore(type, score);
    }

    private void addSkills(GameCharacter character, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("skills");
        for (Object json : jsonArray) {
            JSONObject nextSkill = (JSONObject) json;
            addSkill(character, nextSkill);
        }
    }

    private void addSkill(GameCharacter character, JSONObject jsonObject) {
        SkillType type = SkillType.valueOf(jsonObject.getString("type"));

        AbilityType abilityType = AbilityType.valueOf(jsonObject.getString("associatedAbility"));
        AbilityScore associatedAbility = character.getAbilityScoreByType(abilityType);

        boolean isProficient = jsonObject.getBoolean("isProficient");

        Skill skill = new Skill(type, associatedAbility, isProficient);
        character.addSkill(skill);
    }


    private void addBuffsDebuffs(GameCharacter character, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("activeBuffsDebuffs");
        for (Object json : jsonArray) {
            JSONObject nextBuffDebuff = (JSONObject) json;
            addBuffDebuff(character, nextBuffDebuff);
        }
    }

    private void addBuffDebuff(GameCharacter character, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        AbilityType effectAbility = AbilityType.valueOf(jsonObject.getString("effectAbility"));
        int effectMagnitude = jsonObject.getInt("effectMagnitude");
        int duration = jsonObject.getInt("duration");

        BuffDebuff buffDebuff = new BuffDebuff(name, effectAbility, effectMagnitude, duration);
        character.addBuffDebuff(buffDebuff); // Assuming GameCharacter has a method to add a buff or debuff.
    }

}
