package com.avv.fjet.core.board;

import com.avv.fjet.core.board.util.UtilCoordinates;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/03/2016
 * -------------------------------------------
 * Square cell coordinates
 */
public class SquareCoords implements ICoordinates {

    // region - Constants

    private static final SquareCoords [] NEIGHBORS = {
            new SquareCoords(0, 1),
            new SquareCoords(-1, 0), new SquareCoords(1, 0),
            new SquareCoords(0, -1)};

    // endregion - Constants

    // region - Fields

    private int x;
    private int y;

    // endregion - Fields

    // region - Constructors

    public SquareCoords(int x, int y){
        this.x = x;
        this.y = y;
    }

    // endregion - Constructors

    // region - Getters and Setters

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public ICoordinates[] getNeighborsArray() {
        SquareCoords [] neighbors = new SquareCoords[NEIGHBORS.length];

        for (int i = 0; i < NEIGHBORS.length; i++){
            neighbors[i] = UtilCoordinates.addSquareCoords(this, NEIGHBORS[i]);
        }
        return neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SquareCoords){
            SquareCoords coords = (SquareCoords) o;
            return this.x == coords.x
                    && this.y == coords.y;
        }
        return false;
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
