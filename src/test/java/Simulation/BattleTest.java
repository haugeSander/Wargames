package Simulation;

import Army.Army;
import Army.Units.CavalryUnit;
import Army.Units.InfantryUnit;
import Army.Units.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {
    Unit CavalryBlue;
    Unit CavalryRed;
    Army blue;
    Army red;
    Battle battle;

    @BeforeEach
    void constructor() {
        CavalryBlue = new CavalryUnit("Alfa Blue", 20);
        CavalryRed = new CavalryUnit("Alfa Red", 0);

        blue = new Army("Blue");
        red = new Army("Red");
        battle = new Battle(blue, red);

        blue.add(CavalryBlue);
        red.add(CavalryRed);
    }

    /**
     * Tests simulate method from battle class.
     */
    @Test
    void simulate() {
        red.getUnits().add(new InfantryUnit("Test", 1000));
    }

    @Test
    void testUpdateArmies() {
        battle.updateArmies(red,blue);
    }
}