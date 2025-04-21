package org.app;
import java.sql.*;
import java.util.List;
import java.util.Objects;

public class AuthService {
    //create variables
    Connection connection;
    public static String responseCode;

    //double check connection is stable, if not, close app
    public AuthService() {
        connection = LocalSqliteConnection.Connector();
        if (connection == null)

            System.exit(1);
    }
    //method to check if the db is connected, see AuthPanel
    public boolean isDbConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //method to check if the given username and password are present in the local database
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
    //setter and getters for the response code for various errors
    public static void setResponseCode(String code) {
        responseCode = code;
    }

    public static String getResponseCode() {
        return responseCode;
    }

    //method for check in a password is valid.
    //A valid password will not be null, contain white space, have 8 characters, contain special characters, and contain a digit.
    //if any of the above are flagged, a popup is given to the user with the error
    public boolean isValidPassword(String password){
        try {
            System.out.println("We are in isValidPassword");
            System.out.println(password);
            //Regex's that check for whitespace, digits, and special characters.
            //I chose to allow any and all special characters for fun as it won't impact the app
            String whiteSpaceRegex = ".*\\s.*";
            String digitRegex = ".*\\d.*";
            String specialCharacterRegex = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*";
            if (Objects.equals(password,"")) {
                System.out.println("Null Test");
                AuthService.setResponseCode("Password is Empty!");
                return false;
            }else if(password.matches(whiteSpaceRegex)){
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
        //method for checking a given username is valid
        //A username is valid by not being null and not already existing in the database
        public boolean isValidUsername(String username){
        try{
            if(Objects.equals(username,"")){
                AuthService.setResponseCode("Username must not be blank!");
                return false;
            }
            List<User> userList = UserDAO.fetchUserByUsername(username);
            if(!userList.isEmpty()){
                AuthService.setResponseCode("Username already exists, please choose another!");
                return false;
            }else{
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
        }

        //Pretty simple here, a role is valid if it is not left blank in the combo box
        public boolean isValidRole(String role){
        try{
            if (role == null) {
                System.out.println("Null Test");
                AuthService.setResponseCode("Please Select a Role!");
                return false;
            }
            }catch(Exception e){
            e.printStackTrace();
        }
        return true;
        }

        //This method takes the username, password, and role of a new user and inserts it into the local database
        //upon completion the user will receive a message that the user has been created successfully.
        //if there is an error, it returns false and an error is given by AuthPanel
    public boolean newUser(String username, String password, String role, String userUUID) {
        String query = "INSERT INTO users(userUUID,username,password,role) values(?,?,?,?)";

        try (Connection connection = LocalSqliteConnection.Connector()){

                if (connection == null) {
                    System.out.println("Failed to establish a database connection!");
                    return false;
                }else if(isValidPassword(password) && isValidUsername(username) && isValidRole(role)){
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    System.out.println("We made it here");
                    preparedStatement.setString(1, userUUID);
                    preparedStatement.setString(2, username);
                    preparedStatement.setString(3, password);
                    preparedStatement.setString(4, role);

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

        //this method handles updating the local database with the new password given in ForgotPasswordPanel
        //it uses the users UUID which is created automatically when a new user is created. See UserDAO for more
    public boolean ForgotPassword(String userUUID, String newPassword){
            String query =  "UPDATE users SET " +
                            "password = ? " +
                            "WHERE userUUID = ?;";

            try(Connection connection = LocalSqliteConnection.Connector()){
                if (connection == null) {
                    System.out.println("Failed to establish a database connection!");
                    return false;
                }else if(isValidPassword(newPassword)){
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setString(1,newPassword);
                    ps.setString(2,userUUID);

                    int rowUpdated = ps.executeUpdate();
                    if(rowUpdated > 0){
                        setResponseCode("Password Updated Sec");
                        return true;
                    } else{
                        return false;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return false;
        }
        //this method checks whether the newpassword matches itself as there is a ConfirmPassword method in the ForgotPassword Panel.
    public boolean ConfirmNewPasswordMatch(String newpassword,String confirmedPassword){
        if(Objects.equals(newpassword, confirmedPassword)){
            return true;
        }else {
            setResponseCode("Passwords do not match, please check.");
            return false;
        }
    }


}


