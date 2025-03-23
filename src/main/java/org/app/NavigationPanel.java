package org.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NavigationPanel implements Initializable {


    @FXML
    private Label NavTitle;
    @FXML
    private Button Logout;
    @Override
    public void initialize(URL location, ResourceBundle resources){

    }
    public void SetNavTitle(String inputUsername){

       NavTitle.setText("Welcome to CEC "+ inputUsername+ "!");
    }
    public void Logout(ActionEvent logout){
        try {

            ((Node)logout.getSource()).getScene().getWindow().hide();

            Stage navPanel = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root = loader.load(getClass().getResource("/AuthPanel.fxml").openStream());
            navPanel.setTitle("Navigation Panel");
            navPanel.setScene(new Scene(root));
            navPanel.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
