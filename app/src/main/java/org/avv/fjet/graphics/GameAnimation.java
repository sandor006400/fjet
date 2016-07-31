package org.avv.fjet.graphics;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;

import org.avv.fjet.core.geometry.FJetRect;

/**
 * Created by Alexander Vorobiev on 17/05/16.
 */
public class GameAnimation {

    // region - Constants

    private static final String DEBUG_TAG = "GameAnimation";

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

    public GameAnimation(){
        init();
    }

    public GameAnimation(GameAnimation gameAnimation){
        init();
        setAnimationValues(gameAnimation);
    }

    // endregion - Constructors

    // region - Getters and Setters

    @NonNull
    public GameAnimation setDrawables(Drawable [] drawables){

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
    public GameAnimation setDuration(Duration duration, int cycles){
        this.duration = duration;
        if (duration == Duration.X_TIMES) {
            this.cycles = cycles;
            this.updatesLeft = cycles * this.updatesPerFrame * this.drawables.length;

        } else if (duration == Duration.INFINITE){
            this.cycles = 1;    // Its enought
            this.updatesLeft = cycles * this.updatesPerFrame * this.drawables.length;

        } else if (duration == Duration.ONE_TIME){
            this.cycles = 1;
            this.updatesLeft = this.updatesPerFrame * this.drawables.length;
        }
        //Log.d(DEBUG_TAG, "setDuration -> updatesLeft: " + String.valueOf(this.updatesLeft));
        return this;
    }

    /**
     * This method calculates a total amounts of game updates for each frame refresh
     * @param gameFPS
     * @param animationFPS
     * @return
     */
    public GameAnimation setUpdatesPerFrame(int gameFPS, int animationFPS){
        int upsPerFrame = Math.round((float)gameFPS / (float)animationFPS);

        if (upsPerFrame < MAX_UPDATES_PER_FRAME){
            this.updatesPerFrame = upsPerFrame;
            this.updatesLeft = cycles * this.updatesPerFrame * this.drawables.length;
        }
        /*Log.d(DEBUG_TAG, "setUpdatesPerFrame -> animationFPS: " + String.valueOf(gameFPS)
                    + " animationFPS: " + String.valueOf(animationFPS)
                    + " updatesPerFrame: " + String.valueOf(upsPerFrame)
                + " updatesLeft: " + String.valueOf(this.updatesLeft));*/
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

    public void draw(Canvas c, FJetRect r){

        Drawable d = getCurrentDrawable();

        if (d != null){
            d.setBounds(r.getRect());
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

                if (this.updatesLeft == 0){
                    //Log.d("update", "duration: " + String.valueOf(this.duration) + " updatesLeft: " + String.valueOf(this.updatesLeft));
                    //this.updatesLeft = cycles * this.updatesPerFrame * this.drawables.length;
                    return false;
                }
                this.updatesLeft--;
                return true;
            }

        } else {    // This case is INFINITE

            if (this.updatesLeft % this.updatesPerFrame != 0) {
                this.updatesLeft--;
                return false;

            } else {

                if (this.updatesLeft == 0){
                    //Log.d("update", "duration: " + String.valueOf(this.duration) + " updatesLeft: " + String.valueOf(this.updatesLeft));
                    this.updatesLeft = cycles * this.updatesPerFrame * this.drawables.length;
                    return false;
                }
                this.updatesLeft--;
                return true;
            }
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

    /**
     * Reset updatesLeft attribute with updatesPerFrame, the number of drawables and cycles
     * depending on duration
     */
    public void reset(){
        if (duration == Duration.X_TIMES
                || duration == Duration.INFINITE){
            this.updatesLeft = cycles * this.updatesPerFrame * this.drawables.length;

        } else if (duration == Duration.ONE_TIME){
            this.updatesLeft = this.updatesPerFrame * this.drawables.length;
        }
    }

    private void setAnimationValues(GameAnimation gameAnimation){
        this.updatesLeft = gameAnimation.updatesLeft;
        this.cycles = gameAnimation.cycles;
        this.currentDrawable = gameAnimation.currentDrawable;
        this.duration = gameAnimation.duration;
        this.updatesPerFrame = gameAnimation.updatesPerFrame;
        this.drawables = gameAnimation.drawables;
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
