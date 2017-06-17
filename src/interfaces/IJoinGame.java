/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import Classes.Game;
import Classes.User;

/**
 *
 * @author piete
 */
public interface IJoinGame {

    /**
     * adds client to game
     * @param client user class of client
     * @return game
     */
    public Game joinGame(User client);
}
