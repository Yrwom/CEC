package org.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateUserPanel implements Initializable {
    public AuthService authService = new AuthService();

    @FXML
    private TextField newUsername;
    @FXML
    private TextField newPassword;
    @FXML
    private ComboBox<String> newRole;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        newRole.getItems().addAll("Event Coordinator","Participant","Guest");
    }
    public void NewUser(ActionEvent createNewUser){
        try{
            System.out.println("We are in NewUser");
            if (authService.newUser(newUsername.getText(), newPassword.getText(), newRole.getValue())) {
                System.out.println("User Created!");

                ((Node)createNewUser.getSource()).getScene().getWindow().hide();

                Stage AuthPanel = new Stage();
                FXMLLoader loader = new FXMLLoader();

                Pane root = loader.load(getClass().getResource("/AuthPanel.fxml").openStream());
                AuthPanel.setTitle("Auth Panel");
                AuthPanel.setScene(new Scene(root));
                AuthPanel.show();

            }else{
                System.out.println("Its broken");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        }
    }

