/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

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
public class UserTest {

    public UserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }
    User testUser;

    @Before
    public void setUp() {
        testUser = new User(1, "test", "password", "test@abc.com", 200, 100, 2);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getUsername method, of class User.
     */
    @Test
    public void testGetUsername() {
        System.out.println("getUsername");
        String expResult = "test";
        String result = testUser.getUsername();
        assertEquals(expResult, result);
    }

    /**
     * Test of checkPassword method, of class User.
     */
    @Test
    public void testCheckPassword() {
        System.out.println("checkPassword");
        String password = "password";
        User expResult = testUser;
        User result = testUser.checkPassword(password);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckPasswordwrong() {
        System.out.println("checkPassword");
        String password = "password123";
        User expResult = null;
        User result = testUser.checkPassword(password);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkSessionId method, of class User.
     */
    @Test
    public void testCheckSessionId() {
        System.out.println("checkSessionId");
        int session = 0;
        User instance = null;
        boolean expResult = false;
        boolean result = instance.checkSessionId(session);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSessionId method, of class User.
     */
    @Test
    public void testGetSessionId() {
        System.out.println("getSessionId");
        User instance = null;
        int expResult = 0;
        int result = instance.getSessionId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUserInfo method, of class User.
     */
    @Test
    public void testGetUserInfo() {
        System.out.println("getUserInfo");
        String expResult = "test;200;100;2";
        String result = testUser.getUserInfo();
        System.out.println(testUser.getUserInfo());
        assertEquals(expResult, result);
    }

    /**
     * Test of updateScore method, of class User.
     */
    @Test
    public void testUpdateScore() {
        int begin = testUser.getTotalScore();
        System.out.println("updateScore");
        int score = 50;
        int expResult = begin + score;
        testUser.updateScore(score);
        int result = testUser.getTotalScore();
        assertEquals(expResult, result);
    }

    @Test
    public void testUpdateScoreHighScore() {
        int begin = testUser.getTotalScore();
        System.out.println("updateScore");
        int score = 500;
        int expResult = score;
        testUser.updateScore(score);
        int result = testUser.highestScore;
        assertEquals(expResult, result);
    }

    /**
     * Test of getUserId method, of class User.
     */
    @Test
    public void testGetUserId() {
        System.out.println("getUserId");
        int expResult = 1;
        int result = testUser.getUserId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTotalScore method, of class User.
     */
    @Test
    public void testGetTotalScore() {
        System.out.println("getTotalScore");
        int expResult = 200;
        int result = testUser.getTotalScore();
        assertEquals(expResult, result);
    }

}
