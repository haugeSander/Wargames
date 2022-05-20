package no.ntnu.idatg2001.sandeth.Simulation;

public interface BattleObserver {
  void update(String status);
  void updateSize(int army1Amount, int army2Amount);
}
