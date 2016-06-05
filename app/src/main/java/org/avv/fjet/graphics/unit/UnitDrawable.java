package org.avv.fjet.graphics.unit;

import android.graphics.Canvas;

import org.avv.fjet.core.geometry.FJetPoint;
import org.avv.fjet.core.geometry.FJetRect;
import org.avv.fjet.graphics.GameAnimation;
import org.avv.fjet.graphics.GameDrawable;

/**
 * Created by Alexander Vorobiev on 17/05/16.
 */
public class UnitDrawable extends GameDrawable {

    // region - Constants

    public enum State {
        ATTACKING,
        WAITING,
        MOVING
    }

    // endregion - Constants

    // region - Fields

    private State currentState = State.WAITING;
    private FJetPoint vector;       // Movement vector
    private GameAnimation[] animations;

    // endregion - Fields

    // region - Constructors

    public UnitDrawable(){
        super();

        init();
    }

    // endregion - Constructors

    // region - Getters and Setters

    public void addAnimation(State state, GameAnimation animation){
        this.animations[state.ordinal()] = animation;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    protected void drawInRect(FJetRect drawRect, Canvas c) {

        if (this.animations[this.currentState.ordinal()] != null){
            this.animations[this.currentState.ordinal()].draw(c, drawRect);
        }
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    private void init(){
        this.vector = new FJetPoint(0,0);
        this.animations = new GameAnimation[State.values().length];
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
