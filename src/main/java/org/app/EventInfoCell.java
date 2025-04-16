package org.app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;
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
    @FXML
    private Group Vote;
    @FXML
    private RadioButton VoteYes;
    @FXML
    private RadioButton VoteNo;
    @FXML
    private TextArea voteResponseBox;
    @FXML
    private Label NoCount;
    @FXML
    private Label YesCount;

    private List<Event> events;
    private Event currentEvent;

    private int eventIndex;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
            voteResponseBox.setVisible(false);

    }
    public void setEvents(List<Event> events) {
        System.out.println("EventInfoCell: Number of events = " + events.size());
        this.events = events;
    }


    public void SetEventIndex(int index) {
        eventIndex = index;
    }
    public void PopulateExpandDay(int eventIndex){
        Vote.setVisible(false);
        if (events == null || events.size() <= eventIndex) {
            System.out.println("Event list is not properly set or index is out of range.");
            return;
        }

        System.out.println("We are in populate expand day in the info cell class");
        User currentUser = UserSession.getCurrentUser();

        if(!Objects.equals(currentUser.getRole(), "Guest")){
            Vote.setVisible(true);
        }
        Event event = events.get(eventIndex);
        this.currentEvent = events.get(eventIndex);
        List<User> userList = UserDAO.fetchUserByUserID(event.getUserUUID());
        if(!userList.isEmpty()) {
            User creator = userList.get(0);
            if(event.getVotingStatus()){
                    System.out.println("we are in voting status check");
                    Vote.setVisible(true);
            }
            checkHasVoted();

            String votesByEventUUID = event.getEventUUID();
            Votes votes = VotesDAO.fetchVotesByEventUUID(votesByEventUUID);
            System.out.println("EventUUID after VotesDAO" + votesByEventUUID);
            votes.SetEventUUID(event.getEventUUID());
            NoCount.setText(String.valueOf(votes.GetVoteNoCount()));
            System.out.println("No Count: " + votes.GetVoteNoCount());
            YesCount.setText(String.valueOf(votes.GetVoteYesCount()));
            System.out.println("Yes Count: " + votes.GetVoteYesCount());

            System.out.println("Event Index: " + eventIndex);
            eventName.setText(event.getTitle());
            eventCreator.setText(creator.getUsername());
            System.out.println(creator.getUsername());
            eventStartDate.setText(event.getStartDate());
            eventEndDate.setText(event.getEndDate());
            eventParticipants.setText(String.valueOf(event.getMaxParticipants()));
            eventLocation.setText(event.getLocation());
            eventType.setText(event.getEventType());
            eventVoting.setText(String.valueOf(event.getVotingStatus()));
            eventDescription.setText(event.getEventDescription());
        }
    }
    public void VotedYes(){
        int voteValue = 1;
        String eventUUID = this.currentEvent.getEventUUID();
        String userUUID = UserSession.getUserUUID();
        String query = "INSERT INTO votes(voteValue,eventID,userID) values(?,?,?)";

        try(Connection connection = SqliteConnection.Connector()) {
            PreparedStatement prstm = connection.prepareStatement(query);

            prstm.setInt(1, voteValue);
            prstm.setString(2, eventUUID);
            prstm.setString(3, userUUID);

            int rowsInserted = prstm.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A vote inserted successfully!");
                Vote.setVisible(false);

            }
        }catch(Exception e){
            voteResponseBox.setVisible(true);
            System.out.println("Error: Already Voted on Event!");
            e.printStackTrace();
        }

    }
    public void VotedNo(){
        String eventUUID = this.currentEvent.getEventUUID();
        String userUUID = UserSession.getUserUUID();
        int voteValue = 0;
        String query = "INSERT INTO votes(voteValue,eventID,userID) values(?,?,?)";

        try(Connection connection = SqliteConnection.Connector()) {
            PreparedStatement prstm = connection.prepareStatement(query);

            prstm.setInt(1, voteValue);
            prstm.setString(2, eventUUID);
            prstm.setString(3, userUUID);

            int rowsInserted = prstm.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A vote inserted successfully!");
                Vote.setVisible(false);



            }
        }catch(Exception e){
            voteResponseBox.setVisible(true);
            System.out.println("Error in Prepared Statement Try block");
            e.printStackTrace();
        }

    }
public void checkHasVoted(){
        if(currentEvent == null)return;
        String eventUUID = currentEvent.getEventUUID();
        String userUUID = UserSession.getUserUUID();

        boolean hasVoted = VotesDAO.HasVotedCheck(eventUUID, userUUID);

        if(hasVoted){
            Vote.setVisible(false);

            voteResponseBox.setVisible(true);
        }else {
            Vote.setVisible(true);
        }
}
}
