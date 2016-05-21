package org.avv.fjet;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import org.avv.fjet.core.Game;
import org.avv.fjet.graphics.GameView;
import org.avv.fjet.graphics.GameViewThread;

/**
 * Created by Alexander Vorobiev on 21/05/16.
 */
public class GameEngine extends GameViewThread implements GameView.IGameViewObserver {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private Game game;

    // endregion - Fields

    // region - Constructors

    protected GameEngine(Game game, SurfaceHolder surfaceHolder) {
        super(surfaceHolder);

        this.game = game;
    }

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public void drawObjects(Canvas c) {
        //Log.d("GameEngine", "drawObjects -> " + String.valueOf(System.currentTimeMillis()));
    }

    @Override
    protected void doPreDrawingTasks() {
        //Log.d("GameEngine", "doPreDrawingTasks -> " + String.valueOf(System.currentTimeMillis()));
    }

    @Override
    protected void doPostDrawingTasks() {
        //Log.d("GameEngine", "doPostDrawingTasks -> " + String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public void onSurfaceCreated() {
        if (this.getState() == Thread.State.NEW) {
            start();
        }
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
