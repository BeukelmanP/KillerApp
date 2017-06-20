/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.Views;

import Classes.User;
import interfaces.ILogin;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author piete
 */
public class MainScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    User loggedInUser;

    @FXML
    Button startServerBtn;

    Registry registry = null;
    ILogin loginInterface = null;

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
            loginInterface = (ILogin) registry.lookup("MainServer");
            if (loginInterface != null) {
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
    }

    public void setUp(User loggedIn) {
        loggedInUser = loggedIn;
    }

    public void openServer() throws IOException {
        if (loginInterface.checkAuthorized(loggedInUser.getSessionId(),loggedInUser.getUsername())) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/battleship/Views/StartServerView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            StartServerViewController controller = (StartServerViewController) fxmlLoader.getController();
            controller.setUp(loggedInUser);
            stage.setScene(new Scene(root1));
            stage.show();
            Stage stage2 = (Stage) startServerBtn.getScene().getWindow();
            stage2.close();
        } else {
            JOptionPane.showMessageDialog(null, "You're not authorized please login again", "sorry", JOptionPane.INFORMATION_MESSAGE);
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/battleship/Views/Login.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage.setScene(new Scene(root1));
            stage.show();
            Stage stage2 = (Stage) startServerBtn.getScene().getWindow();
            stage2.close();
        }
    }

    public void openSearch() throws IOException {
        if (loginInterface.checkAuthorized(loggedInUser.getSessionId(),loggedInUser.getUsername())) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/battleship/Views/JoinGameView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            JoinGameViewController controller = (JoinGameViewController) fxmlLoader.getController();
            controller.setUp(loggedInUser);
            stage.setScene(new Scene(root1));
            stage.show();
            Stage stage2 = (Stage) startServerBtn.getScene().getWindow();
            stage2.close();
        } else {
            JOptionPane.showMessageDialog(null, "You're not authorized please login again", "sorry", JOptionPane.INFORMATION_MESSAGE);
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/battleship/Views/Login.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage.setScene(new Scene(root1));
            stage.show();
            Stage stage2 = (Stage) startServerBtn.getScene().getWindow();
            stage2.close();
        }
    }

}
