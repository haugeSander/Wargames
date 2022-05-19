package UI;

import Simulation.Battle;
import Simulation.BattleObserver;

public class Facade {
  private static volatile Facade instance; //Stops other threads to access at the same time.
  private Battle battle;
  private String terrain;

  /**
   * Private constructor due to this being a
   * singleton.
   */
  private Facade() {
  }

  /**
   * Facade is a singleton, only one object should
   * exist at a time. Gets this instance if object
   * is already made or creates it if not.
   * @return The one single item of facade.
   */
  public static Facade getInstance() {
    if (instance == null) {
      synchronized (Facade.class) {
        instance = new Facade();
      }
    }
    return instance;
  }

  public void subscribeController(BattleObserver battleObserver) {
    battle.subscribe(battleObserver);
  }

  /**
   * Set the battle so it may be accessed
   * other places in program.
   * @param battle Battle object to be used
   *               between controllers.
   */
  public void setBattle(Battle battle) {
    this.battle = battle;
  }

  /**
   * Sets terrain for a battle.
   * @param terrain String representation of
   *                bonuses Enum.
   */
  public void setTerrain(String terrain) {
    this.terrain = terrain;
  }

  /**
   * Getter for the battle saved in facade.
   * @return Battle to be accessed from all controllers.
   */
  public Battle getBattle() {
    return battle;
  }

  /**
   * Getter for terrain set in facade.
   * @return Return String representation of terrain.
   */
  public String getTerrain() {
    return terrain;
  }
}
