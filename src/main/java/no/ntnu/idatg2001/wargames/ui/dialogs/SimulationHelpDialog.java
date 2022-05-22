package no.ntnu.idatg2001.wargames.ui.dialogs;

import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Class to show help dialog in simulateController.
 * It is used to display useful information about
 * the features on the page.
 */
public class SimulationHelpDialog {
  private final String boldFont;

  /**
   * Constructor for simulationHelpDialog.
   */
  public SimulationHelpDialog() {
    boldFont = "-fx-font-weight: bold";
  }

  /**
   * Method which shows help dialog in battleManager class.
   */
  public void showDialog() {
    createDialog();
  }

  /**
   * Method to create FAQ/Help dialog in the simulateController.
   */
  private void createDialog() {
    Dialog<ButtonType> helpSimulationPage = new Dialog<>();
    helpSimulationPage.setTitle("Simulation - Info");
    Label information = new Label("This is the Simulation page where you can place the armies\n" +
        "made in the previous page against each other. You may run both single\n" +
        "and multiple simulations at the same time.");
    helpSimulationPage.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
    VBox faq = new VBox(information, functionalityInformation());
    faq.setAlignment(Pos.CENTER);
    faq.setSpacing(20);
    helpSimulationPage.getDialogPane().setContent(faq);
    helpSimulationPage.showAndWait();
  }

  /**
   * Method to fill a VBox with necessary information.
   * @return Filled VBox about the different simulation possibilities.
   */
  private VBox functionalityInformation() {
    Label singleSimulationTitle = new Label("Single simulation");
    Label multipleSimulationTitle = new Label("Multiple simulations");
    singleSimulationTitle.setStyle(boldFont);
    multipleSimulationTitle.setStyle(boldFont);
    Label infoSingleSimulation = new Label();
    infoSingleSimulation.setText(
            "This will run a single simulation and show its status on a graph throughout the simulation.\n" +
            "The graph updates based on the army sizes, you may also see units change HP in real time.\n" +
            "The simulation speed may be changed by adjusting the spinner underneath the graph.\n" +
            "A log is also viewable by pressing: Show log, in the right bottom corner. It will display\n" +
                "key events throughout the simulation. There are two logs, one for each team.");
    Label infoMultipleSimulation = new Label();
    infoMultipleSimulation.setText(
        "This will prompt you to enter amount of simulations to run and then simulate said amount.\n" +
            "When the simulations are done a pie chart will be presented with information about all\n" +
            "simulation winners. The Show log, button will preview winner each round.");
    VBox newSimulation = new VBox(singleSimulationTitle, infoSingleSimulation, multipleSimulationTitle, infoMultipleSimulation);
    newSimulation.setAlignment(Pos.CENTER);
    newSimulation.setSpacing(15);

    return newSimulation;
  }
}
