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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ResourceBundle;
import java.util.UUID;

public class EventEditorPanel implements Initializable {
    public EventService eventService = new EventService();
    private Event eventToEdit;
    private String eventType;
    private Boolean votingStatus;
    @FXML
    private Button eventClose;
    @FXML
    private Button submitEditEvent;
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
    private int maxParticipants;
    private YearMonth dateFocus = YearMonth.now();


    private String nullIfEmpty(String s){
        if(s == null) return null;
        String trimmed = s.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
    private LocalDate NullIfEmptyDate(DatePicker dp){
        return dp.getValue();
    }
    public void setCurrentEvent(Event currentEvent){
        this.eventToEdit = currentEvent;
    }
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

    public void CloseEventCreationPanel(ActionEvent closeEditPanel) {
        try {
            ((Node) closeEditPanel.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void submitEditEvent(){
        //new values
         String newEventName = nullIfEmpty(inputEventName.getText());
         LocalDate newStartDate = NullIfEmptyDate(inputStartDate);
         LocalDate newEndDate = NullIfEmptyDate(inputEndDate);
         Integer newMaxParticipants = inputMaxParticipants.getValue();
         String newLocation = nullIfEmpty(inputLocation.getText());
         String newEventType = nullIfEmpty(eventType);
         Boolean newVotingStatus = Boolean.valueOf(nullIfEmpty(String.valueOf(votingStatus)));
         String newEventDescription = nullIfEmpty(inputEventDescription.getText());
         String eventUUID = eventToEdit.getEventUUID();
        try {
            if(eventService.EditEvent(newEventName, newStartDate, newEndDate, newMaxParticipants, newLocation, newEventType, newEventDescription, newVotingStatus, eventUUID)){
                statusLabel.setTextFill(Color.GREEN);
                submitEditEvent.setVisible(false);
                eventClose.setVisible(false);
                System.out.println("We are in SubmitEvent and passed. Check db");
                final int [] secondsRemaining = {5};

                Timeline countDown = new Timeline(new KeyFrame(Duration.seconds(1), event ->{
                    secondsRemaining[0]--;

                    eventResponseCode.setText("Event updated successfully, window closing in " + secondsRemaining[0] + " seconds!");
                    if(secondsRemaining[0]<= 0){
                        Stage stage = (Stage) eventResponseCode.getScene().getWindow();
                        stage.close();
                    }

                }));
                countDown.setCycleCount(5);
                countDown.play();

            }else {
                statusLabel.setTextFill(EventService.getStatusLabelColor());
                System.out.println("ReponseCode:"+EventService.getResponseCode());
                eventResponseCode.setText(EventService.getResponseCode());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void DateCheck(){
        if(!eventService.DateCompare(inputStartDate.getValue(), inputEndDate.getValue())) {
            EventService.setResponseCode("End Date must be the same or later than the start date!");
            eventResponseCode.setText(EventService.getResponseCode());
        }
    }












}
