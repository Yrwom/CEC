package org.app;
import java.sql.*;
public class AuthService {

    Connection connection;
    public static String responseCode;


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
    public static void setResponseCode(String code) {
        responseCode = code;
    }
    public static String getResponseCode() {
        return responseCode;
    }
    public boolean isValidPassword(String password){
        try {
            System.out.println("We are in isValidPassword");

            String whiteSpaceRegex = ".*\\s.*";
            String digitRegex = ".*\\d.*";
            String specialCharacterRegex = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*";
            if (password == null) {
                System.out.println("Null Test");
                AuthService.setResponseCode("Password is Null");
                return false;
            } else if(password.matches(whiteSpaceRegex)){
                System.out.println("Whitespace Test");
                System.out.println(password);
                AuthService.setResponseCode("Password has Whitespace");
                return false;
            }else if(!password.matches(digitRegex)) {
                System.out.println("contains digit");
                System.out.println(password);
                AuthService.setResponseCode("Password requires digits");
                return false;
            }else if (!password.matches(specialCharacterRegex)){
                System.out.println("Special Character test");
                System.out.println(password);
                AuthService.setResponseCode("Password requires at least 1 special character");
                return false;
            }else if (password.length() < 8){
                System.out.println("length check");
                System.out.println(password);
                AuthService.setResponseCode("Password must be 8 characters long");
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
                }else if(isValidPassword(password)){
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


