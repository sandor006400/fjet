package org.avv.fjet;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import org.avv.fjet.core.Game;
import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.ICoords;
import org.avv.fjet.core.geometry.FJetPoint;
import org.avv.fjet.core.board.SquareCoords;
import org.avv.fjet.core.board.util.UtilCoordinates;
import org.avv.fjet.graphics.GameView;
import org.avv.fjet.graphics.GameViewThread;
import org.avv.fjet.graphics.board.BoardDrawable;
import org.avv.fjet.graphics.util.UtilCellDrawing;

/**
 * Created by Alexander Vorobiev on 21/05/16.
 */
public class GameEngine extends GameViewThread implements GameView.IGameViewObserver {

    // region - Constants

    static final float DEFAULT_SCALE = 1.0f;

    // endregion - Constants

    // region - Fields

    float scale = DEFAULT_SCALE;
    int edgeSize = 30;

    private Game game;
    private BoardDrawable boardDrawable;

    // endregion - Fields

    // region - Constructors

    protected GameEngine(Game game, BoardDrawable boardDrawable, SurfaceHolder surfaceHolder) {
        super(surfaceHolder);

        this.game = game;
        this.boardDrawable = boardDrawable;
    }

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public void drawObjects(Canvas c) {



        FJetPoint p;
        float currentEdgeSize = this.edgeSize*this.scale;

        Paint paint = new Paint();
        paint.setStrokeWidth(3);
        paint.setColor(Color.BLACK);

        for (Object coords : game.getBoard().getCells().keySet()) {

            if (coords instanceof HexCoords) {
                UtilCellDrawing.drawHexCellEdge(c, this.edgeSize, this.scale, (HexCoords) coords, Color.BLACK, 3f);
                p = UtilCoordinates.hexCoordsToPixel(currentEdgeSize, (HexCoords) coords);

            } else {
                UtilCellDrawing.drawSquareCellEdge(c, this.edgeSize, this.scale, (SquareCoords) coords, Color.BLACK, 3f);
                p = UtilCoordinates.squareCoordsToPixel(currentEdgeSize, (SquareCoords) coords);
            }
            paint.setTextSize(12);
            paint.setColor(Color.BLACK);
            c.drawText(((ICoords) coords).toShortString(), p.getX() - currentEdgeSize / 2, p.getY(), paint);
        }
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
