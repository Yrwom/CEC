package org.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateUserErrorPanel implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private Label userErrorMessage;
    @Override
    public void initialize(URL location, ResourceBundle resources){
        //sets the response code when an error occurs in the CreateUserPanel java file
        AuthService code = new AuthService();
        userErrorMessage.setText(AuthService.getResponseCode());
    }
    //This will reopen the previous window as it is closed when this error is shown
    public void ReopenCreateUser(ActionEvent closeButton){
        try {
            ((Node)closeButton.getSource()).getScene().getWindow().hide();
        }catch(Exception e){
            e.printStackTrace();
        }
        }

}


