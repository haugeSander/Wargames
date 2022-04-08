package UI.Controllers;

import Army.Army;
import Army.Units.Unit;
import Army.Units.UnitFactory;
import Simulation.Battle;
import UI.Facade;
import UI.GUI;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Simulation controller class to control
 * simulation-view.fxml file.
 */
public class SimulationController implements Initializable {
  @FXML private TableView army1View;
  @FXML private TableColumn army1NameColumn;
  @FXML private TableColumn army1HPColumn;
  @FXML private TableView army2View;
  @FXML private TableColumn army2NameColumn;
  @FXML private TableColumn army2HPColumn;
  @FXML private Label terrain;
  @FXML private Label winnerLabel;
  @FXML private LineChart chart;
  @FXML private ListView log;
  @FXML private Label army1Name;
  @FXML private Label army2Name;

  private ObservableList<Unit> observableListArmy1;
  private ObservableList<Unit> observableListArmy2;
  private XYChart.Series<String,Number> unitsArmy1;
  private XYChart.Series<String,Number> unitsArmy2;

  private int counter;
  private Timeline timeline;
  private Battle battle;
  private Army army1;
  private Army army2;

  private Army duplicateArmy1;
  private Army duplicateArmy2;

  public SimulationController() {
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    init();
  }

  /**
   * Initialize method available to classes in simulationController.
   */
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

    duplicateArmy(army1, army2);
  }

  /**
   * Attempt to make deep copy of armies
   * gotten from the previous page.
   * @param army1 Army 1 made in previous page.
   * @param army2 Army 2 made in previous page.
   */
  private void duplicateArmy(Army army1, Army army2) {
    duplicateArmy1 = new Army(army1.getName());
    duplicateArmy2 = new Army(army2.getName());

    for (Unit u : army1.getUnits()) {
      duplicateArmy1.add(UnitFactory.createUnit(u.getClassName(), u.getName(), u.getHealth()));
    }
    for (Unit u : army2.getUnits()) {
      duplicateArmy2.add(UnitFactory.createUnit(u.getClassName(), u.getName(), u.getHealth()));
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
    chart.getXAxis().setTickLabelsVisible(false);
    chart.verticalGridLinesVisibleProperty().setValue(false);
    chart.horizontalGridLinesVisibleProperty().setValue(false);
    chart.getXAxis().setLabel("Time");
    timeline.play();
  }

  /**
   * Uses step method from battle class to run
   * simulation over time. When run simulation button is pressed
   * the simulation will run with x amount of duration between
   * steps.
   * @param actionEvent Required to use this::step in keyframe control.
   */
  private void step(ActionEvent actionEvent) {
    counter +=1;
    unitsArmy1.setName(army1.getName());
    unitsArmy2.setName(army2.getName());

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
          onRefreshPressed(); //Simple way of allowing multiple simulations.
        }
        winnerLabel.setText(winnerEachRound.stream().collect(Collectors.groupingBy(t -> t, Collectors.counting())).toString());
        winnerLabel.setFont(new Font(13));

        ObservableList<String> observableList = FXCollections.observableList(winnerEachRound);
        log.setItems(observableList);
      } catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(e.getMessage());
        alert.showAndWait();
      }
    }
  }

  /**
   * When go back button is pressed user will
   * be sent to main view fxml.
   */
  @FXML
  private void onGoBackPressed() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 800, 600);
    Stage stage = (Stage) winnerLabel.getScene().getWindow();
    stage.setScene(scene);
  }

  /**
   * Button in menu to refresh armies.
   */
  @FXML
  private void onRefreshPressed() {
    army1 = duplicateArmy1;
    army2 = duplicateArmy2;
    observableListArmy1.setAll(duplicateArmy1.getUnits());
    observableListArmy2.setAll(duplicateArmy2.getUnits());
    winnerLabel.setText("");
    init();
  }

  /**
   * Button in menu to exit program.
   */
  @FXML
  private void onClosePressed() {
    GUI.exit((Stage)winnerLabel.getScene().getWindow());
  }
}
