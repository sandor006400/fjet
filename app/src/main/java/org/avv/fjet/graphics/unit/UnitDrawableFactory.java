package org.avv.fjet.graphics.unit;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

import org.avv.fjet.R;
import org.avv.fjet.core.Game;
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

        initBasicUnitAnimations(c);
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

    private void initBasicUnitAnimations(Context c){

        // TODO : DO IT BETTER
        Drawable dU1 = c.getDrawable(R.drawable.unit_dw_01);
        Drawable dU2 = c.getDrawable(R.drawable.unit_dw_02);
        Drawable dU3 = c.getDrawable(R.drawable.unit_dw_03);
        GameAnimation gameAnimation = new GameAnimation();
        gameAnimation.setDrawables(new Drawable[]{dU1, dU2, dU3});
        gameAnimation.setDuration(GameAnimation.Duration.INFINITE, 1);
        gameAnimation.setUpdatesPerFrame(60, 2);
        Map<String, GameAnimation> animationsMap = new HashMap<>();
        animationsMap.put(UnitDrawable.State.WAITING.toString(), gameAnimation);
        this.unitAnimations.put("Warrior", animationsMap);

    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
