package org.avv.fjet.core.board;

import android.graphics.drawable.Drawable;

/**
 * Created by Alexander Vorobiev on 22/05/16.
 */
public class Terrain {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private Drawable drawableSq;    // Drawable for square Cells
    private Drawable drawableHe;    // Drawable for hex Cells
    private String type;
    private float movementCost;
    private float defenseCoefficient;

    // endregion - Fields

    // region - Constructors

    public Terrain(String type, Drawable drawableSq, Drawable drawableHe, float movementCost, float defenseCoefficient){
        this.type = type;
        this.drawableSq = drawableSq;
        this.drawableHe = drawableHe;
        this.movementCost = movementCost;
        this.defenseCoefficient = defenseCoefficient;
    }

    // endregion - Constructors

    // region - Getters and Setters

    public String getType(){
        return this.type;
    }

    public Drawable getDrawableSq(){
        return this.drawableSq;
    }

    public Drawable getDrawableHe(){
        return this.drawableHe;
    }

    public float getMovementCost(){
        return this.movementCost;
    }

    public  float getDefenseCoefficient(){
        return this.defenseCoefficient;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public String toString() {
        return this.type;
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
