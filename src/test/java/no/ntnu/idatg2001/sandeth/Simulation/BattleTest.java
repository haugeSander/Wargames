package no.ntnu.idatg2001.sandeth.Simulation;

import no.ntnu.idatg2001.sandeth.Army.Army;
import no.ntnu.idatg2001.sandeth.Army.Units.CavalryUnit;
import no.ntnu.idatg2001.sandeth.Army.Units.InfantryUnit;
import no.ntnu.idatg2001.sandeth.Army.Units.Unit;
import no.ntnu.idatg2001.sandeth.Simulation.Battle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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