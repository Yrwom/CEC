package org.app;

import javafx.scene.control.Button;
import org.app.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Queue;
import java.util.ResourceBundle;

public class DayCell implements Initializable {
    Connection connection;
    @FXML
    private Label dayNumber;
    @FXML
    private Label event1Name;
    @FXML
    private Label event1Creator;
    @FXML
    private  Label event2Name;
    @FXML
    private Label event2Creator;
    @FXML
    private Button expandButton;

    private List<Event> allEvents;

    public void initialize(URL location, ResourceBundle resources){


    }
    public void setDayNumber(LocalDate date){
        dayNumber.setText(String.valueOf(date.getDayOfMonth()));

    }

    public List<Event> fetchEventByDate(LocalDate currentDate){

        List<Event> events = new ArrayList<>();

        String query = "SELECT * FROM events WHERE startDate = ? ORDER BY created_at ASC";

        try(Connection connection = SqliteConnection.Connector();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setString(1, currentDate.toString());

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){

                    Event event = new Event();
                    event.setTitle(resultSet.getString("eventName"));
                    event.setStartDate(resultSet.getString("startDate"));
                    event.setEndDate(resultSet.getString("endDate"));
                    event.setMaxParticipants(resultSet.getInt("maxParticipants"));
                    event.setLocation(resultSet.getString("location"));
                    event.setEventType(resultSet.getString("eventType"));
                    event.setEventDescription(resultSet.getString("eventDescription"));
                    event.setVotingStatus(resultSet.getBoolean("votingEnabled"));
                    event.setCreatedAt(resultSet.getString("created_at"));

                    events.add(event);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return events;
    }
    public boolean PopulateDayCell(LocalDate currentDate){
        List<Event> events = fetchEventByDate(currentDate);

        if(events != null && !events.isEmpty()){
            System.out.println("Event 1 should be printed");
            Event firstEvent = events.get(0);
            System.out.println("Event 1 Name under this line");
            System.out.println(firstEvent.getTitle());
            event1Name.setText(firstEvent.getTitle());
            System.out.println("Event 1 Creator under this line");
            System.out.println(firstEvent.getCreator());
            event1Creator.setText(firstEvent.getCreator());

            if(events.size() > 1){
                System.out.println("Event 2 should be printed");
                Event secondEvent = events.get(1);
                event2Name.setText(secondEvent.getTitle());
                event2Creator.setText(secondEvent.getCreator());
            } else {
                event2Name.setText("");
                event2Creator.setText("");
            }
            return true;
        }else {
            System.out.println("Evenything is set to null");
            event1Name.setText("");
            event1Creator.setText("");
            event2Name.setText("");
            event2Creator.setText("");
            return false;
        }

    }
    public void ExpandDayCell(ActionEvent expandButton){

    }
}
