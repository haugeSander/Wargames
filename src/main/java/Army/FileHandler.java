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
import java.util.ArrayList;
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
   * Method which reads battle .csv files.
   * @param fileName String name of file.
   * @return Battle object created from file.
   */
  public static List<Army> readFile(String fileName) {
    Path path = Path.of(fileName);
    Army army1 = new Army("");
    Army army2 = new Army("");
    boolean twoArmies = false;
    List<Army> readFromFile = new ArrayList<>();

    try(BufferedReader reader = Files.newBufferedReader(path)) {
      String lineOfText;

      while ((lineOfText = reader.readLine()) != null) {
        String[] words = lineOfText.split(",");

        if (lineOfText.isEmpty()) {
          twoArmies = true;
        } else if (words.length < 2 && !twoArmies) {
          army1.setName(lineOfText);
        } else if (!twoArmies) {
          army1.add(UnitFactory.createUnit(words[0].strip(), words[1].strip(),
              Integer.parseInt(words[2].strip())));
        } else if (twoArmies && words.length < 2) {
          army2.setName(lineOfText);
        } else if (twoArmies) {
          army2.add(UnitFactory.createUnit(words[0].strip(), words[1].strip(),
              Integer.parseInt(words[2].strip())));
        }
      }
    } catch (IOException e) {
      e.getMessage();
    }

    readFromFile.add(army1);

    if (twoArmies)
      readFromFile.add(army2);

    return readFromFile;
  }
}
