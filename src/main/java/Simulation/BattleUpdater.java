package Simulation;

import java.util.ArrayList;
import java.util.List;

public abstract class BattleUpdater {
  private List<BattleObserver> observers = new ArrayList<>();

  public void subscribe(BattleObserver battleObserver) {
    observers.add(battleObserver);
  }

  public void battleActions(String status) {
    observers.forEach(battleObserver -> battleObserver.update(status));
  }

  public void battleStatus(int army1Amount, int army2Amount) {
    observers.forEach(battleObserver -> battleObserver.updateSize(army1Amount, army2Amount));
  }
}
