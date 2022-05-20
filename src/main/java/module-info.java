module Wargames.Sandeth {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires java.desktop;

  opens no.ntnu.idatg2001.sandeth.Army to javafx.fxml, javafx.base;
  opens no.ntnu.idatg2001.sandeth.Army.Units to javafx.base;
  opens no.ntnu.idatg2001.sandeth.Simulation to javafx.fxml, javafx.base;
  opens no.ntnu.idatg2001.sandeth.UI to javafx.fxml;
  opens no.ntnu.idatg2001.sandeth.UI.Controllers to javafx.fxml;

  exports no.ntnu.idatg2001.sandeth.UI.Controllers;
  exports no.ntnu.idatg2001.sandeth.UI to javafx.graphics;
  exports no.ntnu.idatg2001.sandeth.Simulation to javafx.graphics;
  opens no.ntnu.idatg2001.sandeth.Utility to javafx.base, javafx.fxml;
  exports no.ntnu.idatg2001.sandeth.Utility to javafx.graphics;
  exports no.ntnu.idatg2001.sandeth.Model to javafx.graphics;
  opens no.ntnu.idatg2001.sandeth.Model to javafx.base, javafx.fxml;
}