package org.avv.fjet.core.action;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 23/03/2016
 */
public abstract class Action {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private List<ActionObserver> obsevers = new ArrayList<>();

    // endregion - Fields

    // region - Constructors

    // endregion - Constructors

    // region - Getters and Setters

    public void setObserver(ActionObserver observer){
        this.obsevers.add(observer);
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    abstract protected ActionResult getExecutionResult();

    abstract protected ActionUndoResult getExecutionUndoResult();

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public void execute(){
        final ActionResult result = getExecutionResult();

        for (ActionObserver observer : this.obsevers){
            observer.sendActionResult(result);
        }
    }

    public void undo(){
        final ActionUndoResult undoResult = getExecutionUndoResult();

        for (ActionObserver observer : this.obsevers){
            observer.sendActionUndoResult(undoResult);
        }
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    public interface IActionData {

        String toJson();

    }

    public interface ActionObserver {

        void sendActionResult(ActionResult result);

        void sendActionUndoResult(ActionUndoResult undoResult);

    }

    public interface ActionResult {

    }

    public interface ActionUndoResult {

    }

    // endregion - Inner and Anonymous Classes

}
