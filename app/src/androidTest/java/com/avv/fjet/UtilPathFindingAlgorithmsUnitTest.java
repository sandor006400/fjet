package com.avv.fjet;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import org.avv.fjet.core.board.Cell;
import org.avv.fjet.core.board.SquareCoords;
import org.avv.fjet.core.board.Terrain;
import org.avv.fjet.core.board.TerrainFactory;
import org.avv.fjet.algorithms.FJetCellsGraph;
import org.avv.fjet.algorithms.UtilPathFindingAlgorithms;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Vorobiev on 26/06/16.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class UtilPathFindingAlgorithmsUnitTest extends InstrumentationTestCase {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private Context c;
    private TerrainFactory tF;

    // endregion - Fields

    // region - Constructors

    @Override
    protected void setUp() throws Exception {
        tF = TerrainFactory.getInstance();
        c = getInstrumentation().getContext();
    }

    @Test
    public void aStar_sameMovementCost(){
        SquareCoords[] rangeNCoords = {
                new SquareCoords(3,2),
                new SquareCoords(2,3), new SquareCoords(3,3), new SquareCoords(4,3),
                new SquareCoords(1,4), new SquareCoords(2,4), new SquareCoords(3,4), new SquareCoords(4,4), new SquareCoords(5,4),
                new SquareCoords(2,5), new SquareCoords(3,5), new SquareCoords(4,5),
                new SquareCoords(3,6)
        };

        List<Cell> cells = new ArrayList<>();
        Terrain newTerrain = new Terrain("Grassland", new ShapeDrawable(), new ShapeDrawable(), 1, 1);
        TerrainFactory.getInstance().addNewTerrain(newTerrain.getType(), newTerrain);
        Terrain t = tF.getTerrain(newTerrain.getType());   // Al cells must have the same movement cost

        for (SquareCoords coords : rangeNCoords){
            cells.add(new Cell(coords, t));
        }
        FJetCellsGraph graph = new FJetCellsGraph(cells);

        // Origin -> coords(2,3) -> index: 1
        // Destination -> coords(4,4) -> index: 7
        List<Cell> path = UtilPathFindingAlgorithms.aStar(cells.get(1), cells.get(7), graph);

        //assertEquals();
    }

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
