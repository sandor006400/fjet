package org.avv.fjet.graphics.unit;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by Alexander Vorobiev on 17/05/16.
 */
public class Animation {

    // region - Constants

    private static final String DEBUG_TAG = "Animation";

    public enum Duration {
        INFINITE,
        ONE_TIME,
        X_TIMES
    }

    public final static int DEFAULT_CYCLES = 0;
    public final static int DEFAULT_UPDATES_PER_FRAME = 1;
    public final static int MAX_UPDATES_PER_FRAME = 60;

    // endregion - Constants

    // region - Fields

    private Drawable[] drawables;
    private int currentDrawable;
    private int cycles;
    private Duration duration;
    private int updatesPerFrame;
    private int updatesLeft;

    // endregion - Fields

    // region - Constructors

    public Animation(){
        init();
    }

    // endregion - Constructors

    // region - Getters and Setters

    @NonNull
    public Animation setDrawables(Drawable [] drawables){

        if (drawables != null){
            this.drawables = drawables;
        }
        return this;
    }

    /**
     * Used to set a animation type and the number of cycles of a animation
     * @param duration
     * @param cycles
     * @return
     */
    public Animation setDuration(Duration duration, int cycles){
        this.duration = duration;
        if (duration == Duration.X_TIMES){
            this.cycles = cycles;
            this.updatesLeft = cycles * this.updatesPerFrame;
        }
        Log.d(DEBUG_TAG, "setDuration -> updatesLeft: " + String.valueOf(this.updatesLeft));
        return this;
    }

    /**
     * This method calculates a total amounts of game updates for each frame refresh
     * @param gameFPS
     * @param animationFPS
     * @return
     */
    public Animation setUpdatesPerFrame(int gameFPS, int animationFPS){
        int upsPerFrame = Math.round((float)gameFPS / (float)animationFPS);

        if (upsPerFrame < MAX_UPDATES_PER_FRAME){
            this.updatesPerFrame = upsPerFrame;
            this.updatesLeft = cycles * this.updatesPerFrame;
        }
        Log.d(DEBUG_TAG, "setUpdatesPerFrame -> animationFPS: " + String.valueOf(gameFPS)
                    + " animationFPS: " + String.valueOf(animationFPS)
                    + " updatesPerFrame: " + String.valueOf(upsPerFrame)
                + " updatesLeft: " + String.valueOf(this.updatesLeft));
        return this;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    private void init(){
        this.drawables = null;
        this.cycles = DEFAULT_CYCLES;
        this.updatesLeft = 0;
        this.duration = Duration.ONE_TIME;
        this.updatesPerFrame = DEFAULT_UPDATES_PER_FRAME;
        this.currentDrawable = 0;
    }

    public void draw(Canvas c, Rect r){

        Drawable d = getCurrentDrawable();

        if (d != null){
            d.setBounds(r);
            d.draw(c);
        }
    }

    private boolean update(){

        if (this.duration == Duration.ONE_TIME
                || this.duration == Duration.X_TIMES){

            if (this.updatesLeft % this.updatesPerFrame != 0) {
                this.updatesLeft--;
                return false;

            } else {
                this.updatesLeft--;
                return true;
            }

        } else {    // This case is INFINITE
            return true;
        }
    }

    private Drawable getCurrentDrawable() {

        if (this.drawables.length > 0
                && this.drawables.length > this.currentDrawable) {
            if (update()) {
                this.currentDrawable++;
                this.currentDrawable = this.currentDrawable % this.drawables.length;
            }
            return this.drawables[this.currentDrawable];
        }
        return null;
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
