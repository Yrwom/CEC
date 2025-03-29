package org.app;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class EventCreationPanel implements Initializable {
    public EventCreationService eventCreationService = new EventCreationService();
    private String eventType;
    private Boolean votingStatus;
    @FXML
    private Button eventClose;
    @FXML
    private Button submitEvent;
    @FXML
    private TextField inputEventName;
    @FXML
    private DatePicker inputStartDate;
    @FXML
    private DatePicker inputEndDate;
    @FXML
    private Spinner inputMaxParticipants;
    @FXML
    private TextField inputLocation;
    @FXML
    private TextField inputEventDescription;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void SetEventTypePublic(ActionEvent RadioButton) {
        eventType = "Public";
    }

    public void SetEventTypePrivate(ActionEvent RadioButton) {
        eventType = "Private";
    }

    public void SetVotingEnabled(ActionEvent VotingRadioButton) {
        votingStatus = true;
    }

    public void SetVotingDisabled(ActionEvent VotingRadioButton) {
        votingStatus = false;
    }

    public void CloseEventCreationPanel(ActionEvent closeCreationPanel) {
        try {
            ((Node) closeCreationPanel.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();

    }
    }
    public void SubmitEvent(ActionEvent submitEvent){
        try {
            if(eventCreationService.NewEvent(inputEventName.getText(), inputStartDate.getValue(), inputEndDate.getValue(), (Integer) inputMaxParticipants.getValue(), inputLocation.getText(), eventType, inputEventDescription.getText(), votingStatus)){

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}