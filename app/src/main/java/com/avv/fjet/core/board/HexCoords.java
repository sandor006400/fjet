package com.avv.fjet.core.board;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/03/2016
 * -------------------------------------------
 * Hexagonal cell axial coordinates
 */
public class HexCoords implements ICoordinates {

    // region - Constants

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

    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public boolean equals(HexCoords coords) {
        return this.q == coords.q
                && this.r == coords.r;
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
