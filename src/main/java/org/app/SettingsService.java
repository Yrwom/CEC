package org.app;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SettingsService {
    public static boolean updateUserPrefrences(String userUUID, String fontColor, String fontFamily){

        String query = "UPDATE users SET " +
                " font_style = COALESCE(?, font_style)," +
                " font_color = COALESCE(?, font_color) " +
                "WHERE userUUID = ?;";
        try(Connection connection = LocalSqliteConnection.Connector();
            PreparedStatement ps = connection.prepareStatement(query)){

            ps.setString(1, fontFamily);
            ps.setString(2, fontColor);
            ps.setString(3, userUUID);

            int rowsUpdated = ps.executeUpdate();
            if(rowsUpdated > 0){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
