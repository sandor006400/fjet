package org.avv.fjet;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;

import org.avv.fjet.core.Game;
import org.avv.fjet.core.action.Action;
import org.avv.fjet.core.action.ActionFactory;
import org.avv.fjet.core.action.ScaleViewAction;
import org.avv.fjet.core.action.ScrollBoardViewAction;
import org.avv.fjet.core.board.Board;
import org.avv.fjet.core.board.Cell;
import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.ICoords;
import org.avv.fjet.core.geometry.FJetPoint;
import org.avv.fjet.core.board.SquareCoords;
import org.avv.fjet.core.board.util.UtilCoordinates;
import org.avv.fjet.core.rule.GameRules;
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
    static final int TOUCH_EVENT_SCALE = 4;
    static final int TOUCH_EVENT_LONG_PRESS = 5;
    static final int TOUCH_EVENT_SHOW_PRESS = 6;

    // endregion - Constants

    // region - Fields

    float scale = DEFAULT_SCALE;
    int edgeSize = 80;

    private final BlockingQueue<Action> actions;
    private Game game;
    private BoardDrawable boardDrawable;
    private GameRules gameRules;

    // endregion - Fields

    // region - Constructors

    public GameEngine(Game game, BoardDrawable boardDrawable, SurfaceHolder surfaceHolder, GameRules rules) {
        super(surfaceHolder);

        this.actions = new LinkedBlockingQueue<>();
        this.game = game;
        this.boardDrawable = boardDrawable;
        this.gameRules = rules;
    }

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public void drawObjects(Canvas c) {
        this.boardDrawable.draw(c);
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

    }

    @Override
    protected void doInitialTasks() {

    }

    @Override
    public void onSurfaceCreated() {
        if (this.getState() == Thread.State.NEW) {
            start();
        }
    }

    @Override
    public void receiveActionResult(Action.IActionResult result) {


    }

    @Override
    public void receiveActionUndoResult(Action.IActionUndoResult undoResult) {

    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("--->", "onDown");

        if (this.handler != null) {
            Message msg = this.handler.obtainMessage();
            msg.what = TOUCH_EVENT_TAP_DOWN;
            msg.obj = new FJetPoint((int)e.getX(), (int)e.getY());
            this.handler.sendMessage(msg);
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("--->", "onShowPress");

        if (this.handler != null) {
            Message msg = this.handler.obtainMessage();
            msg.what = TOUCH_EVENT_SHOW_PRESS;
            msg.obj = new FJetPoint((int)e.getX(), (int)e.getY());
            this.handler.sendMessage(msg);
        }
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("--->", "onSingleTapUp");

        if (this.handler != null) {
            Message msg = this.handler.obtainMessage();
            msg.what = TOUCH_EVENT_TAP_UP;
            msg.obj = new FJetPoint((int)e.getX(), (int)e.getY());
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
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d("--->", "onLongPress");

        if (this.handler != null) {
            Message msg = this.handler.obtainMessage();
            msg.what = TOUCH_EVENT_LONG_PRESS;
            msg.obj = new FJetPoint((int)e.getX(), (int)e.getY());
            this.handler.sendMessage(msg);
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("--->", "onFling");
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        Log.d("--->", "onScale");
        if (this.handler != null) {
            Message msg = this.handler.obtainMessage();
            msg.what = TOUCH_EVENT_SCALE;
            msg.obj = detector.getScaleFactor();
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

    private void drawSelectedCells(Canvas c){
        float currentEdgeSize = this.edgeSize * this.boardDrawable.getScale();

        Paint paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setColor(Color.GREEN);

        for (Object cell : game.getBoard().getSelectedCells()) {

            ICoords coords = ((Cell)cell).getCoords();
            FJetPoint offset = boardDrawable.getOffset();

            if (coords instanceof HexCoords) {
                UtilCellDrawing.drawHexCellEdge(c, this.edgeSize, this.boardDrawable.getScale(), (HexCoords) coords, Color.GREEN, 5f, offset);
                UtilCoordinates.hexCoordsToPixel(currentEdgeSize, (HexCoords) coords);

            } else {
                UtilCellDrawing.drawSquareCellEdge(c, this.edgeSize, this.boardDrawable.getScale(), (SquareCoords) coords, Color.GREEN, 5f, offset);
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

                case TOUCH_EVENT_TAP_DOWN:
                    Log.d("handleMessage", "TOUCH_EVENT");
                    ICoords tapDownCoords = getCoordsFromPoint((FJetPoint)msg.obj);

                    if (GameEngine.this.gameRules != null) {
                        Action action = GameEngine.this.gameRules.getActionWithOnTapDown(tapDownCoords);
                        action.setObserver(GameEngine.this);

                        if (action != null) {
                            GameEngine.this.addActionToQueue(action);
                        }
                    }
                    break;

                case TOUCH_EVENT_LONG_PRESS:
                    Log.d("handleMessage", "TOUCH_EVENT");
                    ICoords longPressCoords = getCoordsFromPoint((FJetPoint)msg.obj);

                    if (GameEngine.this.gameRules != null) {
                        Action action = GameEngine.this.gameRules.getActionWithOnLongPress(longPressCoords);
                        action.setObserver(GameEngine.this);

                        if (action != null) {
                            GameEngine.this.addActionToQueue(action);
                        }
                    }
                    break;

                case TOUCH_EVENT_TAP_UP:
                    Log.d("handleMessage", "TOUCH_EVENT");
                    ICoords tapUpCoords = getCoordsFromPoint((FJetPoint)msg.obj);

                    if (GameEngine.this.gameRules != null) {
                        Action action = GameEngine.this.gameRules.getActionWithOnTapUp(tapUpCoords);
                        action.setObserver(GameEngine.this);

                        if (action != null) {
                            GameEngine.this.addActionToQueue(action);
                        }
                    }
                    break;

                case TOUCH_EVENT_SCROLL:
                    Log.d("handleMessage", "TOUCH_EVENT_SCROLL");
                    Action actionScroll = getScrollAction((FJetPoint)msg.obj);
                    GameEngine.this.addActionToQueue(actionScroll);
                    break;

                case TOUCH_EVENT_SCALE:
                    Log.d("handleMessage", "TOUCH_EVENT_SCALE");
                    Action actionScale = getScaleAction((float)msg.obj);
                    GameEngine.this.addActionToQueue(actionScale);
                    break;
            }
        }

        private ICoords getCoordsFromPoint(FJetPoint p){
            ICoords coords = null;
            FJetPoint offset = boardDrawable.getOffset();

            if (game.getBoard().getType() == Board.BoardType.HEX_CELLS) {
                coords = UtilCoordinates.hexCoordsFromPixel(
                        p.getX() - offset.getX(),
                        p.getY() - offset.getY(),
                        edgeSize * boardDrawable.getScale());

            } else if (game.getBoard().getType() == Board.BoardType.SQUARE_CELLS) {
                coords = UtilCoordinates.squareCoordsFromPixelCoords(
                        p.getX() - offset.getX(),
                        p.getY()- offset.getY(),
                        edgeSize * boardDrawable.getScale());
            }
            return coords;
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

        private Action getScaleAction(float scale) {
            ScaleViewAction aScale = (ScaleViewAction) ActionFactory.createAction(
                    ActionFactory.SCALE_BOARD_VIEW_ACTION);

            if (aScale != null) {
                aScale.setScale(scale)
                        .setBoardDrawable(GameEngine.this.boardDrawable)
                        .setObserver(GameEngine.this);
            }
            return aScale;
        }

    };

    // endregion - Inner and Anonymous Classes

}
