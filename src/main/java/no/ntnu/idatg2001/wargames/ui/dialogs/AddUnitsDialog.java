package no.ntnu.idatg2001.wargames.ui.dialogs;

import java.io.File;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import no.ntnu.idatg2001.wargames.model.WargamesModel;

/**
 * Custom dialog class for adding units to an army.
 * Extends dialog.
 * @author Sander Hauge
 * @version 1.0-SNAPSHOT
 */
public class AddUnitsDialog extends Dialog<ButtonType> {
  private TextField name;
  private ComboBox<String> typeUnit;
  private TextField hp;
  private TextField amount;

  /**
   * Constructor calling super(): Dialog constructor,
   * to create its dialog object.
   */
  public AddUnitsDialog() {
    super();
  }

  /**
   * Shows the dialog after it is created.
   * @param armyNumber Takes an armyNumber of which army to add the units to.
   */
  public void showDialog(int armyNumber) {
    File logo = new File
        ("src/main/resources/no/ntnu/idatg2001/wargames/ui/controllers/Logos/Tank.png");
    ((Stage)getDialogPane().getScene().getWindow()).getIcons().add(new Image(logo.toURI().toString())); //Sets logo.
    setTitle("Add units to army " + armyNumber);
    Optional<ButtonType> result = createDialog();

    if (result.isPresent() && result.get() == ButtonType.OK) {
      WargamesModel wargamesModel = WargamesModel.getInstance();

      try {
        int hpInt = Integer.parseInt(hp.getText());
        int amountInt = Integer.parseInt(amount.getText());

        if (armyNumber == 1)
          WargamesModel.getInstance().createNewUnits(wargamesModel.getArmy1(), typeUnit.getValue(),
              name.getText(), hpInt, amountInt);
        else if (armyNumber == 2)
          WargamesModel.getInstance().createNewUnits(wargamesModel.getArmy2(), typeUnit.getValue(),
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
    typeUnit = new ComboBox<>();
    typeUnit.getItems().addAll("InfantryUnit", "RangedUnit", "CavalryUnit", "CommanderUnit");
    getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    getDialogPane().getChildren().addAll(typeUnit);
    getDialogPane().setPrefHeight(200);
    getDialogPane().setPrefWidth(250);

    name = new TextField();
    textFieldListener(name, ","); //Replaces commas with whitespace.
    //Without this, if user input name with commas, save file may not work.
    hp = new TextField();
    textFieldListener(hp, "[^\\d]"); //Replaces digits with whitespace.

    amount = new TextField();
    textFieldListener(amount, "[^\\d]");

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
    getDialogPane().setContent(vBox);

    return showAndWait();
  }

  /**
   * Method which restricts textField input to only being integers.
   * @param textField TextField in GUI.
   * @param toReplace String of type to replace with whitespace.
   */
  private void textFieldListener(TextField textField, String toReplace) {
    ChangeListener<String> cl = (observableValue, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        textField.setText(newValue.replaceAll(toReplace, ""));
      }
    };
    textField.textProperty().addListener(cl);
  }
}