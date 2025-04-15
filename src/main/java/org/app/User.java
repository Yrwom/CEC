package org.app;

public class User {
private  String userUUID;
private  String username;
private  String password;
private  String role;
    public User(){



    }
    // Instance setter for userID
    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    // Instance setter for username (expects a String)
    public void setUsername(String username) {
        this.username = username;
    }

    // Instance setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    // Instance setter for role
    public void setRole(String role) {
        this.role = role;
    }

    // Instance getter for userID
    public String getUserUUID() {
        return this.userUUID;
    }

    // Instance getter for username
    public String getUsername() {
        return this.username;
    }

    // Instance getter for password
    public String getPassword() {
        return this.password;
    }

    // Instance getter for role
    public String getRole() {
        return this.role;
    }


}
