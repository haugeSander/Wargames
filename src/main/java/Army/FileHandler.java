package Army;

import Army.Units.Unit;
import Army.Units.UnitFactory;
import Simulation.Battle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileHandler {

  /**
   * Method which saves army, and also saves battles.
   * When two armies is in list, both will be saved, equivalent to a battle.
   * @param armies List of armies to be saved.
   * @param fileName File to be saved.
   */
  public static void writeFile(List<Army> armies, File fileName) throws Exception {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".csv"));
      writer.write(armies.get(0).getName());

      for (Unit u : armies.get(0).getUnits())
        writer.write("\n" + u.getClass().getName() + "," + u.getName() + "," + u.getHealth());

      if (armies.size() == 2) {
        writer.write("\n\n" + armies.get(1).getName());

        for (Unit u : armies.get(1).getUnits())
          writer.write("\n" + u.getClass().getName() + "," + u.getName() + "," + u.getHealth());
      }
      writer.close();

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  /**
   * Static method which reads a saved file from army.
   * @param fileName String of
   * @return Army from save file.
   */
  public static Army readFile(String fileName) {
    Path path = Path.of(fileName);
    Army army = new Army("");

    try(BufferedReader reader = Files.newBufferedReader(path)) {
      String lineOfText;

      while ((lineOfText = reader.readLine()) != null) {
        String[] words = lineOfText.split(",");

        if (words.length < 2) {
          army.setName(lineOfText);
        } else {
          army.add(UnitFactory.createUnit(words[0].strip(), words[1].strip(),
              Integer.parseInt(words[2].strip())));
        }
      }
    } catch (IOException e) {
      e.getMessage();
    }
    return army;
  }

  /**
   * Method which reads battle .csv files.
   * @param fileName String name of file.
   * @return Battle object created from file.
   */
  public static Battle readFile1(String fileName) {
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
