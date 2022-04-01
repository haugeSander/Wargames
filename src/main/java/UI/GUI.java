package UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GUI extends Application {

  /**
   * Starts application, and opens main menu.fxml.
   */
  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Controllers/main-menu.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 800, 600);

    stage.getIcons().add(new Image(String.valueOf(getClass().getResource("Controllers/tank.png"))));
    stage.setTitle("Wargames");
    stage.setResizable(false);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * PSVM launches the GUI.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
