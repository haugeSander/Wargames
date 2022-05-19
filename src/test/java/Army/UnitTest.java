package Army;

import Army.Units.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {
    private Unit infantry;
    private Unit commander;
    private Unit ranged;
    private Unit cavalry;

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
        assertEquals(infantry.getAttack(), 15);
        assertEquals(commander.getAttack(), 20);
        assertEquals(ranged.getAttack(), 20);
        assertEquals(cavalry.getAttack(), 15);
    }

    /**
     * Checks if health is correctly calculated after attacks.
     */
    @Test
    void getHealth() {
        assertEquals(infantry.getHealth(), 100);
        assertEquals(ranged.getHealth(), 100);

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
        assertEquals(infantry.getAttackBonus(), 0);
        assertEquals(commander.getAttackBonus(), 6);
        assertEquals(ranged.getAttackBonus(), 1);
        assertEquals(cavalry.getAttackBonus(), 6);

        cavalry.attack(ranged);

        assertEquals(ranged.getAttackBonus(), 0);
        assertEquals(cavalry.getAttackBonus(), 1);
    }

    /**
     * Tests initial resist bonus.
     */
    @Test
    void getBaseResistBonus() {
        assertEquals(infantry.getResistBonus(), 1);
        assertEquals(ranged.getResistBonus(), 6);
        assertEquals(cavalry.getResistBonus(), 0);
        assertEquals(commander.getResistBonus(), 0);
    }

    @Test
    void getAndSetTerrain() {
        assertNull(infantry.getTerrain());
        infantry.setTerrain("Hill");
        assertEquals(infantry.getTerrain(), Bonuses.terrain.HILL);
        assertNotEquals(commander.getTerrain(), Bonuses.terrain.HILL);
        IllegalArgumentException invalidTerrain = Assertions.assertThrows(IllegalArgumentException.class, () ->
            infantry.setTerrain("Mountain"), "Mountain is an invalid terrain!");
    }

    /**
     * Tests terrain bonuses.
     */
    @Test
    void getAttackBonusWithTerrainBonus() {
        infantry.setTerrain("Forest");
        assertEquals(infantry.getTerrain(), Bonuses.terrain.FOREST);
        assertEquals(infantry.getAttackBonus(), 3);
    }

    /**
     * Checks that hits dealt/taken increments after attacks.
     */
    @Test
    void getHitsTakenOrDealt() {
        assertEquals(infantry.getHitsTaken(), 0);
        assertEquals(infantry.getHitsDealt(), 0);
        assertEquals(ranged.getHitsTaken(), 0);
        assertEquals(ranged.getHitsDealt(), 0);

        ranged.attack(infantry);

        assertEquals(infantry.getHitsTaken(), 1);
        assertEquals(ranged.getHitsDealt(), 1);
        assertEquals(ranged.getHitsTaken(), 0);
    }

    /**
     * Tests setHealth and setName. Negative test which expects a thrown exception for name
     * and hp being set to 0.
     */
    @Test
    void setHealthAndName() {
        NullPointerException name_cannot_be_null = Assertions.assertThrows(NullPointerException.class, () ->
        commander.setName(""), "Name cannot be null");

        commander.setHealth(-5);
        assertEquals(commander.getHealth(), 0);
    }
}