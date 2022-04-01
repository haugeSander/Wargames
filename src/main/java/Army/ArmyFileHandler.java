package Army;

import Army.Units.Unit;
import Army.Units.UnitFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ArmyFileHandler {

  /**
   * Static method which writes a file based on inputs.
   * @param army Army object which is to be saved.
   * @param fileName Name of file as String.
   */
  public static void saveFile(Army army, String fileName) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".csv"));
      writer.write(army.getName());
      for (Unit u : army.getUnits()) {
        writer.write("\n"+u.getClass().getName()+","+u.getName()+","+u.getHealth());
      }
      writer.close();
    } catch (IOException e) {
      System.err.println(e.getMessage());
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
      int i = 0;

      while ((lineOfText = reader.readLine()) != null) {
        String[] words = lineOfText.split(",");

        if (i == 0) {
          army.setName(lineOfText);
        } else {
          army.add(UnitFactory.createUnit(words[0].strip(), words[1].strip(),
              Integer.parseInt(words[2].strip())));
        } i++;
      }
    } catch (IOException e) {
      e.getMessage();
    }
    return army;
  }
}
