package no.ntnu.idatg2001.wargames.simulation;

/**
 * Observer in observer pattern. Gets information
 * from battle class.
 * @author Sander Hauge
 * @version 1.0-SNAPSHOT
 */
public interface BattleObserver {
  void update(String status); //String update from simulate step method.
  void updateSize(int army1Amount, int army2Amount); //Int size of armies from simulate step method.
}