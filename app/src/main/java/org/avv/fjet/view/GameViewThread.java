package org.avv.fjet.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

/**
 * Created by Alexander Vorobiev on 11/05/16.
 */
public abstract class GameViewThread extends Thread {

    // region - Constants

    private static final long DEFAULT_FPS = 60;
    private static final long MAX_FPS = 200;

    // endregion - Constants

    // region - Fields

    private long interval = 1000 / DEFAULT_FPS;
    private SurfaceHolder holder;
    private boolean running = false;

    // endregion - Fields

    // region - Constructors

    protected GameViewThread(SurfaceHolder surfaceHolder){
        this.holder = surfaceHolder;
    }

    // endregion - Constructors

    // region - Getters and Setters

    public boolean isRunning(){
        return this.running;
    }

    public GameViewThread setFPS(long fps){
        if (fps != 0 && 1000 / fps <= MAX_FPS){
            this.interval = 1000 / fps;
        }
        return this;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public void run() {
        long timeToWait = System.currentTimeMillis() + this.interval;

        while(running){
            if (System.currentTimeMillis() > timeToWait) {

                // Do tasks that must be done before drawing
                doPreDrawingTasks();
                Canvas c = null;

                if (this.holder != null) {
                    try {
                        c = holder.lockCanvas(null);

                        synchronized (holder) {
                            if (c != null) {
                                cleanCanvas(c);

                                // Here a drawable objects are drawn
                                drawObjects(c);
                            }
                        }

                    } finally {
                        if (c != null) {
                            holder.unlockCanvasAndPost(c);
                        }
                    }
                }

                // Do tasks that must be done after drawing
                doPostDrawingTasks();
                timeToWait = System.currentTimeMillis() + this.interval;
            }
        }
    }

    /**
     * This method is called in each refresh of a thread.
     * @param c
     */
    abstract void drawObjects(Canvas c);

    /**
     * This method can be used to do additional operations before drawing.
     */
    abstract void doPreDrawingTasks();

    /**
     * This method can be used to do additional operations after drawing.
     */
    abstract void doPostDrawingTasks();

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    /**
     * Fill a canvas with white color
     * @param c
     */
    private void cleanCanvas(Canvas c){
        if (c != null) {
            c.drawColor(Color.WHITE);
        }
    }

    public void setRunning(boolean running){
        synchronized (this) {
            this.running = running;
        }
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
