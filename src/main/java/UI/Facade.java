package UI;

import Simulation.Battle;

public class Facade {
  private static volatile Facade instance;
  private Battle battle;
  private String terrain;

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

  public void setBattle(Battle battle) {
    this.battle = battle;
  }

  public void setTerrain(String terrain) {
    this.terrain = terrain;
  }

  public Battle getBattle() {
    return battle;
  }

  public String getTerrain() {
    return terrain;
  }
}
