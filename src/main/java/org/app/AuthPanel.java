package org.app;

import javafx.application.Platform;
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
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AuthPanel implements Initializable {
    //public AuthService to access methods in my AuthService class
    public AuthService authService = new AuthService();

    //all FXML items that I use or wanted to keep track of. This goes for every java file using javaFX so I will only notate it here
    @FXML
    private Label isConnected;
    @FXML
    private TextField inputUsername;
    @FXML
    private TextField inputPassword;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ToggleButton revealPass;
    @FXML
    private Button userCreation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // this gives a line of text to in the main UI letting know that the connection has been established
        if (authService.isDbConnected()) {
            isConnected.setText("Database Connection Established!");
        } else {
            isConnected.setText("Database Connection Failed!");
        }
        //hides password with toggle button to reveal
        inputPassword.textProperty().bindBidirectional(passwordField.textProperty());

        revealPass.selectedProperty().addListener((obs, was, isNow) -> {
            if (isNow) {
                inputPassword.setVisible(true);
                inputPassword.setManaged(true);
                passwordField.setVisible(false);
                passwordField.setManaged(false);
            } else {
                inputPassword.setVisible(false);
                inputPassword.setManaged(false);
                passwordField.setVisible(true);
                passwordField.setManaged(true);
            }
        });
    }

    public void Login(ActionEvent login) {
        //main logic for logging into CEC. It calls the isLogin method providing the username and password to verify the user exists.
        // If it does not, and error is given to the user. See AuthService for more details
        try {
            if (authService.isLogin(inputUsername.getText(), inputPassword.getText())) {
                String enteredUsername = inputUsername.getText();
                List<User> users = UserDAO.fetchUserByUsername(enteredUsername);
                if(!users.isEmpty()){
                    User currentUser = users.getFirst();
                    UserSession.setCurrentUser(currentUser);
                    System.out.println(currentUser.getUsername());
                isConnected.setText("Login Success!");
                System.out.println("Correct Login");


                //close login window on NavPanel open
                System.out.println("Opening Navigation Panel...");
                ((Node) login.getSource()).getScene().getWindow().hide();

                //load navigation panel on successful login
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/app/NavigationPanel.fxml"));
                Parent navRoot = loader.load();
                Scene navScene = new Scene(navRoot);
                Stage navStage = new Stage();
                navStage.setScene(navScene);
                NavigationPanel navigationPanel = (NavigationPanel) loader.getController();
                navigationPanel.SetNavTitle(inputUsername.getText());
                navStage.show();
                }
            } else {
                //set main status label to let user know login failed
                isConnected.setText("Login Failed! Username or Password is incorrect.");
                System.out.println("Fail");
                System.out.println(inputUsername.getText());
                System.out.println(inputPassword.getText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CreateUser(ActionEvent userCreation) {
        //opens the CreateUserPanel to allow a new user to make an account
        try {
            System.out.println("Opening User Creation Panel...");
            System.out.println("Creating Stage...");
            Stage userPanel = new Stage();
            FXMLLoader loader = new FXMLLoader();
            System.out.println("Opening Stream...");
            Pane root = loader.load(getClass().getResource("/org/app/CreateUserPanel.fxml").openStream());
            System.out.println("Loading Controller...");
            CreateUserPanel createUserPanel = (CreateUserPanel) loader.getController();
            System.out.println("Setting Title...");
            userPanel.setTitle("User Creation Panel");
            userPanel.setResizable(false);
            System.out.println("Setting Scene...");
            userPanel.setScene(new Scene(root));
            System.out.println("Displaying Panel...");
            userPanel.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void OpenOffline(ActionEvent openOffline) {
        //allows the user to open the dialoug window for entering offline mode
        try {
            System.out.println("Creating Stage...");
            Stage offlinePanel = new Stage();
            FXMLLoader loader = new FXMLLoader();
            System.out.println("Opening Stream...");
            Pane root = loader.load(getClass().getResource("/org/app/OfflineModePanel.fxml").openStream());
            System.out.println("Loading Controller...");
            OfflineModePanel offlineOptionPanel = (OfflineModePanel) loader.getController();
            System.out.println("Setting Title...");
            offlinePanel.setResizable(false);
            offlinePanel.setTitle("Settings  Panel");
            System.out.println("Setting Scene...");
            offlinePanel.setScene(new Scene(root));
            offlinePanel.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void CloseCEC(ActionEvent closeCEC){
        //closes the application
            System.out.println("Closing CEC!");
            Platform.exit();
    }
   public void OpenForgotPassword(ActionEvent forgotPassword){
        //open dialog window allowing user to reset their password
       try {
           System.out.println("Opening User Creation Panel...");
           System.out.println("Creating Stage...");
           Stage forgotPassPanel = new Stage();
           FXMLLoader loader = new FXMLLoader();
           System.out.println("Opening Stream...");
           Pane root = loader.load(getClass().getResource("/org/app/ForgotPasswordPanel.fxml").openStream());
           System.out.println("Loading Controller...");
           ForgotPasswordPanel forgotPasswordPanel = (ForgotPasswordPanel) loader.getController();
           System.out.println("Setting Title...");
           forgotPassPanel.setTitle("User Creation Panel");
           forgotPassPanel.setResizable(false);
           System.out.println("Setting Scene...");
           forgotPassPanel.setScene(new Scene(root));
           System.out.println("Displaying Panel...");
           forgotPassPanel.show();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
}
