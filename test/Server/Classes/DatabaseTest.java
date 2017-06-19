/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Classes;

import Classes.User;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.sql.SQLException;
import static org.junit.Assert.*;

/**
 *
 * @author piete
 */
public class DatabaseTest {

    public DatabaseTest() {
    }

    Database instance;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        instance = new Database();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getConnection method, of class Database.
     */
    @Test
    public void testGetConnection() throws Exception {
        System.out.println("getConnection");
        Database instance = new Database();
        boolean expResult = true;
        boolean result = instance.getConnection();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUsers method, of class Database.
     */
    @Test
    public void testGetUsers() {
        System.out.println("getUsers");
        ArrayList<User> users = instance.getUsers();
        String result = users.get(0).getUsername();
        String expResult = "test1";
        assertEquals(expResult, result);
    }

    /**
     * Test of updateScore method, of class Database.
     */
    @Test
    public void testUpdateScore() throws SQLException, ClassNotFoundException {
        System.out.println("updateScore");
        int userID = 2;
        int totalScore = 250;
        ArrayList<User> users = instance.getUsers();
        int before = users.get(1).getTotalScore();
        instance.updateScore(userID, totalScore);
        Database instance2 = new Database();
        ArrayList<User> users2 = instance2.getUsers();
        int result = users2.get(1).getTotalScore();
        assertEquals(totalScore, result);
    }

//    @Test(expected = SQLException.class)
//    public void testUpdateScoreEx() throws SQLException, ClassNotFoundException {
//        System.out.println("updateScore");
//        int userID = 1999999999;
//        int totalScore = 250;
//        ArrayList<User> users = instance.getUsers();
//        int before = users.get(1).getTotalScore();
//        instance.updateScore(userID, totalScore);
//        Database instance2 = new Database();
//        instance2.getConnection();
//        ArrayList<User> users2 = instance2.getUsers();
//        int result = users2.get(1).getTotalScore();
//
//    }

    /**
     * Test of closeConnection method, of class Database.
     */
    @Test
    public void testCloseConnection() {
        System.out.println("closeConnection");
        boolean expResult = true;
        boolean result = instance.closeConnection();
        assertEquals(expResult, result);
    }

}
