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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class CreateUserPanel implements Initializable {
    public AuthService authService = new AuthService();

    @FXML
    private TextField newUsername;
    @FXML
    private TextField newPassword;
    @FXML
    private ComboBox<String> newRole;
    @FXML
    private Label userErrorMessage;

    private String userUUID = UUID.randomUUID().toString();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newRole.getItems().addAll("Event Coordinator", "Participant", "Guest");
    }
    public void CloseUserCreationPanel(ActionEvent closeCreationPanel) {
        try {
            ((Node) closeCreationPanel.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public void NewUser(ActionEvent createNewUser) {
        try {

            if (authService.newUser(newUsername.getText(), newPassword.getText(), newRole.getValue(), userUUID)){
                try {
                    System.out.println("We are in NewUser");

                    System.out.println("User Created!");

                    ((Node) createNewUser.getSource()).getScene().getWindow().hide();

                    Stage userSuccessPanel = new Stage();
                    userSuccessPanel.initModality(Modality.APPLICATION_MODAL);
                    FXMLLoader loader = new FXMLLoader();

                    Pane root = loader.load(getClass().getResource("/CreateUserSuccessPanel.FXML").openStream());
                    CreateUserSuccessPanel createUserSuccessPanel = (CreateUserSuccessPanel) loader.getController();
                    userSuccessPanel.setTitle("Success!");
                    userSuccessPanel.setResizable(false);
                    userSuccessPanel.setScene(new Scene(root));
                    userSuccessPanel.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                try {

                    System.out.println("Its broken");
                    Stage userErrorPanel = new Stage();
                    userErrorPanel.initModality(Modality.APPLICATION_MODAL);
                    FXMLLoader loader = new FXMLLoader();

                    Pane root = loader.load(getClass().getResource("/CreateUserErrorPanel.fxml").openStream());
                    CreateUserErrorPanel createUserErrorPanel = (CreateUserErrorPanel) loader.getController();
                    userErrorPanel.setTitle("Error!");
                    userErrorPanel.setResizable(false);
                    userErrorPanel.setScene(new Scene(root));
                    userErrorPanel.show();


                } catch (IOException I) {
                    I.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

