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

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    protected Object getExecutionResult() {
        this.unit.getCell().removeUnit();
        this.unit.setCell(this.board.getCellWithCoords(this.destination));
        return null;
    }

    @Override
    protected Object getExecutionUndoResult() {
        this.unit.getCell().removeUnit();
        this.unit.setCell(this.board.getCellWithCoords(this.origin));
        return null;
    }


    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
