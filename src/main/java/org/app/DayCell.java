package org.app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DayCell implements Initializable {
    @FXML
    private Label dayNumber;

    public void initialize(URL location, ResourceBundle resources){


    }
    public void setDayNumber(LocalDate date){
        dayNumber.setText(String.valueOf(date.getDayOfMonth()));

    }
}
