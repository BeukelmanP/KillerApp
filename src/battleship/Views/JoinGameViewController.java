/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.Views;

import Classes.User;
import Server.Classes.JoinGames;
import interfaces.IJoin;
import interfaces.ILiveGame;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author piete
 */
public class JoinGameViewController implements Initializable {

    @FXML
    ListView gameList;
    Registry registry = null;
    IJoin joinInterface = null;
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
            joinInterface = (IJoin) registry.lookup("MainServer");
            if (joinInterface != null) {
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
        try {
            for (String g : joinInterface.getAvailableGames()) {
                gameList.getItems().add(g);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(JoinGameViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setUp(User loggedIn) {
        loggedInUser = loggedIn;
    }

    public void RefreshClick() {
        try {
            List<String> games = joinInterface.getAvailableGames();
            gameList.getItems().clear();
            for (String g : games) {
                gameList.getItems().add(g);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(JoinGameViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void infoBox(String infoMessage, String titleBar, String headerMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    public void ConnectClick() throws RemoteException, IOException {
        String game = (String) gameList.getSelectionModel().getSelectedItem();
        System.out.println("trying to connect to: " + game);
        ILiveGame liveGame = joinInterface.getGameServer(game);
        if (liveGame != null) {
            liveGame.Join(loggedInUser);
            System.out.println(liveGame.getGameInfo());
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/battleship/Views/GameView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            GameViewController controller = (GameViewController) fxmlLoader.getController();
            controller.setUpClient(liveGame);
            stage.setScene(new Scene(root1));
            stage.show();
        } else {
            infoBox("Game not available anymore", "Oops", "We're Sorry :(");
        }
    }

}
