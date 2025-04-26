package org.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

public class ExpandedDayCell implements Initializable {

    @FXML
    private GridPane expandedBase;
    @FXML
    private Button leftArrow;
    @FXML
    private Button rightArrow;
    @FXML
    private Button CloseExpandedDay;

    private int currentPage = 0;
    private static final int EVENTS_PER_PAGE = 4;
    private List<Event> events;


    @Override
    public void initialize(URL location, ResourceBundle resources){


    }
    //allows program to set instance of events for use in this class
    public void setEvents(List<Event> events) {
        this.events = events;
        System.out.println("ExpandedDayCell: Number of events = " + events.size());
        updateExpandedDay();
    }
    //fet the current event
    public List<Event> getEvents(){
        return events;
    }

    //main for loop to fill out the expanded day view when clicking "More" on a certain day
    public void updateExpandedDay(){
            expandedBase.getChildren().clear();

            int start = currentPage * EVENTS_PER_PAGE;
            int end = Math.min(start + EVENTS_PER_PAGE, events.size());
        System.out.println("Number of events: " + events.size());
            Node cell;
            int columns = 2;

            try {
                for (int i = start; i < end; i++) {

                    FXMLLoader eventLoader = new FXMLLoader((getClass().getResource("/org/app/EventInfoCell.fxml")));
                    cell = eventLoader.load();
                    EventInfoCell eventInfoCell = eventLoader.getController();
                    eventInfoCell.setEvents(this.events);
                    eventInfoCell.SetEventIndex(i);
                    eventInfoCell.PopulateExpandDay(i);

                    int localIndex = i - start;
                    int col = localIndex % columns;
                    int row = localIndex / columns;
                    expandedBase.add(cell, col, row);

                }
                    leftArrow.setVisible(currentPage > 0);
                    rightArrow.setVisible(end < events.size());
            }catch (Exception e){
                e.printStackTrace();
            }
            }
            //allows user to click left if they have clicked right to view another page
        public void PageLeft(ActionEvent event){
            if( currentPage > 0){
                currentPage--;
                updateExpandedDay();
        }
        }

        //allows the user to page to the right if more than 4 events exit on a given day
        public void PageRight(ActionEvent event){
            int totalPages = (int) Math.ceil(events.size() / (double) EVENTS_PER_PAGE);
            if( currentPage < totalPages - 1){
                currentPage++;
                updateExpandedDay();
        }
    }
        //allows user to close expanded day view cleanly
        public void CloseExpandedDay(ActionEvent event){
            ((Node) event.getSource()).getScene().getWindow().hide();
        }
}
