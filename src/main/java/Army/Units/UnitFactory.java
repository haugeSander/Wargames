package Army.Units;

import java.util.ArrayList;
import java.util.List;

public class UnitFactory {
  private static volatile UnitFactory instance;

  /**
   * Singleton of unitFactory.
   */
  private UnitFactory() {
  }

  public static UnitFactory getInstance() {
    if (instance == null) {
      synchronized (UnitFactory.class) {
        instance = new UnitFactory();
      }
    }
    return instance;
  }

  /**
   * Method which creates different types of units
   * based on a string input.
   * @param unit String unitType.
   * @return new Unit.
   */
  public static Unit createUnit(String unit, String name, int health) {
    if (unit.equalsIgnoreCase("InfantryUnit") || unit.equalsIgnoreCase("Army.Units.InfantryUnit")) {
      return new InfantryUnit(name, health);
    } else if (unit.equalsIgnoreCase("RangedUnit") || unit.equalsIgnoreCase("Army.Units.RangedUnit")) {
      return new RangedUnit(name, health);
    } else if (unit.equalsIgnoreCase("CavalryUnit") || unit.equalsIgnoreCase("Army.Units.CavalryUnit")) {
      return new CavalryUnit(name, health);
    } else if (unit.equalsIgnoreCase("CommanderUnit") || unit.equalsIgnoreCase("Army.Units.CommanderUnit")) {
      return new CommanderUnit(name, health);
    } else
      return null;
  }

  /**
   * Returns a list of units, size based on input.
   * @param unitType String representation of unitType.
   * @param names String name.
   * @param health integer of health.
   * @param amount integer of amount of units wanted.
   * @return List of units created.
   */
  public static List<Unit> createListOfUnits(String unitType, String names, int health, int amount) {
    List<Unit> listOfUnits = new ArrayList<>();
    int i = 0;
    while (i < amount) {
      listOfUnits.add(createUnit(unitType, names, health));
      i++;
    }
    return listOfUnits;
  }
}
