package org.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class OfflineModePanel implements Initializable {

    @FXML
    private Button offlineClose;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void CloseOfflineMode(ActionEvent closeOffline){
        try{
            ((Node) closeOffline.getSource()).getScene().getWindow().hide();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
