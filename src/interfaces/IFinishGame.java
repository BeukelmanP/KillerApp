/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import Classes.User;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author piete
 */
public interface IFinishGame extends Remote{

    /**
     * update scores in the server after finishing the game
     * @param user1 server user 
     * @param user2 client user
     * @param score1 score of server user
     * @param score2 score of client user
     * @return true or false
     */
    public void updateScores(User user1, User user2, int score1, int score2) throws RemoteException ;
}
