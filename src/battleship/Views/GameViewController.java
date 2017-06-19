/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.Views;

import Classes.Coordinate;
import Classes.Game;
import Classes.Ship;
import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForListener;
import interfaces.IFinishGame;
import interfaces.IJoin;
import interfaces.ILiveGame;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author piete
 */
public class GameViewController extends UnicastRemoteObject implements IRemotePropertyListener, Initializable {

    @FXML
    Label lblGameName;
    @FXML
    Pane boardPane;
    @FXML
    Pane panePlacement;
    @FXML
    Pane paneShoot;
    @FXML
    Pane myShipsPane;
    @FXML
    TextField txtFireX;
    @FXML
    TextField txtFireY;
    @FXML
    TextField txtShip1X;
    @FXML
    TextField txtShip1Y;
    @FXML
    TextField txtShip2X;
    @FXML
    TextField txtShip2Y;
    @FXML
    TextField txtShip3X;
    @FXML
    TextField txtShip3Y;
    @FXML
    ChoiceBox HVShip1;
    @FXML
    ChoiceBox HVShip2;
    @FXML
    ChoiceBox HVShip3;
    @FXML
    Button btnFire;
    @FXML
    Label lblOpponentName;
    @FXML
    Label lblTotalScore;
    @FXML
    Label lblHighestScore;
    @FXML
    Label lblGamesPlayed;

    ILiveGame game;
    //0=server,  1=client
    int player;
    IRemotePublisherForListener gamePublisher;
    Button[][] boardBtns;
    Button[][] MyshipBtns;

    AudioClip explosionSF;
    AudioClip blubSF;
    AudioClip hitSF;
    AudioClip missedSF;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        blubSF = new AudioClip(this.getClass().getResource("/Sounds/blub.wav").toExternalForm());
        explosionSF = new AudioClip(this.getClass().getResource("/Sounds/explosion.wav").toExternalForm());
        hitSF = new AudioClip(this.getClass().getResource("/Sounds/hit.mp3").toExternalForm());
        missedSF = new AudioClip(this.getClass().getResource("/Sounds/missed.mp3").toExternalForm());

        int columns = 5;
        int rows = 5;

        boardBtns = new Button[columns][rows];
        for (int c = 0; c < columns; c++) {
            for (int r = 0; r < rows; r++) {
                boardBtns[c][r] = new Button();
                boardBtns[c][r].setPrefSize(95, 95);
                boardBtns[c][r].setLayoutY(r * 100);
                boardBtns[c][r].setLayoutX(c * 100);
                if (c % 2 == 1 & r % 2 == 1) {
                    boardBtns[c][r].setStyle("-fx-color: #80e5ff");
                } else if (c % 2 == 0 & r % 2 == 1) {
                    boardBtns[c][r].setStyle("-fx-color: #00ccff");
                } else if (c % 2 == 1 & r % 2 == 0) {
                    boardBtns[c][r].setStyle("-fx-color: #00b8e6");
                } else {
                    boardBtns[c][r].setStyle("-fx-color: #b3f0ff");
                }
                boardPane.getChildren().add(boardBtns[c][r]);

            }
        }
        int shipcolumns = 5;
        int shipRows = 5;
        MyshipBtns = new Button[shipcolumns][shipRows];
        for (int c = 0; c < columns; c++) {
            for (int r = 0; r < rows; r++) {
                MyshipBtns[c][r] = new Button();
                MyshipBtns[c][r].setPrefSize(33, 33);
                MyshipBtns[c][r].setLayoutY(r * 35);
                MyshipBtns[c][r].setLayoutX(c * 35);
                MyshipBtns[c][r].setStyle("-fx-color: #80e5ff");
                myShipsPane.getChildren().add(MyshipBtns[c][r]);
            }
        }
    }

    public GameViewController() throws RemoteException {
        super();
    }

    public void setUpClient(ILiveGame game) throws RemoteException {
        this.game = game;
        player = 1;
        setUpMain();
    }

    public void setUpServer(Game game) throws RemoteException {
        this.game = game;
        player = 0;
        setUpMain();

    }

    public void setUpMain() throws RemoteException {
        paneShoot.setVisible(false);
        panePlacement.setVisible(false);
        changeTurn(game.getTurn());
        lblGameName.setText(game.getGameInfo());

        Registry registry = LocateRegistry.getRegistry(game.getHostIp(), 1098);
        try {
            gamePublisher = (IRemotePublisherForListener) registry.lookup("gamePublisher");
        } catch (NotBoundException ex) {
            Logger.getLogger(GameViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessException ex) {
            Logger.getLogger(GameViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        gamePublisher.subscribeRemoteListener(this, "Turn");
        gamePublisher.subscribeRemoteListener(this, "Winner");
        HVShip1.getItems().clear();
        HVShip2.getItems().clear();
        HVShip3.getItems().clear();
        HVShip1.getItems().add("Horizontal");
        HVShip2.getItems().add("Horizontal");
        HVShip3.getItems().add("Horizontal");
        HVShip1.getItems().add("Vertical");
        HVShip2.getItems().add("Vertical");
        HVShip3.getItems().add("Vertical");
    }

    public void changeTurnTest() {
        try {
            game.getGameInfo();
        } catch (RemoteException ex) {
            Logger.getLogger(GameViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void btnFireCLick() throws RemoteException, UnsupportedAudioFileException {
        Coordinate C = new Coordinate(Integer.parseInt(txtFireX.getText()) - 1, Integer.parseInt(txtFireY.getText()) - 1);
        if (!game.checkShot(C)) {
            boolean hit = game.fireShot(C);
            if (hit) {
                boardBtns[C.getX()][C.getY()].setStyle("-fx-color: red");
                explosionSF.play();
            } else {
                blubSF.play();
            }
        } else {
            infoBox("You''ve already hit this coordinate", "Try Again", "Oops!");
        }
    }

    void playSound(String soundFile) throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
//    File f = new File("/"+soundFile);
//    AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  
//    Clip clip = AudioSystem.getClip();
//    clip.open(audioIn);
//    clip.start();
    }

    public static void infoBox(String infoMessage, String titleBar, String headerMessage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    public void changeTurn(int turn) throws RemoteException {
        if (turn == player) {
            txtFireX.setText("");
            txtFireY.setText("");
            txtFireX.setEditable(true);
            txtFireY.setEditable(true);
            btnFire.setDisable(false);
            System.out.println("Turn Changed: My Turn");
            System.out.println("im player" + player);
            panePlacement.setVisible(false);
            paneShoot.setVisible(true);
            Coordinate hit = game.getLastTurn();
            if (hit != null) {
                MyshipBtns[hit.getX()][hit.getY()].setStyle("-fx-color: red");
                hitSF.play();
            } else {
                missedSF.play();
            }
        } else {
            txtFireX.setText("");
            txtFireY.setText("");
            txtFireX.setEditable(false);
            txtFireY.setEditable(false);
            btnFire.setDisable(true);
            System.out.println("Turn Changed: Opponents Turn");
            System.out.println("im player" + player);
            panePlacement.setVisible(false);
            paneShoot.setVisible(true);
        }
        if (player + 10 == turn) {
            panePlacement.setVisible(true);
            paneShoot.setVisible(false);
        }

    }

    public void placeShipsClick() throws RemoteException {
        Ship[] ships = new Ship[3];
        boolean ship1Ok = false;
        boolean ship2Ok = false;
        boolean ship3Ok = false;
        if (HVShip1.getSelectionModel().getSelectedIndex() == 0) {
            if (Integer.parseInt(txtShip1X.getText()) < 5 && Integer.parseInt(txtShip1Y.getText()) < 6) {
                ship1Ok = true;
            }
        } else if (HVShip1.getSelectionModel().getSelectedIndex() == 1) {
            if (Integer.parseInt(txtShip1X.getText()) < 6 && Integer.parseInt(txtShip1Y.getText()) < 5) {
                ship1Ok = true;
            }
        }

        if (HVShip2.getSelectionModel().getSelectedIndex() == 0) {
            if (Integer.parseInt(txtShip2X.getText()) < 4 && Integer.parseInt(txtShip2Y.getText()) < 6) {
                ship2Ok = true;
            }
        } else if (HVShip2.getSelectionModel().getSelectedIndex() == 1) {
            if (Integer.parseInt(txtShip2X.getText()) < 6 && Integer.parseInt(txtShip2Y.getText()) < 4) {
                ship2Ok = true;
            }
        }

        if (HVShip3.getSelectionModel().getSelectedIndex() == 0) {
            if (Integer.parseInt(txtShip3X.getText()) < 3 && Integer.parseInt(txtShip3Y.getText()) < 6) {
                ship3Ok = true;
            }
        } else if (HVShip3.getSelectionModel().getSelectedIndex() == 1) {
            if (Integer.parseInt(txtShip3X.getText()) < 6 && Integer.parseInt(txtShip3Y.getText()) < 3) {
                ship3Ok = true;
            }
        }

        if (ship1Ok && ship2Ok && ship3Ok) {
            Coordinate[] ship1 = new Coordinate[2];
            ship1[0] = new Coordinate(Integer.parseInt(txtShip1X.getText()) - 1, Integer.parseInt(txtShip1Y.getText()) - 1);
            if (HVShip1.getSelectionModel().getSelectedIndex() == 0) {
                ship1[1] = new Coordinate(Integer.parseInt(txtShip1X.getText()), Integer.parseInt(txtShip1Y.getText()) - 1);
            } else if (HVShip1.getSelectionModel().getSelectedIndex() == 1) {
                ship1[1] = new Coordinate(Integer.parseInt(txtShip1X.getText()) - 1, Integer.parseInt(txtShip1Y.getText()));
            }
            Coordinate[] ship2 = new Coordinate[3];
            ship2[0] = new Coordinate(Integer.parseInt(txtShip2X.getText()) - 1, Integer.parseInt(txtShip2Y.getText()) - 1);
            if (HVShip2.getSelectionModel().getSelectedIndex() == 0) {
                ship2[1] = new Coordinate(Integer.parseInt(txtShip2X.getText()), Integer.parseInt(txtShip2Y.getText()) - 1);
                ship2[2] = new Coordinate(Integer.parseInt(txtShip2X.getText()) + 1, Integer.parseInt(txtShip2Y.getText()) - 1);
            } else if (HVShip2.getSelectionModel().getSelectedIndex() == 1) {
                ship2[1] = new Coordinate(Integer.parseInt(txtShip2X.getText()) - 1, Integer.parseInt(txtShip2Y.getText()));
                ship2[2] = new Coordinate(Integer.parseInt(txtShip2X.getText()) - 1, Integer.parseInt(txtShip2Y.getText()) + 1);
            }
            Coordinate[] ship3 = new Coordinate[4];
            ship3[0] = new Coordinate(Integer.parseInt(txtShip3X.getText()) - 1, Integer.parseInt(txtShip3Y.getText()) - 1);
            if (HVShip3.getSelectionModel().getSelectedIndex() == 0) {
                ship3[1] = new Coordinate(Integer.parseInt(txtShip3X.getText()), Integer.parseInt(txtShip3Y.getText()) - 1);
                ship3[2] = new Coordinate(Integer.parseInt(txtShip3X.getText()) + 1, Integer.parseInt(txtShip3Y.getText()) - 1);
                ship3[3] = new Coordinate(Integer.parseInt(txtShip3X.getText()) + 2, Integer.parseInt(txtShip3Y.getText()) - 1);
            } else if (HVShip3.getSelectionModel().getSelectedIndex() == 1) {
                ship3[1] = new Coordinate(Integer.parseInt(txtShip3X.getText()) - 1, Integer.parseInt(txtShip3Y.getText()));
                ship3[2] = new Coordinate(Integer.parseInt(txtShip3X.getText()) - 1, Integer.parseInt(txtShip3Y.getText()) + 1);
                ship3[3] = new Coordinate(Integer.parseInt(txtShip3X.getText()) - 1, Integer.parseInt(txtShip3Y.getText()) + 2);
            }
            ships[0] = new Ship(ship1, "small");
            ships[1] = new Ship(ship2, "medium");
            ships[2] = new Ship(ship3, "large");
            boolean free1 = ships[0].checkFree(ships[1].getCoordinates());
            boolean free2 = ships[1].checkFree(ships[2].getCoordinates());
            boolean free3 = ships[0].checkFree(ships[2].getCoordinates());
            if (free1 && free2 && free3) {
                game.addShips(ships, player);
                for (Ship s : game.getMyShips(player)) {
                    for (Coordinate c : s.getCoordinates()) {
                        MyshipBtns[c.getX()][c.getY()].setStyle("-fx-color: black");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Your ships cross each other", "sorry", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Your ships are not placed on the board", "sorry", JOptionPane.INFORMATION_MESSAGE);
        }
        String[] info = game.getOpponentInfo(player).split(";");
        lblOpponentName.setText("UserName: " + info[0]);
        lblTotalScore.setText("Total score: " + info[1]);
        lblHighestScore.setText("Highest Score: " + info[2]);
        lblGamesPlayed.setText("Played Games: " + info[3]);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        int Old = (int) evt.getOldValue();
        int New = (int) evt.getNewValue();
        System.out.println(Integer.toString(New));
        if (New != 3 && New != 4) {
            System.out.println("old: " + Old + " new: " + New);
            changeTurn(New);
        } else {
            int playerwin = player + 3;
            System.out.println(Integer.toString(playerwin) + " : " + player);
            if (New == playerwin) {
                System.out.println("won");
                JOptionPane.showMessageDialog(null, "You''ve won the war", "Won", JOptionPane.INFORMATION_MESSAGE);
                Stage stage2 = (Stage) lblOpponentName.getScene().getWindow();
                stage2.close();
            } else {
                System.out.println("lost");
                JOptionPane.showMessageDialog(null, "You''ve lost the war", "Lost", JOptionPane.INFORMATION_MESSAGE);
                Stage stage2 = (Stage) lblOpponentName.getScene().getWindow();
                stage2.close();
            }
            Stage stage2 = (Stage) lblOpponentName.getScene().getWindow();
            stage2.close();
        }

    }
}
