package org.avv.fjet.core.board;

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

    public enum BoardType {
        HEX_CELLS,
        SQUARE_CELLS
    }

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

    public static Board createBoard(BoardType t, int width, int height){
        Board b = new Board();

        if (width != 0 && height != 0) {
            switch (t) {

                case HEX_CELLS:
                    //Log.d(DEBUG_TAG, "createBoard, HEX_CELLS");
                    for (int i = 0; i < height; i++){
                        for (int j = 0; j < width; j++){
                            ICoords coords = UtilCoordinates.offsetHexCoordsToAxialHexCoords(i, j);
                            b.setCellAndCoords(
                                    coords,
                                    new Cell(coords));
                        }
                    }
                    break;

                case SQUARE_CELLS:
                    //Log.d(DEBUG_TAG, "createBoard, SQUARE_CELLS");
                    for (int i = 0; i < height; i++){
                        for (int j = 0; j < width; j++){
                            ICoords coords = new SquareCoords(i, j);
                            b.setCellAndCoords(
                                    coords,
                                    new Cell(coords));
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