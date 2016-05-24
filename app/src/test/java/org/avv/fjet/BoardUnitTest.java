package org.avv.fjet;

import org.avv.fjet.core.board.Board;
import org.avv.fjet.core.board.BoardFactory;
import org.avv.fjet.core.board.Cell;
import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.ICoords;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 23/04/2016
 */
public class BoardUnitTest {

    // region - Constants

    /*
    @Test
    public void test_selectCell() throws Exception {

        Board b = BoardFactory.createBoard(null, BoardFactory.BoardType.HEX_CELLS, 3, 3);
        HexCoords coords = new HexCoords(1, 1);
        b.selectCells(new ICoords[]{coords});
        HexCoords selectedCoords = null;

        if (b.getSelectedCells() != null && b.getSelectedCells().size() > 0) {
            selectedCoords = (HexCoords) b.getSelectedCells().get(0).getCoords();
        }
        assertEquals(coords, selectedCoords);
    }

    @Test
    public void test_getCell() throws Exception {

        Board b = BoardFactory.createBoard(null, BoardFactory.BoardType.HEX_CELLS, 3, 3);
        HexCoords coords = new HexCoords(1, 1);

        Cell selectedCell = (Cell) b.getCellWithCoords(coords);
        assertEquals(coords, selectedCell.getCoords());
    }
    */

    // endregion - Constants

}
