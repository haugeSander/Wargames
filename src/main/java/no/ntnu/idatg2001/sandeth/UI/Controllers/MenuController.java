package no.ntnu.idatg2001.sandeth.UI.Controllers;

import no.ntnu.idatg2001.sandeth.Model.BattleModel;
import no.ntnu.idatg2001.sandeth.UI.GUI;
import no.ntnu.idatg2001.sandeth.Model.Model;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuController implements Initializable {
  @FXML private ImageView openSimLogo;
  @FXML private ImageView newSimLogo;
  @FXML private ImageView logo;
  @FXML private ImageView exitLogo;

  /**
   * Constructor for main menu.
   * Sets image.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    setLogos();
  }

  private void setLogos() {
    File tankLogo = new File("src/main/resources/no/ntnu/idatg2001/sandeth/UI/Controllers/Logos/Tank.png");
    File editLogo = new File("src/main/resources/no/ntnu/idatg2001/sandeth/UI/Controllers/Logos/edit.png");
    File importLogo = new File("src/main/resources/no/ntnu/idatg2001/sandeth/UI/Controllers/Logos/import.png");
    File leaveLogo = new File("src/main/resources/no/ntnu/idatg2001/sandeth/UI/Controllers/Logos/exit.png");

    logo.setImage(new Image(tankLogo.toURI().toString()));
    newSimLogo.setImage(new Image(editLogo.toURI().toString()));
    openSimLogo.setImage(new Image(importLogo.toURI().toString()));
    exitLogo.setImage(new Image(leaveLogo.toURI().toString()));
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
    chooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("*.csv","Comma Separated File"));

    try {
      if (selectedFile != null && selectedFile.getName().contains(".csv")) {
        BattleModel.getInstance().readFromFile(selectedFile.getPath(), "");
        //Empty string counts as an object. Battle file is read, when anything but army objects are passed.
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage) logo.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 815, 600);
        stage.setScene(scene);
      }
    } catch (Exception e) {
      Alert noFileExists = new Alert(Alert.AlertType.WARNING);
      noFileExists.setTitle("File error");
      noFileExists.setHeaderText("The file selected not supported or nothing selected!");
      noFileExists.setContentText
          ("Remember only .csv files are supported. Battle save file necessary.");
      noFileExists.showAndWait();
    }
  }

  public void onHelpButtonPressed() {
  }
}