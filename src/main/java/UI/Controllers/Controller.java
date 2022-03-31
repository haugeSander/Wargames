package UI.Controllers;

import Army.Army;
import Army.ArmyFileHandler;
import Army.Units.InfantryUnit;
import Army.Units.Unit;
import Simulation.Battle;

import UI.MakeArmyPopup;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

public class Controller implements Initializable {
  @FXML private Label winnerLabel;
  @FXML private ListView actionsListView;
  @FXML private TableView<Unit> armyOneTableView;
  @FXML private TableView<Unit> armyTwoTableView;
  @FXML private TableColumn<Unit, String> armyOneTableColumn;
  @FXML private TableColumn<Unit, String> armyTwoTableColumn;

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
    army1 = new Army("Blue");
    army2 = new Army("Red");
    army1.add(new InfantryUnit("Infantry delta", 10));
    army2.add(new InfantryUnit("Infantry charlie", 10));

    battleSimulation = new Battle(army1,army2);
    updateArmies(army1, army2);

    armyOneTableColumn.setCellValueFactory(new PropertyValueFactory<>("listViewGUI"));
    armyTwoTableColumn.setCellValueFactory(new PropertyValueFactory<>("listViewGUI"));

  }

  /**
   * Open button in menu.
   * When a .csv file is selected, the selected
   * army will be switched with the file.
   */
  @FXML
  private void onOpenButtonClicked() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setHeaderText("Select");
    alert.setHeaderText("Select which army to import into.");
    alert.setContentText("Import .csv files, all other filetypes will not work!");

    ButtonType army1Button = new ButtonType("Army 1");
    ButtonType army2Button = new ButtonType("Army 2");
    ButtonType cancel = new ButtonType("Cancel");

    alert.getButtonTypes().clear();
    alert.getButtonTypes().addAll(army1Button,army2Button,cancel);

    Optional<ButtonType> option = alert.showAndWait();
    FileChooser chooser = new FileChooser();
    Army buttonSelect = null;
    File selectedFile;

    if (option.get() == army1Button) {
      buttonSelect = army1;
    } else if (option.get() == army2Button) {
      buttonSelect = army2;
    } else {
      alert.close();
    }
    selectedFile = chooser.showOpenDialog(null);

    if (selectedFile != null && buttonSelect == army1 && selectedFile.getName().contains(".csv")) {
      army1 = ArmyFileHandler.readFile(selectedFile.getPath());
      updateArmies(army1, null);
      armyOneTableColumn.setText(army1.getName());
    } else if (selectedFile != null && buttonSelect == army2 && selectedFile.getName().contains(".csv")) {
      army2 = ArmyFileHandler.readFile(selectedFile.getPath());
      updateArmies(null, army2);
      armyTwoTableColumn.setText(army2.getName());
    } else {
      Alert noFileExists = new Alert(Alert.AlertType.WARNING);
      noFileExists.setTitle("File error");
      noFileExists.setHeaderText("The file selected not supported or nothing selected!");
      noFileExists.setContentText("Remember only .csv files are supported.");
      noFileExists.showAndWait();
    }
  }

  /**
   * Method to update the lists and army.
   * @param armyOne New army1.
   * @param armyTwo New army2.
   */
  private void updateArmies(Army armyOne, Army armyTwo) {
     battleSimulation.updateArmies(armyOne, armyTwo);

     observableListOfUnitsArmyOne = FXCollections.observableList(battleSimulation.getArmy1().getUnits());
     armyOneTableView.setItems(observableListOfUnitsArmyOne);

     observableListOfUnitsArmyTwo = FXCollections.observableList(battleSimulation.getArmy2().getUnits());
     armyTwoTableView.setItems(observableListOfUnitsArmyTwo);
  }

  /**
   * Close button in menu, exits application.
   */
  @FXML
  private void onCloseButtonClicked() {
    System.exit(0);
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
   * Runs the simulation.
   */
  @FXML
  private void onStartSimulationClicked() {
    winnerLabel.setText(battleSimulation.simulate().getName());
    ObservableList<Unit> listOfUnits = FXCollections.observableList(battleSimulation.simulate().getUnits());
    actionsListView.setItems(listOfUnits);
  }

  /**
   * Button to open dialog box where user may
   * make their own army.
   */
  @FXML
  private void onMakeArmyClicked() {
    MakeArmyPopup makeArmy = new MakeArmyPopup();
  }

  @FXML
  private void onSaveButtonClicked() {

  }
}
