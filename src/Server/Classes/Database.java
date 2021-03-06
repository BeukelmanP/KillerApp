/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Classes;

import Classes.User;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author piete
 */
public class Database {

    private java.sql.Connection myConn;
    private PreparedStatement pstmt;
    private ResultSet myRs;
    private String getUsers = "SELECT user.UserID , username , password , email , score, highestgamescore, gamesplayed from user , scores where scores.userid = user.userid order by user.userid";

    public boolean getConnection() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");
        myConn = DriverManager.getConnection("jdbc:mysql://localhost:3307/battleship", "root", "usbw");
        System.out.println("started connection to database...");
        return true;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        myRs = null;
        if (myConn != null) {
            try {
                pstmt = myConn.prepareStatement(getUsers);
                myRs = pstmt.executeQuery();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                while (myRs.next()) {
                    int userId = myRs.getInt("UserID");
                    String username = myRs.getString("username");
                    String password = myRs.getString("password");
                    String email = myRs.getString("email");
                    int score = myRs.getInt("score");
                    int highestgamescore = myRs.getInt("highestgamescore");
                    int gamesplayed = myRs.getInt("gamesplayed");
                    users.add(new User(userId, username, password, email, score, highestgamescore, gamesplayed));
                }
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            try {
                getConnection();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            users = getUsers();
        }
        return users;
    }

    public void insertUser(int UserID, String username, String password, String email) {
        String query = "INSERT INTO user VALUES (?,?,?,?);";
        String query2 = "INSERT INTO scores VALUES (?,0,0,0);";
        if (myConn != null) {
            try {
                pstmt = myConn.prepareStatement(query);
                pstmt.setInt(1, UserID);
                pstmt.setString(2, username);
                pstmt.setString(3, password);
                pstmt.setString(4, email);
                pstmt.execute();
                PreparedStatement pstmt2 = myConn.prepareStatement(query2);
                pstmt2.setInt(1, UserID);
                pstmt2.execute();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                this.getConnection();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            insertUser(UserID, username, password, email);
        }
    }

    public void updateScore(int userID, int totalScore, int highestScore, int gamesPlayed) {
        System.out.println("UPDATE SCORES DB");
        String query = "UPDATE scores SET Score = ?,GamesPlayed = ?, HighestGameScore=? WHERE UserID =?;";
        if (myConn != null) {
            try {
                pstmt = myConn.prepareStatement(query);
                pstmt.setInt(1, totalScore);
                pstmt.setInt(2, gamesPlayed);
                pstmt.setInt(3, highestScore);
                pstmt.setInt(4, userID);
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                this.getConnection();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateScore(userID, totalScore, gamesPlayed, highestScore);
        }
    }

    public boolean closeConnection() {
        try {
            if (myRs != null) {
                myRs.close();
            }
            if (myConn != null) {
                myConn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            System.out.println("Closing connection to database...");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
