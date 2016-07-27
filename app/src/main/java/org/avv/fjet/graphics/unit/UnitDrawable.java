package org.avv.fjet.graphics.unit;

import android.graphics.Canvas;
import android.graphics.drawable.Animatable;

import org.avv.fjet.core.geometry.FJetPoint;
import org.avv.fjet.core.geometry.FJetRect;
import org.avv.fjet.graphics.GameAnimation;
import org.avv.fjet.graphics.GameDrawable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander Vorobiev on 17/05/16.
 */
public class UnitDrawable extends GameDrawable {

    // region - Constants

    /**
     * Default states of UnitDrawable
     */
    public enum State {
        ATTACKING("Attacking"),
        WAITING("Waiting"),
        MOVING("Moving");

        private String state;

        State(String state){
            this.state = state;
        }

        public String toString(){
            return this.state;
        }
    }

    // endregion - Constants

    // region - Fields

    private String currentState = State.WAITING.toString();
    private FJetPoint vector;       // Movement vector
    private Map<String, GameAnimation> animations;

    // endregion - Fields

    // region - Constructors

    public UnitDrawable(){
        super();

        init();
    }

    // endregion - Constructors

    // region - Getters and Setters

    public void addAnimation(String state, GameAnimation animation){
        this.animations.put(state, animation);
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    protected void drawInRect(FJetRect drawRect, Canvas c) {
        GameAnimation gameAnimation = this.animations.get(this.currentState);

        if (gameAnimation != null){
            gameAnimation.draw(c, drawRect);
        }
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    private void init(){
        this.vector = new FJetPoint(0,0);
        this.animations = new HashMap<>();
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
