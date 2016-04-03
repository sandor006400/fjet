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
    private ICoords destiny;
    private Board board;

    // endregion - Fields

    // region - Constructors

    public MoveUnitAction(Unit u, ICoords d, Board b) {
        super(Type.EXECUTIVE);

        this.unit = u;
        this.origin = u.getCell().getCoords();
        this.destiny = d;
        this.board = b;
    }

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    protected ActionResult getExecutionResult() {
        this.unit.getCell().removeUnit();
        this.unit.setCell(this.board.getCellWithCoords(this.destiny));
        return null;
    }

    @Override
    protected ActionUndoResult getExecutionUndoResult() {
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
