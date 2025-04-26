package org.app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    private AnchorPane VoteContainer;
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
    @FXML
    private Label VoteLabel;

    private List<Event> events;
    private Event currentEvent;
    private int eventIndex;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    //sets the current events
    public void setEvents(List<Event> events) {
        System.out.println("EventInfoCell: Number of events = " + events.size());
        this.events = events;
    }
    public void setCurrentEvent(Event event){currentEvent = event;}
    public Event getCurrentEvent(){return currentEvent;}

    //sets the event index, used in ExpandedDayCell
    public void SetEventIndex(int index) {
        eventIndex = index;
    }

    //used to populate the expanded day cell showing all event info
    public void PopulateExpandDay(int eventIndex){
        voteResponseBox.setVisible(false);
        VoteNo.setVisible(false);
        VoteYes.setVisible(false);
        VoteLabel.setVisible(false);
        //if there are no events return
        if (events == null || events.size() <= eventIndex) {
            System.out.println("Event list is not properly set or index is out of range.");
            return;
        }

        System.out.println("We are in populate expand day in the info cell class");
        //gets the current user for limitation checks like voting
        User currentUser = UserSession.getCurrentUser();

        Event event = events.get(eventIndex);
        this.currentEvent = events.get(eventIndex);
        List<User> userList = UserDAO.fetchUserByUserID(event.getUserUUID());
        boolean votingStatus = event.getVotingStatus();
        if(!userList.isEmpty()) {

            User creator = userList.get(0);
            System.out.println(event.getVotingStatus());
            voteCheck(votingStatus);
            roleCheck(currentUser);
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
    //accepts a "Yes" vote from the user and stores it in the database
    public void VotedYes(){
        int voteValue = 1;
        String eventUUID = this.currentEvent.getEventUUID();
        String userUUID = UserSession.getUserUUID();
        String query = "INSERT INTO votes(voteValue,eventID,userID) values(?,?,?)";

        try(Connection connection = LocalSqliteConnection.Connector()) {
            PreparedStatement prstm = connection.prepareStatement(query);

            prstm.setInt(1, voteValue);
            prstm.setString(2, eventUUID);
            prstm.setString(3, userUUID);

            int rowsInserted = prstm.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A vote inserted successfully!");
                VoteNo.setVisible(false);
                VoteYes.setVisible(false);
                VoteLabel.setVisible(false);

            }
        }catch(Exception e){
            voteResponseBox.setVisible(true);
            System.out.println("Error: Already Voted on Event!");
            e.printStackTrace();
        }

    }
    //accepts a "No" Vote from a user and stores it in the database
    public void VotedNo(){
        String eventUUID = this.currentEvent.getEventUUID();
        String userUUID = UserSession.getUserUUID();
        int voteValue = 0;
        String query = "INSERT INTO votes(voteValue,eventID,userID) values(?,?,?)";

        try(Connection connection = LocalSqliteConnection.Connector()) {
            PreparedStatement prstm = connection.prepareStatement(query);

            prstm.setInt(1, voteValue);
            prstm.setString(2, eventUUID);
            prstm.setString(3, userUUID);

            int rowsInserted = prstm.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A vote inserted successfully!");
                VoteNo.setVisible(false);
                VoteYes.setVisible(false);
                VoteLabel.setVisible(false);



            }
        }catch(Exception e){
            voteResponseBox.setVisible(true);
            System.out.println("Error in Prepared Statement Try block");
            e.printStackTrace();
        }

    }
    //method to check if a user has already voted on an event, if so, display the response container instead of voting prompt
    public void checkHasVoted(){
            if(currentEvent == null)return;
            String eventUUID = currentEvent.getEventUUID();
            String userUUID = UserSession.getUserUUID();

            boolean hasVoted = VotesDAO.HasVotedCheck(eventUUID, userUUID);

            if(hasVoted){
                VoteNo.setVisible(false);
                VoteYes.setVisible(false);
                VoteLabel.setVisible(false);

                voteResponseBox.setVisible(true);
            }else {
                VoteNo.setVisible(true);
                VoteYes.setVisible(true);
                VoteLabel.setVisible(true);
            }
    }
    //Checks if the users role is Guest. If it is, it hides the vote options and displays the response box
    public void roleCheck(User user){
        if(Objects.equals(user.getRole(), "Guest")){
            voteResponseBox.setVisible(true);
            voteResponseBox.setText("Guests Are Ineligible to vote!");
            System.out.println("Vote button should not be present");
        }
    }
    //checks if the user has already voted on given event and displays response accordingly
    public void voteCheck(boolean votingStatus){
        if(!votingStatus){
            System.out.println("we are in voting status check");
            System.out.println(VoteContainer.isVisible());
            voteResponseBox.setVisible(true);
            voteResponseBox.setText("Voting Disabled on this event!");
            voteResponseBox.isVisible();
            VoteNo.setVisible(false);
            VoteNo.isVisible();
            VoteYes.setVisible(false);
            VoteLabel.setVisible(false);

            System.out.println("Vote button should be gone");
        }
    }
}
