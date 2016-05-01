package org.avv.fjet.core.board;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 03/04/2016
 * -------------------------------------------
 * Point wrapper
 */
public class Point {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private int x;
    private int y;

    // endregion - Fields

    // region - Constructors

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    // endregion - Constructors

    // region - Getters and Setters

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public android.graphics.Point getAndroidPoint(){
        return new android.graphics.Point(this.x, this.y);
    }

    @Override
    public String toString() {
        return "Point(" + this.x + "," + this.y + ")";
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
