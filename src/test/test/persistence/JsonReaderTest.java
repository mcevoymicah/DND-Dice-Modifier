package test.persistence;

import model.*;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonTest;

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
            assertTrue(character.getRollHistory().getRollList().isEmpty()); // check for an empty roll history
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

            List<Roll> rolls = character.getRollHistory().getRollList();
            assertEquals(2, rolls.size());
            checkRoll("Strength check", 10, 5, 15, rolls.get(0));
            checkRoll("Dexterity save", 8, 4, 12, rolls.get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }

    }

    @Test
    void testReaderCharacterWithBuffsDebuffs() {
        JsonReader reader = new JsonReader("./data/testReaderCharacterWithBuffsDebuffs.json");
        try {
            GameCharacter character = reader.read();
            checkGameCharacter("John Buffed", character, character);

            List<BuffDebuff> activeBuffsDebuffs = character.getActiveBuffsDebuffs();
            assertEquals(1, activeBuffsDebuffs.size());
            BuffDebuff buffDebuff = activeBuffsDebuffs.get(0);

            assertEquals("Strength Buff", buffDebuff.getName());
            assertEquals(AbilityType.STRENGTH, buffDebuff.getEffectAbility());
            assertEquals(2, buffDebuff.getEffectMagnitude());
            assertEquals(5, buffDebuff.getDuration());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}


