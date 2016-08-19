package org.avv.fjet;

import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.graphics.geometry.FJetPoint;
import org.avv.fjet.core.board.SquareCoords;

import org.avv.fjet.core.board.util.UtilCoordinates;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 02/04/2016
 */
public class CoordsUnitTest {

    // region - SquareCoords tests

    @Test
    public void test_squareCoordsEquals() throws Exception {
        assertEquals (new SquareCoords(3,3), new SquareCoords(3,3));
    }

    @Test
    public void test_squareCoordsNeighbors() throws Exception {
        SquareCoords coords = new SquareCoords(3,4);

        // (3,4) neighbors are [(3,3), (2,4), (4,4), (3,5)]
        SquareCoords [] neighbors = {
                new SquareCoords(3,3),
                new SquareCoords(2,4),
                new SquareCoords(4,4),
                new SquareCoords(3,5)};
        assertArrayEquals(neighbors, coords.getNeighborsArray());
    }

    @Test
    public void test_calcSquareCoordsToPixelConversions() throws Exception {
        SquareCoords coords = new SquareCoords(3,4);
        float edgeSize = 20;
        FJetPoint p = new FJetPoint(70, 90);
        FJetPoint pResult = UtilCoordinates.squareCoordsToPixel(edgeSize, coords);
        assertEquals(p, pResult);
    }

    @Test
    public void test_squareCoordsInRangeArray() throws Exception {

        SquareCoords coords = new SquareCoords(3,4);
        int N = 2;

        /* Coords with max distance N = 2 from (3,4) are
           [             (3,2),
                  (2,3), (3,3), (4,3),
           (1,4), (2,4), (3,4), (4,4), (5,4),
                  (2,5), (3,5), (4,5),
                         (3,6)
           ]
         */
        SquareCoords [] rangeNCoords = {
                new SquareCoords(3,2),
                new SquareCoords(2,3), new SquareCoords(3,3), new SquareCoords(4,3),
                new SquareCoords(1,4), new SquareCoords(2,4), new SquareCoords(3,4), new SquareCoords(4,4), new SquareCoords(5,4),
                new SquareCoords(2,5), new SquareCoords(3,5), new SquareCoords(4,5),
                new SquareCoords(3,6)
        };
        assertArrayEquals(rangeNCoords, coords.getCoordsInRangeArray(N));
    }

    // endregion - SquareCoords tests

    // region - HexCoords tests

    @Test
    public void test_hexCoordsEquals() throws Exception {
        assertEquals (new HexCoords(3,3), new HexCoords(3,3));
    }

    @Test
    public void test_hexCoordsNeighbors() throws Exception {

        HexCoords coords = new HexCoords(3,4);

        // (3,4) neighbors are [(3,3), (4,3), (2,4), (4,4), (2,5), (3,5)]
        HexCoords [] neighbors = {
                new HexCoords(3,3),
                new HexCoords(4,3),
                new HexCoords(2,4),
                new HexCoords(4,4),
                new HexCoords(2,5),
                new HexCoords(3,5)};
        assertArrayEquals(neighbors, coords.getNeighborsArray());
    }

    @Test
    public void test_hexCoordsInRangeArray() throws Exception {

        HexCoords coords = new HexCoords(3,4);
        int N = 2;

        /* Coords with max distance N = 2 from (3,4) are
           [       (3,2), (4,2), (5,2),
               (2,3), (3,3), (4,3), (5,3),
           (1,4), (2,4), (3,4), (4,4), (5,4),
               (1,5), (2,5), (3,5), (4,5),
                  (1,6), (2,6), (3,6),
           ]
         */
        HexCoords [] rangeNCoords = {
                                                        new HexCoords(3,2), new HexCoords(4,2), new HexCoords(5,2),
                                    new HexCoords(2,3), new HexCoords(3,3), new HexCoords(4,3), new HexCoords(5,3),
                new HexCoords(1,4), new HexCoords(2,4), new HexCoords(3,4), new HexCoords(4,4), new HexCoords(5,4),
                new HexCoords(1,5), new HexCoords(2,5), new HexCoords(3,5), new HexCoords(4,5),
                new HexCoords(1,6), new HexCoords(2,6), new HexCoords(3,6)
        };
        assertArrayEquals(rangeNCoords, coords.getCoordsInRangeArray(N));

    }

    // endregion - HexCoords tests

    // region - UtilCoordinates tests

    @Test
    public void test_calcPixelToHexCoordsConversions_colAprox() throws Exception {
        float hexCellEdge = 10;
        HexCoords result = new HexCoords(0,0);
        float x = UtilCoordinates.SQRT_OF_3 * hexCellEdge - 0.02f;  // 17.300507
        float y = hexCellEdge;
        HexCoords finalCoord = UtilCoordinates.hexCoordsFromPixel(x, y, hexCellEdge);
        assertEquals(result, finalCoord);
    }

    @Test
    public void test_calcPixelToHexCoordsConversions_rowAprox() throws Exception {
        float hexCellEdge = 10;
        HexCoords result = new HexCoords(0,0);
        float x = (UtilCoordinates.SQRT_OF_3 * 2 / 3) * hexCellEdge;
        float y = hexCellEdge;

        System.out.println("x: " + x + " y: " + y);
        HexCoords finalCoord = UtilCoordinates.hexCoordsFromPixel(x, y, hexCellEdge);
        assertEquals(result, finalCoord);
    }

    @Test
    public void test_roundHexCoords() throws Exception {
        HexCoords result = new HexCoords(3,4);
        assertEquals(result, UtilCoordinates.roundHexCoords(3.232243f, 3.62321f));
    }

    @Test
    public void test_offsetHexCoordsToAxialHexCoords() throws Exception {
        HexCoords result = UtilCoordinates.offsetHexCoordsToAxialHexCoords(0, 4);
        assertEquals(new HexCoords(-2, 4), result);
    }

    @Test
    public void test_getCoordLeftPaddingInMatrix() throws Exception {
        int padding = UtilCoordinates.getCoordLeftPaddingInMatrix(3);
        assertEquals(1, padding);
    }

    // endregion - UtilCoordinates tests

}
