/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.Views;

import Classes.Game;
import Classes.User;
import Server.BattleShip_Server;
import Server.Server_MainController;
import fontyspublisher.RemotePublisher;
import interfaces.ICreateGame;
import interfaces.IJoin;
import interfaces.ILiveGame;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author piete
 */
public class StartServerViewController implements Initializable {

    @FXML
    TextField txtName;
    @FXML
    Label lblStatus;
    @FXML
    Pane paneCreate;

    Game liveGame;

    Registry registry = null;
    ICreateGame createInterface;
    InetAddress localhost;
    RemotePublisher gamePublisher;
    User loggedInUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            registry = LocateRegistry.getRegistry("192.168.31.1", 1099);
            System.out.println("registry Binded");
        } catch (RemoteException ex) {
            System.out.println("Cannot locate registry");
            System.out.println("RemoteException: " + ex.getMessage());
            registry = null;
            Logger.getLogger(JoinGameViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            createInterface = (ICreateGame) registry.lookup("MainServer");
            if (createInterface != null) {
                System.out.println("createInterface Found");
            } else {
                System.out.println("createInterface NOT Found");
            }
        } catch (RemoteException ex) {
            System.out.println("remoteExeption");
            Logger.getLogger(JoinGameViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            System.out.println("notBoundExeption");
            Logger.getLogger(JoinGameViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            localhost = InetAddress.getLocalHost();
            System.out.println("IP Address: " + localhost.getHostAddress());
        } catch (UnknownHostException ex) {
            Logger.getLogger(BattleShip_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setUp(User loggedIn) {
        loggedInUser = loggedIn;
    }

    public void registerGame() throws RemoteException {
        try {
            registry = LocateRegistry.createRegistry(1098);
            System.out.println("Registry created on port 1098");
        } catch (RemoteException ex) {
            Logger.getLogger(BattleShip_Server.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Registry creating failed");
        }
        gamePublisher = new RemotePublisher();
        gamePublisher.registerProperty("Turn");
        gamePublisher.registerProperty("Winner");
        liveGame = new Game(gamePublisher);
        liveGame.addServerUser(loggedInUser);
        liveGame.Name = txtName.getText();
        liveGame.ipAdress = localhost.getHostAddress();
        try {
            registry.rebind("gameServer", liveGame);
            System.out.println("liveGame Binded");
        } catch (AccessException ex) {
            System.out.println("liveGame NOT Binded");
            Logger.getLogger(Server_MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            registry.rebind("gamePublisher", gamePublisher);
            System.out.println("gamePublisher Binded");
        } catch (AccessException ex) {
            System.out.println("gamePublisher NOT Binded");
            Logger.getLogger(Server_MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        paneCreate.visibleProperty().set(false);
        lblStatus.setVisible(true);
        createInterface.createGame(localhost.getHostAddress(), txtName.getText(), 1098);
        Timer timer = new Timer();
        class PeriodiekeActie extends java.util.TimerTask {

            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (liveGame.clientUser != null) {
                            timer.cancel();
                            Stage stage = new Stage();
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/battleship/Views/GameView.fxml"));
                            Parent root1 = null;
                            try {
                                root1 = (Parent) fxmlLoader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(StartServerViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            GameViewController controller = (GameViewController) fxmlLoader.getController();
                            try {
                                controller.setUpServer(liveGame, loggedInUser);
                            } catch (RemoteException ex) {
                                Logger.getLogger(StartServerViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            stage.setScene(new Scene(root1));
                            stage.show();
                            Stage stage2 = (Stage) lblStatus.getScene().getWindow();
                            stage2.close();

                        } else if (lblStatus.getText() == "Waiting for opponent.") {
                            lblStatus.setText("Waiting for opponent..");
                        } else if (lblStatus.getText() == "Waiting for opponent..") {
                            lblStatus.setText("Waiting for opponent...");
                        } else if (lblStatus.getText() == "Waiting for opponent...") {
                            lblStatus.setText("Waiting for opponent....");
                        } else if (lblStatus.getText() == "Waiting for opponent....") {
                            lblStatus.setText("Waiting for opponent");
                        } else {
                            lblStatus.setText("Waiting for opponent.");
                        }
                    }
                });
            }
        }
        timer.scheduleAtFixedRate(new PeriodiekeActie(), 0, 500);

    }

    public void backbtnClick() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/battleship/Views/MainScreen.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        MainScreenController controller = (MainScreenController) fxmlLoader.getController();
        controller.setUp(loggedInUser);
        stage.setScene(new Scene(root1));
        stage.show();
        Stage stage2 = (Stage) lblStatus.getScene().getWindow();
        stage2.close();
    }

}
