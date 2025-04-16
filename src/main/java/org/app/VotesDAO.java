package org.app;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class VotesDAO {

    public static Votes fetchVotesByEventUUID(String eventUUID){
        Votes votes = new Votes();

        String yesQuery = "SELECT COUNT(*) AS yesCount FROM votes WHERE eventID = ? AND voteValue = 1";

        try (Connection connection = SqliteConnection.Connector();
            PreparedStatement prsmt = connection.prepareStatement(yesQuery)){

            prsmt.setString(1, eventUUID);
            try(ResultSet resultSet = prsmt.executeQuery()){
                if(resultSet.next()){
                    System.out.println(resultSet);
                    votes.SetVoteYesCount(resultSet.getInt("yesCount"));
                    System.out.println("Fetched Vote Count Yes" + resultSet.getInt("yesCount"));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        String noQuery = "SELECT COUNT(*) AS noCount FROM votes WHERE eventID = ? AND voteValue = 0";

        try (Connection connection = SqliteConnection.Connector();
             PreparedStatement prsmt = connection.prepareStatement(noQuery)){

            prsmt.setString(1, eventUUID);
            try(ResultSet resultSet = prsmt.executeQuery()){
                if(resultSet.next()){
                    votes.SetVoteNoCount(resultSet.getInt("noCount"));
                    System.out.println("Fetched Vote Count No" + resultSet.getInt("noCount"));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return votes;
    }
    public static boolean HasVotedCheck(String eventUUID, String userUUID){
        String query = "SELECT 1 FROM votes WHERE eventID = ? AND userID = ? LIMIT 1";
        try(Connection connection = SqliteConnection.Connector();
            PreparedStatement prstm = connection.prepareStatement(query)){
            prstm.setString(1,eventUUID);
            prstm.setString(2,userUUID);
            try(ResultSet resultSet = prstm.executeQuery()){
                return resultSet.next();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
