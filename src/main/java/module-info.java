module Wargames.Sandeth {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires java.desktop;

  opens no.ntnu.idatg2001.wargames.Army to javafx.fxml, javafx.base;
  opens no.ntnu.idatg2001.wargames.Army.Units to javafx.base;
  opens no.ntnu.idatg2001.wargames.Simulation to javafx.fxml, javafx.base;
  opens no.ntnu.idatg2001.wargames.UI to javafx.fxml;
  opens no.ntnu.idatg2001.wargames.UI.Controllers to javafx.fxml;

  exports no.ntnu.idatg2001.wargames.UI.Controllers;
  exports no.ntnu.idatg2001.wargames.UI to javafx.graphics;
  exports no.ntnu.idatg2001.wargames.Simulation to javafx.graphics;
  opens no.ntnu.idatg2001.wargames.Utility to javafx.base, javafx.fxml;
  exports no.ntnu.idatg2001.wargames.Utility to javafx.graphics;
  exports no.ntnu.idatg2001.wargames.Model to javafx.graphics;
  opens no.ntnu.idatg2001.wargames.Model to javafx.base, javafx.fxml;
  exports no.ntnu.idatg2001.wargames.UI.Dialogs to javafx.graphics;
  opens no.ntnu.idatg2001.wargames.UI.Dialogs to javafx.fxml;
}