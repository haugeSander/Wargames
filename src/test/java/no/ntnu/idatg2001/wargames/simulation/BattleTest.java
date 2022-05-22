package no.ntnu.idatg2001.wargames.simulation;

import no.ntnu.idatg2001.wargames.army.Army;
import no.ntnu.idatg2001.wargames.army.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.army.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.army.units.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BattleTest {
    private Army blue;
    private Army red;
    private Battle battle;

    /**
     * Creates objects used for tests before they are run.
     * Sort of a constructor.
     */
    @BeforeEach
    void constructor() {
        Unit cavalryBlue = new CavalryUnit("Alfa Blue", 20);
        Unit cavalryRed = new CavalryUnit("Alfa Red", 20);

        blue = new Army("Blue");
        red = new Army("Red");
        battle = new Battle(blue, red);

        blue.add(cavalryBlue);
        red.add(cavalryRed);
    }

    /**
     * Tests simulate method from battle class.
     */
    @Test
    void simulate() {
        red.getUnits().add(new InfantryUnit("Test", 1000));
        assertEquals(battle.simulate(), red.getName());
    }

    /**
     * Tests simulateStep method in Battle.
     */
    @Test
    void simulateStep() {
        blue.getUnits().clear();
        red.getUnits().clear();

        Assertions.assertThrows(NullPointerException.class, () ->
            battle.simulateStep(blue, red), "");
    }

    /**
     * Tests the updateArmies method.
     */
    @Test
    void testUpdateArmies() {
        Army newArmy1 = new Army("Test");
        Army newArmy2 = new Army("Test2");
        battle.updateArmies(newArmy1,newArmy2);

        assertEquals(battle.getArmy1(), newArmy1);
        assertEquals(battle.getArmy2(), newArmy2);
    }

    /**
     * Tests setTerrain method in battle.
     */
    @Test
    void setTerrain() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            battle.setTerrain(""), "No such terrain exist");

        battle.setTerrain("Hill");
        assertEquals(battle.getArmy1().getUnits().get(0).getTerrain().toString(), "HILL");
    }
}