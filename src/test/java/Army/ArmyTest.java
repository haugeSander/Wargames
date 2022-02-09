package Army;

import Army.Units.InfantryUnit;
import Army.Units.Unit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArmyTest {

    @Test
    public void getRandomAndRemove() {
        Army fedArmy = new Army("Fed Army");
        Unit sSquad = new InfantryUnit("S squad", 100);
        fedArmy.add(sSquad);
        Unit temp = fedArmy.getRandom();

        assertEquals(temp, sSquad);

        fedArmy.remove(sSquad);
        assertEquals(fedArmy.getRandom(), null);
    }

}
