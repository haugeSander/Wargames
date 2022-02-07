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

        unit2.attack(unit1);
        unit1.attack(unit3);
        unit3.attack(unit4);
        unit4.attack(unit2);

        assertEquals(unit1.getAttack(), 15);
        assertEquals(unit2.getAttack(), 20);
        assertEquals(unit3.getAttack(), 20);
        assertEquals(unit4.getAttack(), 15);
    }
}