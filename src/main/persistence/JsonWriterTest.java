package persistence;

import model.GameCharacter;
import model.AbilityType;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            GameCharacter character = new GameCharacter("John Doe");
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
            GameCharacter character = new GameCharacter("John Doe");
            // Clear default ability scores to ensure character is truly "empty"
            character.getAbilityScores().clear();

            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCharacter.json");
            writer.open();
            writer.write(character);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCharacter.json");
            character = reader.read();
            checkGameCharacter("John Doe", character, character);
            assertTrue(character.getActiveBuffsDebuffs().isEmpty());
            assertTrue(character.getSkills().isEmpty());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCharacter() {
        try {
            GameCharacter character = new GameCharacter("John Doe");
            character.updateAbilityScore(AbilityType.STRENGTH, 15);
            // You can add more attributes like skills, buffs/debuffs etc.

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCharacter.json");
            writer.open();
            writer.write(character);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCharacter.json");
            character = reader.read();
            checkGameCharacter("John Doe", character, character);
            checkAbilityScore(AbilityType.STRENGTH, 15, character.getAbilityScoreByType(AbilityType.STRENGTH));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
