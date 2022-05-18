package Simulation;

import Army.Army;
import Army.Units.Unit;
import Army.Units.UnitFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BattleFileHandler {

  public BattleFileHandler() {
  }

  /**
   * Method which saves battle.csv files.
   * @param battle Battle object to save.
   * @param fileName File to be saved.
   */
  public static void writeFile(Battle battle, File fileName) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
      writer.write(battle.getArmy1().getName());
      for (Unit u : battle.getArmy1().getUnits()) {
        writer.write("\n" + u.getClass().getName() + "," + u.getName() + "," + u.getHealth());
      }
      writer.write("\n\n" + battle.getArmy2().getName());
      for (Unit u : battle.getArmy2().getUnits()) {
        writer.write("\n" + u.getClass().getName() + "," + u.getName() + "," + u.getHealth());
      }
      writer.close();

    } catch (Exception e) {
      e.getMessage();
    }
  }

  /**
   * Method which reads battle .csv files.
   * @param fileName String name of file.
   * @return Battle object created from file.
   */
  public static Battle readFile(String fileName) {
    Battle battle = new Battle();

    Path path = Path.of(fileName);
    Army army1 = new Army("");
    Army army2 = new Army("");
    boolean nextArmy = false;

    try(BufferedReader reader = Files.newBufferedReader(path)) {
      String lineOfText;

      while ((lineOfText = reader.readLine()) != null) {
        String[] words = lineOfText.split(",");
        if (lineOfText.isEmpty()) {
          nextArmy = true;
          continue;
        } else if (words.length < 2 && !nextArmy)
          army1.setName(lineOfText);
        else if (!nextArmy)
          army1.add(UnitFactory.createUnit(words[0].strip(), words[1].strip(),
              Integer.parseInt(words[2].strip())));
        else if (nextArmy && words.length < 2)
          army2.setName(lineOfText);
        else if (nextArmy)
          army2.add(UnitFactory.createUnit(words[0].strip(), words[1].strip(),
              Integer.parseInt(words[2].strip())));
      }
    } catch (IOException e) {
      e.getMessage();
    }
    battle.updateArmies(army1, army2);

    return battle;
  }
}