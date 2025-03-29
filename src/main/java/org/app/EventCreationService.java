package org.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class EventCreationService {

    Connection connection;
    public static String responseCode;


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

    public boolean NewEvent(String eventName, LocalDate startDate, LocalDate endDate, int maxParticipants, String location, String eventType, String eventDescription, Boolean votingStatus) {
        String query = "INSERT INTO events(eventName,startDate,endDate, maxParticipants, location, eventType, eventDescription, votingEnabled) values(?,?,?,?,?,?,?,?)";

        try (Connection connection = SqliteConnection.Connector()){

            if (connection == null) {
                System.out.println("Failed to establish a database connection!");
                return false;
            }else {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                System.out.println("We made it here");

                preparedStatement.setString(1, eventName);
                preparedStatement.setString(2, String.valueOf(startDate));
                preparedStatement.setString(3, String.valueOf(endDate));
                preparedStatement.setString(3, String.valueOf(maxParticipants));
                preparedStatement.setString(3, location);
                preparedStatement.setString(3, eventType);
                preparedStatement.setString(3, eventDescription);
                preparedStatement.setString(3, String.valueOf(votingStatus));

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("A new user was inserted successfully!");
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
        }

}
