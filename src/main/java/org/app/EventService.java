package org.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import javafx.scene.paint.Color;

public class EventService {

    Connection connection;
    public static String responseCode;
    public static String dateCompareResult;
    public static int dateCompareResultInt;
    public static Color statusLabelColor;

    //checks for connection to database, if connection isnt present application closes
    public EventService() {
        connection = LocalSqliteConnection.Connector();
        if (connection == null)

            System.exit(1);
    }

    public boolean isDbConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //getters and setters for various results used for events accorss program
    public static void setDateComparedResultInt(int code) {dateCompareResultInt = code;}
    public static int getDateCompareResultInt() {return dateCompareResultInt;}

    public static void setDateComparedResult(String code) {dateCompareResult = code;}
    public static String getDateCompareResult() {return dateCompareResult;}

    public static void setResponseCode(String code) {
        responseCode = code;
    }
    public static String getResponseCode() {
        return responseCode;
    }

    public static void setStatusLabelColor(Color code) {
        statusLabelColor = code;
    }
    public static Color getStatusLabelColor() {
        return statusLabelColor;
    }

    //main logic for creating a new event. Will check each varible for null and if said variable has any restrictions those are checks as well before submitting
    public boolean NewEvent(String eventName, LocalDate startDate, LocalDate endDate, int maxParticipants, String location, String eventType, String eventDescription, Boolean votingStatus, String userID, String eventID) {
        System.out.println("Max Pass" + maxParticipants);
        System.out.println(eventName);
        System.out.println(startDate);
        System.out.println(endDate);
        System.out.println(maxParticipants);
        System.out.println(location);
        System.out.println(eventType);
        System.out.println(eventDescription);
        System.out.println(votingStatus);
        System.out.println(userID);
        String query = "INSERT INTO events(eventName,startDate,endDate,maxParticipants,location,eventType,eventDescription,votingEnabled,userUUID,eventUUID) values(?,?,?,?,?,?,?,?,?,?)";

        try (Connection connection = LocalSqliteConnection.Connector()) {

            if (connection == null) {
                System.out.println("Failed to establish a database connection!");
                setStatusLabelColor(Color.RED);
                return false;
            }
            if (eventName.isEmpty()) {
                EventService.setResponseCode("Event Name Cannot be empty!");
                setStatusLabelColor(Color.RED);
                return false;
            }
            if (startDate == null) {

                EventService.setResponseCode("Start Date Cannot be empty!");
                setStatusLabelColor(Color.RED);
                return false;
            }
            if (endDate == null) {
                EventService.setResponseCode("End Date Cannot be empty!");
                setStatusLabelColor(Color.RED);
                return false;
            }
            if(dateCompareResultInt == -1){
                EventService.setResponseCode("End Date must be the same or later than the start date!");
                setStatusLabelColor(Color.RED);
                return false;
            }
            if (location.isEmpty()) {
                EventService.setResponseCode("Event must have a location!");
                setStatusLabelColor(Color.RED);
                return false;
            }
            if (eventType == null) {
                EventService.setResponseCode(("Please select an event type!"));
                setStatusLabelColor(Color.RED);
                return false;
            }
            if (votingStatus == null) {
                EventService.setResponseCode("Please select a voting option!");
                setStatusLabelColor(Color.RED);
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something aint right here....");

        }

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            System.out.println("We made it to NewEvent Prep stmnt");
            System.out.println(eventName);
            System.out.println(startDate);
            System.out.println(endDate);
            System.out.println(maxParticipants);
            System.out.println(location);
            System.out.println(eventName);
            preparedStatement.setString(1, (eventName));
            preparedStatement.setString(2, String.valueOf(startDate));
            preparedStatement.setString(3, String.valueOf(endDate));
            preparedStatement.setString(4, String.valueOf(maxParticipants));
            preparedStatement.setString(5, (location));
            preparedStatement.setString(6, (eventType));
            preparedStatement.setString(7, (eventDescription));
            preparedStatement.setString(8, String.valueOf(votingStatus));
            preparedStatement.setString(9, String.valueOf(userID));
            preparedStatement.setString(10, (eventID));

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new Event was inserted successfully!");
                EventService.setResponseCode("Event Created Successfully, window closing in 5 seconds.");
                setStatusLabelColor(Color.GREEN);

                return true;
            }
        }catch(Exception e){
            System.out.println("Error in Prepared Statement Try block");
            e.printStackTrace();
        }

        return false;
        }
    //follows the same logic as NewEvent but is used fo editing an existing event with the use of the events UUID
    public boolean EditEvent(String eventName, LocalDate startDate, LocalDate endDate, int maxParticipants, String location, String eventType, String eventDescription, Boolean votingStatus, String eventUUID) {
        String query = "UPDATE events SET " +
                "eventName = COALESCE(?,eventName)," +
                "startDate = COALESCE(?,startDate)," +
                "endDate = COALESCE(?,endDate)," +
                "maxParticipants = COALESCE(?,maxParticipants)," +
                "location = COALESCE(?,location)," +
                "eventType = COALESCE(?,eventType)," +
                "eventDescription = COALESCE(?,eventDescription)," +
                "votingEnabled = COALESCE(?,votingEnabled)" +
                "WHERE eventUUID = ?;";
        try (Connection connection = LocalSqliteConnection.Connector();
             PreparedStatement ps = connection.prepareStatement(query)) {

           if(eventName == null) ps.setNull(1, Types.VARCHAR);
           else                  ps.setString(1, eventName);
           if(startDate == null) ps.setNull(2, Types.DATE);
           else                  ps.setString(2, String.valueOf(startDate));
           if(endDate == null)   ps.setNull(3, Types.DATE);
           else                  ps.setString(3,String.valueOf(endDate));
           ps.setString(4, String.valueOf(maxParticipants));
           if(location == null)  ps.setNull(5, Types.VARCHAR);
           else                  ps.setString(5,location);
           if(eventType == null) ps.setNull(6, Types.VARCHAR);
           else                  ps.setString(6,eventType);
           if(eventDescription == null) ps.setNull(7,Types.VARCHAR);
           else                  ps.setString(7, eventDescription);
           if(votingStatus == null) ps.setNull(8, Types.VARCHAR);
           else                  ps.setString(8, String.valueOf(votingStatus));
           ps.setString(9, eventUUID);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("A new Event was updated successfully!");
                EventService.setResponseCode("Event Edited Successfully, window closing in 5 seconds.");
                setStatusLabelColor(Color.GREEN);

                return true;

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    //Will compare the start date and end date of an event to ensure the end date is after the start date
    public boolean DateCompare(LocalDate startDate, LocalDate endDate){
        int dateCompare;

        dateCompare = startDate.compareTo(endDate);
        System.out.println(dateCompare);
        if(dateCompare <= 0) {
            setDateComparedResult("true");
            setDateComparedResultInt(1);
            return true;
        }else{
            setDateComparedResult("false");
            setDateComparedResultInt(-1);
            return false;
        }

    }
    //creates object to check whether an event has been deleted or not for use in SyncPanel
    public class DeleteResult{
        public final boolean localDeleted;
        public final boolean centralDeleted;
        public DeleteResult(boolean localDeleted, boolean centralDeleted){
            this.localDeleted = localDeleted;
            this.centralDeleted = centralDeleted;
        }
    }
    //method to set deletion status to give feedback to the user in SyncPanel
    public DeleteResult deleteEventGlobally(String eventUUID){
        DeleteEventConfirmation deleteEventConfirmation = new DeleteEventConfirmation();
        boolean localOk   = false;
        boolean centralOk = false;

        try (Connection local = LocalSqliteConnection.Connector()) {
            localOk = EventDAO.deleteEvent(local, eventUUID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection central = CentralSqliteConnection.Connector()) {
            centralOk = EventDAO.deleteEvent(central, eventUUID);
        }catch (SQLException e) {
            e.printStackTrace();

        }
        return new DeleteResult(localOk, centralOk);
    }

}


