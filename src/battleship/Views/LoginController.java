/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.Views;

import Classes.User;
import interfaces.IJoin;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author piete
 */
public class LoginController implements Initializable {

    @FXML
    TextField txtUsername;
    @FXML
    PasswordField txtPassword;

    Registry registry = null;
    ILogin loginInterface = null;

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

    public void logInClick() throws IOException {
        User u = loginInterface.login(txtUsername.getText(), txtPassword.getText());
        if (u != null) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/battleship/Views/MainScreen.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            MainScreenController controller = (MainScreenController) fxmlLoader.getController();
            controller.setUp(u);
            stage.setScene(new Scene(root1));
            stage.show();
            Stage stage2 = (Stage) txtUsername.getScene().getWindow();
            stage2.close();
        } else {
            JOptionPane.showMessageDialog(null, "Your username or password are not correct", "sorry", JOptionPane.INFORMATION_MESSAGE);

        }
    }

}
