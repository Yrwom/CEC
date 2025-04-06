package org.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

import static javax.swing.UIManager.get;

public class NavigationPanel implements Initializable {


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

    //varibles for calendar creation
    private YearMonth dateFocus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dateFocus = YearMonth.now();

        createCalendar(dateFocus);

    }

    public void MonthForward(ActionEvent monthForward){
        dateFocus = dateFocus.plusMonths(1);
        calendarBase.getChildren().clear();
        createCalendar(dateFocus);
    }
    public void MonthBackward(ActionEvent monthBackward){
        dateFocus = dateFocus.minusMonths(1);
        calendarBase.getChildren().clear();

        createCalendar(dateFocus);
    }
    public void createCalendar(YearMonth yearMonth){

        currentMonth.setText(String.valueOf(dateFocus.getMonth()+ " " + (String.valueOf(dateFocus.getYear()))));
       LocalDate firstOfMonth = yearMonth.atDay(1);

       int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
       int dayOffset = dayOfWeek % 7;
       int daysInMonth = yearMonth.lengthOfMonth();

       if((dateFocus.getYear() & 4) !=0 && daysInMonth == 29){
           daysInMonth = 28;
       }

       for(int i = 0; i < dayOffset; i++){
           try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/BlankCell.fxml"));
               Node BlankCell = loader.load();
               BlankCell blankController = loader.getController();
               calendarBase.add(BlankCell, i, 0);
           }catch(Exception e){
               e.printStackTrace();
           }
           }
        int totalCells = dayOffset + daysInMonth;
        int remainder = totalCells % 7;
        if(remainder != 0) {
            int blankFill = 7 - remainder;
            int row = totalCells / 7;
            for(int i = 0; i < blankFill; i++){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/BlankCell.fxml"));
                    Node blank = loader.load();
                    BlankCell blankController = loader.getController();
                    calendarBase.add(blank, remainder + i, row);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

       for(int day = 1; day <= daysInMonth; day++){
           int dayIndex = day - 1;
           int cellIndex = dayIndex + dayOffset;
           int col = cellIndex % 7;
           int row = cellIndex / 7;

           try{
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/DayCell.fxml"));
               Node dayCell = loader.load();

               DayCell dayController = loader.getController();
               dayController.setDayNumber(yearMonth.atDay(day));

               calendarBase.add(dayCell, col, row);
           }catch (Exception e){
               e.printStackTrace();
           }
       }


    }
    public void SetNavTitle(String inputUsername) {

        NavTitle.setText("Welcome to CEC " + inputUsername + "!");
    }

    public void Logout(ActionEvent logout) {
        try {

            ((Node) logout.getSource()).getScene().getWindow().hide();

            Stage authPanel = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root = loader.load(getClass().getResource("/AuthPanel.fxml").openStream());
            authPanel.setTitle("Authorization Panel");
            authPanel.setResizable(false);
            authPanel.setScene(new Scene(root));
            authPanel.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void OpenEventCreation(ActionEvent openEventCreation) {
        try {
            System.out.println("Creating Stage...");
            Stage eventPanel = new Stage();
            FXMLLoader loader = new FXMLLoader();

            System.out.println("Opening Stream...");
            Pane root = loader.load(getClass().getResource("/EventCreationPanel.fxml").openStream());
            System.out.println("Loading Controller...");
            EventCreationPanel eventCreationPanel = (EventCreationPanel) loader.getController();

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
    public void OpenSettings(ActionEvent openSettings) {
        try {
            System.out.println("Creating Stage...");
            Stage settingPanel = new Stage();
            FXMLLoader loader = new FXMLLoader();

            System.out.println("Opening Stream...");
            Pane root = loader.load(getClass().getResource("/SettingsPanel.fxml").openStream());
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

    public void OpenSync(ActionEvent openSync) {
        try {
            System.out.println("Creating Stage...");
            Stage syncPanel = new Stage();
            FXMLLoader loader = new FXMLLoader();

            System.out.println("Opening Stream...");
            Pane root = loader.load(getClass().getResource("/SyncPanel.fxml").openStream());
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
}
