package org.avv.fjet.core.board;

import android.content.Context;
import android.graphics.drawable.Drawable;

import org.avv.fjet.R;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Alexander Vorobiev on 22/05/16.
 */
public class TerrainFactory {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private static TerrainFactory INSTANCE;
    private Map<String, Terrain> terrainsMap;

    // endregion - Fields

    // region - Constructors

    private TerrainFactory(Context c){
        this.terrainsMap = new LinkedHashMap<>();
    }

    // endregion - Constructors

    // region - Getters and Setters

    public Terrain getTerrain(String terrainType){
        return this.terrainsMap.get(terrainType);
    }

    /**
     * Adds new Terrain subclass to terrains map. The terrainType cant be same as one of
     * basic terrain types deffined in Terrain.TerrainType
     * @param terrainType
     * @param terrain
     * @return
     */
    public void addNewTerrain(String terrainType, Terrain terrain){

        if (!this.terrainsMap.containsKey(terrainType)) {
            this.terrainsMap.put(terrainType, terrain);
        }
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public static TerrainFactory getInstance(Context c){
        if (INSTANCE == null){
            INSTANCE = new TerrainFactory(c);
        }
        return INSTANCE;
    }

    /**
     * Returns a random Terrain
     * @return
     */
    public Terrain getRandomTerrain(){
        Random random = new Random();
        int rand = random.nextInt(this.terrainsMap.size());

        Collection<Terrain> values = this.terrainsMap.values();
        int j = 0;

        for(Terrain t : values) {
            if (j++ == rand) {
                return t;
            }
        }
        return null;
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}