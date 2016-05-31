package org.avv.fjet;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import org.avv.fjet.core.Game;
import org.avv.fjet.core.action.Action;
import org.avv.fjet.core.action.ActionFactory;
import org.avv.fjet.core.action.ScrollBoardViewAction;
import org.avv.fjet.core.action.SelectCellAction;
import org.avv.fjet.core.board.Board;
import org.avv.fjet.core.board.Cell;
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

    static final int TOUCH_EVENT_TAP_UP = 1;
    static final int TOUCH_EVENT_TAP_DOWN = 2;
    static final int TOUCH_EVENT_SCROLL = 3;

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

        /*for (Object coords : game.getBoard().getCells().keySet()) {

            if (coords instanceof HexCoords) {
                //UtilCellDrawing.drawHexCellEdge(c, this.edgeSize, this.scale,
                //        (HexCoords) coords, Color.BLACK, 3f, this.boardDrawable.getOffset());
                p = UtilCoordinates.hexCoordsToPixel(currentEdgeSize, (HexCoords) coords);

            } else {
                //UtilCellDrawing.drawSquareCellEdge(c, this.edgeSize, this.scale,
                //        (SquareCoords) coords, Color.BLACK, 3f, this.boardDrawable.getOffset());
                p = UtilCoordinates.squareCoordsToPixel(currentEdgeSize, (SquareCoords) coords);
            }
            paint.setTextSize(13);
            paint.setColor(Color.BLACK);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            c.drawText(
                    ((ICoords) coords).toShortString(),
                    p.getX() - currentEdgeSize / 2 + this.boardDrawable.getOffset().getX(),
                    p.getY() + this.boardDrawable.getOffset().getY(), paint);
        }*/

        drawSelectedCells(c);
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

        if (result instanceof SelectCellAction.SelectCellActionResult){

        }
    }

    @Override
    public void receiveActionUndoResult(Action.IActionUndoResult undoResult) {

    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("--->", "onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("--->", "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("--->", "onSingleTapUp");

        if (this.handler != null) {
            Message msg = this.handler.obtainMessage();
            msg.what = TOUCH_EVENT_TAP_UP;
            msg.obj = e;
            this.handler.sendMessage(msg);
        }
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d("--->", "onScroll");

        if (this.handler != null) {
            Message msg = this.handler.obtainMessage();
            msg.what = TOUCH_EVENT_SCROLL;
            msg.obj = new FJetPoint((int)-distanceX, (int)-distanceY);
            this.handler.sendMessage(msg);
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d("--->", "onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("--->", "onFling");
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

    private void drawSelectedCells(Canvas c){
        FJetPoint p;
        float currentEdgeSize = this.edgeSize * this.scale;

        Paint paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setColor(Color.GREEN);

        for (Object cell : game.getBoard().getSelectedCells()) {

            ICoords coords = ((Cell)cell).getCoords();
            FJetPoint offset = boardDrawable.getOffset();

            if (coords instanceof HexCoords) {
                UtilCellDrawing.drawHexCellEdge(c, this.edgeSize, this.scale, (HexCoords) coords, Color.GREEN, 5f, offset);
                UtilCoordinates.hexCoordsToPixel(currentEdgeSize, (HexCoords) coords);

            } else {
                UtilCellDrawing.drawSquareCellEdge(c, this.edgeSize, this.scale, (SquareCoords) coords, Color.GREEN, 5f, offset);
                UtilCoordinates.squareCoordsToPixel(currentEdgeSize, (SquareCoords) coords);
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

                case TOUCH_EVENT_TAP_UP:
                    Log.d("handleMessage", "TOUCH_EVENT");
                    Action action = getActionWithEvent((MotionEvent)msg.obj);
                    GameEngine.this.addActionToQueue(action);
                    break;

                case TOUCH_EVENT_SCROLL:
                    Log.d("handleMessage", "TOUCH_EVENT_SCROLL");
                    Action actionScroll = getScrollAction((FJetPoint)msg.obj);
                    GameEngine.this.addActionToQueue(actionScroll);
                    break;
            }
        }

        private Action getActionWithEvent(MotionEvent event){
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    return null;

                case MotionEvent.ACTION_UP:
                    SelectCellAction a = (SelectCellAction) ActionFactory.createAction(
                            ActionFactory.SELECT_CELL_ACTION);
                    FJetPoint point = new FJetPoint((int) event.getX(), (int) event.getY());

                    if (a != null) {
                        ICoords coords = null;
                        FJetPoint offset = boardDrawable.getOffset();

                        if (game.getBoard().getType() == Board.BoardType.HEX_CELLS) {
                            coords = UtilCoordinates.hexCoordsFromPixel(
                                    point.getX() - offset.getX(),
                                    point.getY() - offset.getY(),
                                    edgeSize);

                        } else if (game.getBoard().getType() == Board.BoardType.SQUARE_CELLS) {
                            coords = UtilCoordinates.squareCoordsFromPixelCoords(
                                    point.getX() - offset.getX(),
                                    point.getY()- offset.getY(),
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

        private Action getScrollAction(FJetPoint p) {
            ScrollBoardViewAction aScroll = (ScrollBoardViewAction) ActionFactory.createAction(
                    ActionFactory.SCROLL_BOARD_VIEW_ACTION);

            if (aScroll != null) {
                aScroll.setOffset(p)
                        .setBoardDrawable(GameEngine.this.boardDrawable)
                        .setObserver(GameEngine.this);
            }
            return aScroll;
        }

    };

    // endregion - Inner and Anonymous Classes

}
