package com.avv.fjet.core.board.util;

import com.avv.fjet.core.board.HexCoords;
import com.avv.fjet.core.board.SquareCoords;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/03/2016
 * -------------------------------------------
 * Coordinates conversion and others algorithms are from:
 * http://www.redblobgames.com/grids/hexagons/#conversions
 */
public class UtilCoordinates {

    // region - Constants

    // endregion - Constants

    // region - Fields

    // endregion - Fields

    // region - Constructors

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    /**
     * Distance between two hexagon grid coordinates.
     * @param coordsA
     * @param coordsB
     * @return
     */
    public static int distanceBetweenHexCoords(HexCoords coordsA, HexCoords coordsB){
        return Math.max(
                Math.abs(coordsA.getQ() - coordsB.getQ()),
                Math.abs(coordsA.getR() - coordsB.getR()));
    }

    public static HexCoords addHexCoords(HexCoords coordsA, HexCoords coordsB){
        int q = coordsA.getQ() + coordsB.getQ();
        int r = coordsA.getR() + coordsB.getR();
        return new HexCoords(q, r);
    }

    /**
     * Chebyshev distance between two square grid coordinates.
     * @param coordsA
     * @param coordsB
     * @return
     */
    public static int chebyshevDistanceBetweenSquareCoords(SquareCoords coordsA, SquareCoords coordsB){
        return Math.max(
                Math.abs(coordsA.getX() - coordsB.getX()),
                Math.abs(coordsA.getX() - coordsB.getY()));
    }

    /**
     * Distance between two square grid coordinates if the only way to move is thru neighbor
     * coordinates.
     * @param coordsA
     * @param coordsB
     * @return
     */
    public static int distanceBetweenSquareCoords(SquareCoords coordsA, SquareCoords coordsB){
        return Math.abs(coordsA.getX() - coordsB.getX())
                + Math.abs(coordsA.getX() - coordsB.getY());
    }

    public static SquareCoords addSquareCoords(SquareCoords coordsA, SquareCoords coordsB){
        int x = coordsA.getX() + coordsB.getX();
        int y = coordsA.getY() + coordsB.getY();
        return new SquareCoords(x, y);
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
