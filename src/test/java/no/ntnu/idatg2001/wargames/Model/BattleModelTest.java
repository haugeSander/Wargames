package no.ntnu.idatg2001.wargames.Model;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg2001.wargames.Army.Army;
import no.ntnu.idatg2001.wargames.Army.Units.InfantryUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BattleModelTest {
  private Army test1;
  private Army test2;
  private BattleModel battleModel;
  private InfantryUnit testInfantry;

  @BeforeEach
  void constructor() {
    test1 = new Army("Test1");
    test2 = new Army("Test2");
    battleModel = BattleModel.getInstance();
    testInfantry = new InfantryUnit("TestInfantry", 10);

    test1.add(testInfantry);
    test1.add(new InfantryUnit("Infantry2", 10));
    test2.add(new InfantryUnit("Infantry3", 10));
    test2.add(new InfantryUnit("Infantry4", 10));

    battleModel.getArmy1().setName(test1.getName());
    battleModel.getArmy2().setName(test2.getName());
    battleModel.getArmy1().setUnits(test1.getUnits());
    battleModel.getArmy2().setUnits(test2.getUnits());
  }

  @Test
  void reset() {
    assertEquals(battleModel.getArmy1().toString(), test1.toString());
    battleModel.reset();
    assertTrue(battleModel.isEmpty());
    assertNotEquals(battleModel.getArmy1().toString(), test1.toString());
  }

  @Test
  void refreshDuplicates() {
    assertEquals(battleModel.getArmy1().toString(), test1.toString());
    battleModel.makeDuplicateArmies();
    battleModel.getArmy1().getUnits().clear();
    battleModel.refreshDuplicates();
    assertNotEquals(battleModel.getArmy1().toString(), test1.toString());
  }

  @Test
  void setTerrain() {
    try {
      battleModel.setTerrain("Hill");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail();
    }
  }

  @Test
  void getTerrain() {
    setTerrain();
    assertEquals(battleModel.getTerrain(), "Hill");
  }
}