package org.avv.fjet.core;

import org.avv.fjet.core.action.Action;
import org.avv.fjet.core.action.ActionExecutor;
import org.avv.fjet.core.board.Board;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 22/03/2016
 */
public class Game {

    // region - Constants

    // endregion - Constants

    // region - Fields

    protected final ActionExecutor executor = new ActionExecutor();
    protected Board board;

    // endregion - Fields

    // region - Constructors

    public Game(Board board){
        this.board = board;
    }

    // endregion - Constructors

    // region - Getters and Setters

    public Board getBoard(){
        return this.board;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public void processAction(Action a){
        this.executor.executeAction(a);
    }

    public void undoLastAction(){
        this.executor.undoLastAction();
    }

    public void redoLastAction(){
        this.executor.redoLastAction();
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
