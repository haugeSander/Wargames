package Army;

import Army.Units.InfantryUnit;
import Army.Units.Unit;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArmyTest {

    @Test
    void getRandomAndRemove() {
        Army fedArmy = new Army("Fed Army");
        Unit sSquad = new InfantryUnit("S squad", 100);
        fedArmy.add(sSquad);
        Unit temp = fedArmy.getRandom();

        assertEquals(temp, sSquad);

        fedArmy.remove(sSquad);
        assertEquals(fedArmy.getRandom(), null);
    }

    @Test
    void addAll() {
        List<Unit> list = new ArrayList<>();
        Unit unit1 = new InfantryUnit("1", 10);
        Unit unit2 = new InfantryUnit("1", 10);
        list.add(unit1);
        list.add(unit2);

        Army fedArmy = new Army("Fed army");
        fedArmy.addAll(list);

        assertTrue(fedArmy.hasUnits()); //Returns true if any units are added to army list.
    }
}
