package org.app;

public class UserSession {
    private static User currentUser;

    public static void setCurrentUser(User user){
        currentUser = user;
    }
    public static User getCurrentUser(){
        return currentUser;
    }
    public static int getUserID(){
        return currentUser != null ? currentUser.getUserID() : -1;
    }
}

