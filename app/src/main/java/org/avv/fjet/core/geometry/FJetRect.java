package org.avv.fjet.core.geometry;

import android.graphics.Rect;

/**
 * Created by Alexander Vorobiev on 22/05/16.
 * ------------------------------------------
 * Android Rect wrapper
 */
public class FJetRect {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private Rect rect;

    // endregion - Fields

    // region - Constructors

    public FJetRect(int x, int y, int width, int height){
        this.rect = new Rect(x, y, x + width, y + height);
    }

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public int getWidth(){
        return this.rect.width();
    }

    public int getHeight(){
        return this.rect.height();
    }

    public int getX(){
        return this.rect.left;
    }

    public int getY(){
        return this.rect.top;
    }

    public void offsetTo(FJetPoint p){
        this.rect.offsetTo(p.getX(), p.getY());
    }

    public void offsetTo(int x, int y){
        this.rect.offsetTo(x, y);
    }

    public void offsetBy(FJetPoint p){
        this.rect.offset(p.getX(), p.getY());
    }

    public void offsetBy(int x, int y){
        this.rect.offset(x, y);
    }

    public Rect getRect(){
        return this.rect;
    }

    public Rect getRectCopy(){
        return new Rect(this.rect);
    }

    public void setAttributes(int x, int y, int width, int height){
        this.rect.set(x, y, x + width, y + height);
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
