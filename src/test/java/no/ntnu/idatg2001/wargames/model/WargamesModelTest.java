package no.ntnu.idatg2001.wargames.model;

import static org.junit.jupiter.api.Assertions.*;
import no.ntnu.idatg2001.wargames.army.Army;
import no.ntnu.idatg2001.wargames.army.units.InfantryUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WargamesModelTest {
  private Army test1;
  private WargamesModel wargamesModel;

  /**
   * Creates objects used for tests before they are run.
   * Sort of a constructor.
   */
  @BeforeEach
  void constructor() {
    test1 = new Army("Test1");
    Army test2 = new Army("Test2");
    wargamesModel = WargamesModel.getInstance();
    InfantryUnit testInfantry = new InfantryUnit("TestInfantry", 10);

    test1.add(testInfantry);
    test1.add(new InfantryUnit("Infantry2", 10));
    test2.add(new InfantryUnit("Infantry3", 10));
    test2.add(new InfantryUnit("Infantry4", 10));

    wargamesModel.getArmy1().setName(test1.getName());
    wargamesModel.getArmy2().setName(test2.getName());
    wargamesModel.getArmy1().setUnits(test1.getUnits());
    wargamesModel.getArmy2().setUnits(test2.getUnits());
  }

  /**
   * Tests the reset() method in battleModel.
   */
  @Test
  void reset() {
    assertEquals(wargamesModel.getArmy1().toString(), test1.toString());
    wargamesModel.reset();
    assertTrue(wargamesModel.isEmpty());
    assertNotEquals(wargamesModel.getArmy1().toString(), test1.toString());
  }

  /**
   * Tests refreshDuplicates() and makeDuplicateArmies() in battleModel.
   */
  @Test
  void refreshDuplicates() {
    assertEquals(wargamesModel.getArmy1().toString(), test1.toString());
    wargamesModel.makeDuplicateArmies();
    wargamesModel.getArmy1().getUnits().clear();
    wargamesModel.refreshDuplicates();
    assertNotEquals(wargamesModel.getArmy1().toString(), test1.toString());
  }

  /**
   * Tests setTerrain in battleModel.
   */
  @Test
  void setTerrain() {
    try {
      wargamesModel.setTerrain("Hill");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail();
    }
  }

  /**
   * Tests getTerrain method in battleModel.
   */
  @Test
  void getTerrain() {
    setTerrain();
    assertEquals(wargamesModel.getTerrain(), "Hill");
  }
}