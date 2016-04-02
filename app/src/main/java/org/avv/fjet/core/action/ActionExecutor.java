package org.avv.fjet.core.action;

import java.util.ArrayList;
import java.util.List;

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

    // endregion - Fields

    // region - Constructors

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    /**
     * Updates actionList. If currentAction isn't the last of acrtionList discards all following
     * Actions
     * @param action
     */
    private void updateActionsList(Action action){
        if (this.currentAction + 1 < this.actionsList.size()){
            this.actionsList.set(this.currentAction, action);
        } else {
            this.actionsList.add(action);
        }
        this.currentAction++;
    }

    /**
     * Executes undo of last Action of actionList and sets value of currentAction to the
     * next Action index of actionList
     */
    public void undoLastAction(){
        if (this.currentAction != -1 && this.currentAction < this.actionsList.size()) {
            this.actionsList.get(this.currentAction).undo();
            this.currentAction--;
        }
    }

    /**
     * Executes redo of last Action of actionList and sets value of currentAction to the
     * previous Action index of actionList
     */
    public void redoLastAction(){
        if (this.currentAction + 1 < this.actionsList.size()) {
            this.currentAction++;
            this.actionsList.get(this.currentAction).execute();
        }
    }

    /**
     * Executes the action and add it to actionsList
     * @param action
     */
    public void executeAction(Action action){
        action.execute();
        updateActionsList(action);
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
