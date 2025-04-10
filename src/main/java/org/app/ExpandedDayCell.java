package org.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import javafx.event.ActionEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ExpandedDayCell implements Initializable {

    @FXML
    private GridPane expandedBase;
    @FXML
    private Button leftArrow;
    @FXML
    private Button rightArrow;

    private int currentPage = 0;
    private static final int EVENTS_PER_PAGE = 4;
    private List<Event> events;

    @Override
    public void initialize(URL location, ResourceBundle resources){


    }
    public void setEvents(List<Event> events) {
        this.events = events;
        updateExpandedDay();
    }
    public List<Event> getEvents(){
        return events;
    }

public void updateExpandedDay(){
        expandedBase.getChildren().clear();

        int start = currentPage * EVENTS_PER_PAGE;
        int end = Math.min(start + EVENTS_PER_PAGE, events.size());
    System.out.println("Number of events: " + events.size());
        Node cell;
        int col = 0;
        int row;
        try {
            for (int i = start; i < end; i++) {
            System.out.println("We are in for loop");
                System.out.println("We are in for loop");
                FXMLLoader eventLoader = new FXMLLoader((getClass().getResource("/EventInfoCell.fxml")));
                cell = eventLoader.load();
                EventInfoCell eventInfoCell = eventLoader.getController();
                System.out.println("Cell should load here");
                eventInfoCell.setEvents(this.events);
                eventInfoCell.SetEventIndex(i);
                eventInfoCell.PopulateExpandDay(i);

                expandedBase.add(cell, col++, 0);
                System.out.println("node added");
            }
                leftArrow.setVisible(currentPage > 0);
                rightArrow.setVisible(end < events.size());
        }catch (Exception e){
            e.printStackTrace();
        }
        }
        public void PageLeft(ActionEvent event){
            if( currentPage > 0){
                currentPage--;
                updateExpandedDay();
        }
        }
        public void PageRight(ActionEvent event){
            DayCell dayCell = new DayCell();
            LocalDate dateFocus = dayCell.getCurrentDate();
            List<Event> events = EventDAO.fetchEventByDate(dateFocus);
            int totalPages = (int) Math.ceil(events.size() / (double) EVENTS_PER_PAGE);
            if( currentPage < totalPages - 1){
                currentPage++;
                updateExpandedDay();
        }
    }
}
