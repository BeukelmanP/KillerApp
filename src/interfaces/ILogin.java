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
public interface ILogin extends Remote{

    /**
     * logging in in the system
     * if succesful login returns User else null
     * @param userName username of the user who wants to login
     * @param password password of the user who wants to login
     * @return The logged in User Class or if failed null
     */
    public User login (String userName, String password)throws RemoteException;

    /**
     * Logs current user out from server
     * @param userToLogout user to be logged out
     * @return true if succeeded false when error occured
     */
    public boolean logout(User userToLogout) throws RemoteException;

    /**
     *registers user into the system users can imedeately login after registering
     * @param username username of the user who wants to register
     * @param password password of the user who wants to register
     * @param email emailadress of the user who wants to register
     * @return true or false
     */
    public boolean register(String username, String password,String email)throws RemoteException;

    /**
     * get the user with the specified username
     * @param username username of the user to retrieve from the server
     * @return User or null
     */
    public User getUser(String username)throws RemoteException;
}
