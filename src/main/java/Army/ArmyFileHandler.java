package Army;

import Army.Units.Unit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArmyFileHandler {
  private Army army;
  String saveFile;

  public static void saveFile(Army army, String fileName) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".csv"));
      writer.write(army.getName());
      for (Unit u : army.getUnits()) {
        writer.write("\n"+u.getClass().getName()+","+u.getName()+","+u.getHealth());
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static List<Unit> readFile(String fileName) {
    Path path = Path.of(fileName);
    List<Unit> list = new ArrayList<>();

    try(BufferedReader reader = Files.newBufferedReader(path)) {
      Army army = new Army("");
      String lineOfText;
      int i = 0;

      while ((lineOfText = reader.readLine()) != null) {
        String[] words = lineOfText.split(",");

        if (i == 0) {
          army.setName(lineOfText);
        } else {
          

        } i++;
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    return list;
  }
}
