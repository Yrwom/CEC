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

public class CreateUserSuccessPanel implements Initializable {

    @FXML
    private Button closeSuccessPanel;
    @FXML
    private Label userCreateSuccess;
    @Override
    public void initialize(URL location, ResourceBundle resources){
        userCreateSuccess.setText("User created successfully, welcome! :)");
    }

    public void ReopenAuthPanel(ActionEvent closeSuccessPanel){
        try {
           ((Node)closeSuccessPanel.getSource()).getScene().getWindow().hide();
            Stage AuthPanel = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root = loader.load(getClass().getResource("/AuthPanel.fxml").openStream());
            AuthPanel.setTitle("Auth Panel");
            AuthPanel.setScene(new Scene(root));
            AuthPanel.show();


        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
