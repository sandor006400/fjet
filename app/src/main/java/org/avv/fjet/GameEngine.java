package org.avv.fjet;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import org.avv.fjet.core.Game;
import org.avv.fjet.core.action.Action;
import org.avv.fjet.core.action.ActionFactory;
import org.avv.fjet.core.action.SelectCellAction;
import org.avv.fjet.core.board.Board;
import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.ICoords;
import org.avv.fjet.core.geometry.FJetPoint;
import org.avv.fjet.core.board.SquareCoords;
import org.avv.fjet.core.board.util.UtilCoordinates;
import org.avv.fjet.graphics.GameView;
import org.avv.fjet.graphics.GameViewThread;
import org.avv.fjet.graphics.board.BoardDrawable;
import org.avv.fjet.graphics.util.UtilCellDrawing;
import org.avv.fjet.touch.FJetTouchListener;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Alexander Vorobiev on 21/05/16.
 */
public class GameEngine extends GameViewThread implements GameView.IGameViewObserver, Action.ActionObserver, FJetTouchListener.IFjetTouchListenerDelegate {

    // region - Constants

    static final float DEFAULT_SCALE = 1.0f;

    static final int TOUCH_EVENT = 1;

    // endregion - Constants

    // region - Fields

    float scale = DEFAULT_SCALE;
    int edgeSize = 80;

    private final BlockingQueue<Action> actions;
    private Game game;
    private BoardDrawable boardDrawable;

    // endregion - Fields

    // region - Constructors

    public GameEngine(Game game, BoardDrawable boardDrawable, SurfaceHolder surfaceHolder) {
        super(surfaceHolder);

        this.actions = new LinkedBlockingQueue<>();
        this.game = game;
        this.boardDrawable = boardDrawable;
    }

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public void drawObjects(Canvas c) {
        this.boardDrawable.draw(c);


        FJetPoint p;
        float currentEdgeSize = this.edgeSize * this.scale;

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
        // Executes all pending actions
        Action currentAction;

        synchronized (this.actions) {
            while ((currentAction = this.actions.poll()) != null) {
                Log.d("----->", "There is an available Action!!!");
                this.game.processAction(currentAction);
            }
        }
    }

    @Override
    protected void doPostDrawingTasks() {
        //Log.d("GameEngine", "doPostDrawingTasks -> " + String.valueOf(System.currentTimeMillis()));
    }

    @Override
    protected void doInitialTasks() {
        Log.d("----------->", "doInitialTasks");
    }

    @Override
    public void onSurfaceCreated() {
        if (this.getState() == Thread.State.NEW) {
            start();
        }
    }

    @Override
    public void receiveActionResult(Action.IActionResult result) {
        Log.d("---->", "ActionResut received!!!!!");
    }

    @Override
    public void receiveActionUndoResult(Action.IActionUndoResult undoResult) {

    }

    @Override
    public boolean processTouchEvent(MotionEvent event) {
        Log.d("---->" , "processTouchEvent");

        if (this.handler != null) {
            Message msg = this.handler.obtainMessage();
            msg.what = TOUCH_EVENT;
            msg.obj = event;
            this.handler.sendMessage(msg);
        }
        return true;
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    private void addActionToQueue(Action a) {

        if (a != null){
            synchronized (this.actions) {
                this.actions.add(a);
            }
        }
    }

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){

                case TOUCH_EVENT:
                    Log.d("handleMessage", "TOUCH_EVENT");
                    Action action = getActionWithEvent((MotionEvent)msg.obj);
                    GameEngine.this.addActionToQueue(action);
                    break;
            }
        }

        private Action getActionWithEvent(MotionEvent event){
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    return null;

                case MotionEvent.ACTION_MOVE:
                    return null;

                case MotionEvent.ACTION_UP:
                    SelectCellAction a = (SelectCellAction) ActionFactory.createAction(
                            ActionFactory.SELECT_CELL_ACTION);
                    FJetPoint p = new FJetPoint((int) event.getX(), (int) event.getY());

                    if (a != null) {
                        ICoords coords = null;

                        if (game.getBoard().getType() == Board.BoardType.HEX_CELLS) {
                            coords = UtilCoordinates.hexCoordsFromPixel(
                                    p.getX(),
                                    p.getY(),
                                    edgeSize);

                        } else if (game.getBoard().getType() == Board.BoardType.SQUARE_CELLS) {
                            coords = UtilCoordinates.squareCoordsFromPixelCoords(
                                    p.getX(),
                                    p.getY(),
                                    edgeSize);
                        }

                        if (coords != null) {
                            a.setBoard(game.getBoard())
                                    .setCoords(coords)
                                    .setObserver(GameEngine.this);
                        }
                    }
                    return a;

                default:
                    return null;
            }
        }
    };

    // endregion - Inner and Anonymous Classes

}
