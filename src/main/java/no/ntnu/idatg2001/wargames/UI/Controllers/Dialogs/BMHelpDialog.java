package no.ntnu.idatg2001.wargames.UI.Controllers.Dialogs;

import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Class to show help dialog in battleManager.
 * It is used to display useful information about
 * the features on the page.
 */
public class BMHelpDialog {

  public BMHelpDialog() {
  }

  /**
   * Method which shows help dialog in battleManager class.
   */
  public void showDialog() {
      createDialog();
    }

  /**
   * Method to create FAQ/Help dialog in the BattleManager controller.
   */
  private void createDialog() {
      Dialog<ButtonType> helpFrontPage = new Dialog<>();
      helpFrontPage.setTitle("BattleManager - Info");
      Label information = new Label("This is the BattleManager page where you could \n" +
          "manage your armies before putting them against each other on the simulation page.");
      helpFrontPage.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
      VBox FAQ = new VBox(information, terrainInfo(), armyManageButtonsInfo(), restOfButtons());
      FAQ.setAlignment(Pos.CENTER);
      FAQ.setSpacing(20);
      helpFrontPage.getDialogPane().setContent(FAQ);
      helpFrontPage.showAndWait();
    }

  /**
   * Method to fill a VBox with necessary information.
   * @return Filled VBox about terrain.
   */
  private VBox terrainInfo() {
      Label terrainTitle = new Label("Terrain");
      terrainTitle.setStyle("-fx-font-weight: bold");
      Label infoTerrain = new Label();
      infoTerrain.setText(
          "There are three types of terrain in wargames:\n" +
              "1. Forest - Tightly packed area with trees, perfect for infantries\n" +
              "2. Plains - Open fields perfect for cavalry and commander units.\n" +
              "3. Hills - Terrain with hills and small mountains, perfect for ranged units.");
      VBox newSimulation = new VBox(terrainTitle, infoTerrain);
      newSimulation.setAlignment(Pos.CENTER);
      newSimulation.setSpacing(15);

      return newSimulation;
    }

  /**
   * Method to fill a VBox with necessary information.
   * @return Filled VBox about the army table buttons' features.
   */
  private VBox armyManageButtonsInfo() {
      Label titleInfoButtons = new Label("Functions of Army table buttons");
      titleInfoButtons.setStyle("-fx-font-weight: bold");
      Label infoTableButtons = new Label();
      infoTableButtons.setText("Underneath the army tables you could find three buttons to manage the\n" +
          "army above. These buttons and their features: \n\n" +
          "1. Import army - Opens fileWindow where you could select army save file. \n Battle files also" +
          " work, but will only import the first army within.\n" +
          "2. Add units - This will open a window to add units of you choice.\n" +
          "3. Remove units - First select units by pressing the left mouse button in the table\n" +
          "then press remove units to delete them, a prompt will be presented.\n" +
          "4. Above the table there is a logo to change the army name, just press it and a" +
          "prompt will show.");
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
    titleInfoButtons.setStyle("-fx-font-weight: bold");
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
