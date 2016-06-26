package org.avv.fjet;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import org.avv.fjet.core.Game;
import org.avv.fjet.core.board.Board;
import org.avv.fjet.core.board.BoardFactory;
import org.avv.fjet.core.board.Cell;
import org.avv.fjet.core.board.SquareCoords;
import org.avv.fjet.core.board.Terrain;
import org.avv.fjet.core.board.TerrainFactory;
import org.avv.fjet.graph.FJetCellsGraph;
import org.avv.fjet.graph.UtilPathFindingAlgorithms;
import org.avv.fjet.graphics.GameView;
import org.avv.fjet.graphics.board.BoardDrawable;
import org.avv.fjet.graphics.board.BoardDrawableFactory;
import org.avv.fjet.touch.FJetTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Vorobiev on 21/05/16.
 */
public class GameActivity extends Activity {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private GameView gameView;
    private GameEngine gameEngine;
    private FJetTouchListener touchListener;

    // endregion - Fields

    // region - Constructors

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("GameActivity", "onCreate");

        // Hiding title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hiding notification bar
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Terrain[][] terrains = new Terrain[50][50];

        for (int i = 0; i < terrains.length; i++){
            for (int j = 0; j < terrains[0].length; j++){
                terrains[i][j] = TerrainFactory.getInstance(this).getRandomTerrain();
            }
        }

        Board b = BoardFactory.createBoard(this, Board.BoardType.SQUARE_CELLS, terrains);
        Game g = new Game(b);
        BoardDrawable boardDrawable = BoardDrawableFactory.getInstance().createBoardDrawable(b, 300, 300, 80);

        this.gameView = new GameView(this);
        this.gameEngine = new GameEngine(g, boardDrawable, this.gameView.getHolder(), null);
        this.gameEngine.setFPS(1);

        this.touchListener = new FJetTouchListener(this.gameEngine, this);
        this.gameView.setOnTouchListener(touchListener);

        this.gameEngine.onCreate();
        this.gameView.setObserver(this.gameEngine);

        // Setting a content view
        //setContentView(this.gameView);
        setContentView(R.layout.activity_main_2);

        View v  = findViewById(R.id.surfaceView);
        ViewGroup parent = (ViewGroup) v.getParent();
        int index = parent.indexOfChild(v);
        parent.removeView(v);
        parent.addView(this.gameView, index);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("GameActivity", "OnDestroy");
        this.gameEngine.onDestroyed();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("GameActivity", "onResume");

        this.gameEngine.onResume();

        pruebecilla();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("GameActivity", "onPause");

        this.gameEngine.onPause();
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    private Point getWindowSize(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    private void pruebecilla(){
        SquareCoords[] rangeNCoords = {
                new SquareCoords(3,2),
                new SquareCoords(2,3), new SquareCoords(3,3), new SquareCoords(4,3),
                new SquareCoords(1,4), new SquareCoords(2,4), new SquareCoords(3,4), new SquareCoords(4,4), new SquareCoords(5,4),
                new SquareCoords(2,5), new SquareCoords(3,5), new SquareCoords(4,5),
                new SquareCoords(3,6)
        };

        List<Cell> cells = new ArrayList<>();
        Terrain t = TerrainFactory.getInstance(this).getTerrain(Terrain.TerrainType.GRASSLAND);   // Al cells must have the same movement cost

        for (SquareCoords coords : rangeNCoords){
            cells.add(new Cell(coords, t));
        }
        FJetCellsGraph graph = new FJetCellsGraph(cells);

        // Origin -> coords(2,3) -> index: 1
        // Destination -> coords(4,4) -> index: 7
        List<Cell> path = UtilPathFindingAlgorithms.aStar(cells.get(1), cells.get(7), graph);
        Log.d("---->", path.toString());
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
