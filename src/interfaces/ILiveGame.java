/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import Classes.Coordinate;
import Classes.Ship;
import Classes.User;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author piete
 */
public interface ILiveGame extends Remote {

    /**
     * returns info about game
     *
     * @return string containing game info
     */
    public String getGameInfo() throws RemoteException;

    /**
     *
     * @param reciever user stored on machine asking for the opponents info
     * @return String containing info of opponent
     */
    public String getOpponentInfo(int player) throws RemoteException;

    public void Join(User client) throws RemoteException;

    public String getHostIp() throws RemoteException;

    public int getTurn() throws RemoteException;

    public boolean fireShot(Coordinate C) throws RemoteException;

    public boolean checkShot(Coordinate C) throws RemoteException;

    public void addShips(Ship[] ships, int player) throws RemoteException;

    public Ship[] getMyShips(int player) throws RemoteException;

    public void addServerUser(User serverUser) throws RemoteException;

    public Coordinate getLastTurn() throws RemoteException;
}
