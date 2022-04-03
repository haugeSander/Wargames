package UI.Controllers;

import Simulation.BattleFileHandler;
import UI.Facade;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuController implements Initializable {
  @FXML
  private ImageView logo;

  /**
   * Constructor for main menu.
   * Sets image.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    logo.setImage(new Image(String.valueOf(getClass().getResource("tank.png"))));
  }

  /**
   * Opens the main page with no units.
   * Prompts user to select a type of terrain.
   */
  public void onNewSimulationButtonPressed() throws IOException {
    Facade facade = Facade.getInstance();

      try {
        facade.newSimulation(selectTerrainTypeDialog());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage) logo.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
      } catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
        alert.setHeaderText("No such terrain exist!");
        alert.showAndWait();
      }
  }

  /**
   * Exit button, exits application after a confirmation.
   */
  public void onExitButtonPressed() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
    alert.setHeaderText("Are you sure you want to exit?");
    alert.showAndWait()
        .filter(response -> response == ButtonType.OK)
        .ifPresent(response -> System.exit(0));
  }

  /**
   * Button to open a save file for battle.
   */
  public void onOpenSimulationButtonPressed() {
    FileChooser chooser = new FileChooser();
    File selectedFile = chooser.showOpenDialog(null);
    Facade facade = Facade.getInstance();

    try {
      facade.setBattle(BattleFileHandler.readFile(selectedFile.getPath()));
      facade.setTerrain(selectTerrainTypeDialog());
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
      Stage stage = (Stage) logo.getScene().getWindow();
      Scene scene = new Scene(fxmlLoader.load(), 800, 600);
      stage.setScene(scene);
    } catch (Exception e) {
      Alert noFileExists = new Alert(Alert.AlertType.WARNING);
      noFileExists.setTitle("File error");
      noFileExists.setHeaderText("The file selected not supported or nothing selected!");
      noFileExists.setContentText("Remember only .csv files are supported.");
      noFileExists.showAndWait();
    }
  }

  private String selectTerrainTypeDialog() {
    Dialog<ButtonType> td = new Dialog<>();
    td.setHeaderText("Enter a terrain type.");
    BorderPane pane = new BorderPane();
    pane.setPrefHeight(200);
    pane.setPrefWidth(200);

    ComboBox<String> combo = new ComboBox<>();
    td.getDialogPane().getChildren().addAll(combo);
    combo.getItems().add("Hill");
    combo.getItems().add("Forest");
    combo.getItems().add("Plains");

    td.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    pane.setCenter(combo);
    td.getDialogPane().setContent(pane);

    Optional<ButtonType> result = td.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.OK)
      return combo.getValue().toLowerCase();
    else {
      td.close();
      return null;
    }
  }
}