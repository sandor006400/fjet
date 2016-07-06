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
        //initBasicTerrains(c);
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

    /*private void initBasicTerrains(Context c){
        this.terrainsMap = new LinkedHashMap<>();

        if (c != null) {

            // Coast
            Drawable cD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_01, c.getTheme());
            Drawable cDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_01, c.getTheme());
            Drawable x = c.getResources().
            Terrain cT = new Terrain(Terrain.TerrainType.COAST.toString(), cD, cDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.COAST.name(), cT);

            // Atoll
            Drawable aD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_02, c.getTheme());
            Drawable aDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_02, c.getTheme());
            Terrain aT = new Terrain(Terrain.TerrainType.ATOLL.toString(), aD, aDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.ATOLL.name(), aT);

            // Desert
            Drawable dD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_03, c.getTheme());
            Drawable dDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_03, c.getTheme());
            Terrain dT = new Terrain(Terrain.TerrainType.DESERT.toString(), dD, dDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.DESERT.name(), dT);

            // Flood
            Drawable fD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_04, c.getTheme());
            Drawable fDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_04, c.getTheme());
            Terrain fT = new Terrain(Terrain.TerrainType.FLOOD.toString(), fD, fDH, 1.2f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.FLOOD.name(), fT);

            // Forest
            Drawable foD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_05, c.getTheme());
            Drawable foDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_05, c.getTheme());
            Terrain foT = new Terrain(Terrain.TerrainType.FOREST.toString(), foD, foDH, 2.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.FOREST.name(), foT);

            // Grassland
            Drawable gD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_06, c.getTheme());
            Drawable gDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_06, c.getTheme());
            Terrain gT = new Terrain(Terrain.TerrainType.GRASSLAND.toString(), gD, gDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.GRASSLAND.name(), gT);

            // Grassland
            Drawable hD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_07, c.getTheme());
            Drawable hDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_07, c.getTheme());
            Terrain hT = new Terrain(Terrain.TerrainType.HILL.toString(), hD, hDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.HILL.name(), hT);

            // Ice
            Drawable iD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_08, c.getTheme());
            Drawable iDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_08, c.getTheme());
            Terrain iT = new Terrain(Terrain.TerrainType.ICE.toString(), iD, iDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.ICE.name(), iT);

            // Jungle
            Drawable jD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_09, c.getTheme());
            Drawable jDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_09, c.getTheme());
            Terrain jT = new Terrain(Terrain.TerrainType.JUNGLE.toString(), jD, jDH, 2.5f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.JUNGLE.name(), jT);

            // Lakes
            Drawable lD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_10, c.getTheme());
            Drawable lDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_10, c.getTheme());
            Terrain lT = new Terrain(Terrain.TerrainType.LAKES.toString(), lD, lDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.LAKES.name(), lT);

            // Marsh
            Drawable mD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_11, c.getTheme());
            Drawable mDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_11, c.getTheme());
            Terrain mT = new Terrain(Terrain.TerrainType.MARSH.toString(), mD, mDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.MARSH.name(), mT);

            // Mountain
            Drawable moD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_12, c.getTheme());
            Drawable moDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_12, c.getTheme());
            Terrain moT = new Terrain(Terrain.TerrainType.MOUNTAIN.toString(), moD, moDH, 3.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.MOUNTAIN.name(), moT);

            // Oasis
            Drawable oD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_13, c.getTheme());
            Drawable oDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_13, c.getTheme());
            Terrain oT = new Terrain(Terrain.TerrainType.OASIS.toString(), oD, oDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.OASIS.name(), oT);

            // Ocean
            Drawable ocD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_13, c.getTheme());
            Drawable ocDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_13, c.getTheme());
            Terrain ocT = new Terrain(Terrain.TerrainType.OCEAN.toString(), ocD, ocDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.OCEAN.name(), ocT);

            // Plains
            Drawable pD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_14, c.getTheme());
            Drawable pDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_14, c.getTheme());
            Terrain pT = new Terrain(Terrain.TerrainType.PLAINS.toString(), pD, pDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.PLAINS.name(), pT);
        }
    }*/

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