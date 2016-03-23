package com.avv.fjet.core;

import com.avv.fjet.core.action.Action;
import com.avv.fjet.core.action.ActionExecutor;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 22/03/2016
 */
public class Game {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private final ActionExecutor executor = new ActionExecutor();

    // endregion - Fields

    // region - Constructors

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public void processAction(Action a){
        synchronized (this.executor){
            this.executor.executeAction(a);
        }
    }

    public void undoLastAction(){
        synchronized (this.executor){
            this.executor.undoLastAction();
        }
    }

    public void redoLastAction(){
        synchronized (this.executor){
            this.executor.redoLastAction();
        }
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
