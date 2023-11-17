package test.persistence;

import model.*;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            GameCharacter character = new GameCharacter("Jane Doe", 10);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCharacter() {
        try {
            GameCharacter character = new GameCharacter("John Doe", 10);
            // Clear default ability scores to ensure character is truly "empty"
            character.getAbilityScores().clear();
            character.getRollHistory().clearRollHistory();

            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCharacter.json");
            writer.open();
            writer.write(character);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCharacter.json");
            character = reader.read();
            checkGameCharacter("John Doe", character.getLevel(), character, character);

            assertTrue(character.getActiveBuffsDebuffs().isEmpty());
            assertTrue(character.getSkills().isEmpty());
            assertTrue(character.getRollHistory().getRollList().isEmpty());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCharacter() {
        try {
            GameCharacter character = new GameCharacter("John Doe", 10);
            character.updateAbilityScore(AbilityType.STRENGTH, 15);
            Roll sampleRoll = new Roll("Test Roll", 10, 2);
            character.getRollHistory().addRoll(sampleRoll);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCharacter.json");
            writer.open();
            writer.write(character);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCharacter.json");
            character = reader.read();
            checkGameCharacter("John Doe", character.getLevel(), character, character);

            checkAbilityScore(AbilityType.STRENGTH, 15, character.getAbilityScoreByType(AbilityType.STRENGTH));

            // Check roll history
            Roll readRoll = character.getRollHistory().getLastRoll();
            checkRoll(sampleRoll.getType(), sampleRoll.getBaseResult(), sampleRoll.getAppliedModifier(),
                    sampleRoll.getFinalOutcome(), readRoll);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}


