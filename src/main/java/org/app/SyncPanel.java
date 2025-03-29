package org.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SyncPanel implements Initializable {

    @FXML
    private Button closeSync;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void CloseSync(ActionEvent closeSync){
        ((Node) closeSync.getSource()).getScene().getWindow().hide();
    }
}
