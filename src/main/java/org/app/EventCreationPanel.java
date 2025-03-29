package org.app;

import javafx.application.Application;
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

public class EventCreationPanel implements Initializable {

    @FXML
    private Button eventClose;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void CloseEventCreationPanel(ActionEvent closeCreationPanel){

        try {

            ((Node) closeCreationPanel.getSource()).getScene().getWindow().hide();

            /*Stage navPanel = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root = loader.load(getClass().getResource("/NavigationPanel.fxml").openStream());
            navPanel.setTitle("Navigation Panel");
            navPanel.setScene(new Scene(root));
            navPanel.show();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}