package UI.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MenuController implements Initializable {
  @FXML private ImageView logo;

  public void onNewSimulationButtonPressed() throws IOException {




    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
    Stage stage = (Stage) logo.getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load(), 800, 600);
    stage.setScene(scene);
  }

  public void onExitButtonPressed() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
    alert.setHeaderText("Are you sure you want to exit?");
    alert.showAndWait()
        .filter(response -> response == ButtonType.OK)
        .ifPresent(response -> System.exit(0));
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
  }

  public void onOpenSimulationButtonPressed() {

  }
}
