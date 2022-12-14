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
 * Class to open help dialog on main menu page.
 * It is used to display useful information about
 * features on the main menu page.
 * @author Sander Hauge.
 * @version 1.0-SNAPSHOT
 */
public class MainHelpDialog extends Dialog<ButtonType> {
  private final String boldFont;

  /**
   * Constructor to call for super(): Dialog constructor,
   * to create a dialog object. CSS boldFont also set as string.
   */
  public MainHelpDialog() {
    super();
    boldFont = "-fx-font-weight: bold";
  }

  /**
   * Method to show the help dialog.
   */
  public void showDialog() {
    File logo = new File
        ("src/main/resources/no/ntnu/idatg2001/wargames/ui/controllers/Logos/Tank.png");
    ((Stage)getDialogPane().getScene().getWindow()).getIcons().add(new Image(logo.toURI().toString())); //Sets logo.
    createDialog();
  }

  /**
   * Creates the dialog.
   */
  private void createDialog() {
    setTitle("Wargames - Info");
    Label information = new Label("Wargames is a simulation based application which could simulate a battle\n" +
        "between two armies. This application also allows a user to create their own\n" +
        "armies to battle in the simulation page.");
    getDialogPane().getButtonTypes().addAll(ButtonType.OK);
    VBox faq = new VBox(information, newSimulationDetails(), openSimulationDetails());
    faq.setAlignment(Pos.CENTER);
    faq.setSpacing(20);
    getDialogPane().setContent(faq);
    showAndWait();
  }

  /**
   * Fills an HBox with necessary information about
   * the button newSimulation.
   * @return HBox filled with information.
   */
  private HBox newSimulationDetails() {
    Label labelNewSim = new Label("New simulation: ");
    labelNewSim.setStyle(boldFont);
    Label infoNewSim = new Label();
    infoNewSim.setText(
        "New simulation, will open an empty battle, with only default names. \n" +
            "From there you may add units, import armies or battle files.");
    HBox newSimulation = new HBox(labelNewSim, infoNewSim);
    newSimulation.setAlignment(Pos.CENTER);
    newSimulation.setSpacing(15);

    return newSimulation;
  }

  /**
   * Fills and HBox with necessary information about
   * the button openSimulation.
   * @return HBox filled with information.
   */
  private HBox openSimulationDetails() {
    Label labelOpenSim = new Label("Open simulation: ");
    labelOpenSim.setStyle(boldFont);
    Label infoOpenSim = new Label();
    infoOpenSim.setText("Open simulation will open an existing battle save file and import data\n" +
        "into a simulation. This requires a .csv file to be opened, but also needs two\n" +
        "armies to be saved within the file. To open army files, go to the next page\n" +
        "by pressing New simulation, and import from there.");
    HBox openSimulation = new HBox(labelOpenSim, infoOpenSim);
    openSimulation.setAlignment(Pos.CENTER);
    openSimulation.setSpacing(15);

    return openSimulation;
  }
}
