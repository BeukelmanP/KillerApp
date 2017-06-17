/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Classes;

import Classes.User;
import battleship.Views.JoinGameViewController;
import interfaces.ICreateGame;
import interfaces.IFinishGame;
import interfaces.IJoin;
import interfaces.ILiveGame;
import interfaces.ILogin;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author piete
 */
public class JoinGames extends UnicastRemoteObject implements IJoin, ICreateGame, ILogin, IFinishGame {

    private ArrayList<String> games;
    private ArrayList<User> users;
    private ArrayList<User> allowedUsers;
    private Database database;

    public JoinGames() throws RemoteException {
        games = new ArrayList<>();
        database = new Database();
        try {
            database.getConnection();
            users = database.getUsers();
        } catch (SQLException ex) {
            Logger.getLogger(JoinGames.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JoinGames.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ILiveGame getGameServer(String Game) throws RemoteException {
        String[] gameInfo = Game.split(";");
        Registry gameRegistry = null;
        ILiveGame game = null;
        if (games.contains(Game)) {
            games.remove(Game);
            try {
                gameRegistry = LocateRegistry.getRegistry(gameInfo[1], 1098);
                System.out.println("registry Binded");
            } catch (RemoteException ex) {
                System.out.println("Cannot locate registry");
                System.out.println("RemoteException: " + ex.getMessage());
                Logger.getLogger(JoinGameViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                game = (ILiveGame) gameRegistry.lookup("gameServer");
                if (game != null) {
                    System.out.println("JoinInterface Found");
                } else {
                    System.out.println("JoinInterface NOT Found");
                }
            } catch (RemoteException ex) {
                System.out.println("remoteExeption");
                Logger.getLogger(JoinGameViewController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                System.out.println("notBoundExeption");
                Logger.getLogger(JoinGameViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("game send");
            System.out.println(game.getGameInfo());
        }
        return game;
    }

    @Override
    public List<String> getAvailableGames() throws RemoteException {
        return games;
    }

    @Override
    public boolean createGame(String ipAdress, String gameName, int portNumber) throws RemoteException {
        String newgame = gameName + ";" + ipAdress + ";" + portNumber;
        games.add(newgame);
        System.out.println("game added!!!!!");
        return true;
    }

    @Override
    public boolean startGame(String gameName, String ipAdress) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User login(String userName, String password) {
        if (userName != null && password != null) {
            int index = -1;
            for (User u : users) {
                if (u.getUsername().equals(userName)) {
                    return u.checkPassword(password);
                }

            }
        }
        return null;
    }

    @Override
    public boolean logout(User userToLogout) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean register(String username, String password, String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getUser(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateScores(User user1, User user2, int score1, int score2) {
        for (User u : users) {
            if (u.getUserId() == user1.getUserId()) {
                u.updateScore(score1);
                database.updateScore(u.getUserId(), u.getTotalScore());
            }
            if (u.getUserId() == user2.getUserId()) {
                u.updateScore(score2);
                database.updateScore(u.getUserId(), u.getTotalScore());
            }
        }

    }

}
