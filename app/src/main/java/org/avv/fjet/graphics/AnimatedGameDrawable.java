package org.avv.fjet.graphics;

import android.graphics.Canvas;

import org.avv.fjet.core.geometry.FJetRect;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander Vorobiev on 27/07/16.
 */
public class AnimatedGameDrawable extends GameDrawable {

    // region - Constants

    /**
     * Default states of UnitDrawable
     */
    public enum State {
        DEFAULT("Default"),
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

    private String currentState = State.DEFAULT.toString();
    private Map<String, GameAnimation> animations;

    // endregion - Fields

    // region - Constructors

    public AnimatedGameDrawable(){
        this.animations = new HashMap<>();
    }

    // endregion - Constructors

    // region - Getters and Setters

    public void addAnimation(String state, GameAnimation animation){
        this.animations.put(state, animation);
    }

    public GameAnimation getCurrentAnimation(){
        return this.animations.get(this.currentState);
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    @Override
    protected void drawInRect(FJetRect drawRect, Canvas c) {
        GameAnimation gameAnimation = getCurrentAnimation();

        if (gameAnimation != null){
            gameAnimation.draw(c, drawRect);
        }
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
