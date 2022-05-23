package no.ntnu.idatg2001.wargames.ui.dialogs;

import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class to show help dialog in battleManager.
 * It is used to display useful information about
 * the features on the page.
 * @author Sander Hauge
 * @version 1.0-SNAPSHOT
 */
public class BMHelpDialog extends Dialog<ButtonType> {
  private final String boldFont;

  /**
   * Constructor to call for super(): Dialog constructor,
   * to create a dialog object. CSS boldFont also set as string.
   */
  public BMHelpDialog() {
    super();
    boldFont = "-fx-font-weight: bold";
  }

  /**
   * Method which shows help dialog in battleManager class.
   */
  public void showDialog() {
    File logo = new File
        ("src/main/resources/no/ntnu/idatg2001/wargames/ui/controllers/Logos/Tank.png");
    ((Stage)getDialogPane().getScene().getWindow()).getIcons().add(new Image(logo.toURI().toString())); //Sets logo.
      createDialog();
    }

  /**
   * Method to create FAQ/Help dialog in the BattleManager controller.
   */
  private void createDialog() {
      setTitle("BattleManager - Info");
      Label information = new Label("This is the BattleManager page where you can manage your armies\n" +
          "before putting them against each other on the simulation page.");
      getDialogPane().getButtonTypes().addAll(ButtonType.OK);
      HBox typeAndTerrain = new HBox(unitInfo(), terrainInfo());
      typeAndTerrain.setAlignment(Pos.CENTER);
      typeAndTerrain.setSpacing(20);
      HBox buttons = new HBox(armyManageButtonsInfo(), restOfButtons());
      buttons.setSpacing(10);
      buttons.setAlignment(Pos.CENTER);
      VBox faq = new VBox(information, typeAndTerrain, buttons);
      faq.setAlignment(Pos.CENTER);
      faq.setSpacing(20);
      getDialogPane().setContent(faq);
      showAndWait();
    }

  /**
   * Method to fill a VBox with necessary information.
   * @return Filled VBox about terrain.
   */
  private VBox terrainInfo() {
      Label terrainTitle = new Label("Terrain");
      terrainTitle.setStyle(boldFont);
      Label infoTerrain = new Label();
      infoTerrain.setText(
          "There are three types of terrain in wargames:\n" +
              "1. Forest - Tightly packed area with trees, perfect for infantries.\n" +
              "Difficult for cavalry and commanders.\n" +
              "2. Plains - Open fields perfect for cavalry and commander units.\n" +
              "3. Hills - Terrain with hills and small mountains, perfect for ranged units.");
      VBox terrainInfoVBox = new VBox(terrainTitle, infoTerrain);
      terrainInfoVBox.setAlignment(Pos.CENTER);
      terrainInfoVBox.setSpacing(15);

      return terrainInfoVBox;
    }

  /**
   * Method to fill a VBox with necessary information.
   * @return Filled VBox about the unit types.
   */
  private VBox unitInfo() {
    Label unitTypeTitle = new Label("Unit types");
    unitTypeTitle.setStyle(boldFont);
    Label infoUnit = new Label();
    infoUnit.setText("Information about the units:\n" +
        "1. Infantry - This unit type is strong in close range combat.\n" +
        "2. Ranged - This unit type is best at range.\n" +
        "3. Cavalry - This unit is best in open fields, and also does a lot of damage \non its first hit.\n" +
        "4. Commander - This is a stronger cavalry unit.");
    VBox unitInfoVBox = new VBox(unitTypeTitle, infoUnit);
    unitInfoVBox.setAlignment(Pos.CENTER);
    unitInfoVBox.setSpacing(15);

    return unitInfoVBox;
  }

  /**
   * Method to fill a VBox with necessary information.
   * @return Filled VBox about the army table buttons' features.
   */
  private VBox armyManageButtonsInfo() {
      Label titleInfoButtons = new Label("Functions of Army table buttons");
      titleInfoButtons.setStyle(boldFont);
      Label infoTableButtons = new Label();
      infoTableButtons.setText("Underneath the army tables you could find three buttons to manage the\n" +
          "army above. These buttons and their features are: \n\n" +
          "1. Import army - Opens file window where you may select an army save file. \n Battle files also" +
          " work, but will only import the first army within.\n" +
          "2. Add units - This will open a window to add units of you choice.\n" +
          "3. Remove units - First select units by pressing the left mouse button in the table\n" +
          "then press remove units to delete them, a prompt will be presented. It is also\n possible" +
          "to remove multiple units at once, select one unit, hold shift and\n select another, finally hit remove.\n" +
          "4. Above the table there is a logo to change the army name, just press it and a\n" +
          "dialog will be shown.");
      VBox armyManageButtons = new VBox(titleInfoButtons, infoTableButtons);
      armyManageButtons.setAlignment(Pos.CENTER);
      armyManageButtons.setSpacing(15);

      return armyManageButtons;
  }

  /**
   * Method to fill a VBox with necessary information.
   * @return Filled VBox about the buttons' features.
   */
  private VBox restOfButtons() {
    Label titleInfoButtons = new Label("Other important buttons");
    titleInfoButtons.setStyle(boldFont);
    Label infoTableButtons = new Label();
    infoTableButtons.setText("" +
        "More important buttons and their functions: \n" +
        "1. Back - This will send you back to main menu.\n" +
        "2. Simulate! - When armies are setup and terrain selected you will be sent to the\n" +
        "simulation page, where you may start a battle.\n" +
        "3. Import Battle - When pressed you may import a full battle file, only .csv and\n" +
        "battle files work, meaning two armies stored in one file." +
        "\nMenubar:\n" +
        "1. File->Save - Press save and select either battle, army1 or 2 to save, finally select whatever\n" +
        "file location you want it to be saved. You will then find the file in the location selected.\n" +
        "2. File->Close - This will close the application after a prompt.\n" +
        "3. Edit->Reset - This will reset the armies and terrain back to default.");

    VBox armyManageButtons = new VBox(titleInfoButtons, infoTableButtons);
    armyManageButtons.setAlignment(Pos.CENTER);
    armyManageButtons.setSpacing(15);

    return armyManageButtons;
  }
}
