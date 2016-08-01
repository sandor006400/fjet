package org.avv.fjet.graphics.unit;

import android.content.Context;

import org.avv.fjet.graphics.GameAnimation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander Vorobiev on 3/07/16.
 */
public class UnitDrawableFactory {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private static UnitDrawableFactory INSTANCE = null;
    private Map<String, Map<String, GameAnimation>> unitAnimations;

    // endregion - Fields

    // region - Constructors

    // endregion - Constructors

    private UnitDrawableFactory(Context c){
        this.unitAnimations = new HashMap<>();
    }

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public static UnitDrawableFactory getInstance(Context c){

        if (INSTANCE == null){
            INSTANCE = new UnitDrawableFactory(c);
        }
        return INSTANCE;
    }

    public UnitDrawable createUnitDrawable(String unitType){
        Map<String, GameAnimation> gameAnimations = this.unitAnimations.get(unitType);

        if (gameAnimations != null) {
            UnitDrawable uD = new UnitDrawable();

            for (Map.Entry<String, GameAnimation> entry : gameAnimations.entrySet()) {
                uD.addAnimation(entry.getKey(), new GameAnimation(entry.getValue()));
            }
            return uD;
        }
        return null;
    }

    public void addNewUnitAnimation(Map<String, GameAnimation> gameAnimation, String unitType){
        this.unitAnimations.put(unitType, gameAnimation);
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
