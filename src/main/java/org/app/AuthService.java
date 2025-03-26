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
    public boolean isValidPassword(String password){
        try {
            System.out.println("We are in isValidPassword");
            String whiteSpaceRegex = "(?=\\\\S+$)";
            String digitRegex = "(?=.*[0-9])";
            String specialCharacterRegex = "((?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$)";
            if (password == null) {
                return false;
            } else if(password.matches(whiteSpaceRegex)){
                return false;
            }else if(password.matches(digitRegex)) {
                return false;
            }else if (password.matches(specialCharacterRegex)){
                return false;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return true;
        }
    public boolean newUser(String username, String password, String role) {
        String query = "INSERT INTO users(username,password,role) values(?,?,?)";

        try (Connection connection = SqliteConnection.Connector()){

                if (connection == null) {
                    System.out.println("Failed to establish a database connection!");
                    return false;
                }else if(!isValidPassword(password)){
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

