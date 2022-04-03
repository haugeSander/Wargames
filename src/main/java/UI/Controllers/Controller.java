package UI.Controllers;

import Army.Army;
import Army.ArmyFileHandler;
import Army.Units.Unit;
import Simulation.Battle;

import UI.Facade;
import UI.MakeArmyPopup;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller implements Initializable {
  @FXML private Label terrain;
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
   Facade facade = Facade.getInstance();
    battleSimulation = facade.getBattle();
    updateArmies(facade.getBattle().getArmy1(), facade.getBattle().getArmy2());
    terrain.setText(facade.getTerrain().toUpperCase());

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
     battleSimulation.setTerrain(Facade.getInstance().getTerrain());

     observableListOfUnitsArmyOne = FXCollections.observableList(battleSimulation.getArmy1().getUnits());
     armyOneTableView.setItems(observableListOfUnitsArmyOne);

     observableListOfUnitsArmyTwo = FXCollections.observableList(battleSimulation.getArmy2().getUnits());
     armyTwoTableView.setItems(observableListOfUnitsArmyTwo);
  }

  /**
   * Close button in menu, exits application after a confirmation.
   */
  @FXML
  private void onCloseButtonClicked() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
    alert.setHeaderText("Are you sure you want to exit?");
    alert.showAndWait()
        .filter(response -> response == ButtonType.OK)
        .ifPresent(response -> System.exit(0));
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
  private void onStartSimulationClicked() {
    try {
      winnerLabel.setText(battleSimulation.simulate().getName());
      ObservableList<Unit> listOfUnits =
          FXCollections.observableList(battleSimulation.simulate().getUnits());
      actionsListView.setItems(listOfUnits);
    }catch (Exception e) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("No units to fight each other..");
      alert.setContentText("To simulate add units.");
      alert.showAndWait();
    }
  }

  /**
   * Button to open dialog box where user may
   * make their own army.
   */
  @FXML
  private void onMakeArmyClicked() {
    MakeArmyPopup makeArmy = new MakeArmyPopup();
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
  public void onBackButtonClicked() throws IOException {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
    alert.setHeaderText("Are you sure you want to exit?");
    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.OK) {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-menu.fxml"));
      Scene scene = new Scene(fxmlLoader.load(), 800, 600);
      Stage stage = (Stage) winnerLabel.getScene().getWindow();
      stage.setScene(scene);
    } else
      alert.close();
  }
}
