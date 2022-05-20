package no.ntnu.idatg2001.sandeth.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.sandeth.Army.Army;
import no.ntnu.idatg2001.sandeth.Army.Units.UnitFactory;
import no.ntnu.idatg2001.sandeth.Utility.FileHandler;

public class Model {
  private static volatile Model instance; //Stops other threads to access at the same time.
  private final BattleModel battleModel;

  /**
   * Private constructor due to this being a
   * singleton.
   */
  private Model() {
    battleModel = BattleModel.getInstance();
  }

  /**
   * Model is a singleton and facade, only one object should
   * exist at a time. Gets this instance if object
   * is already made or creates it if not.
   * @return The one single item of model.
   */
  public static Model getInstance() {
    if (instance == null) {
      synchronized (BattleModel.class) {
        instance = new Model();
      }
    }
    return instance;
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

    if (armyOrBattle.equals(battleModel.getArmy1())) {
      battleModel.getArmy1().setName(listFromFile.get(0).getName());
      battleModel.getArmy1().setUnits(listFromFile.get(0).getUnits());
    } else if (armyOrBattle.equals(battleModel.getArmy2())) {
      battleModel.getArmy2().setName(listFromFile.get(0).getName());
      battleModel.getArmy2().setUnits(listFromFile.get(0).getUnits());
    } else {
      battleModel.getArmy1().setName(listFromFile.get(0).getName());
      battleModel.getArmy2().setName(listFromFile.get(1).getName());
      BattleModel.getInstance().getBattle().getArmy1().setUnits(listFromFile.get(0).getUnits());
      BattleModel.getInstance().getBattle().getArmy2().setUnits(listFromFile.get(1).getUnits());
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

    if (armyOrBattle.equals(battleModel.getArmy1())) {
      listToFile.add(battleModel.getArmy1());
    } else if (armyOrBattle.equals(battleModel.getArmy2())) {
      listToFile.add(battleModel.getArmy2());
    } else {
      listToFile.add(battleModel.getArmy1());
      listToFile.add(battleModel.getArmy2());
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
}
