package UI;

import Army.Army;
import Simulation.Battle;

public class Facade {
  private static volatile Facade instance;

  private Facade() {
    Battle battle = new Battle(new Army("Army 1"), new Army("Army 2"));
  }

  public static Facade getInstance() {
    if (instance == null) {
      synchronized (Facade.class) {
        instance = new Facade();
      }
    }
    return instance;
  }
}
