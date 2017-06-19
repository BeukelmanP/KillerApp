/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Classes;

import Classes.User;
import interfaces.ILiveGame;
import java.rmi.RemoteException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author piete
 */
public class JoinGamesTest {
    
    public JoinGamesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getGameServer method, of class JoinGames.
     */
    @Test
    public void testGetGameServer() throws Exception {
        System.out.println("getGameServer");
        String Game = "";
        JoinGames instance = new JoinGames();
        ILiveGame expResult = null;
        ILiveGame result = instance.getGameServer(Game);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAvailableGames method, of class JoinGames.
     */
    @Test
    public void testGetAvailableGames() throws Exception {
        System.out.println("getAvailableGames");
        JoinGames instance = new JoinGames();
        List<String> expResult = null;
        List<String> result = instance.getAvailableGames();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createGame method, of class JoinGames.
     */
    @Test
    public void testCreateGame() throws Exception {
        System.out.println("createGame");
        String ipAdress = "192.168.0.1";
        String gameName = "testCreateGame";
        int portNumber = 1098;
        JoinGames instance = new JoinGames();
        boolean expResult = true;
        boolean result = instance.createGame(ipAdress, gameName, portNumber);
        assertEquals(expResult, result);
    }
    @Test
    public void testCreateGamenoname() throws Exception {
        System.out.println("createGameNoName");
        String ipAdress = "192.168.0.1";
        String gameName = "";
        int portNumber = 1098;
        JoinGames instance = new JoinGames();
        boolean expResult = false;
        boolean result = instance.createGame(ipAdress, gameName, portNumber);
        assertEquals(expResult, result);
    }
    @Test
    public void testCreateGamewrongport() throws Exception {
        System.out.println("createGameWrongPort");
        String ipAdress = "192.168.0.1";
        String gameName = "test123";
        int portNumber = 1099;
        JoinGames instance = new JoinGames();
        boolean expResult = false;
        boolean result = instance.createGame(ipAdress, gameName, portNumber);
        assertEquals(expResult, result);
    }

    /**
     * Test of login method, of class JoinGames.
     */
    @Test
    public void testLogin() throws RemoteException {
        System.out.println("login");
        String userName = "test1";
        String password = "wachtwoord";
        JoinGames instance = new JoinGames();
        int expResult = 1;
        int result = instance.login(userName, password).getUserId();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testLoginfalse() throws RemoteException {
        System.out.println("login");
        String userName = "test1";
        String password = "wachtwoordt";
        JoinGames instance = new JoinGames();
        User expResult = null;
        User result = instance.login(userName, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testLoginfalse2() throws RemoteException {
        System.out.println("login");
        String userName = null;
        String password = "wachtwoordt";
        JoinGames instance = new JoinGames();
        User expResult = null;
        User result = instance.login(userName, password);
        assertEquals(expResult, result);
    }
    

    /**
     * Test of logout method, of class JoinGames.
     */
    @Test
    public void testLogout() throws Exception {
        System.out.println("logout");
        User userToLogout = null;
        JoinGames instance = new JoinGames();
        boolean expResult = false;
        boolean result = instance.logout(userToLogout);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of register method, of class JoinGames.
     */
    @Test
    public void testRegister() throws Exception {
        System.out.println("register");
        String username = "";
        String password = "";
        String email = "";
        JoinGames instance = new JoinGames();
        boolean expResult = false;
        boolean result = instance.register(username, password, email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateScores method, of class JoinGames.
     */
    @Test
    public void testUpdateScores() throws Exception {
        System.out.println("updateScores");
        User user1 = null;
        User user2 = null;
        int score1 = 0;
        int score2 = 0;
        JoinGames instance = new JoinGames();
        instance.updateScores(user1, user2, score1, score2);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
