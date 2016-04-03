package org.avv.fjet.core.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 23/03/2016
 * -------------------------------------------
 * Command Pattern implementation
 */
public abstract class Action {

    // region - Constants

    public enum Type {
        INFORMATIVE,    // Informative consequences
        EXECUTIVE       // Permanent changes
    }

    // endregion - Constants

    // region - Fields

    private Type type;
    private String id;
    private List<ActionObserver> obsevers = new ArrayList<>();

    // endregion - Fields

    // region - Constructors

    public Action(Type type){
        this.type = type;
        this.id = generateID();
    }

    // endregion - Constructors

    // region - Getters and Setters

    public void setObserver(ActionObserver observer){
        this.obsevers.add(observer);
    }

    public Type getType(){
        return this.type;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    abstract protected ActionResult getExecutionResult();

    abstract protected ActionUndoResult getExecutionUndoResult();

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    private String generateID(){
        return UUID.randomUUID().toString();
    }

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
