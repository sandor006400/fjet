package org.avv.fjet;

import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.SquareCoords;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 02/04/2016
 */
public class CoordsUnitTest {

    // region - SquareCoords tests

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

    // endregion - UtilCoordinates tests

}
