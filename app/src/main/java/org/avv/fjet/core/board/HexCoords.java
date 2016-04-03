package org.avv.fjet.core.board;

import org.avv.fjet.core.board.util.UtilCoordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/03/2016
 * -------------------------------------------
 * Hexagonal cell axial coordinates
 */
public class HexCoords implements ICoords {

    // region - Constants

    private static final HexCoords [] NEIGHBORS = {
            new HexCoords(0, -1), new HexCoords(1, -1),
            new HexCoords(-1, 0), new HexCoords(1, 0),
            new HexCoords(-1, 1), new HexCoords(0, 1)};

    // endregion - Constants

    // region - Fields

    private int q;
    private int r;

    // endregion - Fields

    // region - Constructors

    public HexCoords(int q, int r){
        this.q = q;
        this.r = r;
    }

    // endregion - Constructors

    // region - Getters and Setters

    public int getR(){
        return this.r;
    }

    public int getQ(){
        return this.q;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public ICoords[] getNeighborsArray() {
        HexCoords [] neighbors = new HexCoords[NEIGHBORS.length];

        for (int i = 0; i < NEIGHBORS.length; i++){
            neighbors[i] = UtilCoordinates.addHexCoords(this, NEIGHBORS[i]);
        }
        return neighbors;
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    @Override
    public boolean equals(Object o) {
        if (o instanceof HexCoords){
            HexCoords coords = (HexCoords) o;
            return this.q == coords.q
                    && this.r == coords.r;
        }
        return false;
    }

    /**
     * Hex coord within N range from current HexCoords. See <a href="http://www.redblobgames.com/grids/hexagons/#range">Coordinate range</a>/>.
     * @param n
     * @return
     */
    @Override
    public ICoords[] getCoordsInRangeArray(int n) {
        List<HexCoords> neighbors = new ArrayList<>();

        for (int dr = -n; dr <= n; dr++){               // -N ≤ dr ≤ N
            int minQ = Math.max(-dr - n, -n);
            int maxQ = Math.min(-dr + n, n);

            for (int dq = minQ; dq <= maxQ; dq++){      // max(-N, -dr-N) ≤ dq ≤ min(N, -dr+N)
                neighbors.add(UtilCoordinates.addHexCoords(this, new HexCoords(dq, dr)));
            }
        }
        return neighbors.toArray(new HexCoords[neighbors.size()]);
    }

    @Override
    public String toString() {
        return "HexCoords(" + this.q + "," + this.r + ")";
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
