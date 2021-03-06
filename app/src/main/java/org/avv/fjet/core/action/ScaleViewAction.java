package org.avv.fjet.core.action;

import android.util.Log;

import org.avv.fjet.graphics.board.BoardDrawable;

/**
 * Created by Alexander Vorobiev on 4/05/16.
 */
public class ScaleViewAction extends Action {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private float scale;
    private BoardDrawable boardDrawable;

    // endregion - Fields

    // region - Constructors

    public ScaleViewAction() {
        super(Type.INFORMATIVE);
    }

    // endregion - Constructors

    // region - Getters and Setters

    public ScaleViewAction setScale(float scale){
        Log.d("------------>", "Scale: " + String.valueOf(scale));
        this.scale = scale;
        return this;
    }

    public ScaleViewAction setBoardDrawable(BoardDrawable boardDrawable){
        this.boardDrawable = boardDrawable;
        return this;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    protected IActionResult getExecutionResult() {
        if (this.boardDrawable != null){
            float finalScale = this.scale*this.boardDrawable.getScale();
            Log.d("------------>", "FinalScale: " + String.valueOf(finalScale));
            this.boardDrawable.setScale(finalScale);
        }
        return new ScaleViewActionResult(this.scale);
    }

    @Override
    protected IActionUndoResult getExecutionUndoResult() {
        return null;
    }


    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    public class ScaleViewActionResult implements IActionResult {

        private float scale;

        public ScaleViewActionResult(float scale){
            this.scale = scale;
        }

        public float getScale(){
            return this.scale;
        }

    }

    // endregion - Inner and Anonymous Classes

}
