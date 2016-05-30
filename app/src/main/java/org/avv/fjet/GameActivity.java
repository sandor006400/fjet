package org.avv.fjet;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import org.avv.fjet.core.Game;
import org.avv.fjet.core.board.Board;
import org.avv.fjet.core.board.BoardFactory;
import org.avv.fjet.graphics.GameView;
import org.avv.fjet.graphics.board.BoardDrawable;
import org.avv.fjet.graphics.board.BoardDrawableFactory;

/**
 * Created by Alexander Vorobiev on 21/05/16.
 */
public class GameActivity extends Activity {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private GameView gameView;
    private GameEngine gameEngine;

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

        Board b = BoardFactory.createBoard(this, Board.BoardType.HEX_CELLS, 2, 2);
        Game g = new Game(b);
        BoardDrawable boardDrawable = BoardDrawableFactory.getInstance().createBoardDrawable(b, 300, 300, 50);

        this.gameView = new GameView(this);
        this.gameEngine = new GameEngine(g, boardDrawable, this.gameView.getHolder());
        this.gameEngine.setFPS(1);

        this.gameEngine.onCreate();
        this.gameView.setObserver(this.gameEngine);

        // Setting a content view
        setContentView(this.gameView);
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
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("GameActivity", "onPause");

        this.gameEngine.onPause();
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}