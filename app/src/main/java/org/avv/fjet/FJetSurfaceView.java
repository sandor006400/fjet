package org.avv.fjet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.avv.fjet.core.Game;
import org.avv.fjet.core.action.Action;
import org.avv.fjet.core.action.ActionFactory;
import org.avv.fjet.core.action.ScaleViewAction;
import org.avv.fjet.core.action.SelectCellAction;
import org.avv.fjet.core.board.Board;
import org.avv.fjet.core.board.BoardFactory;
import org.avv.fjet.core.board.Cell;
import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.ICoords;
import org.avv.fjet.core.geometry.FJetPoint;
import org.avv.fjet.core.board.SquareCoords;
import org.avv.fjet.core.board.util.UtilCoordinates;
import org.avv.fjet.core.geometry.FJetRect;
import org.avv.fjet.graphics.GameAnimation;
import org.avv.fjet.graphics.util.UtilCellDrawing;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 16/04/2016
 */
public class FJetSurfaceView extends SurfaceView
        implements SurfaceHolder.Callback {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private Board.BoardType type = Board.BoardType.HEX_CELLS;
    private SurfaceViewThread thread;

    // endregion - Fields

    // region - Constructors

    public FJetSurfaceView(Context context, AttributeSet attrs){
        super(context, attrs);

    }

    // endregion - Constructors

    // region - Getters and Setters

    public void init(){
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        Board b = BoardFactory.createBoard(getContext(), this.type, 10, 10);

        Log.d("BoardJson", b.getBoardData().toJson());

        Log.d("Board", b.toString());

        Game g = new Game(b);
        this.thread = new SurfaceViewThread(holder, g);
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        if (this.thread.getState() == Thread.State.NEW) {
            Log.d("-->", "New thread!!!");
            thread.setRunning();
            thread.start();

        } else if (this.thread.getState() == Thread.State.TERMINATED){
            Log.d("-->", "Recreating thread!!!");
            initializeThread();
            thread.setRunning();
            thread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public void processTouchEvent(FJetPoint p){
        if (this.thread != null) {
            this.thread.handleTouchEvent(p);
        }
    }

    public void processAction(Action a){
        if (this.thread != null) {
            Log.d("--->", "Action received in View");
            this.thread.processAction(a);
        }
    }

    public void setCellType(Board.BoardType type){
        this.type = type;
        this.thread.setBoardType(type);
    }

    public void run(){
        if (this.thread != null) {
            this.thread.setRunning();
        }
    }

    public void stop(){

        if (this.thread != null) {
            this.thread.setStop();
            try {
                this.thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getBoardJson(){
        return this.thread.game.getBoard().getBoardData().toJson();
    }

    public void initializeThread(){
        Board b = BoardFactory.createBoard(getContext(), this.type, 10, 10);
        Game g = new Game(b);
        this.thread = new SurfaceViewThread(getHolder(), g);
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    private class SurfaceViewThread extends Thread implements Action.ActionObserver {


        static final float DEFAULT_SCALE = 1.0f;
        float scale = DEFAULT_SCALE;
        int edgeSize = 30;
        private Game game;
        private final SurfaceHolder holder;
        private boolean running;
        private int fps = 60;
        private final long INTERVAL = 1000 / fps;
        private final BlockingQueue<Action> actions = new LinkedBlockingQueue<>();


        private FJetPoint p;
        private ICoords [] coords;

        private GameAnimation anim;

        public SurfaceViewThread(SurfaceHolder holder, Game game){
            this.holder = holder;
            this.game = game;

            Drawable d1 = getResources().getDrawable( R.drawable.drawable_01, getContext().getTheme() );
            Drawable d2 = getResources().getDrawable( R.drawable.drawable_02, getContext().getTheme() );
            Drawable d3 = getResources().getDrawable( R.drawable.drawable_03, getContext().getTheme() );
            this.anim = new GameAnimation()
                    .setDrawables(new Drawable[]{d1, d2, d3})
                    .setDuration(GameAnimation.Duration.X_TIMES, 3)
                    .setUpdatesPerFrame(fps, 3);
        }

        @Override
        public void run() {

            while(running){

                Canvas c = null;
                try {
                    c = holder.lockCanvas(null);

                    synchronized (holder) {

                        if (c != null) {
                            redraw(c);
                            long timeToWait = System.currentTimeMillis() + INTERVAL;
                            boolean done = false;

                            while (System.currentTimeMillis() < timeToWait) {

                                // Executes all pending actions
                                Action currentAction;

                                while ((currentAction = this.actions.poll()) != null) {
                                    currentAction.execute();
                                }

                                float currentEdgeSize = getSizedEdge();

                                if (!done) {
                                    Paint paint = new Paint();
                                    paint.setStrokeWidth(3);
                                    paint.setColor(Color.BLACK);
                                    Paint paint2 = new Paint();
                                    paint2.setStrokeWidth(1);
                                    paint2.setColor(Color.GREEN);
                                    paint2.setStyle(Paint.Style.STROKE);

                                    for (Object entry : game.getBoard().getCells().entrySet()) {

                                        Map.Entry<ICoords,Cell> e = ((Map.Entry<ICoords,Cell>)entry);

                                        if (e.getKey() instanceof HexCoords) {

                                            FJetPoint p = UtilCoordinates.hexCoordsToPixel(currentEdgeSize, (HexCoords) e.getKey());
                                            UtilCellDrawing.drawHexCellEdge(c, this.edgeSize, this.scale, (HexCoords) e.getKey(), Color.BLACK, 3f);

                                            if (e.getValue() instanceof Cell){

                                            }
                                            paint.setTextSize(12);
                                            paint.setColor(Color.BLACK);
                                            c.drawText(((ICoords) e.getKey()).toShortString(), p.getX() - currentEdgeSize / 2, p.getY(), paint);

                                        } else if (e.getKey() instanceof SquareCoords) {

                                            UtilCellDrawing.drawSquareCellEdge(c, this.edgeSize, this.scale, (SquareCoords) e.getKey(), Color.BLACK, 3f);

                                            FJetPoint p = UtilCoordinates.squareCoordsToPixel(currentEdgeSize, (SquareCoords) e.getKey());
                                            paint.setTextSize(12);
                                            paint.setColor(Color.BLACK);
                                            c.drawText(((ICoords) e.getKey()).toShortString(), p.getX() - currentEdgeSize / 2, p.getY(), paint);
                                        }


                                    }

                                    if (this.coords != null) {
                                        for (ICoords coordsSel : this.coords) {
                                            Paint paintSel = new Paint();
                                            paintSel.setAlpha(50);
                                            paintSel.setColor(Color.BLUE);
                                            paintSel.setStyle(Paint.Style.STROKE);
                                            paintSel.setStrokeWidth(7);

                                            FJetPoint p = new FJetPoint(0, 0);

                                            if (coordsSel instanceof HexCoords) {
                                                p = UtilCoordinates.hexCoordsToPixel(currentEdgeSize, (HexCoords) coordsSel);
                                                c.drawLine(p.getX(), p.getY() - currentEdgeSize,
                                                        p.getX() + (UtilCoordinates.SQRT_OF_3 * currentEdgeSize / 2), p.getY() - (currentEdgeSize * 0.5f), paintSel);
                                                c.drawLine(p.getX() + (UtilCoordinates.SQRT_OF_3 * currentEdgeSize / 2), p.getY() - (currentEdgeSize * 0.5f),
                                                        p.getX() + (UtilCoordinates.SQRT_OF_3 * currentEdgeSize / 2), p.getY() + (currentEdgeSize * 0.5f), paintSel);
                                                c.drawLine(p.getX() + (UtilCoordinates.SQRT_OF_3 * currentEdgeSize / 2), p.getY() + (currentEdgeSize * 0.5f),
                                                        p.getX(), p.getY() + currentEdgeSize, paintSel);
                                                c.drawLine(p.getX(), p.getY() + currentEdgeSize,
                                                        p.getX() - (UtilCoordinates.SQRT_OF_3 * currentEdgeSize / 2), p.getY() + (currentEdgeSize * 0.5f), paintSel);
                                                c.drawLine(p.getX() - (UtilCoordinates.SQRT_OF_3 * currentEdgeSize / 2), p.getY() + (currentEdgeSize * 0.5f),
                                                        p.getX() - (UtilCoordinates.SQRT_OF_3 * currentEdgeSize / 2), p.getY() - (currentEdgeSize * 0.5f), paintSel);
                                                c.drawLine(p.getX() - (UtilCoordinates.SQRT_OF_3 * currentEdgeSize / 2), p.getY() - (currentEdgeSize * 0.5f),
                                                        p.getX(), p.getY() - currentEdgeSize, paintSel);


                                            } else if (coordsSel instanceof SquareCoords) {
                                                p = UtilCoordinates.squareCoordsToPixel(currentEdgeSize, (SquareCoords) coordsSel);

                                                float x1 = p.getX() - currentEdgeSize / 2;
                                                float y1 = p.getY() - currentEdgeSize / 2;
                                                float x2 = p.getX() + currentEdgeSize / 2;
                                                float y2 = p.getY() + currentEdgeSize / 2;
                                                c.drawRect(x1, y1, x2, y2, paintSel);
                                            }

                                            Paint paintTexto = new Paint();
                                            String coordsString = ((ICoords) coordsSel).toShortString();
                                            Rect bounds = new Rect();
                                            paintTexto.setTextSize(20);
                                            paintTexto.setColor(Color.BLACK);
                                            paintTexto.setStyle(Paint.Style.FILL);
                                            paintTexto.getTextBounds(coordsString, 0, coordsString.length(), bounds);
                                            bounds.offset((int) (p.getX() - currentEdgeSize / 2), p.getY());
                                            c.drawRect(bounds, paintTexto);
                                            paintTexto.setColor(Color.WHITE);
                                            c.drawText(coordsString, p.getX() - currentEdgeSize / 2, p.getY(), paintTexto);


                                            //--------------------------------------------------

                                            anim.draw(c, new FJetRect(p.getX() - 10, p.getY() - 10, 20, 20));

                                            //--------------------------------------------------
                                        }
                                    }

                                    if (this.p != null) {
                                        Paint paintPoint = new Paint();
                                        paintPoint.setColor(Color.RED);
                                        paintPoint.setStrokeWidth(5);

                                        c.drawPoint(p.getX(), p.getY(), paintPoint);
                                    }
                                    done = true;
                                }
                            }
                        }
                    }

                } finally {

                    if (c != null) {
                        holder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        @Override
        public void receiveActionUndoResult(Action.IActionUndoResult undoResult) {



        }

        @Override
        public void receiveActionResult(Action.IActionResult result) {

            if (result != null) {
                synchronized (this) {

                    if (result instanceof SelectCellAction.SelectCellActionResult) {
                        ICoords coor = ((SelectCellAction.SelectCellActionResult) result).getSelectedCoords();

                        if (coor instanceof HexCoords){
                            this.coords = new HexCoords[]{(HexCoords) coor};
                        } else if (coor instanceof SquareCoords){
                            this.coords = new SquareCoords[]{(SquareCoords) coor};
                        }

                    } else if (result instanceof ScaleViewAction.ScaleViewActionResult) {
                        this.scale = ((ScaleViewAction.ScaleViewActionResult) result).getScale();
                    }
                }
            }
        }

        public void setRunning(){
            running = true;
        }

        public void setStop(){
            running = false;
        }

        private void redraw(Canvas c){

            if (c != null) {
                c.drawColor(Color.WHITE);
            }
        }

        private float getSizedEdge(){
            return this.edgeSize * this.scale;
        }

        public void handleTouchEvent(FJetPoint p) {
            SelectCellAction a = (SelectCellAction) ActionFactory.createAction(ActionFactory.SELECT_CELL_ACTION);

            if (a != null) {
                this.p = p;
                ICoords coords = null;

                if (type == Board.BoardType.HEX_CELLS) {
                    //Log.d("Screen coords -> ", p.toString());
                    coords = UtilCoordinates.hexCoordsFromPixel(
                            p.getX(),
                            p.getY(),
                            thread.edgeSize * this.scale);

                } else if (type == Board.BoardType.SQUARE_CELLS) {
                    coords = UtilCoordinates.squareCoordsFromPixelCoords(
                            p.getX(),
                            p.getY(),
                            thread.edgeSize * this.scale);
                }
                //Log.d("Board coords -> ", hexCoords.toString());

                if (coords != null) {
                    a.setBoard(this.game.getBoard())
                            .setCoords(coords)
                            .setObserver(this);
                }

                /// !!!!!!!!!!!!!!!!!!!!!!!!!
                // CUALQUIERA DE LAS DOS LLAMADAS VALDRIA !!!
                this.game.processAction(a);
            }
            this.actions.add(a);
        }

        public void processAction(Action a){
            if (a != null){

                a.setObserver(this);
                this.game.processAction(a);
                //this.actions.add(a);
            }
        }

        public void setBoardType(Board.BoardType type){
            synchronized (this) {
                Board b = BoardFactory.createBoard(getContext(), type, 10, 10);
                this.game = new Game(b);
            }
        }

    }

    // endregion - Inner and Anonymous Classes

}
