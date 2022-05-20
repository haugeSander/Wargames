package no.ntnu.idatg2001.sandeth.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.sandeth.Army.Army;
import no.ntnu.idatg2001.sandeth.Army.Units.UnitFactory;
import no.ntnu.idatg2001.sandeth.Simulation.Battle;
import no.ntnu.idatg2001.sandeth.Simulation.BattleObserver;
import no.ntnu.idatg2001.sandeth.Utility.FileHandler;

public class BattleModel {
  private static volatile BattleModel instance; //Stops other threads to access at the same time.
  private Battle battle;
  private Army army1;
  private Army army2;
  private String terrain;

  /**
   * Private constructor due to this being a
   * singleton.
   */
  private BattleModel() {
    army1 = new Army("Army1");
    army2 = new Army("Army2");
    battle = new Battle(army1, army2);
  }

  /**
   * Facade is a singleton, only one object should
   * exist at a time. Gets this instance if object
   * is already made or creates it if not.
   * @return The one single item of facade.
   */
  public static BattleModel getInstance() {
    if (instance == null) {
      synchronized (BattleModel.class) {
        instance = new BattleModel();
      }
    }
    return instance;
  }

  /**
   * Subscribe method for observing the battleSimulation.
   * @param battleObserver Observer for battle class.
   */
  public void subscribeController(BattleObserver battleObserver) {
    battle.subscribe(battleObserver);
  }

  /**
   * Method to clear armies and set default name.
   */
  public void reset() {
    battle.getArmy1().setName("Army 1");
    battle.getArmy2().setName("Army 2");
    battle.getArmy1().getUnits().clear();
    battle.getArmy2().getUnits().clear();
  }

  /**
   * Method to read any file. Takes object army or battle, however
   * any other object passed will be considered a battle file.
   * Armies are returned from FileHandler and added to list of armies.
   *
   * Removes dependencies between backend FileHandler and controller.
   * @param pathAsString Path of where the file is found.
   * @param armyOrBattle Object of any type.
   */
  public void readFromFile(String pathAsString, Object armyOrBattle) {
    List<Army> listFromFile = FileHandler.readFile(pathAsString);

    if (armyOrBattle.equals(army1)) {
      army1.setName(listFromFile.get(0).getName());
      army1.setUnits(listFromFile.get(0).getUnits());
    } else if (armyOrBattle.equals(army2)) {
      army2.setName(listFromFile.get(0).getName());
      army2.setUnits(listFromFile.get(0).getUnits());
    } else {
      army1.setName(listFromFile.get(0).getName());
      army2.setName(listFromFile.get(1).getName());
      army1.setUnits(listFromFile.get(0).getUnits());
      army2.setUnits(listFromFile.get(1).getUnits());
    }
  }

  /**
   * Method to save both army and battle objects. If army object
   * is entered checks if army 1 or 2 is passed. Any other object is
   * considered as battle object to be saved.
   *
   * Removes dependencies between backend FileHandler and controller.
   * @param file File location to save.
   * @param armyOrBattle Object to save.
   * @throws Exception If an error occurs.
   */
  public void saveToFile(File file, Object armyOrBattle) throws Exception {
    List<Army> listToFile = new ArrayList<>();

    if (armyOrBattle.equals(army1)) {
      listToFile.add(army1);
    } else if (armyOrBattle.equals(army2)) {
      listToFile.add(army2);
    } else {
      listToFile.add(army1);
      listToFile.add(army2);
    }
    FileHandler.writeFile(listToFile, file);
  }

  /**
   * Removes dependencies between backend UnitFactory and controller.
   * @param army Army to be saved into.
   * @param type UnitType to create.
   * @param name Name of new units.
   * @param hp Health of new units.
   * @param amount Amount of units.
   */
  public void createNewUnits(Army army, String type, String name, int hp, int amount) {
    army.addAll(UnitFactory.createListOfUnits(type, name, hp, amount));
  }

  /**
   * Sets terrain for a battle.
   * @param terrain String representation of
   *                bonuses Enum.
   */
  public void setTerrain(String terrain) {
    this.terrain = terrain;
  }

  /**
   * Getter for the battle saved in facade.
   * @return Battle to be accessed from all controllers.
   */
  public Battle getBattle() {
    return battle;
  }

  /**
   * Getter for army 1.
   * @return army1.
   */
  public Army getArmy1() {
    return army1;
  }

  /**
   * Getter for army 2.
   * @return army2
   */
  public Army getArmy2() {
    return army2;
  }

  /**
   * Getter for terrain set in facade.
   * @return Return String representation of terrain.
   */
  public String getTerrain() {
    return terrain;
  }
}
