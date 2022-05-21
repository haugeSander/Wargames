package no.ntnu.idatg2001.sandeth.Simulation;

import no.ntnu.idatg2001.sandeth.Army.Army;
import no.ntnu.idatg2001.sandeth.Army.Units.Bonuses;
import no.ntnu.idatg2001.sandeth.Army.Units.CavalryUnit;
import no.ntnu.idatg2001.sandeth.Army.Units.InfantryUnit;
import no.ntnu.idatg2001.sandeth.Army.Units.Unit;
import org.junit.jupiter.api.Assertions;
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
        CavalryRed = new CavalryUnit("Alfa Red", 20);

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
        assertEquals(battle.simulate(), red.getName());
    }

    @Test
    void simulateStep() {
        blue.getUnits().clear();
        red.getUnits().clear();

        NullPointerException noUnitsToFight = Assertions.assertThrows(NullPointerException.class, () ->
            battle.simulateStep(blue, red), "");
    }

    @Test
    void testUpdateArmies() {
        Army newArmy1 = new Army("Test");
        Army newArmy2 = new Army("Test2");
        battle.updateArmies(newArmy1,newArmy2);

        assertEquals(battle.getArmy1(), newArmy1);
        assertEquals(battle.getArmy2(), newArmy2);
    }

    @Test
    void setTerrain() {
        IllegalArgumentException invalidTerrain = Assertions.assertThrows(IllegalArgumentException.class, () ->
            battle.setTerrain(""), "No such terrain exist");

        battle.setTerrain("Hill");
        assertEquals(battle.getArmy1().getUnits().get(0).getTerrain().toString(), "HILL");
    }
}