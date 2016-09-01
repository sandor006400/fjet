package org.avv.fjet.core.action;

import java.util.ArrayList;
import java.util.List;
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
        INFORMATIVE,    // Informative purpose
        PERMANENT       // Permanent changes
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

    public Action(Type type, String id){
        this.type = type;
        this.id = id;
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

    abstract protected IActionResult getExecutionResult();

    abstract protected IActionUndoResult getExecutionUndoResult();

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    private String generateID(){
        return UUID.randomUUID().toString();
    }

    public void execute(){
        final IActionResult result = getExecutionResult();

        for (ActionObserver observer : this.obsevers){
            observer.receiveActionResult(result);
        }
    }

    public void undo(){
        final IActionUndoResult undoResult = getExecutionUndoResult();

        for (ActionObserver observer : this.obsevers){
            observer.receiveActionUndoResult(undoResult);
        }
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    public interface IActionData {

        String toJson();
    }

    public interface ActionObserver {

        void receiveActionResult(IActionResult result);

        void receiveActionUndoResult(IActionUndoResult undoResult);

    }

    public interface IActionResult {

    }

    public interface IActionUndoResult {

    }

    // endregion - Inner and Anonymous Classes

}
