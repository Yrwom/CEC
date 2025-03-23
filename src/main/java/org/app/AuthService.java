package org.app;
import java.sql.*;
public class AuthService {
    Connection connection;

    public AuthService() {
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

    public boolean isLogin(String username, String password) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM users where username = ? and password = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (Exception closeFail) {
                System.out.println("Close Failed!");
                closeFail.printStackTrace();
            }
        }

    }

    public boolean newUser(String username, String password, String role) {
        String query = "INSERT INTO users(username,password,role) values(?,?,?)";
        Connection connection = SqliteConnection.Connector();
        try {

                if (connection == null) {
                    System.out.println("Failed to establish a database connection!");
                    return false;
                }else {
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    System.out.println("We made it here");

                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    preparedStatement.setString(3, role);

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

