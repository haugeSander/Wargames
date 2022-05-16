module Wargames.Sandeth {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires java.desktop;

  opens Army to javafx.fxml, javafx.base;
  opens Army.Units to javafx.base;
  opens Simulation to javafx.fxml, javafx.base;
  opens UI to javafx.fxml;
  opens UI.Controllers to javafx.fxml;

  exports UI.Controllers;
  exports UI to javafx.graphics;
}