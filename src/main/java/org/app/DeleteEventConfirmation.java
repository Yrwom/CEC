package org.app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

public class DeleteEventConfirmation implements Initializable {
    //eventService to access methods
    public EventService eventService = new EventService();
    private Event eventToDelete;
    private String responseCode;

    @FXML
    private Button yesDelete;
    @FXML
    private Label deleteResponseCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    //setters and getters for future use if needed
    public void SetDeleteResponseCode(String setDeleteResponseCode){responseCode = setDeleteResponseCode;}
    public String GetDeleteResponseCode(){return responseCode;}

    //this allows you to set the current event so you delete the right one
    public void setCurrentEvent(Event currentEvent){
        this.eventToDelete = currentEvent;
    }

    //this method will call the DeleteEventGlobally method to delete the given event
    public boolean YesDelete(ActionEvent yesDelete){
        String eventUUID = eventToDelete.getEventUUID();
        EventService.DeleteResult result = eventService.deleteEventGlobally(eventUUID);
        boolean offlineMode = UserSession.getOfflineStatus();

        //the following blocks check whether the event was deleted locally, gloablly, or not at all and closes the window after 5 seconds
        if(!result.localDeleted){
            deleteResponseCode.setText("Could not Delete Locally");
        }else if(!result.centralDeleted && offlineMode){


            final int [] secondsRemaining = {5};

            Timeline countDown = new Timeline(new KeyFrame(Duration.seconds(1), event ->{
                secondsRemaining[0]--;

                deleteResponseCode.setText("Event deleted Locally, window closing in " + secondsRemaining[0] + " seconds!");
                if(secondsRemaining[0]<= 0){
                    Stage stage = (Stage) deleteResponseCode.getScene().getWindow();
                    stage.close();
                }

            }));
            countDown.setCycleCount(5);
            countDown.play();

        } else{
            final int [] secondsRemaining = {5};

            Timeline countDown = new Timeline(new KeyFrame(Duration.seconds(1), event ->{
                secondsRemaining[0]--;

                deleteResponseCode.setText("Event deleted Globally, window closing in " + secondsRemaining[0] + " seconds!");
                if(secondsRemaining[0]<= 0){
                    Stage stage = (Stage) deleteResponseCode.getScene().getWindow();
                    stage.close();
                }

            }));
            countDown.setCycleCount(5);
            countDown.play();
            return true;
        }
        return false;
    }
    //This closes the window
    public void NoDelete(ActionEvent noDelete){
        Stage stage = (Stage) deleteResponseCode.getScene().getWindow();
        stage.close();
    }
}
