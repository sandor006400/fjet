package org.avv.fjet.core.action;

import org.avv.fjet.core.board.Board;
import org.avv.fjet.core.board.ICoords;
import org.avv.fjet.core.unit.Unit;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 03/04/2016
 */
public class MoveUnitAction extends Action {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private Unit unit;
    private ICoords origin;
    private ICoords destination;
    private ICoords [] pathCoords;  // Coordinates that belong to a Unit movement path
    private Board board;

    // endregion - Fields

    // region - Constructors

    public MoveUnitAction() {
        super(Type.EXECUTIVE);

    }

    // endregion - Constructors

    // region - Getters and Setters

    public MoveUnitAction setUnit(Unit unit){
        this.unit = unit;
        this.origin = unit.getCell().getCoords();
        return this;
    }

    public MoveUnitAction setDestination(ICoords coords){
        this.destination = coords;
        return this;
    }

    public MoveUnitAction setBoard(Board board){
        this.board = board;
        return this;
    }

    public MoveUnitAction setPathCoords(ICoords [] pathCoords){
        this.pathCoords = pathCoords;
        return this;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    protected IActionResult getExecutionResult() {
        this.unit.getCell().removeUnit();
        this.unit.setCell(this.board.getCellWithCoords(this.destination));
        return new MoveUnitActionResult(this.unit, this.pathCoords);
    }

    @Override
    protected IActionUndoResult getExecutionUndoResult() {
        this.unit.getCell().removeUnit();
        this.unit.setCell(this.board.getCellWithCoords(this.origin));
        return null;
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    public class MoveUnitActionResult implements IActionResult {

        private Unit unit;
        private ICoords [] pathCoords;

        public MoveUnitActionResult(Unit unit, ICoords [] pathCoords){
            this.unit = unit;
            this.pathCoords = pathCoords;
        }

        public Unit getUnit(){
            return this.unit;
        }

        public ICoords [] getPathCoords(){
            return this.pathCoords;
        }

    }

    // endregion - Inner and Anonymous Classes

}
