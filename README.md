
# D&D 5E Modifier Manager
## An Interactive Tool for Dungeon Masters

**Description:**
The D&D 5E Modifier Manager is a Java desktop application 
designed to help Dungeon Masters in calculating and 
managing modifiers in Dungeons & Dragons 5th Edition. 
It aids players and DMs in managing ability scores, 
their corresponding modifiers, buffs, and debuffs,
ensuring accurate calculations and an 
immersive gaming experience.


**Target Audience:**
The primary users are D&D 5E players and Dungeon Masters 
who want a hassle-free and efficient way to compute rolls 
and checks, ensuring accuracy and efficiency during 
gameplay.

**Why This Project:**
As a passionate player and Dungeon Master in Dungeons & 
Dragons, I've always sought tools that simplify the complex 
process. This project presents an opportunity 
to combine my passion for D&D with software design, creating 
a tool that can make the life of many Dungeon Masters easier 
and the gaming experience more immersive.


**Features:**
- **Ability Score Input & Modifier Calculation:** Enter and update ability scores with automatic modifier calculation.
- **Buff & Debuff Management:** Easily add, track, or remove active buffs and debuffs.
- **Skill Proficiency & Checks:** Select skills, track proficiency, and incorporate their modifiers into rolls.
- **Roll Checks: Ability & Skills** Roll for various skill and ability checks incorporating all relevant modifiers, proficiency bonuses, and buffs/debuffs.
- **Roll History:** Maintain a record of rolls, showing the base result, applied modifiers, and final outcome.

## User Stories


### Adding Elements to Collections:
1. **Set Ability Scores:**
    - As a user, I want to input my character's six main ability scores and receive the corresponding modifiers.

2. **Add Buffs/Debuffs:**
    - As a user, I want to add custom buffs or debuffs detailing their effects and duration.

3. **Define Skills & Proficiencies:**
    - As a user, I want to select which skills my character is proficient in

### Viewing Lists:
4. **View Ability Scores, Modifiers & Skills:**
    - As a user, I want to see my character's ability scores, their modifiers, and the skills they're proficient in.

5. **View Active Buffs/Debuffs:**
    - As a user, I want to view a list of active buffs and debuffs with their effects and duration.

6. **View Roll History:**
    - As a user, I want to check a list of my previous rolls with their given type and applied modifier.

### Specific Actions:
7. **Roll for Checks:**
    - As a user, when prompted for a skill or ability check, I want the system to consider my ability modifier, 
   any proficiency bonus, and other relevant buffs/debuffs, then show the result.

8. **Input Custom Die Result:**
    - As a user, I want the option to input my own die result instead of relying on the system's roll.

### Data Persistence:
9. **Save Character:**
    - As a user, when I select the quit option from the application menu, I want to be reminded to save my 
   character to file and have the option to do so or not.

10. **Load Character:**
    - As a user, when I start the application, I want to be given the option to load my character from file.

# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by adding buffs 
or debuffs to a character. This is done by selecting the "Add Buff/Debuff" option in the application menu, where you 
can input details like the name, effect, and duration of the buff/debuff.


- You can generate the second required action related to the user story "adding multiple Xs to a Y" by 
selecting skills in which the character is proficient. This is achieved through the "Define Skills 
& Proficiencies" feature, where you can check or uncheck skills to indicate


- You can locate my visual component by selecting create character after starting the application, where there will be
red pixel dragon. 


- You can save the state of my application by selecting the quit option from the application menu, where you are then
  given the option to save your character to file or not.


- You can reload the state of my application by starting the application where you are then given the option to load 
the previous character 

## Phase 4: Task 2

Fri Dec 01 00:58:35 PST 2023  
GameCharacter created with name: karl and level: 10

Fri Dec 01 00:58:44 PST 2023  
Buff/Debuff added to karl: stronk (STRENGTH, 10, Duration: 1 rounds)

Fri Dec 01 00:58:49 PST 2023  
STRENGTH check rolled: 19

Fri Dec 01 00:58:49 PST 2023  
Buff/Debuff expired for karl: stronk

Fri Dec 01 00:58:52 PST 2023  
STRENGTH check rolled: 2

Fri Dec 01 00:58:56 PST 2023  
Skill check rolled for ATHLETICS: 14

Fri Dec 01 00:59:02 PST 2023  
Skill added to karl: ATHLETICS

Fri Dec 01 00:59:10 PST 2023  
Skill check rolled for ATHLETICS: 17

Fri Dec 01 00:59:14 PST 2023  
Character saved: karl
