package com.avv.fjet;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import org.avv.fjet.core.board.Board;
import org.avv.fjet.core.board.Cell;
import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.Terrain;
import org.avv.fjet.core.board.TerrainFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Created by Alexander Vorobiev on 26/05/16.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class CellAndroidUnitTest extends InstrumentationTestCase {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private Cell cell;
    private HexCoords coords;
    private Terrain terrain;
    private Context c;

    // endregion - Fields

    // region - Methods

    @Override
    protected void setUp() throws Exception {
        c = getInstrumentation().getContext();
    }

    @Before
    public void createCell() throws Exception {
        Terrain newTerrain = new Terrain("Grassland", new ShapeDrawable(), new ShapeDrawable(), 1, 1);
        TerrainFactory.getInstance(c).addNewTerrain(newTerrain.getType(), newTerrain);
        terrain = TerrainFactory.getInstance(c).getTerrain("Grassland");
        coords = new HexCoords(4, 5);
        cell = new Cell(coords, terrain);
    }

    /*@Test
    public void cell_jsonSerialization() throws Exception {
        Cell.CellData data = cell.getCellData();
        String json = data.toJson();

        Cell.CellData newCellData = new Cell.CellData();
        Cell newCell = new Cell(json, c, Board.BoardType.HEX_CELLS);

        assertEquals(cell, newCell);
    }*/

    @Test
    public void cell_jsonSerialization() throws Exception {
        Cell.CellData data = cell.getCellData();
        String json = data.toJson();
        Cell.CellData newCellData = new Cell.CellData();

        assertEquals(data, newCellData);
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
