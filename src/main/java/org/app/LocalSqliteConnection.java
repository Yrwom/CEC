package org.app;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
public class LocalSqliteConnection {
    //Handles establishing connection to local database based off of user.home since this program is an executable and does not have a static path.
    public static Connection Connector(){
        try {
            Class.forName("org.sqlite.JDBC");

            String userHome = System.getProperty("user.home");
            Path dataDir    = Paths.get(userHome, ".cec");
            Files.createDirectories(dataDir);
            Path dbFile     = dataDir.resolve("Local.db");

            // 2) (optional) seed from your JAR on first run
            if (Files.notExists(dbFile)) {
                try (InputStream in = Main.class.getResourceAsStream("/templates/Local.db")) {
                    Files.copy(in, dbFile);
                }
            }

            // 3) open it
            String url = "jdbc:sqlite:" + dbFile.toString();
            Connection conn = DriverManager.getConnection(url);
            return conn;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }

    }
}
