package no.ntnu.idatg2001.wargames.army.units;

import no.ntnu.idatg2001.wargames.army.units.Bonuses;
import no.ntnu.idatg2001.wargames.army.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.army.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.army.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.army.units.RangedUnit;
import no.ntnu.idatg2001.wargames.army.units.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for unit and children classes.
 * @author Sander Hauge
 * @version 1.0-SNAPSHOT
 *
 */
class UnitTest {
    private Unit infantry;
    private Unit commander;
    private Unit ranged;
    private Unit cavalry;

    /**
     * Creates objects used for tests before they are run.
     * Sort of a constructor.
     */
    @BeforeEach
    void createUnits() {
        infantry = new InfantryUnit("Alpha", 100);
        commander = new CommanderUnit("Bravo-Six", 100);
        ranged = new RangedUnit("Charlie", 100);
        cavalry = new CavalryUnit("Delta", 100);
    }

    /**
     * Checks if attack value is correct.
     */
    @Test
    void getAttack() {
        assertEquals(15, infantry.getAttack());
        assertEquals(25, commander.getAttack());
        assertEquals(15, ranged.getAttack());
        assertEquals(20, cavalry.getAttack());
    }

    /**
     * Checks if health is correctly calculated after attacks.
     */
    @Test
    void getHealth() {
        assertEquals(100, infantry.getHealth());
        assertEquals(100, ranged.getHealth());

        infantry.attack(ranged);
        assertEquals(ranged.getHealth(), (100 - (infantry.getAttack() + infantry.getAttackBonus()) +
            (ranged.getArmor() + ranged.getResistBonus())));
    }

    /**
     * Checks if a unit is set to isAlive = false, when hp < 0.
     */
    @Test
    void getIsAlive() {
        assertTrue(infantry.getIsAlive());
        infantry.setHealth(0);
        assertFalse(infantry.getIsAlive());
    }

    /**
     * Checks first the attackBonus and then attacks once.
     * Checks again after attack to see change of attackBonus work.
     */
    @Test
    void getBaseAttackBonus() {
        assertEquals(0, infantry.getAttackBonus());
        assertEquals(6, commander.getAttackBonus());
        assertEquals(6, ranged.getAttackBonus());
        assertEquals(6, cavalry.getAttackBonus());

        cavalry.attack(ranged);

        assertEquals(0, ranged.getAttackBonus());
        assertEquals(1, cavalry.getAttackBonus());
    }

    /**
     * Tests initial resist bonus.
     */
    @Test
    void getBaseResistBonus() {
        assertEquals(1, infantry.getResistBonus());
        assertEquals(6, ranged.getResistBonus());
        assertEquals(0, cavalry.getResistBonus());
        assertEquals(0, commander.getResistBonus());
    }

    @Test
    void getAndSetTerrain() {
        assertNull(infantry.getTerrain());
        infantry.setTerrain("Hill");
        assertEquals(Bonuses.terrain.HILL, infantry.getTerrain());
        assertNotEquals(Bonuses.terrain.HILL, commander.getTerrain());

        Assertions.assertThrows(IllegalArgumentException.class, () ->
            infantry.setTerrain("Mountain"), "Mountain is an invalid terrain!");
    }

    /**
     * Tests terrain bonuses.
     */
    @Test
    void getAttackBonusWithTerrainBonus() {
        infantry.setTerrain("Forest");
        assertEquals(Bonuses.terrain.FOREST, infantry.getTerrain());
        assertEquals(3, infantry.getAttackBonus());
    }

    /**
     * Checks that hits dealt/taken increments after attacks.
     */
    @Test
    void getHitsTakenOrDealt() {
        assertEquals(0, infantry.getHitsTaken());
        assertEquals(0, infantry.getHitsDealt());
        assertEquals(0, ranged.getHitsTaken());
        assertEquals(0, ranged.getHitsDealt());

        ranged.attack(infantry);

        assertEquals(1, infantry.getHitsTaken());
        assertEquals(1, ranged.getHitsDealt());
        assertEquals(0, ranged.getHitsTaken());
    }

    /**
     * Tests setHealth and setName. Negative test which expects a thrown exception for name
     * and hp being set to 0.
     */
    @Test
    void setHealthAndName() {
        Assertions.assertThrows(NullPointerException.class, () ->
        commander.setName(""), "Name cannot be null or empty!");

        commander.setHealth(-5);
        assertEquals(0, commander.getHealth());
    }
}