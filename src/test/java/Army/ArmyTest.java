package Army;

import Army.Units.InfantryUnit;
import Army.Units.Unit;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArmyTest {

    /**
     * Adds a single unit to list, only available to getRandom() is the newly added.
     * Checks if it is selected and then removes it to test remove().
     */
    @Test
    void getRandomAndRemove() {
        Army fedArmy = new Army("Fed Army");
        Unit sSquad = new InfantryUnit("S squad", 100);
        Unit unit2 = new InfantryUnit("1", 10);

        fedArmy.add(sSquad);
        fedArmy.add(unit2);
        Unit temp = fedArmy.getRandom();

        assertEquals(temp, sSquad);

        fedArmy.remove(sSquad);
        fedArmy.remove(unit2);
        assertEquals(fedArmy.getRandom(), null);
    }

    /**
     * Tests if addAll method works.
     * Create and adds to an arrayList.
     */
    @Test
    void addAll() {
        List<Unit> list = new ArrayList<>();
        Unit unit1 = new InfantryUnit("1", 10);
        Unit unit2 = new InfantryUnit("1", 10);
        list.add(unit1);
        list.add(unit2);

        Army fedArmy = new Army("Fed army");
        assertFalse(fedArmy.hasUnits());
        fedArmy.addAll(list);

        assertTrue(fedArmy.hasUnits()); //Returns true if any units are added to army list.
    }
}
