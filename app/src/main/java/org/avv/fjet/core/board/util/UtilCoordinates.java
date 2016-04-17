package org.avv.fjet.core.board.util;

import android.util.Log;

import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.Point;
import org.avv.fjet.core.board.SquareCoords;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/03/2016
 * -------------------------------------------
 * Coordinates conversion and others algorithms are from:
 * http://www.redblobgames.com/grids/hexagons/#conversions
 */
public class UtilCoordinates {

    // region - Constants

    public static final float SQRT_OF_3 = 1.732050807568877293527446341505f;   // Aproximation of sqrt(3) with 30 decimals

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
     * Calculates HexCoords using pixel (x,y) coordinate, board height in pixels and
     * number of Board's rows). See <a href="http://www.redblobgames.com/grids/hexagons/#pixel-to-hex">Pixel to Hex</a>
     * @param xCoord
     * @param yCoord
     * @param boardPixHeight
     * @param rows
     * @return
     */
    public static HexCoords hexCoordsFromPixel(int xCoord, int yCoord, int boardPixHeight, int rows){

        // Size is the edge measure of hexagon edge. Size = height * 2 / (rows * 3 + 1)
        float size = (boardPixHeight * 2) / (rows * 3 + 1);
        return hexCoordsFromPixel(xCoord, yCoord, size);
    }

    /**
     * Calculates HexCoords using pixel (x,y) coordinate and edge size of hexagon cell in pixels. See <a href="http://www.redblobgames.com/grids/hexagons/#pixel-to-hex">Pixel to Hex</a>
     * @param xCoord
     * @param yCoord
     * @param edgeSize
     * @return
     */
    public static HexCoords hexCoordsFromPixel(int xCoord, int yCoord, float edgeSize){
        float q = (xCoord * SQRT_OF_3 / 3.0f - yCoord / 3.0f) / edgeSize;
        float r = yCoord * 2.0f / 3.0f / edgeSize;
        return roundHexCoords(q, r);
    }

    /**
     * Rounds float coordinates (q,r) to the nearest hex coordinate. See <a href="http://www.redblobgames.com/grids/hexagons/#rounding">Rounding to nearest hex</a>.
     * @param q
     * @param r
     * @return
     */
    public static HexCoords roundHexCoords(float q, float r){
        float y = - q - r;
        int rx = Math.round(q);
        int rz = Math.round(r);
        int ry = Math.round(y);

        float dX = Math.abs(rx - q);
        float dY = Math.abs(ry - y);
        float dZ = Math.abs(rz - r);

        if (dX > dZ && dX > dZ) {
            rx = -ry - rz;

        } else if (dZ > dY) {
            rz = -rx - ry;
        }
        return new HexCoords(rx, rz);
    }

    /**
     * Calculates the central point of the hexagonal cell in pixels. See <a href="http://www.redblobgames.com/grids/hexagons/#hex-to-pixel">Hex to Pixel</a>.
     * @param edgeSize
     * @param coords
     * @return
     */
    public static Point hexCoordsToPixel(float edgeSize, HexCoords coords){
        int x = Math.round(edgeSize * SQRT_OF_3 * ((float)coords.getQ() + (float) coords.getR() / 2));
        int y = Math.round(edgeSize * 3/2 * coords.getR());
        return new Point(x,y);
    }

    /**
     * Convert “odd-r horizontal layout coordinate" to axial hex coordinate. See <a href="http://www.redblobgames.com/grids/hexagons/#conversions">Coordinate conversion</a>
     * @param x
     * @param y
     * @return
     */
    public static HexCoords offsetHexCoordsToAxialHexCoords(int x, int y){
        int q = x - (y - (x % 2)) / 2;
        return new HexCoords(q, y); // r == y
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

    /**
     * Calculates Square coordinate using pixel (x,y) coordinate, board pixel dimensions (width,height) and
     * Board dimensions (cols,rows)
     * @param xCoord
     * @param yCoord
     * @param width
     * @param height
     * @param cols
     * @param rows
     * @return
     */
    public static SquareCoords calcSquareCoordsFromPixelCoords(int xCoord, int yCoord, int width, int height, int cols, int rows){
        int x = (cols / width) * xCoord;
        int y = (rows / height) * yCoord;
        return new SquareCoords(x, y);
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
