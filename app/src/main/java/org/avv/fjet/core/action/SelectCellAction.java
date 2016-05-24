package org.avv.fjet.core.action;

import org.avv.fjet.core.board.Board;
import org.avv.fjet.core.board.ICoords;

import java.util.Arrays;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 22/04/2016
 */
public class SelectCellAction extends Action {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private ICoords coords;
    private Board board;

    // endregion - Fields

    // region - Constructors

    public SelectCellAction() {
        super(Type.INFORMATIVE);

    }

    // endregion - Constructors

    // region - Getters and Setters

    public SelectCellAction setCoords(ICoords coords){
        this.coords = coords;
        return this;
    }

    public SelectCellAction setBoard(Board board){
        this.board = board;
        return this;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    protected IActionResult getExecutionResult() {
        this.board.deselectCell();
        this.board.selectCells(new ICoords[]{this.coords});

        if (this.board.getSelectedCells() != null && this.board.getSelectedCells().size() == 1
                && this.board.getSelectedCells().get(0).getCoords().equals(this.coords)){
            return new SelectCellActionResult(coords.getCopy());
        }
        return null;
    }

    @Override
    protected IActionUndoResult getExecutionUndoResult() {
        // This action is only informative, implementation not required
        return null;
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    public class SelectCellActionResult implements IActionResult {

        private ICoords selectedCoords;

        public SelectCellActionResult(ICoords coords){
            this.selectedCoords = coords;
        }

        public ICoords getSelectedCoords(){
            return this.selectedCoords;
        }

    }

    // endregion - Inner and Anonymous Classes

}
