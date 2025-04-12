package org.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Fetch users by username. Usually, usernames are unique so you may expect either an empty list or a list containing a single user.
    public static List<User> fetchUserByUsername(String username) {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = SqliteConnection.Connector();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setUserID(resultSet.getInt("userID"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(resultSet.getString("role"));
                    userList.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

    public static List<User> fetchUserByUserID(int userID) {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM users WHERE userID = ?";

        try (Connection connection = SqliteConnection.Connector();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setUserID(resultSet.getInt("userID"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(resultSet.getString("role"));
                    userList.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

}
