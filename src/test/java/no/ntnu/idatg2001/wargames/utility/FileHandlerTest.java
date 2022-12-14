package no.ntnu.idatg2001.wargames.utility;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.wargames.army.Army;
import no.ntnu.idatg2001.wargames.army.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.army.units.Unit;
import no.ntnu.idatg2001.wargames.simulation.Battle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for FileHandler.
 * @author Sander Hauge
 * @version 1.0-SNAPSHOT
 */
class FileHandlerTest {
  private List<Army> list;
  private Unit infantry1;
  private Unit cavalry1;
  private Unit commander1;
  private Unit ranged1;
  private Army armyOne;
  private Battle testBattleSave;

  /**
   * Creates objects used for tests before they are run.
   * Sort of a constructor.
   */
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

  /**
   * Tests writing of files in FileHandler.
   */
  @Test
  void writeFile() {
    armyOne.add(infantry1);
    armyOne.add(ranged1);
    armyOne.add(cavalry1);
    armyOne.add(commander1);

    list.add(armyOne);

    try {
      FileHandler.writeFile(list, new File(armyOne.getName() + ".csv"));
      list.add(testBattleSave.getArmy2());
      FileHandler.writeFile(list, new File(armyOne.getName() + "-vs-" +
          testBattleSave.getArmy2().getName()+ ".csv"));
    } catch (Exception e){
      fail();
      System.out.println(e.getMessage());
    }
  }

  /**
   * Tests reading of files in FileHandler class.
   */
  @Test
  void readFile() {
    List<Army> readFromFile = new ArrayList<>();
    armyOne.add(infantry1);
    armyOne.add(ranged1);
    armyOne.add(cavalry1);
    armyOne.add(commander1);
    readFromFile.add(new Army("Red"));

    try {
      readFromFile = FileHandler.readFile("Blue.csv");
    } catch (Exception e) {
      fail();
      System.out.println(e.getMessage());
    }

    List<Army> finalReadFromFile = readFromFile;
    Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
        finalReadFromFile.get(1), "Index out of bounds, list size 1!");
    //Result is ignored, but tested. Tests to make sure it never saves two armies when one is input.

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