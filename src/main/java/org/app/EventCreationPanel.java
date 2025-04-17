package org.app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.YearMonth;
import java.util.ResourceBundle;
import java.util.UUID;

public class EventCreationPanel implements Initializable {
    public EventService eventService = new EventService();
    private String eventType;
    private Boolean votingStatus;
    private String eventID = UUID.randomUUID().toString();
    private String userUUID = UserSession.getUserUUID();
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
    private Spinner<Integer> inputMaxParticipants;
    @FXML
    private Label eventResponseCode;
    @FXML
    private TextField inputLocation;
    @FXML
    private TextArea inputEventDescription;
    @FXML
    private Label statusLabel;

    private int maxParticipants = 1;

    private YearMonth dateFocus = YearMonth.now();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,99, 1);
            inputMaxParticipants.setValueFactory(valueFactory);
            inputMaxParticipants.getValue();


            inputMaxParticipants.valueProperty().addListener(new ChangeListener<Integer>() {
                @Override
                public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                    maxParticipants = inputMaxParticipants.getValue();
                    System.out.println(maxParticipants);
                }
            });

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
    public void SubmitEvent(){
        try {
            if(eventService.NewEvent(inputEventName.getText(), inputStartDate.getValue(), inputEndDate.getValue(), maxParticipants, inputLocation.getText(), eventType, inputEventDescription.getText(), votingStatus, userUUID, eventID)){
                statusLabel.setTextFill(Color.GREEN);
                System.out.println("We are in SubmitEvent and passed. Check db");
                System.out.println("userID: " + userUUID);
               final int [] secondsRemaining = {5};

               Timeline countDown = new Timeline(new KeyFrame(Duration.seconds(1), event ->{
                   secondsRemaining[0]--;

                   eventResponseCode.setText("Event Created successfully, window closing in " + secondsRemaining[0] + " seconds!");
                    if(secondsRemaining[0]<= 0){
                        Stage stage = (Stage) eventResponseCode.getScene().getWindow();
                        stage.close();
                    }

               }));
               countDown.setCycleCount(5);
               countDown.play();

            }else {
                statusLabel.setTextFill(EventService.getStatusLabelColor());
                System.out.println(EventService.getResponseCode());
                eventResponseCode.setText(EventService.getResponseCode());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    }