package org.app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ForgotPasswordPanel implements Initializable {
    public AuthService authService = new AuthService();

    @FXML
    private TextField currentUsername;
    @FXML
    private TextField inputNewPassword;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField inputConfirmNewPassword;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label responseCode;
    @FXML
    private ToggleButton revealToggle;
    @FXML
    private ToggleButton revealToggle2;
    @FXML
    private Button createNewPassword;
    @FXML
    private Button closeForgotPassword;

    //handles toggle buttons and password fields to hide password for security
    public void initialize(URL location, ResourceBundle resources) {
        inputNewPassword.textProperty().bindBidirectional(passwordField.textProperty());

        revealToggle.selectedProperty().addListener((obs, was, isNow) -> {
            if (isNow) {
                inputNewPassword.setVisible(true);
                inputNewPassword.setManaged(true);
                passwordField.setVisible(false);
                passwordField.setManaged(false);
            } else {
                inputNewPassword.setVisible(false);
                inputNewPassword.setManaged(false);
                passwordField.setVisible(true);
                passwordField.setManaged(true);
            }
        });
        inputConfirmNewPassword.textProperty().bindBidirectional(confirmPasswordField.textProperty());
        revealToggle2.selectedProperty().addListener((obs, was, isNow) -> {
            if (isNow) {
                inputConfirmNewPassword.setVisible(true);
                inputConfirmNewPassword.setManaged(true);
                confirmPasswordField.setVisible(false);
                confirmPasswordField.setManaged(false);
            } else {
                inputConfirmNewPassword.setVisible(false);
                inputConfirmNewPassword.setManaged(false);
                confirmPasswordField.setVisible(true);
                confirmPasswordField.setManaged(true);
            }
        });
    }

    //allows user to reset their password on button click. Has checks for a user existing and same checks as AuthService for NewUser
    public void ForgotPasswordExecute(ActionEvent forgotPassword) {
        List<User> userList = UserDAO.fetchUserByUsername(currentUsername.getText());
        if(userList.isEmpty()){
            responseCode.setText("No user associated with: " + currentUsername.getText());
            return;
        }
        User currentUser = userList.get(0);
        String userUUID = currentUser.getUserUUID();
        String newPassword = inputNewPassword.getText();
        String confirmedNewPassword = inputConfirmNewPassword.getText();

        try {
            if(authService.ConfirmNewPasswordMatch(newPassword, confirmedNewPassword)){
                if (authService.ForgotPassword(userUUID, newPassword)) {
                    final int[] secondsRemaining = {5};

                    Timeline countDown = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                        secondsRemaining[0]--;

                        responseCode.setText("Password Updated! Window closing in " + secondsRemaining[0] + " seconds!");
                        if (secondsRemaining[0] <= 0) {
                            Stage stage = (Stage) responseCode.getScene().getWindow();
                            stage.close();
                        }

                    }));
                    countDown.setCycleCount(5);
                    countDown.play();

                } else {

                    System.out.println(EventService.getResponseCode());
                    responseCode.setText(AuthService.getResponseCode());
                }
            }else{
                responseCode.setText(AuthService.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
