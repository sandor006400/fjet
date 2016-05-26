package org.avv.fjet;

import org.avv.fjet.core.board.BoardFactory;
import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.geometry.FJetPoint;
import org.avv.fjet.core.board.SquareCoords;

import org.avv.fjet.core.board.util.UtilCoordinates;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
/*
    @Test
    public void test_SquareCoordsJsonSerialization() throws Exception {
        SquareCoords result = new SquareCoords(3,4);
        String coordsJson = result.toJsonString();

        SquareCoords newCoords = new SquareCoords(coordsJson);

        System.out.print("New square coords: " + newCoords);

        assertEquals(result, new SquareCoords(coordsJson));
    }
*/
    // endregion - HexCoords tests

    // region - UtilCoordinates tests

    @Test
    public void test_calcHexCoordsToPixelConversions() throws Exception {
        HexCoords result = new HexCoords(3,4);
        FJetPoint p = new FJetPoint(60, 80);
        int hexCellEdge = 20;
        FJetPoint pResul = UtilCoordinates.hexCoordsToPixel(hexCellEdge, result);
        assertEquals(p, pResul);
    }

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
/*
    @Test
    public void test_calcHexCoordsToOffsetCoords() throws Exception {
        Object [] objs = BoardFactory.createBoard(null, BoardFactory.BoardType.HEX_CELLS, 3, 3).getCells().keySet().toArray();
        List<HexCoords> coords = Arrays.asList(Arrays.copyOf(objs, objs.length, HexCoords[].class));
        List<HexCoords> expectedCoords = Arrays.asList(
                new HexCoords(0, 0), new HexCoords(1, 0), new HexCoords(2, 0),
                new HexCoords(0, 1), new HexCoords(1, 1), new HexCoords(2, 1),
                new HexCoords(-1, 2), new HexCoords(0, 2), new HexCoords(1, 2)
        );
        boolean allCoordsMatch = true;

        if (coords.size() != expectedCoords.size()){
            allCoordsMatch = false;

        } else {

            for (HexCoords c : coords) {
                if (!expectedCoords.contains(c)){
                    allCoordsMatch = false;
                }
            }
        }
        assertTrue(allCoordsMatch);
    }
*/
    @Test
    public void test_roundHexCoords() throws Exception {
        HexCoords result = new HexCoords(3,4);
        assertEquals(result, UtilCoordinates.roundHexCoords(3.232243f, 3.62321f));
    }
/*
    @Test
    public void test_HexCoordsJsonSerialization() throws Exception {
        HexCoords result = new HexCoords(3,4);
        String coordsJson = result.toJsonString();

        HexCoords newCoords = new HexCoords(coordsJson);

        System.out.print("New hex coords: " + newCoords);
        assertEquals(result, newCoords);
    }
*/

    // endregion - UtilCoordinates tests

}
