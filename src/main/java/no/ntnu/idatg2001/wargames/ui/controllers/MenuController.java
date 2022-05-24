package no.ntnu.idatg2001.wargames.ui.controllers;

import no.ntnu.idatg2001.wargames.model.WargamesModel;
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
import no.ntnu.idatg2001.wargames.ui.dialogs.MainHelpDialog;
import no.ntnu.idatg2001.wargames.ui.Main;

/**
 * Controller class for main-menu fxml file.
 * Page is used as front page of application.
 * @author Sander Hauge
 * @version 1.0-SNAPSHOT
 */
public class MenuController implements Initializable {
  @FXML private ImageView openSimLogo;
  @FXML private ImageView newSimLogo;
  @FXML private ImageView logo;
  @FXML private ImageView exitLogo;

  /**
   * Initialize for main menu. Primary function is to set images/logos.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    setLogos();
  }

  private void setLogos() {
    File tankLogo = new File("src/main/resources/no/ntnu/idatg2001/wargames/UI/Controllers/Logos/Tank.png");
    File editLogo = new File("src/main/resources/no/ntnu/idatg2001/wargames/UI/Controllers/Logos/edit.png");
    File importLogo = new File("src/main/resources/no/ntnu/idatg2001/wargames/UI/Controllers/Logos/import.png");
    File leaveLogo = new File("src/main/resources/no/ntnu/idatg2001/wargames/UI/Controllers/Logos/exit.png");

    logo.setImage(new Image(tankLogo.toURI().toString()));
    newSimLogo.setImage(new Image(editLogo.toURI().toString()));
    openSimLogo.setImage(new Image(importLogo.toURI().toString()));
    exitLogo.setImage(new Image(leaveLogo.toURI().toString()));
  }

  /**
   * Opens the main page with no units.
   * Prompts user to select a type of terrain.
   */
  @FXML
  private void onNewSimulationButtonPressed() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
      Stage stage = (Stage) logo.getScene().getWindow();
      Scene scene = new Scene(fxmlLoader.load(), 875, 615);
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
  @FXML
  private void onExitButtonPressed() {
    Main.exit((Stage)logo.getScene().getWindow());
  }

  /**
   * Button to open a save file for battle.
   */
  @FXML
  private void onOpenSimulationButtonPressed() {
    FileChooser chooser = new FileChooser();
    File selectedFile = chooser.showOpenDialog(logo.getScene().getWindow());
    chooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("*.csv","Comma Separated File"));

    try {
      if (selectedFile != null && selectedFile.getName().contains(".csv")) {
        WargamesModel.getInstance().readFromFile(selectedFile.getPath(), "");
        //Empty string counts as an object. Battle file is read, when anything but army objects are passed.
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage) logo.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 875, 615);
        stage.setScene(scene);
      }
    } catch (Exception e) {
      Alert noFileExists = new Alert(Alert.AlertType.WARNING);
      noFileExists.setTitle("File error");
      noFileExists.setHeaderText("The file selected not supported or nothing selected!");
      noFileExists.setContentText
          ("Remember only .csv files are supported. Battle save file necessary." + e.getMessage());
      noFileExists.showAndWait();
    }
  }

  /**
   * Shows a help dialog.
   */
  @FXML
  private void onHelpButtonPressed() {
    MainHelpDialog faq = new MainHelpDialog();
    faq.showDialog();
  }
}