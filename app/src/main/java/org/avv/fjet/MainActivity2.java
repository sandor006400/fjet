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

/**
 * Created by Alexander Vorobiev on 21/05/16.
 */
public class MainActivity2 extends Activity {

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

        Log.d("MainActivity2", "onCreate");

        // Hiding title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hiding notification bar
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Board b = BoardFactory.createBoard(this, Board.BoardType.HEX_CELLS, 10, 10);
        Game g = new Game(b);

        this.gameView = new GameView(this);
        this.gameEngine = new GameEngine(g, new BoardDrawable(), this.gameView.getHolder());
        this.gameEngine.setFPS(1);

        this.gameEngine.onCreate();
        this.gameView.setObserver(this.gameEngine);

        // Setting a content view
        setContentView(this.gameView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("MainActivity2", "OnDestroy");
        this.gameEngine.onDestroyed();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("MainActivity2", "onResume");

        this.gameEngine.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("MainActivity2", "onPause");

        this.gameEngine.onPause();
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
