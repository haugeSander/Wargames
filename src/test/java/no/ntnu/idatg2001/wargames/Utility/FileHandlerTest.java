package no.ntnu.idatg2001.wargames.Utility;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.wargames.Army.Army;
import no.ntnu.idatg2001.wargames.Army.Units.InfantryUnit;
import no.ntnu.idatg2001.wargames.Army.Units.Unit;
import no.ntnu.idatg2001.wargames.Simulation.Battle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileHandlerTest {
  private List<Army> list;
  private Unit infantry1;
  private Unit cavalry1;
  private Unit commander1;
  private Unit ranged1;
  private Army armyOne;
  private Battle testBattleSave;

  @BeforeEach
  void constructor() {
    list = new ArrayList<>();
    armyOne = new Army("Blue");
    infantry1 = new InfantryUnit("infantry",10);
    ranged1 = new InfantryUnit("ranged",10);
    cavalry1 = new InfantryUnit("cavalry",10);
    commander1 = new InfantryUnit("commander",10);
    testBattleSave = new Battle(armyOne, new Army("Red"));
  }

  @Test
  void writeFile() {
    armyOne.add(infantry1);
    armyOne.add(ranged1);
    armyOne.add(cavalry1);
    armyOne.add(commander1);

    list.add(armyOne);

    try {
      FileHandler.writeFile(list, new File(armyOne.getName()));
      list.add(testBattleSave.getArmy2());
      FileHandler.writeFile(list, new File(armyOne.getName()+"-vs-"+testBattleSave.getArmy2().getName()));
    } catch (Exception e){
      fail();
      System.out.println(e.getMessage());
    }
  }

  @Test
  void readFile() {
    List<Army> readFromFile = new ArrayList<>();
    armyOne.add(infantry1);
    armyOne.add(ranged1);
    armyOne.add(cavalry1);
    armyOne.add(commander1);
    readFromFile.add(new Army("Red"));

    System.out.println(InfantryUnit.class.getCanonicalName());

    try {
      readFromFile = FileHandler.readFile("Blue.csv");
    } catch (Exception e) {
      fail();
      System.out.println(e.getMessage());
    }

    Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
        FileHandler.readFile("Blue.csv").get(1), "Index out of bounds, list size 1!");

    try {
      readFromFile = FileHandler.readFile("Blue-vs-Red.csv");
    } catch (Exception e) {
      fail();
      System.out.println(e.getMessage());
    }

    assertEquals(armyOne.toString(), readFromFile.get(0).toString());

    assertEquals("Red", readFromFile.get(1).getName());
    assertEquals(0, readFromFile.get(1).getUnits().size());
  }
}