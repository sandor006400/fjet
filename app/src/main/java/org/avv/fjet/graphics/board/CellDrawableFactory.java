package org.avv.fjet.graphics.board;

import android.content.Context;

import org.avv.fjet.graphics.GameAnimation;
import org.avv.fjet.graphics.unit.UnitDrawable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander Vorobiev on 27/07/16.
 */
public class CellDrawableFactory {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private static CellDrawableFactory INSTANCE = null;
    private Map<String, Map<String, GameAnimation>> cellAnimations;

    // endregion - Fields

    // region - Constructors

    // endregion - Constructors

    private CellDrawableFactory(Context c){
        this.cellAnimations = new HashMap<>();
    }

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public static CellDrawableFactory getInstance(Context c){

        if (INSTANCE == null){
            INSTANCE = new CellDrawableFactory(c);
        }
        return INSTANCE;
    }

    /**
     * Returns CellDrawable for a specific terrainType
     * @param terrainType
     * @return
     */
    public CellDrawable createCellDrawable(String terrainType){
        Map<String, GameAnimation> gameAnimations = this.cellAnimations.get(terrainType);

        if (gameAnimations != null) {
            CellDrawable cD = new CellDrawable();

            for (Map.Entry<String, GameAnimation> entry : gameAnimations.entrySet()) {
                cD.addAnimation(entry.getKey(), new GameAnimation(entry.getValue()));
            }
            return cD;
        }
        return null;
    }

    /**
     * Add new dictionary of AnimatedGameDrawables for a new terrainType
     * @param gameAnimation
     * @param terrainType
     */
    public void addNewCellAnimation(Map<String, GameAnimation> gameAnimation, String terrainType){
        this.cellAnimations.put(terrainType, gameAnimation);
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
