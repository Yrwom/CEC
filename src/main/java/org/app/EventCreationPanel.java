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
    private int maxParticipants = 1;
    private YearMonth dateFocus = YearMonth.now();

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //sets value of Spinner to 1-99
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1);
        inputMaxParticipants.setValueFactory(valueFactory);
        inputMaxParticipants.getValue();

        //prints the current value in max participants when changed, used for debugging
        inputMaxParticipants.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                maxParticipants = inputMaxParticipants.getValue();
                System.out.println(maxParticipants);
            }
        });

    }
    //sets the value of eventType and VotingStatus based on which button is click
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

    //closes event creation
    public void CloseEventCreationPanel(ActionEvent closeCreationPanel) {
        try {
            ((Node) closeCreationPanel.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void SubmitEvent() {
        //receives true or false from eventService.NewEvent after passing all event information. See EventService for more info
        //follows same style of closing window after 5 seconds
        try {
            if (eventService.NewEvent(inputEventName.getText(), inputStartDate.getValue(), inputEndDate.getValue(), maxParticipants, inputLocation.getText(), eventType, inputEventDescription.getText(), votingStatus, userUUID, eventID)) {
                statusLabel.setTextFill(Color.GREEN);
                System.out.println("We are in SubmitEvent and passed. Check db");
                System.out.println("userID: " + userUUID);
                eventClose.setVisible(false);
                submitEvent.setVisible(false);
                final int[] secondsRemaining = {5};

                Timeline countDown = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                    secondsRemaining[0]--;

                    eventResponseCode.setText("Event Created successfully, window closing in " + secondsRemaining[0] + " seconds!");
                    if (secondsRemaining[0] <= 0) {
                        Stage stage = (Stage) eventResponseCode.getScene().getWindow();
                        stage.close();
                    }

                }));
                countDown.setCycleCount(5);
                countDown.play();

            } else {
                statusLabel.setTextFill(EventService.getStatusLabelColor());
                System.out.println(EventService.getResponseCode());
                eventResponseCode.setText(EventService.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DateCheck() {
        //verifies that the start date is before the end date
        if (!eventService.DateCompare(inputStartDate.getValue(), inputEndDate.getValue())) {
            EventService.setResponseCode("End Date must be the same or later than the start date!");
            eventResponseCode.setText(EventService.getResponseCode());
        }

    }
}