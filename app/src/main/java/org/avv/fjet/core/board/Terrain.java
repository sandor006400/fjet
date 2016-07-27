package org.avv.fjet.core.board;

/**
 * Created by Alexander Vorobiev on 22/05/16.
 */
public class Terrain {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private String type;
    private float movementCost;
    private float defenseCoefficient;

    // endregion - Fields

    // region - Constructors

    public Terrain(String type, float movementCost, float defenseCoefficient){
        this.type = type;
        this.movementCost = movementCost;
        this.defenseCoefficient = defenseCoefficient;
    }

    // endregion - Constructors

    // region - Getters and Setters

    public String getType(){
        return this.type;
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
