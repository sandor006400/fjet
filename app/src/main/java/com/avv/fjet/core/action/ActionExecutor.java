package com.avv.fjet.core.action;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Creado por Alexander Vladimirovich Vorobiev / 27/02/2016
 */
public class ActionExecutor {

    // region - Constants

    private static final String DEBUD_TAG = "ActionProcessor";

    // endregion - Constants

    // region - Fields

    private List<Action> actionsList = new ArrayList<Action>();
    private int currentAction = -1;

    private BlockingQueue<Action> queue = new LinkedBlockingQueue<>();

    // endregion - Fields

    // region - Constructors

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    private void updateActionsList(Action action){
        if (this.currentAction + 1 < this.actionsList.size()){
            this.actionsList.set(this.currentAction, action);
        } else {
            this.actionsList.add(action);
        }
        this.currentAction++;
    }

    public void undoLastAction(){
        if (this.currentAction != -1 && this.currentAction < this.actionsList.size()) {
            this.actionsList.get(this.currentAction).undo();
            this.currentAction--;
        }
    }

    public void redoLastAction(){
        if (this.currentAction + 1 < this.actionsList.size()) {
            this.currentAction++;
            this.actionsList.get(this.currentAction).execute();
        }
    }

    public void executeAction(Action action){
        updateActionsList(action);
        action.execute();
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
