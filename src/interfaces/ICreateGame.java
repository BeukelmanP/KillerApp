/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author piete
 */
public interface ICreateGame  extends Remote{

    /**
     * creates game on server, 
     * @param ipAdress ipadress of host
     * @param gameName gamename chosen by host
     * @param portNumber portnumber of rmiserver on host
     * @return true or false
     */
    public boolean createGame(String ipAdress, String gameName, int portNumber)throws RemoteException;

    /**
     * removes game from server
     * @param gameName gamename of game
     * @param ipAdress ipadress of host as extra check
     * @return true or false
     */
    public boolean startGame(String gameName, String ipAdress)throws RemoteException;
}
