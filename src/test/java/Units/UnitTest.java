package Units;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {

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

    @Test
    void getIsAlive() {
        Unit infantry = new InfantryUnit("Infantry", 100);
        assertTrue(infantry.getIsAlive());

        infantry.setHealth(0);
        assertFalse(infantry.getIsAlive());
    }
}