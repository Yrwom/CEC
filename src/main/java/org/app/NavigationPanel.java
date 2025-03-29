package org.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NavigationPanel implements Initializable {


    @FXML
    private Label NavTitle;
    @FXML
    private Button Logout;
    @FXML
    private Button openSettings;
    @FXML
    private Button createEvent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void SetNavTitle(String inputUsername) {

        NavTitle.setText("Welcome to CEC " + inputUsername + "!");
    }

    public void Logout(ActionEvent logout) {
        try {

            ((Node) logout.getSource()).getScene().getWindow().hide();

            Stage navPanel = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root = loader.load(getClass().getResource("/AuthPanel.fxml").openStream());
            navPanel.setTitle("Navigation Panel");
            navPanel.setScene(new Scene(root));
            navPanel.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void OpenEventCreation(ActionEvent openEventCreation) {
        try {
            System.out.println("Creating Stage...");
            Stage eventPanel = new Stage();
            FXMLLoader loader = new FXMLLoader();

            System.out.println("Opening Stream...");
            Pane root = loader.load(getClass().getResource("/EventCreationPanel.fxml").openStream());
            System.out.println("Loading Controller...");
            EventCreationPanel eventCreationPanel = (EventCreationPanel) loader.getController();

            System.out.println("Setting Title...");

            eventPanel.setTitle("Event Creation Panel");

            System.out.println("Setting Scene...");
            eventPanel.setScene(new Scene(root));
            eventPanel.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void OpenSettings(ActionEvent openSettings) {
        try {
            System.out.println("Creating Stage...");
            Stage settingPanel = new Stage();
            FXMLLoader loader = new FXMLLoader();

            System.out.println("Opening Stream...");
            Pane root = loader.load(getClass().getResource("/SettingsPanel.fxml").openStream());
            System.out.println("Loading Controller...");
            SettingsPanel settingsPanel = (SettingsPanel) loader.getController();

            System.out.println("Setting Title...");

            settingPanel.setTitle("Settings  Panel");

            System.out.println("Setting Scene...");
            settingPanel.setScene(new Scene(root));
            settingPanel.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void OpenSync(ActionEvent openSync) {
        try {
            System.out.println("Creating Stage...");
            Stage syncPanel = new Stage();
            FXMLLoader loader = new FXMLLoader();

            System.out.println("Opening Stream...");
            Pane root = loader.load(getClass().getResource("/SyncPanel.fxml").openStream());
            System.out.println("Loading Controller...");
            SyncPanel syncOperationPanel = (SyncPanel) loader.getController();

            System.out.println("Setting Title...");

            syncPanel.setTitle("Settings  Panel");

            System.out.println("Setting Scene...");
            syncPanel.setScene(new Scene(root));
            syncPanel.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
