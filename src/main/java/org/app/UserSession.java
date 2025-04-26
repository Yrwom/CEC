package org.app;

import javafx.scene.Scene;
import javafx.stage.Window;

public class UserSession {

    //captures the current users information and staticly saves it for use through that users session

    private static User currentUser;
    private static boolean offlineMode = false;

    public static void setCurrentUser(User user){
        currentUser = user;
    }
    public static User getCurrentUser(){
        return currentUser;
    }
    public static String getUserUUID(){
        return currentUser != null ? currentUser.getUserUUID() : null;
    }
    public static String getFontFamily(){
        return currentUser != null ? currentUser.getFontFamily() : null;
    }
    public static String getFontColor(){
        return currentUser != null ? currentUser.getFontColor() : null;
    }


    public static void setOfflineStatus(boolean offlineStatus){offlineMode = offlineStatus;}
    public static boolean getOfflineStatus(){return offlineMode;}
}

