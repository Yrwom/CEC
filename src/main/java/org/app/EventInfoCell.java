package org.app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.YearMonth;
import java.util.List;
import java.util.ResourceBundle;

public class EventInfoCell implements Initializable {
    @FXML
    private Label eventName;
    @FXML
    private Label eventCreator;
    @FXML
    private Label eventStartDate;
    @FXML
    private Label eventEndDate;
    @FXML
    private Label eventParticipants;
    @FXML
    private Label eventLocation;
    @FXML
    private Label eventType;
    @FXML
    private Label eventVoting;
    @FXML
    private Label eventDescription;
    private List<Event> events;
    private int eventIndex;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void SetEventIndex(int index) {
        eventIndex = index;
    }
    public void PopulateExpandDay(int eventIndex){
        if (events == null || events.size() <= eventIndex) {
            System.out.println("Event list is not properly set or index is out of range.");
            return;
        }
        System.out.println("We are in populate expand day in the info cell class");

        Event event = events.get(eventIndex);

        eventName.setText(event.getTitle());
        eventCreator.setText("Implement Creator");
        eventStartDate.setText(event.getStartDate());
        eventEndDate.setText(event.getEndDate());
        eventParticipants.setText(String.valueOf(event.getMaxParticipants()));
        eventLocation.setText(event.getLocation());
        eventType.setText(event.getEventType());
        eventVoting.setText(String.valueOf(event.getVotingStatus()));
        eventDescription.setText(event.getEventDescription());
    }

}
