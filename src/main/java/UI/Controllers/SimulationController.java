package UI.Controllers;

import Army.Army;
import Army.Units.Unit;
import Simulation.Battle;
import UI.Facade;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SimulationController implements Initializable {

  public TableView army1View;
  public TableColumn army1NameColumn;
  public TableColumn army1HPColumn;
  public TableView army2View;
  public TableColumn army2NameColumn;
  public TableColumn army2HPColumn;

  public Label terrain;
  public Label winnerLabel;

  public LineChart chart;
  public ListView log;
  public Label army1Name;
  public Label army2Name;

  private XYChart.Series<String,Number> unitsArmy1;
  private XYChart.Series<String,Number> unitsArmy2;
  private int counter;

  private List<String> listOfTypes;

  private Timeline timeline;

  private ObservableList<Unit> observableListArmy1;
  private ObservableList<Unit> observableListArmy2;

  private Battle battle;
  private Army army1;

  private Army army2;
  public SimulationController() {
  }
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    init();

  }

  private void init() {
    Facade facade = Facade.getInstance();
    terrain.setText(facade.getTerrain());

    unitsArmy1 = new XYChart.Series<>();
    unitsArmy2 = new XYChart.Series<>();
    counter = 0;

    chart.getData().addAll(unitsArmy1,unitsArmy2);

    chart.setCreateSymbols(false);

    battle = facade.getBattle();
    army1 = battle.getArmy1();
    army2 = battle.getArmy2();

    observableListArmy1 = FXCollections.observableList(army1.getUnits());
    observableListArmy2 = FXCollections.observableList(army2.getUnits());
    army1View.setItems(observableListArmy1); //Sets the list in armies as the observable list
    army2View.setItems(observableListArmy2); //By doing this the list does not need to be updated.
    army1.setUnits(observableListArmy1);
    army2.setUnits(observableListArmy2);

    army1NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    army2NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    army1HPColumn.setCellValueFactory(new PropertyValueFactory<>("health"));
    army2HPColumn.setCellValueFactory(new PropertyValueFactory<>("health"));
    army1Name.setText(army1.getName());
    army2Name.setText(army2.getName());
  }

  private void duplicateArmy(Army army1, Army army2) {
    Army duplicateArmy1 = new Army(army1.getName());
    Army duplicateArmy2 = new Army(army2.getName());

    for (Unit u : army1.getUnits()) {
      duplicateArmy1.add(u);
    }
    for (Unit u : army2.getUnits()) {
      duplicateArmy2.add(u);
    }
  }

  /**
   * If units are available a simulation will be run.
   * If not an alert telling user to add units will show.
   */
  @FXML
  private void onRunSimulationPressed() {
    timeline = new Timeline(new KeyFrame(Duration.millis(200),this::step));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  private void step(ActionEvent actionEvent) {
    List<String> list = new ArrayList<>();
    counter +=1;

    if(army1.hasUnits() && army2.hasUnits()) {
      String simStep = battle.simulateStep(army1.getRandom(), army2.getRandom());
      unitsArmy1.getData().add(new XYChart.Data<>(String.valueOf(counter),army1.getUnits().size()));
      unitsArmy2.getData().add(new XYChart.Data<>(String.valueOf(counter),army2.getUnits().size()));
      if (!simStep.isEmpty())
        log.getItems().add(simStep);
      log.refresh();
    }else{
      if(!army1.hasUnits()){
        winnerLabel.setText(army2.getName());
        timeline.stop();
      }else {
        winnerLabel.setText(army1.getName());
        timeline.stop();
      }
    }
  }

  /**
   * When run multiple simulation button is pressed,
   * user is prompted to type how many times to run.
   *
   * Not functioning because army is not updated.
   */
  @FXML
  private void onMultipleSimulationsPressed() {
    Battle battle = Facade.getInstance().getBattle();
    TextInputDialog inputDialog = new TextInputDialog();
    Optional<String> result = inputDialog.showAndWait();
    List<String> winnerEachRound = new ArrayList<>();
    int amount = 0;

    if (result.isPresent() && !result.get().isEmpty()) {
      try {
        amount = Integer.parseInt(result.get());

        for (int i = 0; i < amount; i++) {
          winnerEachRound.add(battle.simulate().getName());
          //battle.updateArmies(duplicateArmy1,duplicateArmy2);
        }

        System.out.println(winnerEachRound.stream().collect(Collectors.groupingBy(t -> t, Collectors.counting())));

        ObservableList<String> observableList = FXCollections.observableList(winnerEachRound);
        log.setItems(observableList);
      } catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(e.getMessage());
        alert.showAndWait();
      }
    }
  }

  public void onGoBackPressed() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 800, 600);
    Stage stage = (Stage) winnerLabel.getScene().getWindow();
    stage.setScene(scene);
  }
}
