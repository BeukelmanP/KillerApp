/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Server.Classes.JoinGames;
import battleship.Views.GameViewController;
import battleship.Views.StartServerViewController;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author piete
 */
public class Server_MainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    ListView gameList;
    @FXML
    ProgressIndicator statusIndicator;
    boolean running = false;
    int progress = 5;
    private Registry registry = null;
    JoinGames joinGames;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            joinGames = new JoinGames();
        } catch (RemoteException ex) {
            Logger.getLogger(Server_MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Timer timer = new Timer();
        class PeriodiekeActie extends java.util.TimerTask {

            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        gameList.getItems().clear();
                        try {
                            for (String game : joinGames.getAvailableGames()) {
                                gameList.getItems().add(game);
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(Server_MainController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (running) {
                            if (progress < 90) {
                                progress = progress + 1;
                            } else {
                                progress = 5;
                            }
                            statusIndicator.setProgress(progress/100);
                        }
                    }
                });
            }
        }
        timer.scheduleAtFixedRate(new PeriodiekeActie(), 0, 200);
    }

    public void startServerClick() throws RemoteException {
        try {
            registry = LocateRegistry.createRegistry(1099);
            System.out.println("Registry created on port 1099");
        } catch (RemoteException ex) {
            Logger.getLogger(BattleShip_Server.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Registry creating failed");
        }
        joinGames = new JoinGames();
        try {
            registry.rebind("MainServer", joinGames);
            System.out.println("JoinGames Binded");
        } catch (AccessException ex) {
            System.out.println("JoinGames NOT Binded");
            Logger.getLogger(Server_MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        InetAddress localhost;
        try {
            localhost = InetAddress.getLocalHost();
            System.out.println("IP Address: " + localhost.getHostAddress());
        } catch (UnknownHostException ex) {
            Logger.getLogger(BattleShip_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        running = true;
    }

    public void stopServerClick() {

    }

    public void refreshClick() throws RemoteException {
        for (String g : joinGames.getAvailableGames()) {
            gameList.getItems().add(g);
        }
    }

}
