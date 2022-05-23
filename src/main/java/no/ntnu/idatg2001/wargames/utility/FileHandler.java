package no.ntnu.idatg2001.wargames.utility;

import java.io.IOException;
import no.ntnu.idatg2001.wargames.army.Army;
import no.ntnu.idatg2001.wargames.army.units.UnitFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which handles files, both reading and writing.
 * @author Sander Hauge.
 * @version 1.0-SNAPSHOT
 */
public class FileHandler {

  /**
   * Static classes does not require constructor.
   */
  private FileHandler() {
  }

  /**
   * Method which saves army, and also saves battles.
   * When two armies is in list, both will be saved, equivalent to a battle.
   * @param armies List of armies to be saved.
   * @param fileName File to be saved.
   */
  public static void writeFile(List<Army> armies, File fileName) throws IOException {

    try(BufferedWriter writer = Files.newBufferedWriter(fileName.toPath())) {
      writer.write(armies.get(0).getName());

      for (Object u : armies.get(0).getUnits())
        writer.write("\n" + u.toString()); //Overridden toString into csv format.

      if (armies.size() == 2) {
        writer.write("\n\n" + armies.get(1).getName());

        for (Object u : armies.get(1).getUnits())
          writer.write("\n" + u.toString());
      }
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
  }

  /**
   * Method which reads battle .csv files.
   * @param fileName String name of file.
   * @return Battle object created from file.
   */
  public static List<Army> readFile(String fileName) throws IOException {
    Path path = Path.of(fileName);
    Army army1 = new Army("");
    Army army2 = new Army("");
    boolean twoArmies = false;
    List<Army> readFromFile = new ArrayList<>();

    try(BufferedReader reader = Files.newBufferedReader(path)) {
      String lineOfText;

      while ((lineOfText = reader.readLine()) != null) {
        String[] words = lineOfText.split(",");

        if (lineOfText.isEmpty()) { //If there is a gap between armies, it sets this boolean to true.
          twoArmies = true;
        } else if (words.length < 2 && !twoArmies) {
          army1.setName(lineOfText);
        } else if (!twoArmies) {
          army1.add(UnitFactory.createUnit(words[0].strip(), words[1].strip(),
              Integer.parseInt(words[2].strip())));
        } else if (twoArmies && words.length < 2) {
          army2.setName(lineOfText);
        } else if (twoArmies) { //Not always true.
          army2.add(UnitFactory.createUnit(words[0].strip(), words[1].strip(),
              Integer.parseInt(words[2].strip())));
        }
      }
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }

    readFromFile.add(army1);

    if (twoArmies)
      readFromFile.add(army2);

    return readFromFile;
  }
}
