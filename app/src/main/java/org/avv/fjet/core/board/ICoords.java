package org.avv.fjet.core.board;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/03/2016
 */
public interface ICoords {

    // region - Constants

    // endregion - Constants

    // region - Fields

    // endregion - Fields

    // region - Constructors

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    /**
     * Returns array of coordinates. The neighbor coordinates are those with edge joins.
     * @return
     */
    ICoords[] getNeighborsArray();

    /**
     * Returns coords within N range area from Current Coordinate.
     * @param n
     * @return
     */
    ICoords[] getCoordsInRangeArray(int n);

    ICoords getCopy();

    String toShortString();

    String toJsonString();

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
