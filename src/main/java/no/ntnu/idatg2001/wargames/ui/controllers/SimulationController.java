package no.ntnu.idatg2001.wargames.ui.controllers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import no.ntnu.idatg2001.wargames.army.units.Unit;
import no.ntnu.idatg2001.wargames.simulation.BattleObserver;
import no.ntnu.idatg2001.wargames.model.BattleModel;
import no.ntnu.idatg2001.wargames.ui.dialogs.SimulationHelpDialog;
import no.ntnu.idatg2001.wargames.ui.Main;
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
  @FXML private LineChart chart; //Difficult to generify.
  @FXML private Label army1Name;
  @FXML private Label army2Name;
  private ListView<String> logNo1;
  private ListView<String> logNo2;
  private int threadSpeed = 10; //Initial speed of simulation.

  private XYChart.Series<String,Number> unitsArmy1Chart;
  private XYChart.Series<String,Number> unitsArmy2Chart;

  private int counter;
  private Timeline timeline;
  private int army1Size;
  private int army2Size;
  private String army1NameString;
  private String army2NameString;

  private BattleModel battleModel;

  /**
   * Initializes GUI for simulation view.
   * @param url Takes an outside url, will open for example a webpage.
   * @param resourceBundle ResourceBundle Local specific object.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    battleModel = BattleModel.getInstance();
    army1NameString = battleModel.getArmy1().getName();
    army2NameString = battleModel.getArmy2().getName();

    terrain.setText(BattleModel.getInstance().getTerrain());
    chart.setCreateSymbols(false);

    SpinnerValueFactory<Integer> factory =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 1000, 10,10);
    factory.setValue(10);
    speedSelection.setValueFactory(factory);
    speedSelection.setEditable(true);

    speedSelection.valueProperty().addListener(
        (observableValue, oldValue, newValue) -> threadSpeed =  (newValue)); //Spinner listener.

    army1NameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
    army2NameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
    army1HPColumn.setCellValueFactory(new PropertyValueFactory<>("health"));
    army2HPColumn.setCellValueFactory(new PropertyValueFactory<>("health"));

    File podiumImage = new File("src/main/resources/no/ntnu/idatg2001/wargames/UI/Controllers/Images/podium.png");
    podium.setImage(new Image(podiumImage.toURI().toString()));

    battleModel.subscribeController(this);
    refresh();
  }

  /**
   * Method to refresh after simulation.
   */
  private void refresh() {
    if (timeline != null)
      timeline.stop();

    army1Name.setText(battleModel.getArmy1().getName());
    army2Name.setText(battleModel.getArmy2().getName());

    ObservableList<Unit> observableListArmy1 = FXCollections.observableList(battleModel.getArmy1().getUnits());
    ObservableList<Unit> observableListArmy2 = FXCollections.observableList(battleModel.getArmy2().getUnits());
    battleModel.getArmy1().setUnits(observableListArmy1); //Makes the lists loop. By doing this the list does
    battleModel.getArmy2().setUnits(observableListArmy2); //not need to be updated everytime something happens.

    army1View.setItems(observableListArmy1); //Sets the list in armies as the observable list
    army2View.setItems(observableListArmy2);
  }

  /**
   * If units are available a simulation will be run.
   * If not an alert telling user to add units will show.
   */
  @FXML
  private void onRunSimulationPressed() {
    logNo1 = new ListView<>();
    logNo2 = new ListView<>();
    counter = 0;

    unitsArmy1Chart = new XYChart.Series<>();
    unitsArmy2Chart = new XYChart.Series<>();
    chart.getData().addAll(unitsArmy1Chart, unitsArmy2Chart); //Not generified.
    refresh();

    timeline = new Timeline(new KeyFrame(Duration.millis(threadSpeed),this::step));
    timeline.setCycleCount(Animation.INDEFINITE); //No time limit to timeline.
    chart.getXAxis().setTickLabelsVisible(false);
    chart.verticalGridLinesVisibleProperty().setValue(false);
    chart.horizontalGridLinesVisibleProperty().setValue(false);
    chart.getXAxis().setLabel("Time");
    unitsArmy1Chart.setName(army1NameString);
    unitsArmy2Chart.setName(army2NameString);
    timeline.play();
  }

  /**
   * Uses step method from battle class to run simulation over time.
   * When run simulation button is pressed the simulation will run
   * with x amount of duration between steps.
   * @param actionEvent Required to use this::step in keyframe control.
   */
  private void step(ActionEvent actionEvent) {
    counter++; //Increments chart time.

    if (battleModel.simulationStep()) { //Returns true if simulation is done.
      timeline.stop();

      if (battleModel.getArmy1().hasUnits())
        winnerLabel.setText(army2NameString);
      else
        winnerLabel.setText(army1NameString);
    }

    unitsArmy1Chart.getData().add(new XYChart.Data<>(String.valueOf(counter), army1Size));
    unitsArmy2Chart.getData().add(new XYChart.Data<>(String.valueOf(counter), army2Size));
    //Adds data to chart for every iteration, armySize is updated by observers.
  }

  /**
   * When run multiple simulation button is pressed,
   * user is prompted to type how many times to run.
   */
  @FXML
  private void onMultipleSimulationsPressed() {
    TextInputDialog inputAmount = new TextInputDialog();
    inputAmount.getDialogPane().setHeaderText("Enter amount of simulations to run.");
    textFieldListener(inputAmount.getEditor());
    Optional<String> result = inputAmount.showAndWait();

    List<String> winnerEachRound = new ArrayList<>();
    logNo1 = new ListView<>();
    int amount;

    if (result.isPresent() && !result.get().isEmpty()) {
      try {
        amount = Integer.parseInt(result.get());

        for (int i = 0; i < amount; i++) {
          winnerEachRound.add(battleModel.runSimulation());
        }
        refresh();
        createPieChart(winnerEachRound);
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
   * Method to create pie chart from multiple simulations.
   * @param winners Takes a list of winners from simulations run.
   */
  private void createPieChart(List<String> winners) {
    Map<Object, Long> winnerMap =
        winners.stream().collect(Collectors.groupingBy(t -> t, Collectors.counting()));
    //Stores army name as string and, long amount of wins in a map. Example: "Blue",12.0.

    if (!winnerMap.containsKey(battleModel.getArmy1().getName())) //If one army always lose, the other is
      winnerMap.put(battleModel.getArmy1().getName(), 0L);        //added here with 0 wins.
    if (!winnerMap.containsKey(battleModel.getArmy2().getName())) //Else exception would be thrown.
      winnerMap.put(battleModel.getArmy2().getName(), 0L);

    ObservableList<PieChart.Data> pie =
        FXCollections.observableArrayList(
            new PieChart.Data(battleModel.getArmy1().getName(), winnerMap.get(army1NameString)),
            new PieChart.Data(battleModel.getArmy2().getName(), winnerMap.get(army2NameString)));
    pieChart.setData(pie); //Sets data to pieChart.

    pie.forEach(data ->
        data.nameProperty().bind(
            Bindings.concat(data.getName(), ":  ", (int) data.getPieValue(), " wins"
            ))); //Shows amount of times each army won on pie chart.

    Long maxValueInSet = Collections.max(winnerMap.values());

    Set<Long> values = new HashSet<>(winnerMap.values()); //Since set could only contain one of same value
                                                          //We could check if there is a draw.
    for (Map.Entry<Object, Long> entry : winnerMap.entrySet()) {
      if (values.size() == 1)
        winnerLabel.setText("Draw!");
      else if (Objects.equals(entry.getValue(), maxValueInSet))
        winnerLabel.setText(entry.getKey().toString()); //Sets the winnerLabel to the one with most wins.
    }
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
      refresh();
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
      Scene scene = new Scene(fxmlLoader.load(), 875, 615);
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
    battleModel.refreshDuplicates();
    refresh();
  }

  /**
   * Button in menu to exit program.
   */
  @FXML
  private void onClosePressed() {
    Main.exit((Stage)winnerLabel.getScene().getWindow());
  }

  /**
   * When show log button is pressed a log dialog will appear.
   * This shows information if the simulation has been run.
   */
  @FXML
  private void onShowLogPressed() {
    Dialog<BorderPane> logDialog = new Dialog<>();
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
      leftDialogTitle.setText(battleModel.getArmy1().getName()); //If normal simulation is run, army name is displayed.
      VBox armyRightDialog = new VBox(new Label(battleModel.getArmy2().getName()), logNo2);
      armyRightDialog.setAlignment(Pos.CENTER);
      borderPane.setRight(armyRightDialog);
    }
    logDialog.getDialogPane().setContent(borderPane);
    logDialog.showAndWait();
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
   * Observer of events in simulateStep.
   * @param status Events in a simulateStep.
   */
  @Override
  public void update(String status) {
    if (!status.isEmpty()) {
      if (status.contains(battleModel.getArmy1().getName())) {
        logNo1.getItems().add(status);
        logNo1.refresh();
      } else if (status.contains(battleModel.getArmy2().getName()) && logNo2 != null){
        logNo2.getItems().add(status);
        logNo2.refresh();
      }
    }
  }

  /**
   * Observer
   * @param army1Amount Updated size of army1.
   * @param army2Amount Updated size of army2.
   */
  @Override
  public void updateSize(int army1Amount, int army2Amount) {
    army1Size = army1Amount;
    army2Size = army2Amount;
  }

  /**
   * When info button is pressed a help dialog window is opened.
   */
  @FXML
  private void onInfoButtonPressed() {
    SimulationHelpDialog helpDialog = new SimulationHelpDialog();
    helpDialog.showDialog();
  }
}
