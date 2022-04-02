package UI;

import Army.Army;
import Simulation.Battle;

public class Facade {
  private static volatile Facade instance;
  private Battle battle;

  private Facade() {
  }

  public static Facade getInstance() {
    if (instance == null) {
      synchronized (Facade.class) {
        instance = new Facade();
      }
    }
    return instance;
  }

  private void buildFacade() {

  }

  public void newSimulation(String terrain) {
    battle = new Battle(new Army(""), new Army(""));
    battle.setTerrain(terrain);
  }

  public Battle getBattle() {
    return battle;
  }
}
