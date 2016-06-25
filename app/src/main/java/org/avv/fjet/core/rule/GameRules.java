package org.avv.fjet.core.rule;

import org.avv.fjet.core.Game;
import org.avv.fjet.core.action.Action;
import org.avv.fjet.core.board.ICoords;

/**
 * Creado por Alexander Vladimirovich Vorobiev / 27/02/2016
 */
public abstract class GameRules {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private Game game;

    // endregion - Fields

    // region - Constructors

    public GameRules(Game game){
        this.game = game;
    }

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    public abstract Action getActionWithOnTapDown(ICoords coords);

    public abstract Action getActionWithOnTapUp(ICoords coords);

    public abstract Action getActionWithOnLongPress(ICoords coords);

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
