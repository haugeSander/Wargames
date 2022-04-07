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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
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
  public TableColumn army1TypeColumn;
  public TableColumn army1AmountColumn;
  public TableView army2View;
  public TableColumn army2TypeColumn;
  public TableColumn army2AmountColumn;

  public Label terrain;
  public Label winnerLabel;

  public LineChart chart;
  public ListView log;
  public Label army1Name;
  public Label army2Name;

  private Timeline timeline;
  private ObservableList<Unit> observableListArmy1;
  private ObservableList<Unit> observableListArmy2;
  private List<String> listOfTypes;
  private List<Integer> listOfAmountArmy1;
  private List<Integer> listOfAmountArmy2;
  private Army duplicateArmy1;
  private Army duplicateArmy2;

  public SimulationController() {
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    init();

  }

  private void init() {
    Facade facade = Facade.getInstance();
    terrain.setText(facade.getTerrain());
    observableListArmy1 =
        FXCollections.observableList(facade.getBattle().getArmy1().getUnits());
    observableListArmy2 =
        FXCollections.observableList(facade.getBattle().getArmy2().getUnits());

    facade.getBattle().getArmy1().setUnits(observableListArmy1);
    facade.getBattle().getArmy2().setUnits(observableListArmy2);
    army1View.setItems(observableListArmy1); //Sets the list in armies as the observable list
    army2View.setItems(observableListArmy2); //By doing this the list does not need to be updated.

    army1TypeColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
    army2TypeColumn.setCellValueFactory(new PropertyValueFactory<>("className"));

    army1Name.setText(facade.getBattle().getArmy1().getName());
    army2Name.setText(facade.getBattle().simulate().getName());

    duplicateArmy(facade.getBattle().getArmy1(), facade.getBattle().getArmy2());
  }

  private void duplicateArmy(Army army1, Army army2) {
    duplicateArmy1 = new Army("");
    duplicateArmy2 = new Army("");
    duplicateArmy1.setName(army1.getName());
    duplicateArmy2.setName(army2.getName());

    for (Unit u : army1.getUnits()) {
      duplicateArmy1.add(u);
    }
    for (Unit u : army2.getUnits()) {
      duplicateArmy2.add(u);
    }
  }

  private void gatherTypesToList() {
    Facade facade = Facade.getInstance();

    listOfTypes = new ArrayList<>();
    listOfAmountArmy1 = new ArrayList<>();
    listOfAmountArmy2 = new ArrayList<>();

    listOfTypes.add("InfantryUnit");
    listOfTypes.add("RangedUnit");
    listOfTypes.add("CavalryUnit");
    listOfTypes.add("CommanderUnit");

    listOfAmountArmy1.add(facade.getBattle().getArmy1().getInfantryUnits().size());
    listOfAmountArmy1.add(facade.getBattle().getArmy1().getRangedUnits().size());
    listOfAmountArmy1.add(facade.getBattle().getArmy1().getCavalryUnits().size());
    listOfAmountArmy1.add(facade.getBattle().getArmy1().getCommanderUnits().size());

    listOfAmountArmy2.add(facade.getBattle().getArmy2().getInfantryUnits().size());
    listOfAmountArmy2.add(facade.getBattle().getArmy2().getRangedUnits().size());
    listOfAmountArmy2.add(facade.getBattle().getArmy2().getCavalryUnits().size());
    listOfAmountArmy2.add(facade.getBattle().getArmy2().getCommanderUnits().size());
  }

  /**
   * If units are available a simulation will be run.
   * If not an alert telling user to add units will show.
   */
  @FXML
  private void onRunSimulationPressed() {
    timeline = new Timeline(new KeyFrame(Duration.millis(500)));
    chart= new LineChart(chart.getXAxis(), chart.getYAxis());
    try {
      Army winnerArmy = Facade.getInstance().getBattle().simulate();
      log.setItems(FXCollections.observableList(winnerArmy.getUnits()));
      winnerLabel.setText(winnerArmy.getName());
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
          battle.updateArmies(duplicateArmy1,duplicateArmy2);
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
