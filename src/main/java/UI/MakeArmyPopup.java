package UI;

import Army.Army;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;

/**
 * Class with customized popup dialog window.
 * Dialog is to create your own army.
 */
public class MakeArmyPopup extends Dialog<Army> {
  /**
   * Constructor for the armyPopup dialog.
   */
  public MakeArmyPopup() {
    super();
    createContent();
  }

  /**
   * Method to create the dialog popup.
   */
  private void createContent() {
    setTitle("Make a army");

    getDialogPane().getButtonTypes().addAll(ButtonType.YES);
    BorderPane pane = new BorderPane();
    pane.setPrefHeight(400);
    pane.setPrefWidth(400);

    getDialogPane().setContent(pane);
    showAndWait();
  }
}