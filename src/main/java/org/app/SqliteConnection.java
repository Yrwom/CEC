package org.app;
import java.sql.*;
public class SqliteConnection {
    public static Connection Connector(){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Capstone Java\\CEC\\src\\main\\resources\\Local.db");
            return conn;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }

    }
}
