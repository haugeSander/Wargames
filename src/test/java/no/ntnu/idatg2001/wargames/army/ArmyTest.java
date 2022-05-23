package no.ntnu.idatg2001.wargames.army;

import no.ntnu.idatg2001.wargames.army.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.army.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.army.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.army.units.RangedUnit;
import no.ntnu.idatg2001.wargames.army.units.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArmyTest {
    private List<Unit> list;
    private Unit infantry1;
    private Unit cavalry1;
    private Unit commander1;
    private Unit ranged1;
    private Army armyOne;

    /**
     * Creates objects used for tests before they are run.
     * Sort of a constructor.
     */
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

        Assertions.assertThrows(NullPointerException.class, () ->
            armyOne.remove(ranged1), "Unit does not exist.");
    }

    /**
     * Tests the add method in Army.
     */
    @Test
    void add() {
        assertFalse(armyOne.hasUnits());
        armyOne.add(infantry1);
        assertTrue(armyOne.hasUnits());
        assertEquals(armyOne.getUnits().get(0), infantry1);
    }

    /**
     * Tests if addAll method works.
     * Create and adds to an arrayList.
     */
    @Test
    void addAll() {
        assertFalse(armyOne.hasUnits());

        list.add(infantry1);
        list.add(ranged1);
        armyOne.addAll(list);

        assertTrue(armyOne.hasUnits()); //Returns true if any units are added to army list.
    }

    /**
     * Tests the setName method in Army.
     */
    @Test
    void setName() {
        armyOne.setName("Test");
        assertEquals("Test", armyOne.getName());

        Assertions.assertThrows(IllegalArgumentException.class, () ->
        armyOne.setName(""), "Name cannot be null or empty.");
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
}
