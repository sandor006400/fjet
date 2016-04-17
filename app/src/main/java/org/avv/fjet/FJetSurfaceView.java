package org.avv.fjet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.avv.fjet.core.board.Board;
import org.avv.fjet.core.board.BoardFactory;
import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.Point;
import org.avv.fjet.core.board.util.UtilCoordinates;

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

        this.thread = new SurfaceViewThread(holder, BoardFactory.createBoard(BoardFactory.BoardType.HEX_CELLS, 2, 2));
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

        Board b = BoardFactory.createBoard(BoardFactory.BoardType.HEX_CELLS, 2, 2);
        this.thread = new SurfaceViewThread(getHolder(), b);
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    private class SurfaceViewThread extends Thread {

        private Board board;
        private final SurfaceHolder holder;
        private boolean running;
        private final long INTERVAL = 10000;//1000 / 60;

        public SurfaceViewThread(SurfaceHolder holder, Board board){
            this.holder = holder;
            this.board = board;
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

                            if (!done){
                                Paint paint = new Paint();
                                paint.setStrokeWidth(3);
                                paint.setColor(Color.RED);
                                Paint paint2 = new Paint();
                                paint2.setStrokeWidth(1);
                                paint2.setColor(Color.GREEN);
                                paint2.setStyle(Paint.Style.STROKE);
                                int edgeSize = 10;

                                for (Object coords : board.getCells().keySet()){

                                    if (coords instanceof HexCoords){
                                        Log.d("---->", "coords: " + coords.toString());
                                        Point p = UtilCoordinates.hexCoordsToPixel(edgeSize, (HexCoords) coords);

                                        Log.d("---->", "point: " + p.getX() + "," + p.getY());
                                        c.drawPoint(edgeSize*3 + p.getX(),edgeSize*3 + p.getY(), paint);
                                        c.drawCircle(edgeSize*3 + p.getX(), edgeSize*3 + p.getY(), edgeSize, paint2);
                                    }
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

        public void setRunning(){
            running = true;
        }

        public void setStop(){
            running = false;
        }

        private void redraw(Canvas c){
            c.drawColor(Color.WHITE);
        }

    }

    // endregion - Inner and Anonymous Classes

}
