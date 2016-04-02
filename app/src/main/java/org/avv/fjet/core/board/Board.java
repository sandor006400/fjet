package org.avv.fjet.core.board;

import java.util.HashMap;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/03/2016
 */
public abstract class Board {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private HashMap<ICoordinates, Cell> cellsMap = new HashMap<>();

    // endregion - Fields

    // region - Constructors

    // endregion - Constructors

    // region - Getters and Setters

    public Cell getCellWithCoords(ICoordinates coords){
        return this.cellsMap.get(coords);
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    interface IBoardState {

    }

    // endregion - Inner and Anonymous Classes

}
