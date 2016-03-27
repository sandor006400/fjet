package com.avv.fjet.core.board.util;

import com.avv.fjet.core.board.CubicCoords;
import com.avv.fjet.core.board.HexCoords;

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

    public static HexCoords cubicCoordsToHexCoords(CubicCoords cubicCoords) {
        int x = cubicCoords.getX();
        int y = cubicCoords.getZ();
        return new HexCoords(x ,y);
    }

    public static CubicCoords hexCoordsToCubicCoords(HexCoords hexCoords) {
        int x = hexCoords.getQ();
        int z = hexCoords.getR();
        int y = - x - z;
        return new CubicCoords(x, y, z);
    }

    /**
     * Distance between two cubic versions of hexagon grid coordinates. It's valid because the
     * coordinates are placed on diagonal plane x + y + z = 0. The result is the highest value
     * of absolute diferences between coordinates in each axis.
     * @param coordsA
     * @param coordsB
     * @return
     */
    public static int distanceBetweenCubicCoords(CubicCoords coordsA, CubicCoords coordsB){
        return Math.max(
                Math.max(
                        Math.abs(coordsA.getX() - coordsB.getX()),
                        Math.abs(coordsA.getY() - coordsB.getY())),
                Math.abs(coordsA.getZ() - coordsB.getZ()));
    }

    /**
     * Distance between two hexagon grid coordinates.
     * @param coordsA
     * @param coordsB
     * @return
     */
    public static int distanceBetweenHexCoords(HexCoords coordsA, HexCoords coordsB){
        CubicCoords cubCoordsA = hexCoordsToCubicCoords(coordsA);
        CubicCoords cubCoordsB = hexCoordsToCubicCoords(coordsB);
        return distanceBetweenCubicCoords(
                cubCoordsA,
                cubCoordsB);
    }

    public static CubicCoords addCubicCoords(CubicCoords coordsA, CubicCoords coordsB){
        int x = coordsA.getX() + coordsB.getX();
        int y = coordsA.getY() + coordsB.getY();
        int z = coordsA.getZ() + coordsB.getZ();
        return new CubicCoords(x, y, z);
    }

    public static HexCoords addHexCoords(HexCoords coordsA, HexCoords coordsB){
        int q = coordsA.getQ() + coordsB.getQ();
        int r = coordsA.getR() + coordsB.getR();
        return new HexCoords(q, r);
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
