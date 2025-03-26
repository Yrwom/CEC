package org.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateUserErrorPanel implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private Label userErrorMessage;
    @Override
    public void initialize(URL location, ResourceBundle resources){
        userErrorMessage.setText("Username already taken, please choose another");
    }

    public void ReopenCreateUser(ActionEvent closeButton){
        try {
            ((Node)closeButton.getSource()).getScene().getWindow().hide();
        }catch(Exception e){
            e.printStackTrace();
        }
        }

}


