package org.app;

public class UserSession {
    private static User currentUser;

    public static void setCurrentUser(User user){
        currentUser = user;
    }
    public static User getCurrentUser(){
        return currentUser;
    }
    public static String getUserUUID(){
        return currentUser != null ? currentUser.getUserUUID() : null;
    }
}

