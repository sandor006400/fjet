package org.avv.fjet.core.action;

/**
 * Created by Alexander Vorobiev on 4/05/16.
 */
public class ScaleViewAction extends Action {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private float scale;

    // endregion - Fields

    // region - Constructors

    public ScaleViewAction(Type type) {
        super(type);
    }

    // endregion - Constructors

    // region - Getters and Setters

    public Action setScale(float scale){
        this.scale = scale;
        return this;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    protected Object getExecutionResult() {
        return this.scale;
    }

    @Override
    protected Object getExecutionUndoResult() {
        return null;
    }


    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
