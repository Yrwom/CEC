package org.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
private static LocalDate currentDate;
    public static List<Event> fetchEventByDate(LocalDate currentDate) {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM events WHERE startDate = ? ORDER BY created_at ASC";

        try (Connection connection = SqliteConnection.Connector();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, currentDate.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Event event = new Event();
                    event.setTitle(resultSet.getString("eventName"));
                    event.setStartDate(resultSet.getString("startDate"));
                    event.setEndDate(resultSet.getString("endDate"));
                    event.setMaxParticipants(resultSet.getInt("maxParticipants"));
                    event.setLocation(resultSet.getString("location"));
                    event.setEventType(resultSet.getString("eventType"));
                    event.setEventDescription(resultSet.getString("eventDescription"));
                    event.setVotingStatus(resultSet.getBoolean("votingEnabled"));
                    event.setCreatedAt(resultSet.getString("created_at"));
                    event.setUserUUID(resultSet.getString("userUUID"));
                    event.setEventUUID(resultSet.getString("eventUUID"));
                    events.add(event);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }

    public static void setCurrentDate(LocalDate date) {
        currentDate = date;
    }

    public static LocalDate getCurrentDate() {
        return currentDate;
    }

    public static List<Event> fetchEventByUserUUID(String userUUID) {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM events WHERE userUUID = ? ORDER BY created_at ASC";

        try (Connection connection = SqliteConnection.Connector();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userUUID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Event event = new Event();
                    event.setTitle(resultSet.getString("eventName"));
                    event.setStartDate(resultSet.getString("startDate"));
                    event.setEndDate(resultSet.getString("endDate"));
                    event.setMaxParticipants(resultSet.getInt("maxParticipants"));
                    event.setLocation(resultSet.getString("location"));
                    event.setEventType(resultSet.getString("eventType"));
                    event.setEventDescription(resultSet.getString("eventDescription"));
                    event.setVotingStatus(resultSet.getBoolean("votingEnabled"));
                    event.setCreatedAt(resultSet.getString("created_at"));
                    event.setUserUUID(resultSet.getString("userUUID"));
                    event.setEventUUID(resultSet.getString("eventUUID"));
                    events.add(event);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }
}
