package org.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateUserSuccessPanel implements Initializable {

    @FXML
    private Button closeSuccessPanel;
    @FXML
    private Label userCreateSuccess;
    @Override
    public void initialize(URL location, ResourceBundle resources){
        //sets the main label to the following text
        userCreateSuccess.setText("User created successfully, welcome! :)");
    }

    public void ReopenAuthPanel(ActionEvent closeSuccessPanel){
        //once the user acknowledges they success message, they are returned to the AuthPanel to login
        try {
           ((Node)closeSuccessPanel.getSource()).getScene().getWindow().hide();
            Stage AuthPanel = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root = loader.load(getClass().getResource("/org/app/AuthPanel.fxml").openStream());
            AuthPanel.setTitle("Auth Panel");
            AuthPanel.setScene(new Scene(root));


        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
