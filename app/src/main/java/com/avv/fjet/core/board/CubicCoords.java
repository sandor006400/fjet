package com.avv.fjet.core.board;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/03/2016
 * -------------------------------------------
 * Cubic coordinates
 */
public class CubicCoords implements ICoordinates {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private int x;
    private int y;
    private int z;

    // endregion - Fields

    // region - Constructors

    public CubicCoords(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // endregion - Constructors

    // region - Getters and Setters

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getZ(){
        return this.z;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public ICoordinates[] getNeighborsArray() {
        return
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public boolean equals(CubicCoords coords) {
        return this.x == coords.x
                && this.y == coords.y
                && this.z == coords.z;
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
