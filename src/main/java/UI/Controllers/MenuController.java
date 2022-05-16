package UI.Controllers;

import Simulation.BattleFileHandler;
import UI.Facade;
import UI.GUI;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
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
  @FXML private ImageView openSimLogo;
  @FXML private ImageView newSimLogo;
  @FXML private ImageView logo;

  /**
   * Constructor for main menu.
   * Sets image.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    File tankLogo = new File("src/main/resources/UI/Controllers/Logos/Tank.png");
    logo.setImage(new Image(tankLogo.toURI().toString()));
    File editLogo = new File("src/main/resources/UI/Controllers/Logos/edit.png");
    newSimLogo.setImage(new Image(editLogo.toURI().toString()));
    File importLogo = new File("src/main/resources/UI/Controllers/Logos/import.png");
    openSimLogo.setImage(new Image(importLogo.toURI().toString()));
  }

  /**
   * Opens the main page with no units.
   * Prompts user to select a type of terrain.
   */
  public void onNewSimulationButtonPressed() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
      Stage stage = (Stage) logo.getScene().getWindow();
      Scene scene = new Scene(fxmlLoader.load(), 815, 600);
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
    GUI.exit((Stage)logo.getScene().getWindow());
  }

  /**
   * Button to open a save file for battle.
   */
  public void onOpenSimulationButtonPressed() {
    FileChooser chooser = new FileChooser();
    File selectedFile = chooser.showOpenDialog(logo.getScene().getWindow());
    chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.csv","Comma Separated File"));
    Facade facade = Facade.getInstance();

    try {
      facade.setBattle(BattleFileHandler.readFile(selectedFile.getPath()));
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
      Stage stage = (Stage) logo.getScene().getWindow();
      Scene scene = new Scene(fxmlLoader.load(), 815, 600);
      stage.setScene(scene);
    } catch (Exception e) {
      Alert noFileExists = new Alert(Alert.AlertType.WARNING);
      noFileExists.setTitle("File error");
      noFileExists.setHeaderText("The file selected not supported or nothing selected!");
      noFileExists.setContentText("Remember only .csv files are supported.");
      noFileExists.showAndWait();
    }
  }
}