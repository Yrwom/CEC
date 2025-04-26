package org.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    //fetches a specific user by their username as they are unique
    public static List<User> fetchUserByUsername(String username) {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = LocalSqliteConnection.Connector();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setUserUUID(resultSet.getString("userUUID"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(resultSet.getString("role"));
                    user.setFontColor(resultSet.getString("font_color"));
                    user.setFontFamily(resultSet.getString("font_style"));
                    userList.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }
    //fetches user by UUID typicall for events as they are stored on creation
    public static List<User> fetchUserByUserID(String userUUID) {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM users WHERE userUUID = ?";

        try (Connection connection = LocalSqliteConnection.Connector();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userUUID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setUserUUID(resultSet.getString("userUUID"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(resultSet.getString("role"));
                    user.setFontColor(resultSet.getString("font_color"));
                    user.setFontFamily(resultSet.getString("font_style"));
                    userList.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

}
