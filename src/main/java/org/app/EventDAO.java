package org.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
private static LocalDate currentDate;

    //takes the current date passed to it and querys the local database for the event at that date.
    // this is used to assign the event to a given day and display it
    public static List<Event> fetchEventByDate(LocalDate currentDate) {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM events WHERE startDate = ? ORDER BY created_at ASC";

        try (Connection connection = LocalSqliteConnection.Connector();
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
                    event.setVotingStatus(Boolean.parseBoolean(resultSet.getString("votingEnabled")));
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

    //getters and setters
    public static void setCurrentDate(LocalDate date) {
        currentDate = date;
    }

    public static LocalDate getCurrentDate() {
        return currentDate;
    }

    //this fetches an event by its UUID which is given on event creation
    public static List<Event> fetchEventByUserUUID(String userUUID) {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM events WHERE userUUID = ? ORDER BY created_at ASC";

        try (Connection connection = LocalSqliteConnection.Connector();
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
                    event.setVotingStatus(Boolean.parseBoolean(resultSet.getString("votingEnabled")));
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
    //this uses the events UUID to search the local database and delete it from record
    public static boolean deleteEvent(Connection conn, String eventUUID){
        String query = "DELETE FROM events WHERE eventUUID = ?";
        try(PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1,eventUUID);
            return ps.executeUpdate() > 0;
        }catch (Exception e ){
            e.printStackTrace();
        }
        return false;
    }
}
