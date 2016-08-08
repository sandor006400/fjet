package org.avv.fjet.core.board;

import android.content.Context;

import org.avv.fjet.core.board.util.UtilCoordinates;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 17/04/2016
 */
public class BoardFactory {

    // region - Constants

    private static final String DEBUG_TAG = "BoardFactory";

    // endregion - Constants

    // region - Fields

    // endregion - Fields

    // region - Constructors

    private BoardFactory(){

    }

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public static Board createBoard(Context c, Board.BoardType t, Terrain [][] terrains){
        Board b = null;

        if (terrains.length > 0 && terrains[0].length > 0) {
            b = new Board(t, terrains[0].length, terrains.length);

            switch (t) {

                case HEX_CELLS:
                    for (int row = 0; row < terrains.length; row++){
                        for (int col = 0; col < terrains[0].length; col++){
                            ICoords coords = UtilCoordinates.offsetHexCoordsToAxialHexCoords(col, row);
                            b.setCellAndCoords(coords, new Cell(coords, terrains[row][col]));
                        }
                    }
                    break;

                case SQUARE_CELLS:
                    for (int row = 0; row < terrains.length; row++){
                        for (int col = 0; col < terrains[0].length; col++){
                            ICoords coords = new SquareCoords(col, row);
                            b.setCellAndCoords(coords, new Cell(coords, terrains[row][col]));
                        }
                    }
                    break;
            }
        }
        return b;
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
