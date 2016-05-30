package org.avv.fjet.graphics.board;

import android.graphics.Path;

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
import org.avv.fjet.graphics.util.UtilCellDrawing;

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
     * @param board
     * @param width
     * @param height
     * @return
     */
    public BoardDrawable createBoardDrawable(Board board, int width, int height, int edgeSize){
        BoardDrawable boardDrawable = new BoardDrawable();

        // Setting the bounds rect
        boardDrawable.setBoardBounds(new FJetRect(0, 0, width, height));

        // Initilizing CellDrawables
        for (Object cell : board.getCells().values()){
            CellDrawable cellDrawable = new CellDrawable();

            ICoords coords = ((Cell)cell).getCoords();
            cellDrawable.setDrawable(((Cell)cell).getTerrain().getDrawable());

            if (coords instanceof HexCoords) {
                cellDrawable.setSize(UtilCoordinates.calculateHexCellSize(edgeSize));
                cellDrawable.setPosition(UtilCoordinates.hexCoordsToPixel(edgeSize, (HexCoords)coords));

            } else if (coords instanceof SquareCoords) {
                cellDrawable.setSize(new FJetPoint(edgeSize, edgeSize));
                cellDrawable.setPosition(UtilCoordinates.squareCoordsToPixel(edgeSize, (SquareCoords) coords));
            }
            boardDrawable.addCellDrawable(((Cell) cell).getId(), cellDrawable);
        }

        // Initilizing UnitDrawables
        for (Object unit : board.getUnits().values()){
            UnitDrawable unitDrawable = new UnitDrawable();
            boardDrawable.addUnitDrawable(((Unit)unit).getId(), unitDrawable);
        }

        return boardDrawable;
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
