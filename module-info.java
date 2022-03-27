module UI {
  requires javafx.graphics;
  requires javafx.fxml;

  opens UI.Controllers;
  exports UI.Controllers;
}