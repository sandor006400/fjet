package com.avv.fjet;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import org.avv.fjet.core.board.Board;
import org.avv.fjet.core.board.BoardFactory;
import org.avv.fjet.core.board.Cell;
import org.avv.fjet.core.board.ICoords;
import org.avv.fjet.core.board.TerrainFactory;
import org.avv.fjet.core.unit.Unit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static org.junit.Assert.assertThat;

/**
 * Created by Alexander Vorobiev on 26/05/16.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class BoardAndroidUnitTest extends InstrumentationTestCase {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private Board hexBoard;
    private Board squareBoard;
    private Context c;
    private TerrainFactory tF;

    // endregion - Fields

    // region - Methods

    @Override
    protected void setUp() throws Exception {
        tF = TerrainFactory.getInstance(getInstrumentation().getContext());
        c = getInstrumentation().getContext();
    }

    @Before
    public void createBoard(){
        if (tF != null) {
            Log.d("TF", "TF no es nulo");
        }
        hexBoard = BoardFactory.createBoard(c,
                Board.BoardType.HEX_CELLS, 10, 10);

        squareBoard = BoardFactory.createBoard(c,
                Board.BoardType.SQUARE_CELLS, 10, 10);
    }

    @Test
    public void hexBoard_jsonSerialization(){
        String jsonBoard = hexBoard.toJsonString();
        Board newBoard = new Board(jsonBoard, c);

        Map<ICoords, Cell> cellsMap = newBoard.getCells();
        Map<String, Unit> unitsMap = newBoard.getUnits();

        //assertThat(jsonBoard, is());
        assertEquals(hexBoard.getCells(), cellsMap);
        assertEquals(hexBoard.getUnits(), unitsMap);
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
