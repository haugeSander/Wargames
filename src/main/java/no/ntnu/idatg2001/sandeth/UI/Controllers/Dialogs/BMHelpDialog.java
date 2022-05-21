package no.ntnu.idatg2001.sandeth.UI.Controllers.Dialogs;

import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BMHelpDialog {

    public BMHelpDialog() {
    }

    public void showDialog() {
      createDialog();
    }

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

    private VBox terrainInfo() {
      Label terrainTitle = new Label("Terrain");
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

    private VBox armyManageButtonsInfo() {
      Label titleInfoButtons = new Label("Functions of Army table buttons");
      Label infoTableButtons = new Label();
      infoTableButtons.setText("Underneath the army tables you could find three buttons to manage the\n" +
          "army above. First button is \n" +
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

  private VBox restOfButtons() {
    Label titleInfoButtons = new Label("Other important buttons");
    Label infoTableButtons = new Label();
    infoTableButtons.setText("");

    VBox armyManageButtons = new VBox(titleInfoButtons, infoTableButtons);
    armyManageButtons.setAlignment(Pos.CENTER);
    armyManageButtons.setSpacing(15);

    return armyManageButtons;
  }
}
