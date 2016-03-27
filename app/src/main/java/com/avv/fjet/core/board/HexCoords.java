package com.avv.fjet.core.board;

import com.avv.fjet.core.board.util.UtilCoordinates;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/03/2016
 * -------------------------------------------
 * Hexagonal cell axial coordinates
 */
public class HexCoords implements ICoordinates {

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
    public ICoordinates[] getNeighborsArray() {
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

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
