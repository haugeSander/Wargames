package no.ntnu.idatg2001.wargames.army.units;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for units.
 * Not a singleton since it is a static class.
 */
public class UnitFactory {
  /**
   * Static classes does not need public constructors.
   */
  private UnitFactory() {
  }

  /**
   * Method which creates different types of units
   * based on a string input.
   * @param unit String unitType.
   * @param name String unitName.
   * @param health Int health of unit.
   * @return new Unit.
   * @throws IllegalArgumentException if invalid unit type is presented.
   */
  public static Unit createUnit(String unit, String name, int health) throws IllegalArgumentException {
    if (unit.equalsIgnoreCase(InfantryUnit.class.getCanonicalName()) ||
        unit.equalsIgnoreCase(InfantryUnit.class.getSimpleName())) {
      return new InfantryUnit(name, health);
    } else if (unit.equalsIgnoreCase(RangedUnit.class.getCanonicalName()) ||
        unit.equalsIgnoreCase(RangedUnit.class.getSimpleName())) {
      return new RangedUnit(name, health);
    } else if (unit.equalsIgnoreCase(CavalryUnit.class.getCanonicalName()) ||
        unit.equalsIgnoreCase(CavalryUnit.class.getSimpleName())) {
      return new CavalryUnit(name, health);
    } else if (unit.equalsIgnoreCase(CommanderUnit.class.getCanonicalName()) ||
        unit.equalsIgnoreCase(CommanderUnit.class.getSimpleName())) {
      return new CommanderUnit(name, health);
    } else
      throw new IllegalArgumentException("Invalid unit presented.");
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
