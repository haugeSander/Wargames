package UI.Controllers;

import Army.Army;
import Army.ArmyFileHandler;
import Army.Units.Unit;
import Army.Units.UnitFactory;
import Simulation.Battle;

import Simulation.BattleFileHandler;
import UI.Facade;
import UI.GUI;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BattleManagerController implements Initializable {
  @FXML private Button changeArmy1Name;
  @FXML private Button changeArmy2Name;
  @FXML private ImageView simulateLogo;
  @FXML private ImageView importBattleLogo;
  @FXML private ImageView importA1Logo;
  @FXML private ImageView addUnitA1Logo;
  @FXML private ImageView removeA1Logo;
  @FXML private ImageView importA2Logo;
  @FXML private ImageView addA2Logo;
  @FXML private ImageView removeA2Logo;

  @FXML private Label armyOneName;
  @FXML private TableView<Unit> armyOneTableView;
  @FXML private TableColumn armyOneTypeColumn;
  @FXML private TableColumn armyOneNameColumn;
  @FXML private TableColumn armyOneHPColumn;
  @FXML private TableColumn armyOneBonusColumn;
  @FXML private Label armyTwoName;
  @FXML private TableView<Unit> armyTwoTableView;
  @FXML private TableColumn armyTwoTypeColumn;
  @FXML private TableColumn armyTwoNameColumn;
  @FXML private TableColumn armyTwoHPColumn;
  @FXML private TableColumn armyTwoBonusColumn;
  @FXML private ComboBox terrainSelection;
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
    terrainSelection.getItems().add("Hill");
    terrainSelection.getItems().add("Forest");
    terrainSelection.getItems().add("Plains");
    init();
  }

  /**
   * Initializer accessible to BattleManagerController
   * methods. Used by import battle method.
   */
  private void init() {
    Facade facade = Facade.getInstance();
    if (facade.getBattle() == null) {
      army1 = new Army("Army 1");
      army2 = new Army("Army 2");
      battleSimulation = new Battle(army1, army2);
      facade.setBattle(battleSimulation);
    } else {
      battleSimulation = facade.getBattle();
      army1 = battleSimulation.getArmy1();
      army2 = battleSimulation.getArmy2();
    }
    observableListOfUnitsArmyOne =
        FXCollections.observableList(battleSimulation.getArmy1().getUnits());
    observableListOfUnitsArmyTwo =
        FXCollections.observableList(battleSimulation.getArmy2().getUnits());

    battleSimulation.getArmy1().setUnits(observableListOfUnitsArmyOne);
    battleSimulation.getArmy2().setUnits(observableListOfUnitsArmyTwo);
    armyOneTableView.setItems(
        observableListOfUnitsArmyOne); //Sets the list in armies as the observable list
    armyTwoTableView.setItems(
        observableListOfUnitsArmyTwo); //By doing this the list does not need to be updated.

    armyOneTypeColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
    armyOneNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    armyOneHPColumn.setCellValueFactory(new PropertyValueFactory<>("health"));
    armyOneBonusColumn.setCellValueFactory(new PropertyValueFactory<>("AttackBonus"));

    armyTwoTypeColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
    armyTwoNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    armyTwoHPColumn.setCellValueFactory(new PropertyValueFactory<>("health"));
    armyTwoBonusColumn.setCellValueFactory(new PropertyValueFactory<>("AttackBonus"));

    armyOneName.setText(army1.getName());
    armyTwoName.setText(army2.getName());

    setLogos();
  }

  /**
   * Set images on each of the logos.
   */
  private void setLogos() {
    changeArmy1Name.setGraphic(new ImageView(new Image(String.valueOf(getClass().getResource("edit.png")))));
    changeArmy2Name.setGraphic(new ImageView(new Image(String.valueOf(getClass().getResource("edit.png")))));

    addUnitA1Logo.setImage(new Image(String.valueOf(getClass().getResource("list-plus.png"))));
    importA1Logo.setImage(new Image(String.valueOf(getClass().getResource("import.png"))));
    removeA1Logo.setImage(new Image(String.valueOf(getClass().getResource("list-minus.png"))));

    addA2Logo.setImage(new Image(String.valueOf(getClass().getResource("list-plus.png"))));
    importA2Logo.setImage(new Image(String.valueOf(getClass().getResource("import.png"))));
    removeA2Logo.setImage(new Image(String.valueOf(getClass().getResource("list-minus.png"))));

    importBattleLogo.setImage(new Image(String.valueOf(getClass().getResource("import.png"))));
    simulateLogo.setImage(new Image(String.valueOf(getClass().getResource("play.png"))));
  }

  /**
   * Close button in menu, exits application after a confirmation.
   */
  @FXML
  private void onCloseButtonClicked() {
    GUI.exit((Stage) terrainSelection.getScene().getWindow());
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
  private void onSimulateButtonClicked() {
    Alert alert = new Alert(Alert.AlertType.WARNING);

    try {
      Facade.getInstance().setTerrain(terrainSelection.getValue().toString().toUpperCase());
      Facade.getInstance().setBattle(battleSimulation);

      if (army1.getUnits().isEmpty() || army2.getUnits().isEmpty()) {
        alert.setHeaderText("No units to fight each other..");
        alert.setContentText("To simulate add units.");
        alert.showAndWait();
      } else {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("simulation-view.fxml"));
        Stage stage = (Stage) terrainSelection.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
      }
    } catch (Exception e) {
      if (terrainSelection.getValue() == null) {
        alert.setHeaderText("Select a terrain to continue.");
      } else {
        alert.setHeaderText(e.getMessage());
      }
      alert.showAndWait();
    }
  }

  /**
   * Button to save armies made.
   * Allows user to select save location.
   */
  @FXML
  private void onSaveButtonClicked() {
    try {
      FileChooser chooser = new FileChooser();
      chooser.setInitialFileName(army1.getName().strip()+"-vs-"+ army2.getName().strip());
      chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.csv","Comma Separated File"));
      File selectedPath = chooser.showSaveDialog(terrainSelection.getScene().getWindow());
      chooser.setInitialDirectory(selectedPath.getParentFile()); //Save chosen directory.
      BattleFileHandler.writeFile(battleSimulation,selectedPath);
    } catch (Exception e) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setHeaderText("File saving went wrong.");
      alert.setContentText(e.getMessage());
      alert.showAndWait();
    }
  }

  /**
   * Button which sends user back to front page.
   */
  @FXML
  private void onBackButtonClicked() throws IOException {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
    alert.setHeaderText("Are you sure you want to go back?");
    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.OK) {
      battleSimulation = null;
      army1 = null;
      army2 = null;
      Facade.getInstance().setBattle(null);
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-menu.fxml"));
      Scene scene = new Scene(fxmlLoader.load(), 800, 600);
      Stage stage = (Stage) terrainSelection.getScene().getWindow();
      stage.setScene(scene);
    } else
      alert.close();
  }

  /**
   * Method to open battle formatted .csv save file into both
   * army1 and 2.
   */
  @FXML
  private void onOpenBattleClicked() {
    FileChooser chooser = new FileChooser();
    File selectedFile = chooser.showOpenDialog(terrainSelection.getScene().getWindow());
    Facade facade = Facade.getInstance();

    try {
      facade.setBattle(BattleFileHandler.readFile(selectedFile.getPath()));
      init();
    } catch (Exception e) {
      Alert noFileExists = new Alert(Alert.AlertType.WARNING);
      noFileExists.setTitle("File error");
      noFileExists.setHeaderText("The file selected not supported or nothing selected!");
      noFileExists.setContentText("Remember only .csv files are supported.");
      noFileExists.showAndWait();
    }
  }

  /**
   * Method to open army formatted .csv save file into army1.
   */
  @FXML
  private void onOpenToArmy1ButtonClicked() {
    army1 = addArmyFromFile();
    if (army1 != null) {
      armyOneName.setText(army1.getName());
      observableListOfUnitsArmyOne.addAll(army1.getUnits());
    }
  }

  /**
   * Army 1's method to open add units dialog.
   */
  @FXML
  private void onAddUnitArmy1Clicked() {
    addUnitsFromDialog(1);
  }

  /**
   * Army 2's method to open add units dialog.
   */
  @FXML
  private void onAddUnitArmyTwoClicked() {
    addUnitsFromDialog(2);
  }

  /**
   * Method to open army formatted .csv save file into army2.
   */
  @FXML
  private void onOpenToArmy2ButtonClicked() {
    army2 = addArmyFromFile();
    if (army2 != null) {
      armyTwoName.setText(army2.getName());
      observableListOfUnitsArmyTwo.addAll(army2.getUnits());
    }
  }

  /**
   * Method to remove unit from army1 by selecting from list.
   * Sends integer 1 representing army1.
   */
  @FXML
  private void onRemoveUnitArmy1Clicked() {
    removeUnit(1);
  }

  /**
   * Method to remove unit from army2 by selecting from list.
   * Sends integer 2 representing army2.
   */
  @FXML
  private void onRemoveUnitArmy2Clicked() {
    removeUnit(2);
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
   * Method which allows user to add units from dialog.
   * @param armyNumber Integer representation of army to add to.
   */
  private void addUnitsFromDialog(int armyNumber) {
    Dialog addUnits = new Dialog();
    ComboBox<String> typeUnit = new ComboBox<>();
    typeUnit.getItems().addAll("InfantryUnit", "RangedUnit", "CavalryUnit", "CommanderUnit");
    addUnits.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    addUnits.getDialogPane().getChildren().addAll(typeUnit);
    addUnits.getDialogPane().setPrefHeight(200);
    addUnits.getDialogPane().setPrefWidth(250);

    TextField name = new TextField();
    TextField hp = new TextField();
    textFieldListener(hp);
    TextField amount = new TextField();
    textFieldListener(amount);

    Label type = new Label("Select type: ");
    Label labelName = new Label("Name of unit: ");
    Label labelHp = new Label("HP: ");
    Label labelAmount = new Label("Amount: ");

    HBox typeHBox = new HBox(type, typeUnit);
    typeHBox.setAlignment(Pos.CENTER);
    HBox nameHBox = new HBox(labelName, name);
    nameHBox.setAlignment(Pos.CENTER);
    HBox HPHBox = new HBox(labelHp, hp);
    HPHBox.setAlignment(Pos.CENTER);
    HBox amountHBox = new HBox(labelAmount, amount);
    amountHBox.setAlignment(Pos.CENTER);
    VBox vBox = new VBox(typeHBox, nameHBox, HPHBox, amountHBox);
    vBox.setSpacing(10);
    addUnits.getDialogPane().setContent(vBox);

    Optional<ButtonType> result = addUnits.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.OK) {
      try {
        if (armyNumber == 1)
          observableListOfUnitsArmyOne.addAll(
              UnitFactory.createListOfUnits(typeUnit.getValue(), name.getText(),
                  Integer.parseInt(hp.getText()), Integer.parseInt(amount.getText())));
        else if (armyNumber == 2)
          observableListOfUnitsArmyTwo.addAll(
              UnitFactory.createListOfUnits(typeUnit.getValue(), name.getText(),
                  Integer.parseInt(hp.getText()), Integer.parseInt(amount.getText())));
      } catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Some values were invalid.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
      }
    }
  }

  /**
   * Remove units from either army1 or 2 list.
   * @param armyNumber integer representing the army number.
   */
  private void removeUnit(int armyNumber) {
    try {
      Unit unitToRemove = null;

      if (armyNumber == 1) {
        unitToRemove = armyOneTableView.getSelectionModel().getSelectedItem();
      } else if (armyNumber == 2)
        unitToRemove = armyTwoTableView.getSelectionModel().getSelectedItem();

      if (unitToRemove != null) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
            "Are you sure you want to delete " + unitToRemove.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK && armyNumber == 1) {
          battleSimulation.getArmy1().getUnits().remove(unitToRemove);
        } else if (result.isPresent() && result.get() == ButtonType.OK && armyNumber == 2) {
          battleSimulation.getArmy2().getUnits().remove(unitToRemove);
        }
      }
    } catch (Exception e) {
      Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
      alert.setHeaderText("Unit does not exist.");
      alert.showAndWait();
    }
  }

  /**
   * Method to get a csv file then return the army object.
   *
   * @return Army object.
   */
  private Army addArmyFromFile() {
    FileChooser chooser = new FileChooser();
    Army tempArmy = null;
    File selectedFile;

    selectedFile = chooser.showOpenDialog(terrainSelection.getScene().getWindow());

    try {
      if (selectedFile != null && selectedFile.getName().contains(".csv"))
        tempArmy = ArmyFileHandler.readFile(selectedFile.getPath());
    }catch(Exception e){
      Alert noFileExists = new Alert(Alert.AlertType.WARNING);
      noFileExists.setTitle("File error");
      noFileExists.setHeaderText("The file selected not supported or nothing selected!");
      noFileExists.setContentText("Remember only .csv files are supported.");
      noFileExists.showAndWait();
    }
    return tempArmy;
  }

  /**
   * Method to change army 1 name.
   */
  @FXML
  private void changeArmy1Name() {
    TextInputDialog newArmy1Name = new TextInputDialog();
    Optional<String> result = newArmy1Name.showAndWait();

    if (result.isPresent() && !result.isEmpty()) {
      army1.setName(result.get());
      init();
    } else
      newArmy1Name.close();
  }

  /**
   * Method to change army 1 name.
   */
  @FXML
  private void changeArmy2Name() {
    TextInputDialog newArmy2Name = new TextInputDialog();
    Optional<String> result = newArmy2Name.showAndWait();

    if (result.isPresent() && !result.isEmpty()) {
      army2.setName(result.get());
      init();
    } else
      newArmy2Name.close();
  }
}