module Wargames.Sandeth {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires java.desktop;

  opens no.ntnu.idatg2001.wargames.army to javafx.fxml, javafx.base;
  opens no.ntnu.idatg2001.wargames.army.units to javafx.base;
  opens no.ntnu.idatg2001.wargames.simulation to javafx.fxml, javafx.base;
  opens no.ntnu.idatg2001.wargames.ui to javafx.fxml;
  opens no.ntnu.idatg2001.wargames.ui.controllers to javafx.fxml;

  exports no.ntnu.idatg2001.wargames.army to javafx.base;
  exports no.ntnu.idatg2001.wargames.army.units to javafx.base;
  exports no.ntnu.idatg2001.wargames.simulation to javafx.base;
  exports no.ntnu.idatg2001.wargames.utility to javafx.base;
  exports no.ntnu.idatg2001.wargames.model to javafx.base;
  exports no.ntnu.idatg2001.wargames.ui to javafx.graphics, javafx.base;
  exports no.ntnu.idatg2001.wargames.ui.dialogs to javafx.graphics;

  opens no.ntnu.idatg2001.wargames.model to javafx.base, javafx.fxml;
  opens no.ntnu.idatg2001.wargames.utility to javafx.base, javafx.fxml;
  opens no.ntnu.idatg2001.wargames.ui.dialogs to javafx.fxml;
}