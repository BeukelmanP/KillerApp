/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author piete
 */
public interface IJoin extends Remote{

    /**
     * returns ipadress,portnumber and gamename to client
     * @param Gamename name of game which user would like to connect to
     * @return String
     */
    public ILiveGame getGameServer(String Gamename) throws RemoteException;

    /**
     * returns all names of games available
     * @return List<String>
     */
    public List<String> getAvailableGames() throws RemoteException;
}
