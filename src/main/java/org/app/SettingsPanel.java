package org.app;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SettingsPanel implements Initializable {

    @FXML
    private Button closeSettings;
    @FXML
    private ComboBox<String> fontColorCombo;
    @FXML
    private ComboBox<String> fontFamilyCombo;
    @FXML
    private Label settingsResponseCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> families = Font.getFamilies();
        FXCollections.sort(FXCollections.observableArrayList(families));
        fontFamilyCombo.setItems(FXCollections.observableArrayList(families));

        List<String> colors = Arrays.asList(
                "Black","White","Red","Green","Blue","Yellow",
                "Orange","Purple","Gray","Brown"
        );
        fontColorCombo.setItems(FXCollections.observableArrayList(colors));
    }
    public void CloseSettings(ActionEvent closeSettings){
        ((Node) closeSettings.getSource()).getScene().getWindow().hide();
    }

    public void SaveSettings(ActionEvent saveSettings){
        String inputFamily = fontFamilyCombo.getValue();
        String inputColor = fontColorCombo.getValue();
        String userUUID = UserSession.getUserUUID();
        String fontFamily;
        if (inputFamily == null || inputFamily.isEmpty()) {
            fontFamily = null;
        } else {
            fontFamily = inputFamily;
        }

        String fontColor;
        if (inputColor == null || inputColor.isEmpty()) {
            fontColor = null;
        } else {
            fontColor = inputColor;
        }
        if(SettingsService.updateUserPrefrences(userUUID, fontFamily, fontColor)){
            settingsResponseCode.setText("Settings saved successfully");
        } else{
            settingsResponseCode.setText("Error saving settings, please contact support.");
        }
    }



}
