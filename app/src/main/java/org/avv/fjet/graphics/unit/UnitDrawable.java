package org.avv.fjet.graphics.unit;

import android.graphics.Canvas;

import org.avv.fjet.core.geometry.FJetPoint;
import org.avv.fjet.core.geometry.FJetRect;
import org.avv.fjet.graphics.AnimatedGameDrawable;
import org.avv.fjet.graphics.GameAnimation;

/**
 * Created by Alexander Vorobiev on 17/05/16.
 */
public class UnitDrawable extends AnimatedGameDrawable {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private FJetPoint vector;       // Movement vector

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
        GameAnimation gameAnimation = getCurrentAnimation();

        if (gameAnimation != null){
            gameAnimation.draw(c, drawRect);
        }
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    private void init(){
        this.vector = new FJetPoint(0,0);
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
