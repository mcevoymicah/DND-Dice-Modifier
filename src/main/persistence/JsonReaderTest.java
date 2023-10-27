package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            GameCharacter character = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCharacter() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCharacter.json");
        try {
            GameCharacter character = reader.read();
            checkGameCharacter("John Doe", character, character);
            assertTrue(character.getActiveBuffsDebuffs().isEmpty());
            assertTrue(character.getSkills().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCharacter() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCharacter.json");
        try {
            GameCharacter character = reader.read();
            checkGameCharacter("John Doe", character, character);

            List<AbilityScore> abilities = character.getAbilityScores();
            assertEquals(6, abilities.size());
            checkAbilityScore(AbilityType.STRENGTH, 15, abilities.get(0));
            checkAbilityScore(AbilityType.DEXTERITY, 12, abilities.get(1));

            List<Skill> skills = character.getSkills();
            assertEquals(2, skills.size());
            checkSkill(SkillType.ATHLETICS, true, skills.get(0));
            checkSkill(SkillType.ACROBATICS, false, skills.get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
