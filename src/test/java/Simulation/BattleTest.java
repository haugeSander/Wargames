package Simulation;

import Army.Army;
import Army.Units.CavalryUnit;
import Army.Units.InfantryUnit;
import Army.Units.Unit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    /**
     * Tests simulate method from battle class.
     */
    @Test
    void simulate() {
        Unit CavalryBlue = new CavalryUnit("Alfa Blue", 20);
        Unit CavalryRed = new CavalryUnit("Alfa Red", 0);

        Army Blue = new Army("Blue");
        Army Red = new Army("Red");

        Battle newBattle = new Battle(Blue, Red);
        Blue.add(CavalryBlue);
        Red.add(CavalryRed);

        assertEquals(newBattle.simulate(), Blue);

        Red.getUnits().add(new InfantryUnit("Test", 1000));

        assertEquals(newBattle.simulate(), Red);
    }
}