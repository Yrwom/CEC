package org.app;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;

public class CentralSqliteConnection {
    //This method is used to establish the connection to the central database.
    // It is also configured to get the path needed to be accessed when launched from an EXE
    public static Connection Connector(){
        try {
            Class.forName("org.sqlite.JDBC");
            //userHome refers to the users AppData folder typically and this method fetchs that path
            String userHome = System.getProperty("user.home");
            //this returns the path to the .cec suffix for the user
            Path dataDir    = Paths.get(userHome, ".cec");
            //this creates the directory to contain the DBs
            Files.createDirectories(dataDir);
            //This sets the files name the program should look for when attempting to open the DB
            Path dbFile     = dataDir.resolve("Central.db");

            //if the DBs dont exist on the users machine for whatever reason, this block will search the templates folder and create the db using the Central.db as a template
            //This will create all tables and schemas that are needed for the app to work
            if (Files.notExists(dbFile)) {
                try (InputStream in = Main.class.getResourceAsStream("/templates/Central.db")) {
                    Files.copy(in, dbFile);
                }
            }
            //this sets the path to connect to the database by concatenating all the variables listed before
            String url = "jdbc:sqlite:" + dbFile.toString();
            Connection conn = DriverManager.getConnection(url);
            return conn;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }

    }
}
