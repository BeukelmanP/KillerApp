/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.Serializable;

/**
 *
 * @author piete
 */
public class Ship implements Serializable {

    Coordinate[] coordinates;
    Coordinate[] shotcoordinates;
    boolean isSunk = false;
    String name;
    int length;
    int hits = 0;

    public Ship(Coordinate[] coordinates, String name) {
        this.coordinates = coordinates;
        shotcoordinates = new Coordinate[coordinates.length];
        this.name = name;
        this.length = coordinates.length;
        System.out.println("Coordinates:");

        for (Coordinate C1 : coordinates) {
            if (C1 != null) {
                System.out.println(C1.getX() + ";" + C1.getY());
            }
        }
    }

    public boolean isHit(Coordinate c2) {
        System.out.println("Check hit");
        for (Coordinate c1 : coordinates) {
            System.out.print("Shot: " + c1.getX() + " " + c1.getY() + "  Check: " + c2.getX() + " " + c2.getY() + " -> ");

            if (c1.getX() == c2.getX() && c1.getY() == c2.getY()) {
                System.out.print("hit");
                shotcoordinates[hits] = c1;
                hits++;
                System.out.println("L: " + length + " H: " + hits);
                if (hits == (length)) {
                    isSunk = true;
                    System.out.println("SUNK");
                }
                return true;
            }
            System.out.print("miss");
        }
        return false;
    }

    public boolean getIsSunk() {
        return isSunk;
    }

    public boolean checkAlreadyShot(Coordinate C2) {
        System.out.println("Check already hit");
        for (Coordinate C1 : shotcoordinates) {
            if (C1 != null) {
                System.out.println(C1.getX() + " " + C1.getY() + "   " + C2.getX() + " " + C2.getY());
                if (C1.getX() == C2.getX() && C1.getY() == C2.getY()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    public boolean checkFree(Coordinate[] coordinates2) {
        for (Coordinate C : coordinates) {
            for (Coordinate C2 : coordinates2) {
                if (C.getX() == C2.getX() && C.getY() == C2.getY()) {
                    return false;
                }
            }
        }
        return true;
    }
}
