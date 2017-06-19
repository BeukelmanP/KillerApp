/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import fontyspublisher.RemotePublisher;
import java.rmi.RemoteException;
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
public class GameTest {

    public GameTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }
    Game testGame;
Ship[] shipsClient ;
        Ship[] shipsServer;
    @Before
    public void setUp() throws RemoteException {
        RemotePublisher publisher = new RemotePublisher();
        publisher.registerProperty("Turn");
        publisher.registerProperty("Winner");
        testGame = new Game(publisher);
        testGame.setGameName("testgame");
        User server = new User(1, "test", "password", "test@abc.com", 200, 100, 2);
        User client = new User(1, "test2", "password", "test2@abc.com", 200, 100, 2);
        testGame.addServerUser(server);
        testGame.Join(client);
        Coordinate[] coordinates = new Coordinate[3];
        coordinates[0] = new Coordinate(1, 2);
        coordinates[1] = new Coordinate(2, 2);
        coordinates[2] = new Coordinate(3, 2);
        Ship testShip = new Ship(coordinates, "medium");
        Coordinate[] coordinates2 = new Coordinate[3];
        coordinates2[0] = new Coordinate(1, 3);
        coordinates2[1] = new Coordinate(2, 3);
        coordinates2[2] = new Coordinate(3, 3);
        Ship testShip2 = new Ship(coordinates2, "medium");
        shipsClient = new Ship[1];
        shipsServer = new Ship[1];
        shipsClient[0] = testShip;
        shipsServer[0] = testShip2;
        testGame.addShips(shipsServer, 0);
        testGame.addShips(shipsClient, 1);

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getGameInfo method, of class Game.
     */
    @Test
    public void testGetGameInfo() throws Exception {
        System.out.println("getGameInfo");
        String expResult = "testgame";
        String result = testGame.getGameInfo();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOpponentInfo method, of class Game.
     */
    @Test
    public void testGetOpponentInfo() throws Exception {
        System.out.println("getOpponentInfo");
        int player = 0;
        String expResult = "test2;200;100;2";
        String result = testGame.getOpponentInfo(player);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOpponentInfo2() throws Exception {
        System.out.println("getOpponentInfo");
        int player = 1;
        String expResult = "test;200;100;2";
        String result = testGame.getOpponentInfo(player);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOpponentInfoNull() throws Exception {
        System.out.println("getOpponentInfo");
        int player = 3;
        String expResult = null;
        String result = testGame.getOpponentInfo(player);
        assertEquals(expResult, result);
    }

    /**
     * Test of changeTurn method, of class Game.
     */
    @Test
    public void testChangeTurn() throws Exception {
        System.out.println("changeTurn");
        testGame.changeTurn();
        int expResult = 1;
        int result = testGame.getTurn();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTurn method, of class Game.
     */
    @Test
    public void testGetTurn() throws Exception {
        System.out.println("getTurn");
        int expResult = 0;
        int result = testGame.getTurn();
        assertEquals(expResult, result);
    }

    /**
     * Test of fireShot method, of class Game.
     */
    @Test
    public void testFireShot() throws Exception {
        System.out.println("fireShot");
        Coordinate C = new Coordinate(1, 2);
        boolean expResult = true;
        boolean result = testGame.fireShot(C);
        assertEquals(expResult, result);
    }

    @Test
    public void testFireShot2() throws Exception {
        System.out.println("fireShot2");
        Coordinate C = new Coordinate(1, 3);
        testGame.changeTurn();
        boolean expResult = true;
        boolean result = testGame.fireShot(C);

        assertEquals(expResult, result);
    }

    @Test
    public void testFireShot3() throws Exception {
        System.out.println("fireShot3");
        Coordinate C = new Coordinate(1, 4);
        boolean expResult = false;
        boolean result = testGame.fireShot(C);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkShot method, of class Game.
     */
    @Test
    public void testCheckShot() throws Exception {
        System.out.println("checkShot");
        Coordinate C = new Coordinate(1, 2);
        testGame.changeTurn();
        
        testGame.fireShot(C);
        testGame.changeTurn();
        Game instance = null;
        boolean expResult = true;
        boolean result = testGame.checkShot(C);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testCheckShot2() throws Exception {
        System.out.println("checkShot2");
        
        Coordinate C = new Coordinate(1, 2);
        testGame.fireShot(C);
        testGame.changeTurn();
        Game instance = null;
        boolean expResult = true;
        boolean result = testGame.checkShot(C);
        assertEquals(expResult, result);
    }

    


    /**
     * Test of getMyShips method, of class Game.
     */
    @Test
    public void testGetMyShipsServer() throws Exception {
        System.out.println("getMyShipsServer");
        int player = 0;
        Ship[] expResult = shipsServer;
        Ship[] result = testGame.getMyShips(player);
        assertArrayEquals(expResult, result);
    }
    
    @Test
    public void testGetMyShipsClient() throws Exception {
        System.out.println("getMyShipsClient");
        int player = 1;
        Ship[] expResult = shipsClient;
        Ship[] result = testGame.getMyShips(player);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of addServerUser method, of class Game.
     */
    @Test
    public void testAddServerUser() {
        System.out.println("addServerUser");
        User serverUser = null;
        Game instance = null;
        instance.addServerUser(serverUser);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastTurn method, of class Game.
     */
    @Test
    public void testGetLastTurn() throws Exception {
        System.out.println("getLastTurn");
        Coordinate coordinate = new Coordinate(1, 2);
        testGame.fireShot(coordinate);
        Coordinate expResult = coordinate;
        Coordinate result = testGame.getLastTurn();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testEndGame() throws RemoteException{
    testGame.fireShot(new Coordinate(1, 2));
    testGame.changeTurn();
    testGame.fireShot(new Coordinate(2, 2));
    testGame.changeTurn();
    testGame.fireShot(new Coordinate(3, 2));
    testGame.CheckWinner();
    }

}
