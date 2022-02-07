package Units;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {

    @Test
    void getHealth() {
        Unit unit1 = new Unit("TestUnit1", 10,10,10);
        Unit unit2 = new Unit("TestUnit2", 12,12,8);
        unit1.attack(unit2);

        assertEquals(unit1.getHealth(), 10);
        assertEquals(unit2.getHealth(), 8);
    }
}