package org.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.app.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
//import java.awt.event.ActionEvent;
import java.io.IOException;
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
    private LocalDate cellDate;
    private List<Event> allEvents;

    public void initialize(URL location, ResourceBundle resources){


    }
    public void setDayNumber(LocalDate date){
       this.cellDate = date;
        dayNumber.setText(String.valueOf(date.getDayOfMonth()));

    }
    public LocalDate getCurrentDate(){
        return this.cellDate;
    }

    public boolean PopulateDayCell(LocalDate currentDate){
        List<Event> events = EventDAO.fetchEventByDate(currentDate);

        if(events != null && !events.isEmpty()){

          //  System.out.println("Event 1 should be printed");
            Event firstEvent = events.getFirst();
           List<User> userList = UserDAO.fetchUserByUserID(firstEvent.getUserUUID());
           User creator = userList.getFirst();
           // System.out.println("Event 1 Name under this line");
            System.out.println(firstEvent.getTitle());
            event1Name.setText(firstEvent.getTitle());
          // System.out.println("Event 1 Creator under this line");
            System.out.println(creator.getUsername());
            event1Creator.setText(creator.getUsername());

            if(events.size() > 1){
             //   System.out.println("Event 2 should be printed");
                Event secondEvent = events.get(1);
                List<User> userListSecondSet = UserDAO.fetchUserByUserID(secondEvent.getUserUUID());
                User creatorSecondSet = userListSecondSet.get(0);
                event2Name.setText(secondEvent.getTitle());
                event2Creator.setText(creatorSecondSet.getUsername());
            } else {
                event2Name.setText("");
                event2Creator.setText("");
            }
            return true;
        }else {
           expandButton.setVisible(false);
         //   System.out.println("Evenything is set to blank");
            event1Name.setText("");
            event1Creator.setText("");
            event2Name.setText("");
            event2Creator.setText("");
            return false;
        }

    }
    public void ExpandDayCell(ActionEvent expandButton){
        try {
            List<Event> events = EventDAO.fetchEventByDate(cellDate);
            System.out.println("Creating Stage...");
            Stage expandedDayCell = new Stage();
            FXMLLoader loader = new FXMLLoader();
            System.out.println("Opening Stream...");
            Pane root = loader.load(getClass().getResource("/ExpandedDayCell.fxml").openStream());
            System.out.println("Loading Controller...");
            ExpandedDayCell expandDayCell = (ExpandedDayCell) loader.getController();
            expandDayCell.setEvents(events);
            System.out.println("updateExapndedDay called");
            System.out.println("Setting Title...");
            expandedDayCell.setTitle("Expand Day Panel");
            expandedDayCell.setResizable(false);
            System.out.println("Setting Scene...");
            expandedDayCell.setScene(new Scene(root));
            expandedDayCell.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
