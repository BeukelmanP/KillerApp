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
public class ShipTest {

    public ShipTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }
    Ship testShip;
    Coordinate[] coordinates;

    @Before
    public void setUp() {
        coordinates = new Coordinate[3];
        coordinates[0] = new Coordinate(1, 2);
        coordinates[1] = new Coordinate(2, 2);
        coordinates[2] = new Coordinate(3, 2);
        testShip = new Ship(coordinates, "medium");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of isHit method, of class Ship.
     */
    @Test
    public void testIsHit() {
        System.out.println("isHit");
        Coordinate c2 = new Coordinate(2, 2);
        boolean expResult = true;
        boolean result = testShip.isHit(c2);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsHitfalse() {
        System.out.println("isHit");
        Coordinate c2 = new Coordinate(5, 2);
        boolean expResult = false;
        boolean result = testShip.isHit(c2);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIsSunk method, of class Ship.
     */
    @Test
    public void testGetIsSunk() {
        System.out.println("getIsSunk");
        boolean expResult = false;
        boolean result = testShip.getIsSunk();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetIsSunktrue() {
        System.out.println("getIsSunk");
        testShip.isHit(new Coordinate(1, 2));
        testShip.isHit(new Coordinate(2, 2));
        testShip.isHit(new Coordinate(3, 2));
        boolean expResult = true;
        boolean result = testShip.getIsSunk();
        assertEquals(expResult, result);
    }

    /**
     * Test of checkAlreadyShot method, of class Ship.
     */
    @Test
    public void testCheckAlreadyShot() {
        System.out.println("checkAlreadyShot");
        testShip.isHit(new Coordinate(1, 2));
        boolean expResult = true;
        boolean result = testShip.checkAlreadyShot(new Coordinate(1, 2));
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckAlreadyShotfalse() {
        System.out.println("checkAlreadyShot");
        testShip.isHit(new Coordinate(1, 2));
        boolean expResult = false;
        boolean result = testShip.checkAlreadyShot(new Coordinate(2, 2));
        assertEquals(expResult, result);
    }

    /**
     * Test of getCoordinates method, of class Ship.
     */
    @Test
    public void testGetCoordinates() {
        System.out.println("getCoordinates");
        Coordinate[] expResult = coordinates;
        Coordinate[] result = testShip.getCoordinates();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of checkFree method, of class Ship.
     */
    @Test
    public void testCheckFree() {
        System.out.println("checkFree");
        Coordinate[] coordinates2 = new Coordinate[3];
        coordinates2[0] = new Coordinate(2, 1);
        coordinates2[1] = new Coordinate(2, 2);
        coordinates2[2] = new Coordinate(2, 3);
        boolean expResult = false;
        boolean result = testShip.checkFree(coordinates2);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckFreetrue() {
        System.out.println("checkFree");
        Coordinate[] coordinates2 = new Coordinate[3];
        coordinates2[0] = new Coordinate(1, 3);
        coordinates2[1] = new Coordinate(2, 3);
        coordinates2[2] = new Coordinate(3, 3);
        boolean expResult = true;
        boolean result = testShip.checkFree(coordinates2);
        assertEquals(expResult, result);
    }

}
