package Army;

import Army.Units.CavalryUnit;
import Army.Units.CommanderUnit;
import Army.Units.InfantryUnit;
import Army.Units.RangedUnit;
import Army.Units.Unit;
import Army.Units.UnitFactory;
import java.io.File;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArmyTest {
    private List<Unit> list;
    private Unit infantry1;
    private Unit cavalry1;
    private Unit commander1;
    private Unit ranged1;
    private Army armyOne;

    @BeforeEach
    void constructor() {
        list = new ArrayList<>();
        armyOne = new Army("ArmyOne");
        infantry1 = new InfantryUnit("Alpha", 100);
        ranged1 = new RangedUnit("Bravo", 100);
        cavalry1 = new CavalryUnit("Charlie", 100);
        commander1 = new CommanderUnit("Delta", 100);
    }

    /**
     * Adds a single unit to list, only available to getRandom() is the newly added.
     * Checks if it is selected and then removes it to test remove().
     */
    @Test
    void getRandomAndRemove() {
        armyOne.add(infantry1);
        Unit temp = armyOne.getRandom();

        assertEquals(temp, infantry1);

        armyOne.remove(infantry1);
        assertNull(armyOne.getRandom());

        NullPointerException unitDoesNotExist = Assertions.assertThrows(NullPointerException.class, () ->
            armyOne.remove(ranged1), "Unit does not exist.");
    }

    /**
     * Tests if addAll method works.
     * Create and adds to an arrayList.
     */
    @Test
    void addAll() {
        list.add(infantry1);
        list.add(ranged1);

        assertFalse(armyOne.hasUnits());
        armyOne.addAll(list);

        assertTrue(armyOne.hasUnits()); //Returns true if any units are added to army list.
    }

    /**
     * Tests saving of files as well as writing.
     */
    @Test
    void testSaveAndRead() {
        armyOne.add(infantry1);
        armyOne.add(ranged1);
        List<Army> test = new ArrayList<>();
        test.add(armyOne);

        try {
            FileHandler.writeFile(test, new File(armyOne.getName()));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        Army readFromFile = FileHandler.readFile("ArmyOne.csv");
        assertEquals(armyOne.toString(), readFromFile.toString());

        Army armyTwo = new Army("Russia");
        test.add(armyTwo);

        try {
            FileHandler.writeFile(test, new File(armyOne.getName()));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        assertNotEquals(FileHandler.readFile(armyTwo.getName()).toString(), armyOne.toString());
        assertEquals(FileHandler.readFile("Russia.csv").toString(),armyTwo.toString());
    }

    /**
     * Tests the four methods in Army,
     * which returns a list of a specified class type.
     */
    @Test
    void getInfantryUnit() {
        armyOne.add(infantry1);
        armyOne.add(ranged1);
        armyOne.add(cavalry1);
        armyOne.add(commander1);

        list.add(infantry1);
        assertEquals(armyOne.getInfantryUnits(), list);
        armyOne.remove(infantry1);
        assertNotEquals(armyOne.getInfantryUnits(), list);
    }

    @Test
    void setName() {
        armyOne.setName("Test");
        assertEquals(armyOne.getName(), "Test");

        IllegalArgumentException test = Assertions.assertThrows(IllegalArgumentException.class, () ->
        armyOne.setName(""), "Name cannot be null or empty.");
    }

    @Test
    void getRangedUnit() {
        armyOne.add(infantry1);
        armyOne.add(ranged1);
        armyOne.add(cavalry1);
        armyOne.add(commander1);

        list.add(ranged1);
        assertEquals(armyOne.getRangedUnits(), list);
        armyOne.remove(ranged1);
        assertNotEquals(armyOne.getRangedUnits(), list);
    }

    @Test
    void getCavalryUnit() {
        armyOne.add(infantry1);
        armyOne.add(ranged1);
        armyOne.add(cavalry1);
        armyOne.add(commander1);

        list.add(cavalry1);
        assertEquals(armyOne.getCavalryUnits(), list);
        armyOne.remove(cavalry1);
        assertNotEquals(armyOne.getCavalryUnits(), list);
    }

    @Test
    void getCommanderUnit() {
        armyOne.add(infantry1);
        armyOne.add(ranged1);
        armyOne.add(cavalry1);
        armyOne.add(commander1);

        list.add(commander1);
        assertEquals(armyOne.getCommanderUnits(), list);
        armyOne.remove(commander1);
        assertNotEquals(armyOne.getCommanderUnits(), list);
    }

    @Test
    void getSortedList() {
        armyOne.addAll(UnitFactory.createListOfUnits("InfantryUnit", "Test", 10, 10));
        armyOne.addAll(UnitFactory.createListOfUnits("RangedUnit", "Test", 10, 10));

        Map<String, List<Unit>> test = armyOne.getSortedList();

        System.out.println(test.get(armyOne.getUnits().get(1).toString()));

        assertEquals(test.get(armyOne.getUnits().get(0).toString()).size(), 10);
        assertEquals(test.get(armyOne.getUnits().get(1).toString()).size(), 10);
    }
}
