package org.avv.fjet;

import org.avv.fjet.core.board.Board;
import org.avv.fjet.core.board.BoardFactory;
import org.avv.fjet.core.board.Cell;
import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.ICoords;
import org.avv.fjet.core.board.Terrain;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 23/04/2016
 */
public class BoardUnitTest {

    // region - Constants

    @Test
    public void test_selectCell() throws Exception {

        Terrain [][] terrains = new Terrain[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                terrains[i][j] = new Terrain("Test", 1, 1.0f);
            }
        }

        Board b = BoardFactory.createBoard(null, Board.BoardType.HEX_CELLS, terrains);
        HexCoords coords = new HexCoords(1, 1);
        b.selectCells(new ICoords[]{coords});
        HexCoords selectedCoords = null;

        if (b.getSelectedCells() != null && b.getSelectedCells().size() > 0) {
            selectedCoords = (HexCoords) b.getSelectedCells().get(0).getCoords();
        }
        assertEquals(coords, selectedCoords);
    }

    @Test
    public void test_getCell_topLeft() throws Exception {

        Terrain [][] terrains = new Terrain[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                terrains[i][j] = new Terrain("Test", 1, 1.0f);
            }
        }

        Board b = BoardFactory.createBoard(null, Board.BoardType.HEX_CELLS, terrains);
        HexCoords coords = new HexCoords(0, 0);

        Cell selectedCell =  b.getCellWithCoords(coords);
        assertEquals(coords, selectedCell.getCoords());
    }

    @Test
    public void test_getCell_topRight() throws Exception {

        Terrain [][] terrains = new Terrain[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                terrains[i][j] = new Terrain("Test", 1, 1.0f);
            }
        }

        Board b = BoardFactory.createBoard(null, Board.BoardType.HEX_CELLS, terrains);
        HexCoords coords = new HexCoords(2, 0);

        Cell selectedCell = b.getCellWithCoords(coords);
        assertEquals(coords, selectedCell.getCoords());
    }

    @Test
    public void test_getCell_bottomLeft() throws Exception {

        Terrain [][] terrains = new Terrain[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                terrains[i][j] = new Terrain("Test", 1, 1.0f);
            }
        }

        Board b = BoardFactory.createBoard(null, Board.BoardType.HEX_CELLS, terrains);
        HexCoords coords = new HexCoords(-1, 2);

        Cell selectedCell = b.getCellWithCoords(coords);
        assertEquals(coords, selectedCell.getCoords());
    }

    @Test
    public void test_getCell_bottomRight() throws Exception {

        Terrain [][] terrains = new Terrain[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                terrains[i][j] = new Terrain("Test", 1, 1.0f);
            }
        }

        Board b = BoardFactory.createBoard(null, Board.BoardType.HEX_CELLS, terrains);
        HexCoords coords = new HexCoords(1, 2);

        Cell selectedCell = b.getCellWithCoords(coords);
        assertEquals(coords, selectedCell.getCoords());
    }

    // endregion - Constants

}
