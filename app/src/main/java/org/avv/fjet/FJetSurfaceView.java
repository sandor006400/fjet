package org.avv.fjet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.HeterogeneousExpandableList;

import org.avv.fjet.core.Game;
import org.avv.fjet.core.action.Action;
import org.avv.fjet.core.action.ActionFactory;
import org.avv.fjet.core.action.SelectCellAction;
import org.avv.fjet.core.board.Board;
import org.avv.fjet.core.board.BoardFactory;
import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.ICoords;
import org.avv.fjet.core.board.Point;
import org.avv.fjet.core.board.SquareCoords;
import org.avv.fjet.core.board.util.UtilCoordinates;

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

    private SurfaceViewThread thread;

    // endregion - Fields

    // region - Constructors

    public FJetSurfaceView(Context context, AttributeSet attrs){
        super(context, attrs);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        Board b = BoardFactory.createBoard(BoardFactory.BoardType.HEX_CELLS, 10, 10);
        Game g = new Game(b);
        this.thread = new SurfaceViewThread(holder, g);
    }

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        if (this.thread.getState() == Thread.State.NEW) {
            thread.setRunning();
            thread.start();

        } else if (this.thread.getState() == Thread.State.TERMINATED){
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

    public void processTouchEvent(Point p){
        this.thread.handleTouchEvent(p);
    }

    public void run(){
        this.thread.setRunning();
    }

    public void stop(){

        this.thread.setStop();
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initializeThread(){
        Board b = BoardFactory.createBoard(BoardFactory.BoardType.HEX_CELLS, 10, 10);
        Game g = new Game(b);
        this.thread = new SurfaceViewThread(getHolder(), g);
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    private class SurfaceViewThread extends Thread implements Action.ActionObserver {

        int edgeSize = 30;
        private Game game;
        private final SurfaceHolder holder;
        private boolean running;
        private final long INTERVAL = 1000 / 60;
        private final BlockingQueue<Action> actions = new LinkedBlockingQueue<>();


        private Point p;
        private ICoords [] coords;

        public SurfaceViewThread(SurfaceHolder holder, Game game){
            this.holder = holder;
            this.game = game;
        }

        @Override
        public void run() {

            while(running){

                Canvas c = null;
                try {
                    c = holder.lockCanvas(null);

                    synchronized (holder) {
                        redraw(c);
                        long timeToWait = System.currentTimeMillis() + INTERVAL;
                        boolean done = false;

                        while(System.currentTimeMillis() < timeToWait){

                            // Executes all pending actions
                            Action currentAction;

                            while ((currentAction = this.actions.poll()) != null) {
                                currentAction.execute();
                            }

                            if (!done){
                                Paint paint = new Paint();
                                paint.setStrokeWidth(3);
                                paint.setColor(Color.BLACK);
                                Paint paint2 = new Paint();
                                paint2.setStrokeWidth(1);
                                paint2.setColor(Color.GREEN);
                                paint2.setStyle(Paint.Style.STROKE);

                                for (Object coords : game.getBoard().getCells().keySet()){

                                    /*if (((SquareCoords)coords).getX() % 3 == 0){
                                        paint2.setColor(Color.RED);

                                    } else if (((SquareCoords)coords).getX() % 3 == 1){
                                        paint2.setColor(Color.GREEN);

                                    } else {
                                        paint2.setColor(Color.BLUE);
                                    }*/

                                    if (coords instanceof HexCoords){

                                        Point p = UtilCoordinates.hexCoordsToPixel(edgeSize, (HexCoords) coords);

                                        Log.d("---->", "point Sel: " + p.getX() + "," + p.getY());
                                        //c.drawPoint(/*edgeSize + */p.getX(), /*edgeSize + */p.getY(), paint);
                                        //c.drawCircle(/*edgeSize + */p.getX(), /*edgeSize +*/ p.getY(), edgeSize, paint2);

                                        c.drawLine(p.getX(), p.getY() - edgeSize,
                                                p.getX() + (UtilCoordinates.SQRT_OF_3 * edgeSize / 2), p.getY() - (edgeSize * 0.5f), paint);
                                        c.drawLine(p.getX() + (UtilCoordinates.SQRT_OF_3 * edgeSize / 2), p.getY() - (edgeSize * 0.5f),
                                                p.getX() + (UtilCoordinates.SQRT_OF_3 * edgeSize / 2), p.getY() + (edgeSize * 0.5f), paint);
                                        c.drawLine(p.getX() + (UtilCoordinates.SQRT_OF_3 * edgeSize / 2), p.getY() + (edgeSize * 0.5f),
                                                p.getX(), p.getY() + edgeSize, paint);
                                        c.drawLine(p.getX(), p.getY() + edgeSize,
                                                p.getX() - (UtilCoordinates.SQRT_OF_3 * edgeSize / 2), p.getY() + (edgeSize * 0.5f), paint);
                                        c.drawLine(p.getX() - (UtilCoordinates.SQRT_OF_3 * edgeSize / 2), p.getY() + (edgeSize * 0.5f),
                                                p.getX() - (UtilCoordinates.SQRT_OF_3 * edgeSize / 2), p.getY() - (edgeSize * 0.5f), paint);
                                        c.drawLine(p.getX() - (UtilCoordinates.SQRT_OF_3 * edgeSize / 2), p.getY() - (edgeSize * 0.5f),
                                                p.getX(), p.getY() - edgeSize, paint);


                                        paint.setTextSize(12);
                                        paint.setColor(Color.BLACK);

                                        c.drawText(((ICoords)coords).toShortString(), p.getX() - edgeSize / 2, p.getY(), paint);

                                    } else if (coords instanceof SquareCoords){

                                        Point p = UtilCoordinates.squareCoordsToPixel(edgeSize, (SquareCoords) coords);

                                        //c.drawPoint(/*edgeSize + */p.getX(), /*edgeSize + */p.getY(), paint);
                                        Log.d("---->", "point Sel: " + p.getX() + "," + p.getY());
                                        float x1 = p.getX() - edgeSize / 2;
                                        float y1 = p.getY() - edgeSize / 2;
                                        float x2 = p.getX() + edgeSize / 2;
                                        float y2 = p.getY() + edgeSize / 2;
                                        c.drawRect(x1, y1, x2, y2, paint2);

                                        paint.setTextSize(12);
                                        paint.setColor(Color.BLACK);

                                        c.drawText(((ICoords)coords).toShortString(), p.getX() - edgeSize / 2, p.getY(), paint);
                                    }


                                }

                                if (this.coords != null) {
                                    for (ICoords coordsSel : this.coords) {
                                        Paint paintSel = new Paint();
                                        paintSel.setAlpha(50);
                                        paintSel.setColor(Color.BLUE);
                                        paintSel.setStrokeWidth(7);

                                        //Log.d("---->", "coords Sel: " + coords.toString());
                                        Point p = new Point(0,0);

                                        if (coordsSel instanceof HexCoords) {
                                            p = UtilCoordinates.hexCoordsToPixel(edgeSize, (HexCoords) coordsSel);

                                            //Log.d("---->", "point Sel: " + p.getX() + "," + p.getY());
                                            //c.drawCircle(/*edgeSize + */p.getX(), /*edgeSize + */p.getY(), edgeSize, paintSel);
                                            c.drawLine(p.getX(), p.getY() - edgeSize,
                                                    p.getX() + (UtilCoordinates.SQRT_OF_3 * edgeSize / 2), p.getY() - (edgeSize * 0.5f), paintSel);
                                            c.drawLine(p.getX() + (UtilCoordinates.SQRT_OF_3 * edgeSize / 2), p.getY() - (edgeSize * 0.5f),
                                                    p.getX() + (UtilCoordinates.SQRT_OF_3 * edgeSize / 2), p.getY() + (edgeSize * 0.5f), paintSel);
                                            c.drawLine(p.getX() + (UtilCoordinates.SQRT_OF_3 * edgeSize / 2), p.getY() + (edgeSize * 0.5f),
                                                    p.getX(), p.getY() + edgeSize, paintSel);
                                            c.drawLine(p.getX(), p.getY() + edgeSize,
                                                    p.getX() - (UtilCoordinates.SQRT_OF_3 * edgeSize / 2), p.getY() + (edgeSize * 0.5f), paintSel);
                                            c.drawLine(p.getX() - (UtilCoordinates.SQRT_OF_3 * edgeSize / 2), p.getY() + (edgeSize * 0.5f),
                                                    p.getX() - (UtilCoordinates.SQRT_OF_3 * edgeSize / 2), p.getY() - (edgeSize * 0.5f), paintSel);
                                            c.drawLine(p.getX() - (UtilCoordinates.SQRT_OF_3 * edgeSize / 2), p.getY() - (edgeSize * 0.5f),
                                                    p.getX(), p.getY() - edgeSize, paintSel);

                                        } else if (coordsSel instanceof SquareCoords) {
                                            p = UtilCoordinates.squareCoordsToPixel(edgeSize, (SquareCoords) coordsSel);

                                            //Log.d("---->", "point Sel: " + p.getX() + "," + p.getY());
                                            float x1 = p.getX() - edgeSize / 2;
                                            float y1 = p.getY() - edgeSize / 2;
                                            float x2 = p.getX() + edgeSize / 2;
                                            float y2 = p.getY() + edgeSize / 2;
                                            c.drawRect(x1, y1, x2, y2, paintSel);
                                        }

                                        Paint paintTexto = new Paint();
                                        String coordsString = ((ICoords)coordsSel).toShortString();
                                        Rect bounds = new Rect();
                                        paintTexto.setTextSize(20);
                                        paintTexto.setColor(Color.BLACK);
                                        paintTexto.setStyle(Paint.Style.FILL);
                                        paintTexto.getTextBounds(coordsString, 0, coordsString.length(), bounds);
                                        bounds.offset(p.getX() - edgeSize / 2, p.getY());
                                        c.drawRect(bounds, paintTexto);
                                        paintTexto.setColor(Color.WHITE);
                                        c.drawText(coordsString, p.getX() - edgeSize / 2, p.getY(), paintTexto);
                                    }
                                }

                                if (this.p != null){
                                    Paint paintPoint = new Paint();
                                    paintPoint.setColor(Color.RED);
                                    paintPoint.setStrokeWidth(5);

                                    c.drawPoint(p.getX(), p.getY(), paintPoint);
                                }

                                done = true;
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
        public void receiveActionUndoResult(Object undoResult) {



        }

        @Override
        public void receiveActionResult(Object result) {
            if (result instanceof HexCoords){

                synchronized (this) {
                    Log.d("--->", "coordenada recibida: " + result.toString());
                    this.coords = new HexCoords[]{(HexCoords) result};
                }
            } else if (result instanceof SquareCoords){

                synchronized (this) {
                    Log.d("--->", "coordenada recibida: " + result.toString());
                    this.coords = new SquareCoords[]{(SquareCoords) result};
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
            c.drawColor(Color.WHITE);
        }


        public void handleTouchEvent(Point p) {
            SelectCellAction a = (SelectCellAction) ActionFactory.createAction(ActionFactory.SELECT_CELL_ACTION);

            if (a != null) {
                this.p = p;
                Log.d("Screen coords -> ", p.toString());
                HexCoords hexCoords = UtilCoordinates.hexCoordsFromPixel(p.getX(), p.getY(), thread.edgeSize);
                //SquareCoords hexCoords = UtilCoordinates.squareCoordsFromPixelCoords(p.getX(), p.getY(), thread.edgeSize);
                Log.d("Board coords -> ", hexCoords.toString());
                a.setBoard(this.game.getBoard())
                        .setCoords(hexCoords)
                        .setObserver(this);
                this.game.processAction(a);
            }
            this.actions.add(a);
        }

    }

    // endregion - Inner and Anonymous Classes

}
