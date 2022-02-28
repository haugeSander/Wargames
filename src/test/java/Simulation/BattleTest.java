package Simulation;

import Army.Army;
import Army.Units.CavalryUnit;
import Army.Units.Unit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    /**
     * Non functioning test.
     * Attempted to see the simulator return a draw.
     * Will look further into.
     */
    @Test
    void simulate() {
        Unit CavalryBlue = new CavalryUnit("Alfa Blue", 20);
        Unit CavalryRed = new CavalryUnit("Alfa Red", 20);
        Army Blue = new Army("Blue");
        Army Red = new Army("Red");
        Blue.add(CavalryBlue);
        Red.add(CavalryRed);
        Battle newBattle = new Battle(Blue, Red);

    }
}