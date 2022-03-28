package UI.Controllers;

import Army.Army;
import Army.Units.InfantryUnit;
import Army.Units.Unit;
import Simulation.Battle;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

public class Controller implements Initializable {
  public Label winnerLabel;
  public ListView actionsListView;
  public TableView<Unit> armyOneTableView;
  public TableView<Unit> armyTwoTableView;
  public TableColumn<Unit, String> armyOneTableColumn;
  public TableColumn<Unit, String> armyTwoTableColumn;
  private ObservableList<Unit> observableListOfUnitsArmyOne;
  private ObservableList<Unit> observableListOfUnitsArmyTwo;
  private Battle battleSimulation;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Army army1 = new Army("Blue");
    Army army2 = new Army("Red");
    army1.add(new InfantryUnit("Infantry delta", 10));
    army2.add(new InfantryUnit("Infantry charlie", 10));

    observableListOfUnitsArmyOne = FXCollections.observableList(army1.getUnits());
    observableListOfUnitsArmyTwo = FXCollections.observableList(army2.getUnits());

    armyOneTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    armyOneTableView.setItems(observableListOfUnitsArmyOne);
    armyTwoTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    armyTwoTableView.setItems(observableListOfUnitsArmyTwo);

    battleSimulation = new Battle(army1,army2);
  }

  public void onOpenButtonClicked() {
    FileChooser chooser = new FileChooser();
    chooser.showOpenDialog(null);
  }

  public void onCloseButtonClicked() {
    System.exit(0);
  }

  public void onAboutButtonClicked() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION, "About");
    alert.setContentText("This is an application made by Sander!");
    alert.setTitle("About");
    alert.setHeaderText("This is a war simulator.");
    alert.showAndWait();
  }

  public void onStartSimulationClicked() {
    winnerLabel.setText(battleSimulation.simulate().getName());
    ObservableList<Unit> listOfUnits = FXCollections.observableList(battleSimulation.simulate().getUnits());
    actionsListView.setItems(listOfUnits);
  }

  public void onStopSimulationClicked() {
  }
}
