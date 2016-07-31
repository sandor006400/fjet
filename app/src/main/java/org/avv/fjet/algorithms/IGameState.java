package org.avv.fjet.algorithms;

import java.util.List;

/**
 * Created by Alexander Vorobiev on 29/07/16.
 */
public interface IGameState {

    // region - Constants

    // endregion - Constants

    // region - Fields

    // endregion - Fields

    // region - Constructors

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    List<IGameState> getNextPossibleStates();

    boolean isFinalState();

    float getStateScore();

    int getTurn();

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
