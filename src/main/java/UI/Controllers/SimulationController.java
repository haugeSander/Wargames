package UI.Controllers;

import UI.Facade;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;

public class SimulationController {

  public TableView army1View;
  public TableColumn army1TypeColumn;
  public TableColumn army1AmountColumn;
  public TableView army2View;
  public TableColumn army2TypeColumn;
  public TableColumn army2AmountColumn;
  public Label terrain;
  public Label winnerLabel;
  public LineChart chart;
  public ListView log;

  public SimulationController() {

  }


  /**
   * If units are available a simulation will be run.
   * If not an alert telling user to add units will show.
   */
  @FXML
  private void onSimulateButtonClicked() {
    try {

    }catch (Exception e) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("No units to fight each other..");
      alert.setContentText("To simulate add units.");
      alert.showAndWait();
    }
  }

  /**
   * When run multiple simulation button is pressed,
   * user is prompted to type how many times to run.
   *
   * Not functioning because army is not updated.
   */
  @FXML
  private void onRunMultipleSimulationsClicked() {
    TextInputDialog inputDialog = new TextInputDialog();
    Optional<String> result = inputDialog.showAndWait();
    List<String> winnerEachRound = new ArrayList<>();
    int amount = 0;
    //army1 = Facade.getInstance().getBattle().getArmy1();
    //army2 = Facade.getInstance().getBattle().getArmy2();

    if (result.isPresent() && !result.get().isEmpty()) {
      try {
        amount = Integer.parseInt(result.get());

        for (int i = 0; i < amount; i++) {
          //winnerEachRound.add(battleSimulation.simulate().getName());
          //battleSimulation.updateArmies(army1, army2);
        }

        System.out.println(winnerEachRound.stream().collect(Collectors.groupingBy(t -> t, Collectors.counting())));

        ObservableList<String> observableList =
            FXCollections.observableList(winnerEachRound);
        //actionsListView.setItems(observableList);


      } catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(e.getMessage());
        alert.showAndWait();
      }
    }
  }

  public void onGoBackPressed(ActionEvent actionEvent) {
  }

  public void onRunSimulationPressed(ActionEvent actionEvent) {
  }

  public void onMultipleSimulationsPressed(ActionEvent actionEvent) {
  }
}
