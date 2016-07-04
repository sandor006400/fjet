package org.avv.fjet;

import android.util.Log;

import org.avv.fjet.core.Game;
import org.avv.fjet.core.action.Action;
import org.avv.fjet.core.action.ActionFactory;
import org.avv.fjet.core.action.MoveUnitAction;
import org.avv.fjet.core.action.SelectCellAction;
import org.avv.fjet.core.action.ShowPathAction;
import org.avv.fjet.core.board.Cell;
import org.avv.fjet.core.board.ICoords;
import org.avv.fjet.core.rule.GameRules;
import org.avv.fjet.core.unit.Unit;

/**
 * Created by Alexander Vorobiev on 26/06/16.
 */
public class ExampleGameRules extends GameRules {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private int touchesCount;
    private GameEngine gameEngine;

    // endregion - Fields

    // region - Constructors

    public ExampleGameRules(Game game, GameEngine gameEngine) {
        super(game);

        this.gameEngine = gameEngine;
        this.touchesCount = 0;
    }

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public Action getActionWithOnTapDown(ICoords coords) {


        return null;
    }

    @Override
    public Action getActionWithOnTapUp(ICoords coords) {

        if (this.game.getBoard().getSelectedCells().contains(coords)) {
            MoveUnitAction action = new MoveUnitAction();
            Unit u = this.game.getBoard().getCellsWithCoords(new ICoords[]{coords}).get(0).getUnit();
            action.setUnit(u);
            action.setGameEngine(this.gameEngine);
            action.setDestination(coords);
            return action;

        } else {

            if (this.touchesCount == 1) {
                this.touchesCount = 0;
                if (this.game.getBoard().getSelectedCells().size() == 1) {

                    Log.d("------->", "calculating a path");
                    Cell cellDestination = this.game.getBoard().getCellWithCoords(coords);
                    Cell cellOrigin = this.game.getBoard().getSelectedCells().get(0);
                    ShowPathAction action = (ShowPathAction) ActionFactory.createAction(
                            ActionFactory.SHOW_MOVEMENT_PATH_ACTION);
                    action.setBoard(this.game.getBoard())
                            .setOrigin(cellOrigin)
                            .setDestination(cellDestination);
                    return action;
                }

            } else {

                if (this.game.getBoard().getCellWithCoords(coords).getUnit() != null) {
                    Log.d("------->", "selecting a cell");
                    this.touchesCount++;
                    SelectCellAction action = (SelectCellAction) ActionFactory.createAction(
                            ActionFactory.SELECT_CELL_ACTION);
                    action.setBoard(this.game.getBoard())
                            .setCoords(coords);
                    return action;
                }
            }
        }
        return null;
    }

    @Override
    public Action getActionWithOnLongPress(ICoords coords) {
        return null;
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
