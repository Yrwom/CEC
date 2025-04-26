package org.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewMyEventsPanel implements Initializable {

    //follows same logic as Exapanded Day Cell to load all events for a given day but for a specific user instead
    @FXML
    private Button closeMyEvents;
    @FXML
    private GridPane expandedMyEvents;
    @FXML
    private Button leftArrow;
    @FXML
    private Button rightArrow;

    private int currentPage = 0;
    private static final int EVENTS_PER_PAGE = 4;
    private List<Event> events;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void setEvents(List<Event> events) {
        this.events = events;
        System.out.println("ExpandedDayCell: Number of events = " + events.size());
        updateMyEvents();
    }

    public List<Event> getEvents() {
        return events;
    }

    public void updateMyEvents() {
        expandedMyEvents.getChildren().clear();

        int start = currentPage * EVENTS_PER_PAGE;
        int end = Math.min(start + EVENTS_PER_PAGE, events.size());
        System.out.println("Number of events: " + events.size());
        Node cell;
        int columns = 2;

        try {
            for (int i = start; i < end; i++) {

                // System.out.println("We are in for loop");
                FXMLLoader eventLoader = new FXMLLoader((getClass().getResource("/org/app/MyEventsCell.fxml")));
                cell = eventLoader.load();
                MyEventsInfoCell myEventsInfoCell = eventLoader.getController();
                // System.out.println("Cell should load here");
                myEventsInfoCell.setEvents(this.events);
                myEventsInfoCell.SetEventIndex(i);
                myEventsInfoCell.PopulateMyEvents(i);


                int localIndex = i - start;
                int col = localIndex % columns;
                int row = localIndex / columns;
                expandedMyEvents.add(cell, col, row);

                // System.out.println("node added");
            }
            leftArrow.setVisible(currentPage > 0);
            rightArrow.setVisible(end < events.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void PageLeft(ActionEvent event){
        if( currentPage > 0){
            currentPage--;
            updateMyEvents();
        }
    }
    public void PageRight(ActionEvent event){
        int totalPages = (int) Math.ceil(events.size() / (double) EVENTS_PER_PAGE);
        if( currentPage < totalPages - 1){
            currentPage++;
            updateMyEvents();
        }
    }
    public void CloseExpandedDay(ActionEvent event){
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

}
