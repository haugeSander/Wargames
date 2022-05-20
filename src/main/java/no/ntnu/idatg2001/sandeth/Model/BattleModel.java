package no.ntnu.idatg2001.sandeth.Model;

import no.ntnu.idatg2001.sandeth.Army.Army;
import no.ntnu.idatg2001.sandeth.Simulation.Battle;
import no.ntnu.idatg2001.sandeth.Simulation.BattleObserver;

public class BattleModel {
  private static volatile BattleModel instance; //Stops other threads to access at the same time.
  private Battle battle;
  private Army army1;
  private Army army2;

  private String terrain;

  /**
   * Private constructor due to this being a
   * singleton.
   */
  private BattleModel() {
    army1 = new Army("Army1");
    army2 = new Army("Army2");
    battle = new Battle(army1, army2);
  }

  /**
   * Facade is a singleton, only one object should
   * exist at a time. Gets this instance if object
   * is already made or creates it if not.
   * @return The one single item of facade.
   */
  public static BattleModel getInstance() {
    if (instance == null) {
      synchronized (BattleModel.class) {
        instance = new BattleModel();
      }
    }
    return instance;
  }

  /**
   * Subscribe method for observing the battleSimulation.
   * @param battleObserver Observer for battle class.
   */
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
    if (battle != null)
      this.battle = battle;
  }

  /**
   * Method to clear armies and set default name.
   */
  public void reset() {
    battle.getArmy1().setName("Army 1");
    battle.getArmy2().setName("Army 2");
    battle.getArmy1().getUnits().clear();
    battle.getArmy2().getUnits().clear();
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
   * Getter for army 1.
   * @return army1.
   */
  public Army getArmy1() {
    return army1;
  }

  /**
   * Getter for army 2.
   * @return army2
   */
  public Army getArmy2() {
    return army2;
  }

  /**
   * Getter for terrain set in facade.
   * @return Return String representation of terrain.
   */
  public String getTerrain() {
    return terrain;
  }
}
