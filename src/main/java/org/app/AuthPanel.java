package org.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AuthPanel implements Initializable {
    public AuthService authService = new AuthService();

    @FXML
    private Label isConnected;
    @FXML
    private TextField inputUsername;
    @FXML
    private TextField inputPassword;
    @FXML
    private Button userCreation;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        if(authService.isDbConnected()){
            isConnected.setText("Database Connection Established!");
        } else {
            isConnected.setText("Database Connection Failed!");
        }
    }
    public void Login (ActionEvent login){
        try {
            if (authService.isLogin(inputUsername.getText(), inputPassword.getText())) {
                isConnected.setText("Login Success!");
                System.out.println("Correct Login");

                //close login window on NavPanel open
                System.out.println("Opening Navigation Panel...");
                ((Node)login.getSource()).getScene().getWindow().hide();

                System.out.println("Creating Stage...");
                Stage navPanel = new Stage();
                FXMLLoader loader = new FXMLLoader();

                System.out.println("Opening Stream...");
                Pane root = loader.load(getClass().getResource("/NavigationPanel.fxml").openStream());
                System.out.println("Loading Controller...");
                NavigationPanel navigationPanel = (NavigationPanel)loader.getController();

                System.out.println("Setting Title...");
                navigationPanel.SetNavTitle(inputUsername.getText());
                navPanel.setTitle("Navigation Panel");

                System.out.println("Setting Scene...");
                navPanel.setScene(new Scene(root));
                navPanel.show();

            }else{
                isConnected.setText("Login Failed! Username or Password is incorrect.");
                System.out.println("Fail");
                System.out.println(inputUsername.getText());
                System.out.println(inputPassword.getText());
            }
            //revisit when fleshing out exceptions
       /*  catch (SQLException e)
           isConnected.setText("Login Failed! Username or Password is incorrect.");
           e.printStackTrace(); */
        } catch (IOException e){
            e.printStackTrace();
          }
        }
    public void CreateUser(ActionEvent userCreation)  {
        try {
            System.out.println("Opening User Creation Panel...");
            ((Node) userCreation.getSource()).getScene().getWindow().hide();

            System.out.println("Creating Stage...");
            Stage userPanel = new Stage();
            FXMLLoader loader = new FXMLLoader();

            System.out.println("Opening Stream...");
            Pane root = loader.load(getClass().getResource("/CreateUserPanel.fxml").openStream());
            System.out.println("Loading Controller...");
            CreateUserPanel createUserPanel = (CreateUserPanel) loader.getController();

            System.out.println("Setting Title...");
            userPanel.setTitle("User Creation Panel");

            System.out.println("Setting Scene...");
            userPanel.setScene(new Scene(root));

            System.out.println("Displaying Panel...");
            userPanel.show();
        } catch(Exception e){
            e.printStackTrace();
        }
        }
    }

