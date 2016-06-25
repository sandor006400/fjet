package org.avv.fjet.core.board;

import android.content.Context;
import android.util.Log;

import org.avv.fjet.core.board.util.UtilCoordinates;

import java.util.Map;

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
        Board b = new Board(t);

        if (terrains.length > 0 && terrains[0].length > 0) {
            switch (t) {

                case HEX_CELLS:
                    for (int i = 0; i < terrains.length; i++){
                        for (int j = 0; j < terrains[0].length; j++){
                            ICoords coords = UtilCoordinates.offsetHexCoordsToAxialHexCoords(i, j);
                            b.setCellAndCoords(coords, new Cell(coords, terrains[i][j]));
                        }
                    }
                    break;

                case SQUARE_CELLS:
                    for (int i = 0; i < terrains.length; i++){
                        for (int j = 0; j < terrains[0].length; j++){
                            ICoords coords = new SquareCoords(i, j);
                            b.setCellAndCoords(coords, new Cell(coords, terrains[i][j]));
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
