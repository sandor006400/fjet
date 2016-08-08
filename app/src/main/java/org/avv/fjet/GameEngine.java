package org.avv.fjet;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;

import org.avv.fjet.core.Game;
import org.avv.fjet.core.action.Action;
import org.avv.fjet.core.action.ScaleViewAction;
import org.avv.fjet.core.action.ScrollBoardViewAction;
import org.avv.fjet.core.board.Board;
import org.avv.fjet.core.board.Cell;
import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.ICoords;
import org.avv.fjet.core.geometry.FJetPoint;
import org.avv.fjet.core.board.SquareCoords;
import org.avv.fjet.core.board.util.UtilCoordinates;
import org.avv.fjet.core.geometry.FJetRect;
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

    static final int PROCESS_ACTION = 7;

    // endregion - Constants

    // region - Fields

    private final BlockingQueue<Action> actions;
    private Game game;
    private BoardDrawable boardDrawable;
    private GameRules gameRules;

    private boolean scaleActivated;
    private boolean scrollActivated;

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

    public void setScrollActivated(boolean activated){
        this.scrollActivated = activated;
    }

    public void setScaleActivated(boolean activated){
        this.scaleActivated = activated;
    }

    public Game getGame(){
        return this.game;
    }

    public BoardDrawable getBoardDrawable(){
        return this.boardDrawable;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public void drawObjects(Canvas c) {
        this.boardDrawable.draw(c);
        //drawSelectedCells(c);
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
        float currentEdgeSize = this.boardDrawable.getCurrentEdgeSize();

        for (Object cell : game.getBoard().getSelectedCells()) {

            ICoords coords = ((Cell)cell).getCoords();
            FJetRect bounds = boardDrawable.getCurrentBounds();
            FJetPoint offset = new FJetPoint(bounds.getX(), bounds.getY());

            if (coords instanceof HexCoords) {
                UtilCellDrawing.drawHexCell(c, this.boardDrawable.getEdgeSize(), this.boardDrawable.getScale(), (HexCoords) coords, Color.GREEN, Color.TRANSPARENT, 10f, offset);
                UtilCoordinates.hexCoordsToPixel(currentEdgeSize, (HexCoords) coords);

            } else {
                UtilCellDrawing.drawSquare(c, this.boardDrawable.getEdgeSize(), this.boardDrawable.getScale(), (SquareCoords) coords, Color.GREEN, Color.TRANSPARENT, 10f, offset);
                UtilCoordinates.squareCoordsToPixel(currentEdgeSize, (SquareCoords) coords);
            }
        }
    }

    public void processAction(Action action){
        if (this.handler != null) {
            Message msg = this.handler.obtainMessage();
            msg.what = PROCESS_ACTION;
            msg.obj = action;
            this.handler.sendMessage(msg);
        }
    }

    /**
     * Removes all Units from Game and BoardDrawable
     */
    public void removeAllUnits(){
        this.boardDrawable.removeUnitDrawables();
        this.game.removeAllUnits();
    }

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

                        if (action != null) {
                            action.setObserver(GameEngine.this);
                            GameEngine.this.addActionToQueue(action);
                        }
                    }
                    break;

                case TOUCH_EVENT_LONG_PRESS:
                    Log.d("handleMessage", "TOUCH_EVENT");
                    ICoords longPressCoords = getCoordsFromPoint((FJetPoint)msg.obj);

                    if (GameEngine.this.gameRules != null) {
                        Action action = GameEngine.this.gameRules.getActionWithOnLongPress(longPressCoords);

                        if (action != null) {
                            action.setObserver(GameEngine.this);
                            GameEngine.this.addActionToQueue(action);
                        }
                    }
                    break;

                case TOUCH_EVENT_TAP_UP:
                    Log.d("handleMessage", "TOUCH_EVENT");
                    ICoords tapUpCoords = getCoordsFromPoint((FJetPoint)msg.obj);

                    if (GameEngine.this.gameRules != null) {
                        Action action = GameEngine.this.gameRules.getActionWithOnTapUp(tapUpCoords);

                        if (action != null) {
                            action.setObserver(GameEngine.this);
                            GameEngine.this.addActionToQueue(action);
                        }
                    }
                    break;

                case TOUCH_EVENT_SCROLL:
                    Log.d("handleMessage", "TOUCH_EVENT_SCROLL");
                    if (GameEngine.this.scrollActivated){
                        Action actionScroll = getScrollAction((FJetPoint)msg.obj);
                        GameEngine.this.addActionToQueue(actionScroll);
                    }
                    break;

                case TOUCH_EVENT_SCALE:
                    Log.d("handleMessage", "TOUCH_EVENT_SCALE");
                    if (GameEngine.this.scaleActivated){
                        Action actionScale = getScaleAction((float)msg.obj);
                        GameEngine.this.addActionToQueue(actionScale);
                    }
                    break;

                case PROCESS_ACTION:
                    Log.d("handleMessage", "PROCESS_ACTION");

                    if (msg.obj != null && msg.obj instanceof Action) {
                        GameEngine.this.addActionToQueue((Action) msg.obj);
                    }
                    break;
            }
        }

        private ICoords getCoordsFromPoint(FJetPoint p){
            ICoords coords = null;
            FJetRect boardBounds = boardDrawable.getCurrentBounds();
            FJetPoint offset = new FJetPoint(boardBounds.getX(), boardBounds.getY());

            if (game.getBoard().getType() == Board.BoardType.HEX_CELLS) {
                coords = UtilCoordinates.hexCoordsFromPixel(
                        p.getX() - offset.getX(),
                        p.getY() - offset.getY(),
                        boardDrawable.getCurrentEdgeSize());

            } else if (game.getBoard().getType() == Board.BoardType.SQUARE_CELLS) {
                coords = UtilCoordinates.squareCoordsFromPixelCoords(
                        p.getX() - offset.getX(),
                        p.getY()- offset.getY(),
                        boardDrawable.getCurrentEdgeSize());
            }
            return coords;
        }

        private Action getScrollAction(FJetPoint p) {
            ScrollBoardViewAction aScroll = new ScrollBoardViewAction();
            aScroll.setOffset(p)
                    .setBoardDrawable(GameEngine.this.boardDrawable)
                    .setObserver(GameEngine.this);
            return aScroll;
        }

        private Action getScaleAction(float scale) {
            ScaleViewAction aScale = new ScaleViewAction();
            aScale.setScale(scale)
                    .setBoardDrawable(GameEngine.this.boardDrawable)
                    .setObserver(GameEngine.this);
            return aScale;
        }

    };

    // endregion - Inner and Anonymous Classes

}
