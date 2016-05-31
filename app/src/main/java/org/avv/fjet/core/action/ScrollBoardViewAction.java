package org.avv.fjet.core.action;

import org.avv.fjet.core.geometry.FJetPoint;
import org.avv.fjet.graphics.board.BoardDrawable;

/**
 * Created by Alexander Vorobiev on 31/05/16.
 */
public class ScrollBoardViewAction extends Action {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private FJetPoint offset;
    private BoardDrawable boardDrawable;

    // endregion - Fields

    // region - Constructors

    public ScrollBoardViewAction(){
        super(Type.INFORMATIVE);
    }

    // endregion - Constructors

    // region - Getters and Setters

    public ScrollBoardViewAction setOffset(FJetPoint offset){
        this.offset = offset;
        return this;
    }

    public ScrollBoardViewAction setBoardDrawable(BoardDrawable boardDrawable){
        this.boardDrawable = boardDrawable;
        return this;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    protected IActionResult getExecutionResult() {

        if (this.boardDrawable != null && this.offset != null){
            this.boardDrawable.setOffset(this.offset);
        }

        return new ScrollBoardActionResult(this.offset);
    }

    @Override
    protected IActionUndoResult getExecutionUndoResult() {
        return null;
    }


    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    public class ScrollBoardActionResult implements IActionResult {

        private FJetPoint offset;

        public ScrollBoardActionResult(FJetPoint offset){
            this.offset = offset;
        }

        public FJetPoint getOffset(){
            return this.offset;
        }

    }


    // endregion - Inner and Anonymous Classes

}
