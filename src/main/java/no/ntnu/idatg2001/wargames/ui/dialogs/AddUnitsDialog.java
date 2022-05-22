package no.ntnu.idatg2001.wargames.UI.Dialogs;

import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatg2001.wargames.Model.BattleModel;

/**
 * Custom dialog class for adding units to an army.
 * Does not use constructor.
 */
public class AddUnitsDialog {

  private TextField name;
  private ComboBox<String> typeUnit;
  private TextField hp;
  private TextField amount;

  /**
   * Shows the dialog after it is created.
   * @param armyNumber Takes an armyNumber of which army to add the units to.
   */
  public void showDialog(int armyNumber) {
    Optional<ButtonType> result = createDialog();

    if (result.isPresent() && result.get() == ButtonType.OK) {
      BattleModel battleModel = BattleModel.getInstance();

      try {
        int hpInt = Integer.parseInt(hp.getText());
        int amountInt = Integer.parseInt(amount.getText());

        if (armyNumber == 1)
          BattleModel.getInstance().createNewUnits(battleModel.getArmy1(), typeUnit.getValue(),
              name.getText(), hpInt, amountInt);
        else if (armyNumber == 2)
          BattleModel.getInstance().createNewUnits(battleModel.getArmy2(), typeUnit.getValue(),
              name.getText(), hpInt, amountInt);
      } catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Some values were invalid.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
      }
    }
  }

  /**
   * Creates dialog to add units to an army.
   * @return ButtonType selection, either "OK" or "Cancel".
   */
  private Optional<ButtonType> createDialog() {
    Dialog<ButtonType> addUnits = new Dialog<>();
    typeUnit = new ComboBox<>();
    typeUnit.getItems().addAll("InfantryUnit", "RangedUnit", "CavalryUnit", "CommanderUnit");
    addUnits.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    addUnits.getDialogPane().getChildren().addAll(typeUnit);
    addUnits.getDialogPane().setPrefHeight(200);
    addUnits.getDialogPane().setPrefWidth(250);

    name = new TextField();
    hp = new TextField();
    textFieldListener(hp);

    amount = new TextField();
    textFieldListener(amount);

    Label type = new Label("Select type: ");
    Label labelName = new Label("Name of unit: ");
    Label labelHp = new Label("HP: ");
    Label labelAmount = new Label("Amount: ");

    HBox typeHBox = new HBox(type, typeUnit);
    typeHBox.setAlignment(Pos.CENTER);
    HBox nameHBox = new HBox(labelName, name);
    nameHBox.setAlignment(Pos.CENTER);
    HBox healthHBox = new HBox(labelHp, hp);
    healthHBox.setAlignment(Pos.CENTER);
    HBox amountHBox = new HBox(labelAmount, amount);
    amountHBox.setAlignment(Pos.CENTER);
    VBox vBox = new VBox(typeHBox, nameHBox, healthHBox, amountHBox);
    vBox.setSpacing(10);
    addUnits.getDialogPane().setContent(vBox);

    return addUnits.showAndWait();
  }

  /**
   * Method which restricts textField input to only being integers.
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
}
