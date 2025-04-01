package org.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class EventCreationService {

    Connection connection;
    public static String responseCode;
    public static String dateCompareResult;


    public EventCreationService() {
        connection = SqliteConnection.Connector();
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
    public static void setDateComparedResult(String code) {
        dateCompareResult = code;
    }

    public static String getDateCompareResult() {
        return dateCompareResult;
    }
    public static void setResponseCode(String code) {
        responseCode = code;
    }

    public static String getResponseCode() {
        return responseCode;
    }

    public boolean NewEvent(String eventName, LocalDate startDate, LocalDate endDate, int maxParticipants, String location, String eventType, String eventDescription, Boolean votingStatus) {
        System.out.println("Max Pass" + maxParticipants);
        System.out.println(eventName);
        System.out.println(startDate);
        System.out.println(endDate);
        System.out.println(maxParticipants);
        System.out.println(location);
        System.out.println(eventType);
        System.out.println(eventDescription);
        System.out.println(votingStatus);
        String query = "INSERT INTO events(eventName,startDate,endDate,maxParticipants,location,eventType,eventDescription,votingEnabled) values(?,?,?,?,?,?,?,?)";

        try (Connection connection = SqliteConnection.Connector()) {

            if (connection == null) {
                System.out.println("Failed to establish a database connection!");
                return false;
            }
            if (eventName.isEmpty()) {
                EventCreationService.setResponseCode("Event Name Cannot be empty!");
                return false;
            }
            if (startDate == null) {

                EventCreationService.setResponseCode("Start Date Cannot be empty!");
                return false;
            }
            if (endDate == null) {
                EventCreationService.setResponseCode("End Date Cannot be empty!");
                return false;
            }
            if (location.isEmpty()) {
                EventCreationService.setResponseCode("Event must have a location!");
                return false;
            }
            if (eventType == null) {
                EventCreationService.setResponseCode(("Please select an event type!"));
            }
            if (votingStatus == null) {
                EventCreationService.setResponseCode("Please select a voting option!");
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

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new Event was inserted successfully!");
                EventCreationService.setResponseCode("Event Created Successfully, window closing in 5 seconds.");

                return true;
            }
        }catch(Exception e){
            System.out.println("Error in Prepared Statement Try block");
            e.printStackTrace();
        }

        return false;
        }
    public boolean DateCompare(LocalDate startDate, LocalDate endDate){
        int dateCompare;

        dateCompare = startDate.compareTo(endDate);
        System.out.println(dateCompare);
        if(dateCompare == 1) {
            setDateComparedResult("true");
            return true;
        }else{
            setDateComparedResult("false");
            return false;
        }

    }
        }


