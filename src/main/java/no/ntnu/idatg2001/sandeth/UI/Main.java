package no.ntnu.idatg2001.sandeth.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

  /**
   * Starts application, and opens main menu.fxml.
   */
  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Controllers/main-menu.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 875, 615);

    stage.getIcons().add(new Image(String.valueOf(getClass().getResource("Controllers/Logos/Tank.png"))));
    stage.setTitle("Wargames");
    stage.setResizable(true);
    stage.setScene(scene);

    stage.setOnCloseRequest(windowEvent -> {
      windowEvent.consume();
      exit(stage);
    });

    stage.show();
  }

  /**
   * PSVM launches the GUI.
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * When user in any way tries to exit application,
   * a prompt is opened, asking user if they really want to quit.
   * @param stage Stage.
   */
  public static void exit(Stage stage) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
    alert.setHeaderText("Are you sure you want to exit?");
    alert.showAndWait()
        .filter(response -> response == ButtonType.OK)
        .ifPresent(response -> stage.close());
  }
}
