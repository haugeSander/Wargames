package UI.Controllers;

import Army.Army;
import Army.Units.Unit;
import Army.Units.UnitFactory;
import Simulation.Battle;
import Simulation.BattleObserver;
import UI.Facade;
import UI.GUI;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Simulation controller class to control
 * simulation-view.fxml file.
 */
public class SimulationController implements Initializable, BattleObserver {
  @FXML private Spinner<Integer> speedSelection;
  @FXML private ImageView podium;
  @FXML private PieChart pieChart;
  @FXML private TableView<Unit> army1View;
  @FXML private TableColumn<Unit, String> army1NameColumn;
  @FXML private TableColumn<Unit, Integer> army1HPColumn;
  @FXML private TableView<Unit> army2View;
  @FXML private TableColumn<Unit, String> army2NameColumn;
  @FXML private TableColumn<Unit, Integer> army2HPColumn;
  @FXML private Label terrain;
  @FXML private Label winnerLabel;
  @FXML private LineChart chart;
  @FXML private Label army1Name;
  @FXML private Label army2Name;
  private Dialog<BorderPane> logDialog;
  private ListView<String> logNo1;
  private ListView<String> logNo2;
  private int threadSpeed;

  private ObservableList<Unit> observableListArmy1;
  private ObservableList<Unit> observableListArmy2;
  private XYChart.Series<String,Number> unitsArmy1Chart;
  private XYChart.Series<String,Number> unitsArmy2Chart;

  private int counter = 0;
  private Timeline timeline;
  private Battle battle;
  private Army army1;
  private Army army2;
  private int army1Size;
  private int army2Size;

  private Army duplicateArmy1;
  private Army duplicateArmy2;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    terrain.setText(Facade.getInstance().getTerrain());
    chart.setCreateSymbols(false);

    SpinnerValueFactory<Integer> factory =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 1000, 10,10);
    factory.setValue(10);
    speedSelection.setValueFactory(factory);
    speedSelection.setEditable(true);

    speedSelection.valueProperty().addListener(
        (observableValue, oldValue, newValue) -> threadSpeed =  (newValue)); //Spinner listener.

    army1NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    army2NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    army1HPColumn.setCellValueFactory(new PropertyValueFactory<>("health"));
    army2HPColumn.setCellValueFactory(new PropertyValueFactory<>("health"));

    init();

    File podiumImage = new File("src/main/resources/UI/Controllers/Images/podium.png");
    podium.setImage(new Image(podiumImage.toURI().toString()));
  }

  /**
   * Initialize method available to classes in simulationController.
   */
  private void init() {
    unitsArmy1Chart = new XYChart.Series<>();
    unitsArmy2Chart = new XYChart.Series<>();
    chart.getData().addAll(unitsArmy1Chart, unitsArmy2Chart);

    Facade facade = Facade.getInstance();

    battle = facade.getBattle();
    army1 = battle.getArmy1();
    army2 = battle.getArmy2();

    observableListArmy1 = FXCollections.observableList(army1.getUnits());
    observableListArmy2 = FXCollections.observableList(army2.getUnits());
    army1View.setItems(observableListArmy1); //Sets the list in armies as the observable list
    army2View.setItems(observableListArmy2); //By doing this the list does not need to be updated.
    army1.setUnits(observableListArmy1);
    army2.setUnits(observableListArmy2);

    army1Name.setText(army1.getName());
    army2Name.setText(army2.getName());

    facade.subscribeController(this);

    duplicateArmy(army1, army2); //Makes deep copy of armies.
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
    logNo1 = new ListView<>();
    logNo2 = new ListView<>();

    if (threadSpeed == 0)
      threadSpeed = 10;

    if (army1.getUnits().isEmpty() || army2.getUnits().isEmpty()) {
      onRefreshPressed();
    }

    timeline = new Timeline(new KeyFrame(Duration.millis(threadSpeed),this::step));
    timeline.setCycleCount(Animation.INDEFINITE); //No time limit to timeline.
    chart.getXAxis().setTickLabelsVisible(false);
    chart.verticalGridLinesVisibleProperty().setValue(false);
    chart.horizontalGridLinesVisibleProperty().setValue(false);
    chart.getXAxis().setLabel("Time");
    unitsArmy1Chart.setName(army1.getName());
    unitsArmy2Chart.setName(army2.getName());
    timeline.play();
    counter = 0;
  }

  /**
   * Uses step method from battle class to run
   * simulation over time. When run simulation button is pressed
   * the simulation will run with x amount of duration between
   * steps.
   * @param actionEvent Required to use this::step in keyframe control.
   */
  private void step(ActionEvent actionEvent) {
    counter += 1;

    if(army1.hasUnits() && army2.hasUnits()) {
      battle.simulateStep(army1, army2);
      unitsArmy1Chart.getData().add(new XYChart.Data<>(String.valueOf(counter), army1Size));
      unitsArmy2Chart.getData().add(new XYChart.Data<>(String.valueOf(counter), army2Size));
    }
    else{
      if (!army1.hasUnits()){
        winnerLabel.setText(army2.getName());
      }else {
        winnerLabel.setText(army1.getName());
      }
      timeline.stop();
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
    TextInputDialog inputAmount = new TextInputDialog();
    inputAmount.getDialogPane().setHeaderText("Enter amount of simulations to run.");
    textFieldListener(inputAmount.getEditor());
    Optional<String> result = inputAmount.showAndWait();
    List<String> winnerEachRound = new ArrayList<>();
    logNo1 = new ListView<>();
    int amount = 0;

    if (result.isPresent() && !result.get().isEmpty()) {
      try {
        amount = Integer.parseInt(result.get());

        for (int i = 0; i < amount; i++) {
          winnerEachRound.add(battle.simulate());
          onRefreshPressed();
        }
        Map<Object, Long> winnerMap =
            winnerEachRound.stream().collect(Collectors.groupingBy(t -> t, Collectors.counting()));
        //Stores army name as string and, long amount of wins in a map. Example: "Blue",12.0.

        if (!winnerMap.containsKey(army1.getName())) //If one army always lose, the other is
          winnerMap.put(army1.getName(), 0L);        //added here with 0 wins.
        if (!winnerMap.containsKey(army2.getName())) //Else exception would be thrown.
          winnerMap.put(army2.getName(), 0L);

        ObservableList<PieChart.Data> pie =
            FXCollections.observableArrayList(
                new PieChart.Data(army1.getName(), winnerMap.get(army1.getName())),
                new PieChart.Data(army2.getName(), winnerMap.get(army2.getName())));
        pieChart.setData(pie); //Sets data to pieChart.

        pie.forEach(data ->
            data.nameProperty().bind(
                Bindings.concat(data.getName(), ":  ", (int) data.getPieValue(), " wins"
                ))); //Shows amount of times each army won on pie chart.
        logNo1.getItems().setAll(winnerEachRound);
        logNo2 = null; //Log of multiple simulations will now contain only one listView.

      } catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(e.getMessage());
        alert.setContentText(e.getMessage());
        alert.showAndWait();
      }
    }
  }

  /**
   * Method which restricts textField input to only being ints.
   * @param textField TextField in GUI.
   */
  private void textFieldListener(TextField textField) {
    ChangeListener<String> cl = (observableValue, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        textField.setText(newValue.replaceAll("[^\\d]", ""));
      }
    };
    textField.textProperty().addListener(cl);
  }

  /**
   * When go back button is pressed user will be prompted first
   * if user presses ok, they will be sent to main view fxml.
   */
  @FXML
  private void onGoBackPressed() throws IOException {
    Alert goBackConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
    goBackConfirmation.setTitle("Are you sure?");
    goBackConfirmation.setHeaderText("Do you want to go back?");
    Optional<ButtonType> result = goBackConfirmation.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.OK) {
      onRefreshPressed();
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
      Scene scene = new Scene(fxmlLoader.load(), 800, 600);
      Stage stage = (Stage) winnerLabel.getScene().getWindow();
      stage.setScene(scene);
    } else
      goBackConfirmation.close();
  }

  /**
   * Button in menu to refresh armies.
   */
  @FXML
  private void onRefreshPressed() {
    if (timeline != null)
      timeline.stop();
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

  /**
   * When show log button is pressed a log dialog will appear.
   * This shows information if the simulation has been run.
   */
  @FXML
  private void onShowLogPressed() {
    logDialog = new Dialog<>();
    logDialog.setTitle("Log of battle.");
    logDialog.setHeaderText("To get updates run simulation.");
    logDialog.getDialogPane().getButtonTypes().setAll(ButtonType.CLOSE);
    BorderPane borderPane = new BorderPane();
    Label leftDialogTitle = new Label("Winners each round");

    if (logNo1 != null) { //If simulations have not been run, logs are not created.
      VBox armyLeftDialog = new VBox(leftDialogTitle, logNo1);
      armyLeftDialog.setAlignment(Pos.CENTER);
      borderPane.setLeft(armyLeftDialog);
    }
    if (logNo2 != null){ //When multiple simulations is run, logNo2 is left out. logNo2 = null in multipleSims.
      leftDialogTitle.setText(army1.getName()); //If normal simulation is run, army name is displayed.
      VBox armyRightDialog = new VBox(new Label(army2.getName()), logNo2);
      armyRightDialog.setAlignment(Pos.CENTER);
      borderPane.setRight(armyRightDialog);
    }
    logDialog.getDialogPane().setContent(borderPane);
    logDialog.showAndWait();
  }

  @Override
  public void update(String status) {
    if (!status.isEmpty()) {
      if (status.contains(army1.getName())) {
        logNo1.getItems().add(status);
        logNo1.refresh();
      } else if (status.contains(army2.getName()) && logNo2 != null){
        logNo2.getItems().add(status);
        logNo2.refresh();
      }
    }
  }

  @Override
  public void updateSize(int army1Amount, int army2Amount) {
    army1Size = army1Amount;
    army2Size = army2Amount;
  }
}
