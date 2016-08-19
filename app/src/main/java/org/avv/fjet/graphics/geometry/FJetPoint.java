package org.avv.fjet.graphics.geometry;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 03/04/2016
 * -------------------------------------------
 * FJetPoint wrapper
 */
public class FJetPoint {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private int x;
    private int y;

    // endregion - Fields

    // region - Constructors

    public FJetPoint(int x, int y){
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
        return "FJetPoint(" + this.x + "," + this.y + ")";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof FJetPoint
                && ((FJetPoint)o).getX() == this.getX()
                && ((FJetPoint)o).getY() == this.getY();
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
