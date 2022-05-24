module no.ntnu.idatg2001 {
  requires javafx.controls;
  requires javafx.graphics;
  requires javafx.fxml;
  requires java.logging;

  opens no.ntnu.idatg2001.wargames.army;
  opens no.ntnu.idatg2001.wargames.army.units;
  opens no.ntnu.idatg2001.wargames.model;
  opens no.ntnu.idatg2001.wargames.simulation;
  opens no.ntnu.idatg2001.wargames.ui.controllers;
  opens no.ntnu.idatg2001.wargames.ui.dialogs;
  opens no.ntnu.idatg2001.wargames.utility;

  exports no.ntnu.idatg2001.wargames.ui to javafx.graphics;
  exports no.ntnu.idatg2001.wargames.ui.dialogs;
  exports no.ntnu.idatg2001.wargames.ui.controllers;
}