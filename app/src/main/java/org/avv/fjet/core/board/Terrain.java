package org.avv.fjet.core.board;

import android.graphics.drawable.Drawable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alexander Vorobiev on 22/05/16.
 */
public class Terrain {

    // region - Constants

    public enum TerrainType {
        COAST,
        DESERT,
        GRASSLAND,
        HILL,
        MOUNTAIN,
        OCEAN,
        PLAINS,
        FOREST,
        JUNGLE,
        MARSH,
        ATOLL,
        FLOOD,
        ICE,
        LAKES,
        OASIS
    }

    // endregion - Constants

    // region - Fields

    private Drawable drawable;
    private TerrainType type;
    private float movementCost;
    private float defenseCoefficent;

    // endregion - Fields

    // region - Constructors

    public Terrain(TerrainType type, Drawable drawable, float movementCost, float defenseCoefficent){
        this.type = type;
        this.drawable = drawable;
        this.movementCost = movementCost;
        this.defenseCoefficent = defenseCoefficent;
    }

    // endregion - Constructors

    // region - Getters and Setters

    public TerrainType getType(){
        return this.type;
    }

    public Drawable getDrawable(){
        return this.drawable;
    }

    public float getMovementCost(){
        return this.movementCost;
    }

    public  float getDefenseCoefficent(){
        return this.defenseCoefficent;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public String toString() {
        return "" + this.type;
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
