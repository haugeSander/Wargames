package UI.Controllers;

import Army.Army;
import Army.ArmyFileHandler;
import Army.Units.Unit;
import Simulation.Battle;

import Simulation.BattleFileHandler;
import UI.Facade;
import UI.GUI;
import UI.MakeArmyPopup;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller implements Initializable {
  @FXML
  private Label armyOneName;
  @FXML
  private TableView<Unit> armyOneTableView;
  @FXML
  private TableColumn armyOneTypeColumn;
  @FXML
  private TableColumn armyOneNameColumn;
  @FXML
  private TableColumn armyOneHPColumn;
  @FXML
  private TableColumn armyOneAmountColumn; //Supposed to show amount if there a clones

  @FXML
  private Label armyTwoName;
  @FXML
  private TableView<Unit> armyTwoTableView;
  @FXML
  private TableColumn armyTwoTypeColumn;
  @FXML
  private TableColumn armyTwoNameColumn;
  @FXML
  private TableColumn armyTwoHPColumn;
  @FXML
  private TableColumn armyTwoAmountColumn; //Supposed to show amount if there a clones

  @FXML
  private ComboBox terrainSelection;
  private ObservableList<Unit> observableListOfUnitsArmyOne;
  private ObservableList<Unit> observableListOfUnitsArmyTwo;

  private Battle battleSimulation;
  private Army army1;
  private Army army2;

  /**
   * Constructor for the controller.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    terrainSelection.getItems().add("Hill");
    terrainSelection.getItems().add("Forest");
    terrainSelection.getItems().add("Plains");
    init();
  }

  private void init() {
    Facade facade = Facade.getInstance();
    if (facade.getBattle() == null) {
      army1 = new Army("Army 1");
      army2 = new Army("Army 2");
      battleSimulation = new Battle(army1, army2);
      facade.setBattle(battleSimulation);
    } else {
      battleSimulation = facade.getBattle();
      army1 = battleSimulation.getArmy1();
      army2 = battleSimulation.getArmy2();
    }
    observableListOfUnitsArmyOne =
        FXCollections.observableList(battleSimulation.getArmy1().getUnits());
    observableListOfUnitsArmyTwo =
        FXCollections.observableList(battleSimulation.getArmy2().getUnits());

    battleSimulation.getArmy1().setUnits(observableListOfUnitsArmyOne);
    battleSimulation.getArmy2().setUnits(observableListOfUnitsArmyTwo);
    armyOneTableView.setItems(
        observableListOfUnitsArmyOne); //Sets the list in armies as the observable list
    armyTwoTableView.setItems(
        observableListOfUnitsArmyTwo); //By doing this the list does not need to be updated.

    armyOneTypeColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
    armyOneNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    armyOneHPColumn.setCellValueFactory(new PropertyValueFactory<>("health"));

    armyTwoTypeColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
    armyTwoNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    armyTwoHPColumn.setCellValueFactory(new PropertyValueFactory<>("health"));

    armyOneName.setText(army1.getName());
    armyTwoName.setText(army2.getName());
  }

  /**
   * Close button in menu, exits application after a confirmation.
   */
  @FXML
  private void onCloseButtonClicked() {
    GUI.exit((Stage) terrainSelection.getScene().getWindow());
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
   * If units are available a simulation will be run.
   * If not an alert telling user to add units will show.
   */
  @FXML
  private void onSimulateButtonClicked() {
    try {
      Facade.getInstance().setTerrain(terrainSelection.getValue().toString().toLowerCase());
    } catch (Exception e) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("No units to fight each other..");
      alert.setContentText("To simulate add units.");
      alert.showAndWait();
    }
  }

  /**
   * Button to save armies made.
   * Awaits the makeArmyPopup feature.
   */
  @FXML
  private void onSaveButtonClicked() {
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
      battleSimulation = null;
      army1 = null;
      army2 = null;
      Facade.getInstance().setBattle(null);
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-menu.fxml"));
      Scene scene = new Scene(fxmlLoader.load(), 800, 600);
      Stage stage = (Stage) terrainSelection.getScene().getWindow();
      stage.setScene(scene);
    } else
      alert.close();
  }

  @FXML
  private void onOpenBattleClicked() {
    FileChooser chooser = new FileChooser();
    File selectedFile = chooser.showOpenDialog(null);
    Facade facade = Facade.getInstance();

    try {
      facade.setBattle(BattleFileHandler.readFile(selectedFile.getPath()));
      init();
    } catch (Exception e) {
      Alert noFileExists = new Alert(Alert.AlertType.WARNING);
      noFileExists.setTitle("File error");
      noFileExists.setHeaderText("The file selected not supported or nothing selected!");
      noFileExists.setContentText("Remember only .csv files are supported.");
      noFileExists.showAndWait();
    }
  }

  @FXML
  private void onOpenToArmy1ButtonClicked() {
    army1 = addArmyFromFile();
    if (army1 != null) {
      armyOneName.setText(army1.getName());
      observableListOfUnitsArmyOne.addAll(army1.getUnits());
    }
  }

  @FXML
  private void onAddUnitArmy1Clicked() {
  }

  @FXML
  private void onOpenToArmy2ButtonClicked() {
    army2 = addArmyFromFile();
    if (army2 != null) {
      armyTwoName.setText(army2.getName());
      observableListOfUnitsArmyTwo.addAll(army2.getUnits());
    }
  }

  @FXML
  private void onRemoveUnitArmy1Clicked() {
    removeUnit(1);
  }

  @FXML
  private void onRemoveUnitArmy2Clicked() {
    removeUnit(2);
  }

  @FXML
  private void onAddUnitArmyTwoClicked() {
  }

  /**
   * Remove units from either army1 or 2 list.
   *
   * @param armyNumber integer representing the army number.
   */
  private void removeUnit(int armyNumber) {
    try {
      Unit unitToRemove = null;

      if (armyNumber == 1) {
        unitToRemove = armyOneTableView.getSelectionModel().getSelectedItem();
      } else if (armyNumber == 2)
        unitToRemove = armyTwoTableView.getSelectionModel().getSelectedItem();

      if (unitToRemove != null) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
            "Are you sure you want to delete " + unitToRemove.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK && armyNumber == 1) {
          battleSimulation.getArmy1().getUnits().remove(unitToRemove);
        } else if (result.isPresent() && result.get() == ButtonType.OK && armyNumber == 2) {
          battleSimulation.getArmy2().getUnits().remove(unitToRemove);
        }
      }
    } catch (Exception e) {
      Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
      alert.setHeaderText("Unit does not exist.");
      alert.showAndWait();
    }
  }

  /**
   * Method to get a csv file then return the army object.
   *
   * @return Army object.
   */
  private Army addArmyFromFile() {
    FileChooser chooser = new FileChooser();
    Army tempArmy = null;
    File selectedFile;

    selectedFile = chooser.showOpenDialog(null);

    try {
      if (selectedFile != null && selectedFile.getName().contains(".csv"))
        tempArmy = ArmyFileHandler.readFile(selectedFile.getPath());
    }catch(Exception e){
      Alert noFileExists = new Alert(Alert.AlertType.WARNING);
      noFileExists.setTitle("File error");
      noFileExists.setHeaderText("The file selected not supported or nothing selected!");
      noFileExists.setContentText("Remember only .csv files are supported.");
      noFileExists.showAndWait();
    }
    return tempArmy;
  }
}