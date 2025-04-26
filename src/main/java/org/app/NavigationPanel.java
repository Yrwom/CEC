package org.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;



public class NavigationPanel implements Initializable {

    @FXML
    private AnchorPane navigationPane;
    @FXML
    private Label NavTitle;
    @FXML
    private Button Logout;
    @FXML
    private Button openSettings;
    @FXML
    private Button createEvent;
    @FXML
    private Button monthForward;
    @FXML
    private Button monthBackward;
    @FXML
    private Label currentMonth;
    @FXML
    private GridPane calendarBase;
    @FXML
    private Button refreshButton;
    @FXML
    private Label CurrentRole;
    @FXML
    private Label CurrentRoleFlavorText;
    @FXML
    private Button openSync;

    //varibles for calendar creation
    private YearMonth dateFocus;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //sets the dateFocus todays time making sure the calendar is opened on the current month
        dateFocus = YearMonth.now();
        boolean offlineMode = UserSession.getOfflineStatus();
        createCalendar(dateFocus);

        User currentUser = UserSession.getCurrentUser();
        //checks if the user is an event coordinator. If they are not, the create event button is hidden
        //additionally displays the users role and username
        if(!Objects.equals(currentUser.getRole(),"Event Coordinator")){
            createEvent.setVisible(false);
            CurrentRoleFlavorText.setText("You are currently logged in as a:      ");
            CurrentRole.setText(currentUser.getRole());
        }else{
            CurrentRoleFlavorText.setText("You are currently logged in as an:      ");
            CurrentRole.setText(currentUser.getRole());
        }
        if(offlineMode == true) {
            openSync.setVisible(false);
        }
    }
    //forward arrow allows the user to move forward in months
    public void MonthForward(ActionEvent monthForward){
        dateFocus = dateFocus.plusMonths(1);
        calendarBase.getChildren().clear();
        createCalendar(dateFocus);
    }
    //allows user to move backwards in months
    public void MonthBackward(ActionEvent monthBackward){
        dateFocus = dateFocus.minusMonths(1);
        calendarBase.getChildren().clear();

        createCalendar(dateFocus);
    }
    //allows the user to refresh the calendar after and changes are made
    public void RefreshButton(ActionEvent resetButton){
        dateFocus = YearMonth.now();
        calendarBase.getChildren().clear();
        createCalendar(dateFocus);

    }
    //Creates the calenday utilizing the DayCell class as well as the BlankCell class to fill out the full days and fill any blank spaces with a gray box
    public void createCalendar(YearMonth yearMonth){

        currentMonth.setText(String.valueOf(dateFocus.getMonth()+ " " + (String.valueOf(dateFocus.getYear()))));

        LocalDate firstOfMonth = yearMonth.atDay(1);
       int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
       int dayOffset = dayOfWeek % 7;
       int daysInMonth = yearMonth.lengthOfMonth();
       int calendarCellTotal = 42;
try {
    for (int cellIndex = 0; cellIndex < calendarCellTotal; cellIndex++) {

        int col = cellIndex % 7;
        int row = cellIndex / 7;
        Node cell;

        if (cellIndex < dayOffset || cellIndex >= dayOffset + daysInMonth) {
            FXMLLoader blankLoader = new FXMLLoader((getClass().getResource("/org/app/BlankCell.fxml")));
            cell = blankLoader.load();
        }else {
            int day = cellIndex - dayOffset + 1;
            try {
                FXMLLoader dayLoader = new FXMLLoader((getClass().getResource("/org/app/DayCell.fxml")));
                cell = dayLoader.load();
                LocalDate currentDate = yearMonth.atDay(day);
                DayCell daycell = dayLoader.getController();
                EventDAO.setCurrentDate(currentDate);
                daycell.setDayNumber(yearMonth.atDay(day));
                daycell.PopulateDayCell(currentDate);


            }catch(Exception e){
                e.printStackTrace();
                FXMLLoader blankLoader = new FXMLLoader((getClass().getResource("/org/app/BlankCell.fxml")));
                cell = blankLoader.load();
            }

        }
        calendarBase.add(cell, col, row);
    }

}catch (Exception e){
    e.printStackTrace();
}
    }
    //sets the users username in the top welcoming them to the application
    public void SetNavTitle(String inputUsername) {

        NavTitle.setText("Welcome to CEC " + inputUsername + "!");
    }
    //logs the users out and returns them to the AuthPanel to log in or create a new user
    public void Logout(ActionEvent logout) {
        try {

            ((Node) logout.getSource()).getScene().getWindow().hide();

            Stage authPanel = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root = loader.load(getClass().getResource("/org/app/AuthPanel.fxml").openStream());
            authPanel.setTitle("Authorization Panel");
            authPanel.setResizable(false);
            authPanel.setScene(new Scene(root));
            authPanel.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //opens the event creation modal
    public void OpenEventCreation(ActionEvent openEventCreation) {
        try {
            System.out.println("Creating Stage...");
            Stage eventPanel = new Stage();
            FXMLLoader loader = new FXMLLoader();
            System.out.println("Opening Stream...");
            Pane root = loader.load(getClass().getResource("/org/app/EventCreationPanel.fxml").openStream());
            System.out.println("Loading Controller...");
            System.out.println("Setting Title...");
            eventPanel.setTitle("Event Creation Panel");
            eventPanel.setResizable(false);
            System.out.println("Setting Scene...");
            eventPanel.setScene(new Scene(root));
            eventPanel.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //open the settins modal
    public void OpenSettings(ActionEvent openSettings) {
        try {
            System.out.println("Creating Stage...");
            Stage settingPanel = new Stage();
            FXMLLoader loader = new FXMLLoader();

            System.out.println("Opening Stream...");
            Pane root = loader.load(getClass().getResource("/org/app/SettingsPanel.fxml").openStream());
            System.out.println("Loading Controller...");
            SettingsPanel settingsPanel = (SettingsPanel) loader.getController();

            System.out.println("Setting Title...");

            settingPanel.setTitle("Settings  Panel");
            settingPanel.setResizable(false);
            System.out.println("Setting Scene...");
            settingPanel.setScene(new Scene(root));
            settingPanel.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //opens the sync modal
    public void OpenSync(ActionEvent openSync) {
        try {
            System.out.println("Creating Stage...");
            Stage syncPanel = new Stage();
            FXMLLoader loader = new FXMLLoader();

            System.out.println("Opening Stream...");
            Pane root = loader.load(getClass().getResource("/org/app/SyncPanel.fxml").openStream());
            System.out.println("Loading Controller...");
            SyncPanel syncOperationPanel = (SyncPanel) loader.getController();
            syncPanel.setResizable(false);
            System.out.println("Setting Title...");

            syncPanel.setTitle("Settings  Panel");

            System.out.println("Setting Scene...");
            syncPanel.setScene(new Scene(root));
            syncPanel.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //opens the My Events screen to view any events you've created and edit or delete them
    public void OpenMyEvents(ActionEvent openMyEvents){
        try {
            String userUUID = UserSession.getUserUUID();
            List<Event> events = EventDAO.fetchEventByUserUUID(userUUID);
            System.out.println("Creating Stage...");
            Stage viewMyEventsPanel = new Stage();
            FXMLLoader loader = new FXMLLoader();
            System.out.println("Opening Stream...");
            Pane root = loader.load(getClass().getResource("/org/app/ViewMyEventsPanel.fxml").openStream());
            System.out.println("Loading Controller...");
            ViewMyEventsPanel myEventsPanel = (ViewMyEventsPanel) loader.getController();
            myEventsPanel.setEvents(events);
            System.out.println("Setting Title...");
            viewMyEventsPanel.setTitle("My Events Panel");
            viewMyEventsPanel.setResizable(false);
            System.out.println("Setting Scene...");
            viewMyEventsPanel.setScene(new Scene(root));
            viewMyEventsPanel.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
