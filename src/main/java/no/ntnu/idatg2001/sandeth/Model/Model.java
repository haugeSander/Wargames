package no.ntnu.idatg2001.sandeth.Model;

public class Model {
  private static volatile Model instance; //Stops other threads to access at the same time.

  /**
   * Private constructor due to this being a
   * singleton.
   */
  private Model() {
  }

  /**
   * Model is a singleton and facade, only one object should
   * exist at a time. Gets this instance if object
   * is already made or creates it if not.
   * @return The one single item of model.
   */
  public static Model getInstance() {
    if (instance == null) {
      synchronized (BattleModel.class) {
        instance = new Model();
      }
    }
    return instance;
  }
}
