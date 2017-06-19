/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import battleship.Views.JoinGameViewController;
import fontyspublisher.RemotePublisher;
import interfaces.IFinishGame;
import interfaces.ILiveGame;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author piete
 */
public class Game extends UnicastRemoteObject implements ILiveGame {

    public String Name;
    public User clientUser;
    public User serverUser;
    public Ship[] clientShips = new Ship[3];
    public Ship[] serverShips = new Ship[3];
    public String ipAdress;
    //0=server,  1=client
    public int turn = 10;
    //3=server   4=client
    public int winner;
    public int totalTurns = 0;
    RemotePublisher gamePublisher;
    public Coordinate lastTurn;

    public Game(RemotePublisher publisher) throws RemoteException {
        gamePublisher = publisher;
//        Coordinate[] coordinates = new Coordinate[3];
//        coordinates[0] = new Coordinate(1, 2);
//        coordinates[1] = new Coordinate(2, 2);
//        coordinates[2] = new Coordinate(3, 2);
//        clientShips[0] = new Ship(coordinates, "medium");
//        Coordinate[] coordinates2 = new Coordinate[3];
//        coordinates2[0] = new Coordinate(2, 4);
//        coordinates2[1] = new Coordinate(3, 4);
//        coordinates2[2] = new Coordinate(4, 4);
//        serverShips[0] = new Ship(coordinates2, "medium");
    }

    public void setGameName(String name) {
        this.Name = name;
    }

    @Override
    public String getGameInfo() throws RemoteException {
        return Name;
    }

    @Override
    public String getOpponentInfo(int player) throws RemoteException {
        if (player == 0) {
            return clientUser.getUserInfo();
        } else if (player == 1) {
            return serverUser.getUserInfo();
        } else {
            return null;
        }
    }

    @Override
    public void Join(User client) throws RemoteException {
        this.clientUser = client;
    }

    public void changeTurn() throws RemoteException {
        if (turn == 1) {
            turn = 0;
            gamePublisher.inform("Turn", 1, 0);
        } else if (turn == 0) {
            turn = 1;
            gamePublisher.inform("Turn", 0, 1);
        } else if (turn == 10) {
            turn = 11;
            gamePublisher.inform("Turn", 0, 11);
        } else if (turn == 11) {
            turn = 0;
            gamePublisher.inform("Turn", 0, 0);
        }
    }

    @Override
    public String getHostIp() throws RemoteException {
        return ipAdress;
    }

    @Override
    public int getTurn() throws RemoteException {
        return turn;
    }

    public boolean fireShot(Coordinate C) throws RemoteException {
        boolean hit = false;
        if (turn == 0) {
            for (Ship s : clientShips) {
                if (s != null && hit == false) {
                    hit = s.isHit(C);
                }
            }
        } else if (turn == 1) {
            for (Ship s : serverShips) {
                if (s != null && hit == false) {
                    hit = s.isHit(C);
                }
            }
        }
        if (hit) {
            lastTurn = C;
        } else {
            lastTurn = null;
        }
        CheckWinner();
        changeTurn();
        totalTurns++;
        return hit;
    }

    public boolean checkShot(Coordinate C) throws RemoteException {
        boolean alreadyHit = false;
        if (turn == 0) {
            for (Ship s : clientShips) {
                if (s != null && alreadyHit == false) {
                    alreadyHit = s.checkAlreadyShot(C);
                }
            }
        } else if (turn == 1) {
            for (Ship s : serverShips) {
                if (s != null && alreadyHit == false) {
                    alreadyHit = s.checkAlreadyShot(C);
                }
            }
        }
        return alreadyHit;
    }

    public void CheckWinner() throws RemoteException {
        boolean PlayerServerWin = true;
        boolean PlayerClientWin = true;
        for (Ship s : clientShips) {
            if (s != null) {
                if (!s.getIsSunk()) {
                    PlayerServerWin = false;
                }
            }
        }
        for (Ship s : serverShips) {
            if (s != null) {
                if (!s.getIsSunk()) {
                    PlayerClientWin = false;
                }
            }
        }
        if (PlayerServerWin) {
            sendScores(0);
            gamePublisher.inform("Winner", 0, 3);
        }

        if (PlayerClientWin) {
            sendScores(1);
            gamePublisher.inform("Winner", 0, 4);
        }
    }

    public void sendScores(int winner) throws RemoteException {
        Registry registry = null;
        IFinishGame finishInterface = null;
        try {
            registry = LocateRegistry.getRegistry("192.168.192.37", 1099);
            System.out.println("registry Binded");
        } catch (RemoteException ex) {
            System.out.println("Cannot locate registry");
            System.out.println("RemoteException: " + ex.getMessage());
            registry = null;
            Logger.getLogger(JoinGameViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            finishInterface = (IFinishGame) registry.lookup("MainServer");
            if (finishInterface != null) {
                System.out.println("finishInterface Found");
            } else {
                System.out.println("finishInterface NOT Found");
            }
        } catch (RemoteException ex) {
            System.out.println("remoteExeption");
            Logger.getLogger(JoinGameViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            System.out.println("notBoundExeption");
            Logger.getLogger(JoinGameViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        int score1;
        int score2;
        if (winner == 0) {
            score1 = 500 - (5 * totalTurns);
            score2 = 500 - (5 * totalTurns);
        } else {
            score2 = 500 - (5 * totalTurns);
            score1 = 500 - (5 * totalTurns);
        }
        finishInterface.updateScores(serverUser, clientUser, score1, score2);
    }

    public void addShips(Ship[] ships, int player) throws RemoteException {
        if (player == 0) {
            changeTurn();
            serverShips = ships;
            gamePublisher.inform("Turn", 0, 11);
        } else if (player == 1) {
            changeTurn();
            clientShips = ships;
            gamePublisher.inform("Turn", 0, 0);
        }
    }

    @Override
    public Ship[] getMyShips(int player) throws RemoteException {
        if (player == 0) {
            return serverShips;
        } else if (player == 1) {
            return clientShips;
        } else {
            return null;
        }
    }

    @Override
    public void addServerUser(User serverUser) {
        this.serverUser = serverUser;
    }

    public Coordinate getLastTurn() throws RemoteException {
        return lastTurn;
    }

}
