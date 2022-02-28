package Army;

import Army.Units.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {

    @BeforeEach
    void createUnits() {
        Unit unit1 = new InfantryUnit("Alpha", 40);
        Unit unit2 = new CommanderUnit("Bravo-Six", 120);
        Unit unit3 = new RangedUnit("Charlie", 30);
        Unit unit4 = new CavalryUnit("Delta", 100);
    }

    /**
     * Checks if attack value is correct.
     */
    @Test
    void getAttack() {
        Unit unit1 = new InfantryUnit("Alpha", 40);
        Unit unit2 = new CommanderUnit("Bravo-Six", 120);
        Unit unit3 = new RangedUnit("Charlie", 30);
        Unit unit4 = new CavalryUnit("Delta", 100);

        assertEquals(unit1.getAttack(), 15);
        assertEquals(unit2.getAttack(), 20);
        assertEquals(unit3.getAttack(), 20);
        assertEquals(unit4.getAttack(), 15);
    }

    /**
     * Checks first the attackBonus and then attacks once.
     * Checks again after attack to see change of attackBonus work.
     */
    @Test
    void getAttackBonus() {
        Unit infantry = new InfantryUnit("Alpha", 40);
        Unit commander = new CommanderUnit("Bravo-Six", 120);
        Unit range = new RangedUnit("Charlie", 30);
        Unit cavalry = new CavalryUnit("Delta", 100);

        assertEquals(infantry.getAttackBonus(), 1);
        assertEquals(commander.getAttackBonus(), 6);
        assertEquals(range.getAttackBonus(), 3);
        assertEquals(cavalry.getAttackBonus(), 6);

        cavalry.attack(range);

        assertEquals(range.getAttackBonus(), 0);
        assertEquals(cavalry.getAttackBonus(), 1);
    }

    /**
     * Checks if health is correctly calculated after attacks.
     */
    @Test
    void getHealth() {
        Unit infantry = new InfantryUnit("Infantry", 100);
        assertEquals(infantry.getHealth(), 100);
        Unit ranger = new RangedUnit("Ranger", 100);
        assertEquals(ranger.getHealth(), 100);

        infantry.attack(ranger);
        assertEquals(ranger.getHealth(), (100 - (infantry.getAttack() + infantry.getAttackBonus()) +
                (ranger.getArmor() + ranger.getResistBonus())));
    }

    /**
     * Checks that hits dealt/taken increments after attacks.
     */
    @Test
    void getHitsTakenOrDealt() {
        Unit infantry = new InfantryUnit("Infantry", 100);
        assertEquals(infantry.getHitsTaken(), 0);
        assertEquals(infantry.getHitsDealt(), 0);
        Unit ranger = new RangedUnit("Ranger", 100);
        assertEquals(ranger.getHitsTaken(), 0);
        assertEquals(ranger.getHitsDealt(), 0);

        ranger.attack(infantry);
        assertEquals(infantry.getHitsTaken(), 1);
        assertEquals(ranger.getHitsDealt(), 1);
        assertEquals(ranger.getHitsTaken(), 0);
    }

    /**
     * Checks if a unit is set to isAlive = false, when hp < 0.
     */
    @Test
    void getIsAlive() {
        Unit infantry = new InfantryUnit("Infantry", 100);
        assertTrue(infantry.getIsAlive());

        infantry.setHealth(0);
        assertFalse(infantry.getIsAlive());
    }

    /**
     * Tests setHealth and setName. Negative test which expects a thrown exception for name
     * and hp being set to 0.
     */
    @Test
    void setHealthAndName() {
        CommanderUnit commander = new CommanderUnit("Commadore", 10);

        NullPointerException name_cannot_be_null = Assertions.assertThrows(NullPointerException.class, () ->
        commander.setName(""), "Name cannot be null");

        commander.setHealth(-5);
        assertEquals(commander.getHealth(), 0);
    }
}