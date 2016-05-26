package org.avv.fjet.graphics;

import android.graphics.Canvas;

import org.avv.fjet.core.geometry.FJetPoint;
import org.avv.fjet.core.geometry.FJetRect;

/**
 * Created by Alexander Vorobiev on 22/05/16.
 */
public abstract class GameDrawable {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private FJetPoint position;     // Position in Board, central point of the drawable
    private FJetPoint size;         // Drawable size
    private FJetRect drawRect;      // Rect within GameDrawable is drawing

    // endregion - Fields

    // region - Constructors

    public GameDrawable(){
        init();
    }

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    /**
     * Method used to draw a GameDrawable
     * @param drawRect
     * @param c
     */
    abstract protected void drawInRect(FJetRect drawRect, Canvas c);

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    /**
     * Method used to decide if the drawable must be drawn, to calculate a drawRect and call
     * drawing method using new drawRect
     * @param boardBounds -> in screen coords
     * @param viewBounds -> in screen coords
     * @param scale
     * @param c
     */
    public void draw(FJetRect boardBounds, FJetRect viewBounds, float scale, Canvas c){

        if (drawableIntersectsViewBounds(viewBounds, boardBounds)){

            // Updating drawRect
            updateDrawRect(boardBounds.getX(), boardBounds.getY(), scale);

            // Finally, drawing GameDrawable using canvas and drawRect
            drawInRect(this.drawRect, c);
        }
    }

    private void init(){
        this.position = new FJetPoint(0,0);
        this.size = new FJetPoint(0,0);
    }

    public boolean drawableIntersectsViewBounds(FJetRect viewBounds, FJetRect boardBounds){

        // TODO -> optimization func, calculates if GameDrawable intersects the container View
        return true;
    }

    public void updateDrawRect(int offsetX, int offsetY, float scale){
        this.drawRect.setAttributes(
                Math.round(((float)this.position.getX() * scale) - ((float)this.size.getX() * scale) + offsetX),
                Math.round(((float)this.position.getY() * scale) - ((float)this.size.getY() * scale) + offsetY),
                Math.round(((float)this.position.getX() * scale) + ((float)this.size.getX() * scale) + offsetX),
                Math.round(((float)this.position.getY() * scale) + ((float)this.size.getY() * scale) + offsetY));
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}