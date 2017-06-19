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
public class CoordinateTest {

    public CoordinateTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }
    Coordinate test;

    @Before
    public void setUp() {
        test = new Coordinate(2, 5);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getX method, of class Coordinate.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        int expResult = 2;
        int result = test.getX();
        assertEquals(expResult, result);
    }

    /**
     * Test of getY method, of class Coordinate.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        int expResult = 5;
        int result = test.getY();
        assertEquals(expResult, result);
    }

}
