package org.avv.fjet.graphics.board;

import android.content.Context;

import org.avv.fjet.core.board.Board;
import org.avv.fjet.core.board.Cell;
import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.ICoords;
import org.avv.fjet.core.board.SquareCoords;
import org.avv.fjet.core.board.util.UtilCoordinates;
import org.avv.fjet.core.geometry.FJetPoint;
import org.avv.fjet.core.geometry.FJetRect;
import org.avv.fjet.core.unit.Unit;
import org.avv.fjet.graphics.unit.UnitDrawable;
import org.avv.fjet.graphics.unit.UnitDrawableFactory;

/**
 * Created by Alexander Vorobiev on 29/05/16.
 */
public class BoardDrawableFactory {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private static BoardDrawableFactory INSTANCE = null;

    // endregion - Fields

    // region - Constructors

    private BoardDrawableFactory(){

    }

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public static BoardDrawableFactory getInstance(){
        if (INSTANCE == null){
            INSTANCE = new BoardDrawableFactory();
        }
        return INSTANCE;
    }

    /**
     * Generates BoardDrawable from Board
     * @param context
     * @param board
     * @return
     */
    public BoardDrawable createBoardDrawable(Context context, Board board, int edgeSize, int unitEdgeSize){
        BoardDrawable boardDrawable = new BoardDrawable(edgeSize);
        int width = 0;
        int height = 0;

        // First, board width and heigt are calculated
        if (board.getBoardType().equals(Board.BoardType.HEX_CELLS)){
            FJetPoint size = UtilCoordinates.hexCoordsSize(edgeSize);
            width = size.getX() * board.getWidth();
            height = size.getY() * board.getHeight();

        } else {    // The board have Square cells
            width = edgeSize * board.getWidth();
            height = edgeSize * board.getHeight();
        }

        // Setting the bounds rect
        boardDrawable.setBoardBounds(new FJetRect(0, 0, width, height));

        // Initilizing CellDrawables
        for (Object cell : board.getCells()){
            CellDrawable cellDrawable = new CellDrawable();

            ICoords coords = ((Cell)cell).getCoords();

            if (coords instanceof HexCoords) {
                cellDrawable.setSize(UtilCoordinates.calculateHexCellSize(edgeSize));
                cellDrawable.setPosition(UtilCoordinates.hexCoordsToPixel(edgeSize, (HexCoords)coords));
                cellDrawable.setDrawable(((Cell)cell).getTerrain().getDrawableHe());

            } else if (coords instanceof SquareCoords) {
                cellDrawable.setSize(new FJetPoint(edgeSize, edgeSize));
                cellDrawable.setPosition(UtilCoordinates.squareCoordsToPixel(edgeSize, (SquareCoords) coords));
                cellDrawable.setDrawable(((Cell)cell).getTerrain().getDrawableSq());
            }
            boardDrawable.addCellDrawable(((Cell) cell).getId(), cellDrawable);
        }

        // Initilizing UnitDrawables
        for (Object unit : board.getUnits().values()){
            UnitDrawable unitDrawable = UnitDrawableFactory.getInstance(context).createUnitDrawable(((Unit)unit).getUnitType());
            ICoords unitCoords = ((Unit)unit).getCell().getCoords();

            if (unitCoords instanceof HexCoords){
                unitDrawable.setSize(UtilCoordinates.calculateHexCellSize(unitEdgeSize));
                unitDrawable.setPosition(UtilCoordinates.hexCoordsToPixel(edgeSize, (HexCoords) unitCoords));

            } else if (unitCoords instanceof SquareCoords) {
                unitDrawable.setSize(new FJetPoint(unitEdgeSize, unitEdgeSize));
                unitDrawable.setPosition(UtilCoordinates.squareCoordsToPixel(edgeSize, (SquareCoords) unitCoords));
            }
            boardDrawable.addUnitDrawable(((Unit)unit).getId(), unitDrawable);
        }
        return boardDrawable;
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
