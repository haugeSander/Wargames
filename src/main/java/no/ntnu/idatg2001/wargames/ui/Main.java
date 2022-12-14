package no.ntnu.idatg2001.wargames.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main class which launches application.
 * @author Sander Hauge.
 * @version 1.0-SNAPSHOT
 */
public class Main extends Application {

  /**
   * Starts application, and opens main menu.fxml.
   */
  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("controllers/main-menu.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 875, 615);
    stage.getIcons().add(new Image(String.valueOf(getClass().getResource("Controllers/Logos/Tank.png"))));
    stage.setMinWidth(900);
    stage.setMinHeight(640); //Bigger than fxml load because it is based on different window size.
    stage.setTitle("Wargames");
    stage.setResizable(true);
    stage.setScene(scene);

    stage.setOnCloseRequest(windowEvent -> {
      windowEvent.consume();
      exit(stage);});

    stage.show();
  }

  /**
   * Public static void main launches the GUI.
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
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setHeaderText("Are you sure you want to exit?");
    alert.showAndWait().filter(response -> response == ButtonType.OK)
        .ifPresent(response -> stage.close());
  }
}