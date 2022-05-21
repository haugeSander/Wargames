package no.ntnu.idatg2001.sandeth.UI.Controllers;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.control.SelectionMode;
import no.ntnu.idatg2001.sandeth.Army.Units.Unit;

import no.ntnu.idatg2001.sandeth.Model.BattleModel;
import no.ntnu.idatg2001.sandeth.UI.Controllers.Dialogs.AddUnitsDialog;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import no.ntnu.idatg2001.sandeth.UI.Controllers.Dialogs.BMHelpDialog;
import no.ntnu.idatg2001.sandeth.UI.GUI;

public class BattleManagerController implements Initializable {
  @FXML private Label terrainLabel;
  @FXML private Button changeArmy1Name;
  @FXML private Button changeArmy2Name;
  @FXML private ImageView simulateLogo;
  @FXML private ImageView importBattleLogo;
  @FXML private ImageView importA1Logo;
  @FXML private ImageView addUnitA1Logo;
  @FXML private ImageView removeA1Logo;
  @FXML private ImageView importA2Logo;
  @FXML private ImageView addA2Logo;
  @FXML private ImageView removeA2Logo;
  @FXML private ImageView hillView;
  @FXML private ImageView plainsView;
  @FXML private ImageView forestView;

  @FXML private Label armyOneName;
  @FXML private TableView<Unit> armyOneTableView;
  @FXML private TableColumn<Unit, String> armyOneTypeColumn;
  @FXML private TableColumn<Unit, String> armyOneNameColumn;
  @FXML private TableColumn<Unit, Integer> armyOneHPColumn;

  @FXML private Label armyTwoName;
  @FXML private TableView<Unit> armyTwoTableView;
  @FXML private TableColumn<Unit, String> armyTwoTypeColumn;
  @FXML private TableColumn<Unit, String> armyTwoNameColumn;
  @FXML private TableColumn<Unit, Integer> armyTwoHPColumn;

  private BattleModel battleModel;
  private String army1Name;
  private String army2Name;

  /**
   * Constructor for the controller.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    battleModel = BattleModel.getInstance();

    armyOneTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    armyTwoTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    //Allows user to select more items in table, for example to delete multiple units.

    armyOneTypeColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
    armyOneNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    armyOneHPColumn.setCellValueFactory(new PropertyValueFactory<>("health"));

    armyTwoTypeColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
    armyTwoNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    armyTwoHPColumn.setCellValueFactory(new PropertyValueFactory<>("health"));

    if (battleModel.getTerrain() != null)
      terrainLabel.setText(battleModel.getTerrain());

    init();
  }

  /**
   * Initializer accessible to BattleManagerController
   * methods. Used by import battle method.
   */
  private void init() {
    army1Name = battleModel.getBattle().getArmy1().getName();
    army2Name = battleModel.getBattle().getArmy2().getName();
    armyOneName.setText(army1Name);
    armyTwoName.setText(army2Name);

    ObservableList<Unit> observableListOfUnitsArmyOne =
        FXCollections.observableList(battleModel.getBattle().getArmy1().getUnits());
    ObservableList<Unit> observableListOfUnitsArmyTwo =
        FXCollections.observableList(battleModel.getBattle().getArmy2().getUnits());

    battleModel.getBattle().getArmy1().setUnits(observableListOfUnitsArmyOne);
    battleModel.getBattle().getArmy2().setUnits(observableListOfUnitsArmyTwo);
    armyOneTableView.setItems(observableListOfUnitsArmyOne); //Sets the list in armies as the observable list
    armyTwoTableView.setItems(observableListOfUnitsArmyTwo);

    setLogos();
    gifSetup();
  }

  /**
   * Set images on each of the logos.
   */
  private void setLogos() {
    File editLogo = new File
        ("src/main/resources/no/ntnu/idatg2001/sandeth/UI/Controllers/Logos/edit.png");
    File importLogo = new File
        ("src/main/resources/no/ntnu/idatg2001/sandeth/UI/Controllers/Logos/import.png");
    File listPlusLogo = new File
        ("src/main/resources/no/ntnu/idatg2001/sandeth/UI/Controllers/Logos/list-plus.png");
    File listMinusLogo = new File
        ("src/main/resources/no/ntnu/idatg2001/sandeth/UI/Controllers/Logos/list-minus.png");
    File playLogo = new File
        ("src/main/resources/no/ntnu/idatg2001/sandeth/UI/Controllers/Logos/play.png");

    changeArmy1Name.setGraphic(new ImageView(new Image(editLogo.toURI().toString())));
    addUnitA1Logo.setImage(new Image(listPlusLogo.toURI().toString()));
    importA1Logo.setImage(new Image(importLogo.toURI().toString()));
    removeA1Logo.setImage(new Image(listMinusLogo.toURI().toString()));

    changeArmy2Name.setGraphic(new ImageView(new Image(editLogo.toURI().toString())));
    addA2Logo.setImage(new Image(listPlusLogo.toURI().toString()));
    importA2Logo.setImage(new Image(importLogo.toURI().toString()));
    removeA2Logo.setImage(new Image(listMinusLogo.toURI().toString()));

    importBattleLogo.setImage(new Image(importLogo.toURI().toString()));
    simulateLogo.setImage(new Image(playLogo.toURI().toString()));
  }

  private void gifSetup() {
    File forestGIF = new File
        ("src/main/resources/no/ntnu/idatg2001/sandeth/UI/Controllers/Images/forest.gif");
    File hillsGIF = new File
        ("src/main/resources/no/ntnu/idatg2001/sandeth/UI/Controllers/Images/hills.gif");
    File plainsGIF = new File
        ("src/main/resources/no/ntnu/idatg2001/sandeth/UI/Controllers/Images/plains.gif");

    forestView.setImage(new Image(forestGIF.toURI().toString()));
    hillView.setImage(new Image(hillsGIF.toURI().toString()));
    plainsView.setImage(new Image(plainsGIF.toURI().toString()));
  }

  /**
   * Close button in menu, exits application after a confirmation.
   */
  @FXML
  private void onCloseButtonClicked() {
    GUI.exit((Stage)armyOneName.getScene().getWindow());
  }

  /**
   * About button in menu shows information about creator and program.
   */
  @FXML
  private void onAboutButtonClicked() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION, "About");
    alert.setContentText("This is an application made by Sander!");
    alert.setTitle("About");
    alert.setHeaderText("This is a war simulator.");
    alert.showAndWait();
  }

  /**
   * If units are available and terrain selected simulation page will be opened.
   * If not an alert telling user to add units or select terrain will show.
   */
  @FXML
  private void onSimulateButtonClicked() {
    Alert alert = new Alert(Alert.AlertType.WARNING);

    try {
      if (battleModel.getTerrain() == null)
        throw new Exception("Terrain not selected!");

      if (battleModel.isEmpty()) {
        alert.setHeaderText("No units to fight each other..");
        alert.setContentText("To simulate add units.");
        alert.showAndWait();
      } else {
        battleModel.makeDuplicateArmies();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("simulation-view.fxml"));
        Stage stage = (Stage) simulateLogo.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 815, 600);
        stage.setScene(scene);
      }
    } catch (Exception e) {
      if (BattleModel.getInstance().getTerrain() == null) {
        alert.setHeaderText("Select a terrain to continue.");
      } else {
        alert.setHeaderText(e.getMessage());
      }
      alert.showAndWait();
    }
  }

  /**
   * Button to save armies made.
   * Allows user to select save location.
   */
  @FXML
  private void onSaveButtonClicked() {
    Alert saveSelector = new Alert(Alert.AlertType.CONFIRMATION);
    saveSelector.setTitle("Save");
    saveSelector.setHeaderText("Select what to save or cancel.");
    ButtonType army1Save = new ButtonType("Army 1");
    ButtonType army2Save = new ButtonType("Army 2");
    ButtonType battleSave = new ButtonType("Battle");
    saveSelector.getButtonTypes().setAll(army1Save, army2Save, battleSave, ButtonType.CANCEL);

    Optional<ButtonType> selectionOfSave = saveSelector.showAndWait();

    if (selectionOfSave.isPresent() && !(selectionOfSave.get() == ButtonType.CANCEL)) {
      FileChooser chooser = new FileChooser();

      try {
        Object toSave = "";

        if (selectionOfSave.get() == army1Save) {
          chooser.setInitialFileName(army1Name.strip());
          toSave = battleModel.getArmy1();
        }
        else if (selectionOfSave.get() == army2Save) {
          chooser.setInitialFileName(army2Name.strip());
          toSave = battleModel.getArmy2();
        }
        else if (selectionOfSave.get() == battleSave) {
          chooser.setInitialFileName(army1Name.strip() + "-vs-" + army2Name.strip());
          toSave = battleModel.getBattle();
        }
        chooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("*.csv", "Comma Separated File"));
        File selectedPath = chooser.showSaveDialog(simulateLogo.getScene().getWindow());
        BattleModel.getInstance().saveToFile(selectedPath, toSave);
      } catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Something went wrong.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
      }
    }
  }

  /**
   * Button which sends user back to front page.
   */
  @FXML
  private void onBackButtonClicked() throws IOException {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
    alert.setHeaderText("Are you sure you want to go back?");
    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.OK) {
      battleModel.reset();
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-menu.fxml"));
      Scene scene = new Scene(fxmlLoader.load(), 815, 600);
      Stage stage = (Stage) simulateLogo.getScene().getWindow();
      stage.setScene(scene);
    } else
      alert.close();
  }

  /**
   * Method to open battle formatted .csv save file into both
   * army1 and 2.
   */
  @FXML
  private void onOpenBattleClicked() {
    FileChooser chooser = new FileChooser();
    File selectedFile = chooser.showOpenDialog(simulateLogo.getScene().getWindow());
    chooser.getExtensionFilters().addAll
        (new FileChooser.ExtensionFilter("*.csv", "Comma Separated File"));

    try {
      if (selectedFile != null && selectedFile.getName().contains(".csv")) {
        BattleModel.getInstance().readFromFile(selectedFile.getPath(), battleModel.getBattle());
        init();
      }
    } catch (Exception e) {
      Alert noFileExists = new Alert(Alert.AlertType.WARNING);
      noFileExists.setTitle("File error");
      noFileExists.setHeaderText("The file selected not supported or nothing selected!");
      noFileExists.setContentText("Remember only .csv files are supported.");
      noFileExists.showAndWait();
    }
  }

  /**
   * Army 1's method to open add units dialog.
   */
  @FXML
  private void onAddUnitArmy1Clicked() {
    AddUnitsDialog army1Add = new AddUnitsDialog();
    army1Add.showDialog(1);
  }

  /**
   * Army 2's method to open add units dialog.
   */
  @FXML
  private void onAddUnitArmyTwoClicked() {
    AddUnitsDialog army2Add = new AddUnitsDialog();
    army2Add.showDialog(2);
  }

  /**
   * Method to remove unit from army1 by selecting from list.
   * Sends integer 1 representing army1.
   */
  @FXML
  private void onRemoveUnitArmy1Clicked() {
    removeUnit(1);
  }

  /**
   * Method to remove unit from army2 by selecting from list.
   * Sends integer 2 representing army2.
   */
  @FXML
  private void onRemoveUnitArmy2Clicked() {
    removeUnit(2);
  }

  /**
   * Remove units from either army1 or 2 list.
   * @param armyNumber Integer representing the army to delete from.
   */
  private void removeUnit(int armyNumber) {
    try {
      ObservableList<Unit> selectedRows1 = armyOneTableView.getSelectionModel().getSelectedItems();
      ObservableList<Unit> selectedRows2 = armyTwoTableView.getSelectionModel().getSelectedItems();
      ArrayList<Unit> rows = null;

      if (armyNumber == 1) {
        rows = new ArrayList<>(selectedRows1);
      } else if (armyNumber == 2) {
        rows = new ArrayList<>(selectedRows2);
      }

      if (rows != null && (!selectedRows1.isEmpty() || !selectedRows2.isEmpty())) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
            "Are you sure you want to delete selected item(s)?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK && armyNumber == 1) {
          rows.forEach(row -> armyOneTableView.getItems().remove(row));
        } else if (result.isPresent() && result.get() == ButtonType.OK) {
          rows.forEach(row -> armyTwoTableView.getItems().remove(row));
        }
      }
    } catch (Exception e) {
      Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
      alert.setHeaderText("Unit does not exist.");
      alert.showAndWait();
    }
  }

  /**
   * Method to open army formatted .csv save file into army1.
   */
  @FXML
  private void onOpenToArmy1ButtonClicked() {
    addArmyFromFile(battleModel.getArmy1());
    init();
  }

  /**
   * Method to open army formatted .csv save file into army2.
   */
  @FXML
  private void onOpenToArmy2ButtonClicked() {
    addArmyFromFile(battleModel.getArmy2());
    init();
  }

  /**
   * Method to get a csv file then return the army object.
   */
  private void addArmyFromFile(Object obj) {
    FileChooser chooser = new FileChooser();

    File selectedFile = chooser.showOpenDialog(simulateLogo.getScene().getWindow());
    chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.csv", "Comma Separated File"));

    try {
      if (selectedFile != null)
        BattleModel.getInstance().readFromFile(selectedFile.getPath(), obj);
    }catch(Exception e){
      Alert noFileExists = new Alert(Alert.AlertType.WARNING);
      noFileExists.setTitle("File error");
      noFileExists.setHeaderText("The file selected not supported or nothing selected!");
      noFileExists.setContentText("Remember only .csv files are supported.");
      noFileExists.showAndWait();
    }
  }

  /**
   * Method to change army 1 name.
   */
  @FXML
  private void changeArmy1Name() {
    TextInputDialog newArmy1Name = new TextInputDialog();
    newArmy1Name.setHeaderText("Enter new name.");
    Optional<String> result = newArmy1Name.showAndWait();

    if (result.isPresent()) {
      battleModel.getBattle().getArmy1().setName(result.get());
      armyOneName.setText(result.get());
    } else
      newArmy1Name.close();
  }

  /**
   * Method to change army 1 name.
   */
  @FXML
  private void changeArmy2Name() {
    TextInputDialog newArmy2Name = new TextInputDialog();
    Optional<String> result = newArmy2Name.showAndWait();
    newArmy2Name.setHeaderText("Enter new name.");

    if (result.isPresent()) {
      battleModel.getBattle().getArmy2().setName(result.get());
      armyTwoName.setText(result.get());
    } else
      newArmy2Name.close();
  }

  @FXML
  private void onForestButtonClicked() {
    BattleModel.getInstance().setTerrain("Forest");
    terrainLabel.setText("Forest");
  }

  @FXML
  private void onHillsButtonClicked() {
    BattleModel.getInstance().setTerrain("Hill");
    terrainLabel.setText("Hill");
  }

  @FXML
  private void onPlainsButtonClicked() {
    BattleModel.getInstance().setTerrain("Plains");
    terrainLabel.setText("Plains");
  }

  public void onHelpPressed() {
    BMHelpDialog bmHelpDialog = new BMHelpDialog();
    bmHelpDialog.showDialog();
  }
}