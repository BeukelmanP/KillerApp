/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author piete
 */
public class User implements Serializable {

    int userId;
    String username;
    private transient String password;
    String email;
    int totalScore;
    int highestScore;
    int gamesPlayed;
    int sessionID;

    public User(int userId, String username, String password, String email, int score, int highest, int played) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        totalScore = score;
        highestScore = highest;
        gamesPlayed = played;
    }

    public String getUsername() {
        return username;
    }

    public User checkPassword(String password) {
        if (this.password.equals(password)) {
            Random generator = new Random();
            int i = generator.nextInt(999);
            sessionID = i;
            return this;
        } else {
            return null;
        }
    }

    public boolean checkSessionId(int session) {
        if (this.sessionID == sessionID) {
            return true;
        } else {
            return false;
        }
    }

    public int getSessionId() {
        return sessionID;
    }

    public String getUserInfo() {
        return username + ";" + totalScore + ";" + highestScore + ";" + gamesPlayed;
    }

    public void updateScore(int score) {
        totalScore = totalScore + score;
        gamesPlayed++;
        if (score > highestScore) {
            highestScore = score;
        }
    }

    public int getUserId() {
        return userId;
    }

    public int getTotalScore() {
        return totalScore;
    }
}
