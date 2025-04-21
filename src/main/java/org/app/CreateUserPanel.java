package org.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private PasswordField passwordField;
    @FXML
    private ToggleButton revealPass;
    @FXML
    private Label userErrorMessage;

    //creates a random UUID to assign to any new user created
    private String userUUID = UUID.randomUUID().toString();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //populates the combo box with the allowed roles
        newRole.getItems().addAll("Event Coordinator", "Participant", "Guest");

        //This block handles the password field view. the password field is visible until the toggle button is pressed revealing the text field for new password
        newPassword.textProperty().bindBidirectional(passwordField.textProperty());
        revealPass.selectedProperty().addListener((obs, was, isNow) -> {
            if (isNow) {
                newPassword.setVisible(true);
                newPassword.setManaged(true);
                passwordField.setVisible(false);
                passwordField.setManaged(false);
            } else {
                newPassword.setVisible(false);
                newPassword.setManaged(false);
                passwordField.setVisible(true);
                passwordField.setManaged(true);
            }
        });
    }

    //closes the User Creation Panel
    public void CloseUserCreationPanel(ActionEvent closeCreationPanel) {
        try {
            ((Node) closeCreationPanel.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    //This is the main method that handles displaying the results from the newUser method in AuthService
    public void NewUser(ActionEvent createNewUser) {
        try {

            if (authService.newUser(newUsername.getText(), newPassword.getText(), newRole.getValue(), userUUID)){
                //if a new user is created successfully, the user will get a popup letting them know and they are returned to the AuthPanel to login
                try {
                    System.out.println("We are in NewUser");

                    System.out.println("User Created!");

                    ((Node) createNewUser.getSource()).getScene().getWindow().hide();

                    Stage userSuccessPanel = new Stage();
                    userSuccessPanel.initModality(Modality.APPLICATION_MODAL);
                    FXMLLoader loader = new FXMLLoader();

                    Pane root = loader.load(getClass().getResource("/org/app/CreateUserSuccessPanel.fxml").openStream());
                    CreateUserSuccessPanel createUserSuccessPanel = (CreateUserSuccessPanel) loader.getController();
                    userSuccessPanel.setTitle("Success!");
                    userSuccessPanel.setResizable(false);
                    userSuccessPanel.setScene(new Scene(root));
                    userSuccessPanel.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
                //else they are given a popup that can only be closes by clicking the close button to make sure they understand the error
            } else {
                try {

                    System.out.println("Its broken");
                    Stage userErrorPanel = new Stage();
                    userErrorPanel.initModality(Modality.APPLICATION_MODAL);
                    FXMLLoader loader = new FXMLLoader();

                    Pane root = loader.load(getClass().getResource("/org/app/CreateUserErrorPanel.fxml").openStream());
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

