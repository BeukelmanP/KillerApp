/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.Views;

import Classes.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setUp(User loggedIn) {
        loggedInUser = loggedIn;
    }

    public void openServer() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/battleship/Views/StartServerView.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        StartServerViewController controller = (StartServerViewController) fxmlLoader.getController();
        controller.setUp(loggedInUser);
        stage.setScene(new Scene(root1));
        stage.show();
    }

    public void openSearch() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/battleship/Views/JoinGameView.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        JoinGameViewController controller = (JoinGameViewController) fxmlLoader.getController();
        controller.setUp(loggedInUser);
        stage.setScene(new Scene(root1));
        stage.show();
    }

}
