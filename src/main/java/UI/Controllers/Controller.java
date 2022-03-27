package UI.Controllers;

import Army.Army;
import Army.Units.InfantryUnit;
import Army.Units.Unit;
import Simulation.Battle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
  public TableColumn armyTwoTableColumn;
  public TableView<Unit> armyTwoTableView;
  public TableColumn army1TableColumn;
  public TableView<Unit> armyOneTableView;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Army army1 = new Army("Blue");
    Army army2 = new Army("Red");
    army1.add(new InfantryUnit("Delta", 10));
    army2.add(new InfantryUnit("Charlie", 10));

    Battle battleSimulation = new Battle(army1,army2);

    ObservableList<Unit> observableListOfUnits = FXCollections.observableList(new ArrayList<>());
    armyOneTableView.setItems(observableListOfUnits);
    //armyTwoTableView.setItems(observableListOfUnits);
    army1TableColumn.setCellFactory(new PropertyValueFactory<>("name"));
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

  }

  public void onStopSimulationClicked() {
  }
}
