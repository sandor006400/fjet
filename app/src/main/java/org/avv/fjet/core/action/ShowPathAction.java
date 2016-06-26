package org.avv.fjet.core.action;

import org.avv.fjet.core.board.Board;
import org.avv.fjet.core.board.Cell;
import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.ICoords;
import org.avv.fjet.core.board.SquareCoords;
import org.avv.fjet.core.board.util.UtilCoordinates;
import org.avv.fjet.graph.FJetCellsGraph;
import org.avv.fjet.graph.UtilPathFindingAlgorithms;

import java.util.List;

/**
 * Created by Alexander Vorobiev on 26/06/16.
 */
public class ShowPathAction extends Action {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private Cell origin;
    private Cell destination;
    private Board board;

    // endregion - Fields

    // region - Constructors

    public ShowPathAction() {
        super(Type.INFORMATIVE);

    }

    // endregion - Constructors

    // region - Getters and Setters

    public ShowPathAction setOrigin(Cell cell){
        this.origin = cell;
        return this;
    }

    public ShowPathAction setDestination(Cell cell){
        this.destination = cell;
        return this;
    }

    public ShowPathAction setBoard(Board board){
        this.board = board;
        return this;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    protected IActionResult getExecutionResult() {
        List<Cell> pathCells = getPathCells();

        if (pathCells != null) {
            this.board.deselectCell();
            this.board.selectCells(pathCells);
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

    private List<Cell> getPathCells(){

        if (this.origin != null && this.destination != null){
            int distance = getDistanceBetweenCells();
            ICoords [] coords = getCoordsInMovementRange(distance);
            List<Cell> cells = this.board.getCellsWithCoords(coords);
            FJetCellsGraph graph = new FJetCellsGraph(cells);

            return UtilPathFindingAlgorithms.aStar(
                    this.origin,
                    this.destination,
                    graph);
        }
        return null;
    }

    private int getDistanceBetweenCells(){
        if (this.origin.getCoords() instanceof HexCoords
                && this.destination.getCoords() instanceof HexCoords){
            return UtilCoordinates.distanceBetweenHexCoords(
                    (HexCoords) this.origin.getCoords(),
                    (HexCoords) this.destination.getCoords());
        } else {
            return UtilCoordinates.distanceBetweenSquareCoords(
                    (SquareCoords) this.origin.getCoords(),
                    (SquareCoords) this.destination.getCoords());
        }
    }

    private ICoords [] getCoordsInMovementRange(int range){
        return this.origin.getCoords().getCoordsInRangeArray(range);
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    public class ShowPathActionResult implements Action.IActionResult {

        private List<Cell> cells;

        public ShowPathActionResult(List<Cell> cells){
            this.cells = cells;
        }

        public List<Cell> getPathCells(){
            return this.cells;
        }

    }

    // endregion - Inner and Anonymous Classes

}
