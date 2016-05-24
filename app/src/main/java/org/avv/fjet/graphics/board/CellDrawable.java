package org.avv.fjet.graphics.board;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import org.avv.fjet.core.geometry.FJetRect;
import org.avv.fjet.graphics.GameDrawable;

/**
 * Created by Alexander Vorobiev on 22/05/16.
 */
public class CellDrawable extends GameDrawable {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private Drawable drawable;

    // endregion - Fields

    // region - Constructors

    public CellDrawable(){
        super();

    }

    // endregion - Constructors

    // region - Getters and Setters

    public CellDrawable setDrawable(Drawable drawable){
        this.drawable = drawable;
        return this;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    protected void drawInRect(FJetRect drawRect, Canvas c) {

        if (this.drawable != null){
            this.drawable.setBounds(drawRect.getRect());
            this.drawable.draw(c);
        }
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
