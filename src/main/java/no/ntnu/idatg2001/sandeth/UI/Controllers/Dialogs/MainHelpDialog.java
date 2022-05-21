package no.ntnu.idatg2001.sandeth.UI.Controllers.Dialogs;

import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainHelpDialog {

  public MainHelpDialog() {
  }

  public void showDialog() {
    createDialog();
  }

  private void createDialog() {
    Dialog<ButtonType> helpFrontPage = new Dialog<>();
    helpFrontPage.setTitle("Wargames - Info");
    Label information = new Label("Wargames is a simulation based application which could simulate a battle\n" +
        "between two armies. This application may also allow a user to create their own\n" +
        "armies which could fight.");
    helpFrontPage.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
    VBox FAQ = new VBox(information, newSimulationDetails(), openSimulationDetails());
    FAQ.setAlignment(Pos.CENTER);
    FAQ.setSpacing(20);
    helpFrontPage.getDialogPane().setContent(FAQ);
    helpFrontPage.showAndWait();
  }

  private HBox newSimulationDetails() {
    Label labelNewSim = new Label("New simulation: ");
    Label infoNewSim = new Label();
    infoNewSim.setText(
        "New simulation will open an empty simulation, with only default names. \n" +
            "From there you may add units, import armies or battle.");
    HBox newSimulation = new HBox(labelNewSim, infoNewSim);
    newSimulation.setAlignment(Pos.CENTER);
    newSimulation.setSpacing(15);

    return newSimulation;
  }

  private HBox openSimulationDetails() {
    Label labelOpenSim = new Label("Open simulation: ");
    Label infoOpenSim = new Label();
    infoOpenSim.setText("Open simulation will open an existing battle save file and import data\n" +
        "into a simulation. This requires a .csv file to be opened, but also needs two\n" +
        "armies to be saved within.");
    HBox openSimulation = new HBox(labelOpenSim, infoOpenSim);
    openSimulation.setAlignment(Pos.CENTER);
    openSimulation.setSpacing(15);

    return openSimulation;
  }
}
