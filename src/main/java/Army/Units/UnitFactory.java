package Army.Units;

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
  public static Unit createUnit(String unit) {
    if (unit.equalsIgnoreCase("InfantryUnit")) {
      return new InfantryUnit("Infantry", 100);
    } else if (unit.equalsIgnoreCase("RangedUnit")) {
      return new RangedUnit("Ranged", 75);
    } else if (unit.equalsIgnoreCase("CavalryUnit")) {
      return new CavalryUnit("CavalryUnit", 100);
    } else if (unit.equalsIgnoreCase("CommanderUnit")) {
      return new CommanderUnit("CommanderUnit", 125);
    } else
      return null;
  }
}
