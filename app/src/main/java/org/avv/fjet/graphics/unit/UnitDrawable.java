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

    }

    // endregion - Constants

    // region - Fields

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

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    protected void drawInRect(FJetRect drawRect, Canvas c) {
        // TODO
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    private void init(){
        this.vector = new FJetPoint(0,0);
        this.animations = null;
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
