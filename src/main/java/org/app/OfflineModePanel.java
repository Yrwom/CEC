package org.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class OfflineModePanel implements Initializable {

    @FXML
    private Button offlineClose;
    @FXML
    private Label offlineResponse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //sets the current user into offline mode and stores it in user session
        UserSession.setOfflineStatus(true);
        offlineResponse.setText("You are now in offline mode!");

    }
    //closes offline mode panel
    public void CloseOfflineMode(ActionEvent closeOffline){
        try{
            ((Node) closeOffline.getSource()).getScene().getWindow().hide();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
