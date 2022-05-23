package no.ntnu.idatg2001.wargames.simulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Updater in the observer pattern. Updates when something happens in battle.
 * This notifies the subscribers whenever the observer
 * changes its state.
 * @author Sander Hauge
 * @version 1.0-SNAPSHOT
 */
public abstract class BattleUpdater {
  private final List<BattleObserver> observers = new ArrayList<>();

  /**
   * Method to subscribe a new observer.
   * @param battleObserver New observer of battle class.
   */
  public void subscribe(BattleObserver battleObserver) {
    observers.add(battleObserver);
  }

  /**
   * Method to remove an observer, standard to include in observer classes.
   * Never used in final version.
   * @param battleObserver Observer to be removed.
   */
  public void unsubscribe(BattleObserver battleObserver) {
    observers.remove(battleObserver);
  }

  /**
   * Battle update in simulate step.
   * @param status Simulate step event as string.
   */
  public void battleActions(String status) {
    observers.forEach(battleObserver -> battleObserver.update(status));
  }

  /**
   * Battle update of army sizes.
   * @param army1Amount Army 1 size.
   * @param army2Amount Army 2 size.
   */
  public void battleStatus(int army1Amount, int army2Amount) {
    observers.forEach(battleObserver -> battleObserver.updateSize(army1Amount, army2Amount));
  }
}