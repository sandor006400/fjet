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
    private Map<Integer, Terrain> terrainsMap;

    // endregion - Fields

    // region - Constructors

    private TerrainFactory(Context c){
        initTerrains(c);
    }

    // endregion - Constructors

    // region - Getters and Setters

    public Terrain getTerrain(Terrain.TerrainType terrainType){
        return this.terrainsMap.get(terrainType.ordinal());
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

    private void initTerrains(Context c){
        this.terrainsMap = new LinkedHashMap<>();

        if (c != null) {

            // Coast
            Drawable cD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_01, c.getTheme());
            Drawable cDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_01, c.getTheme());
            Terrain cT = new Terrain(Terrain.TerrainType.COAST, cD, cDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.COAST.ordinal(), cT);

            // Atoll
            Drawable aD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_02, c.getTheme());
            Drawable aDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_02, c.getTheme());
            Terrain aT = new Terrain(Terrain.TerrainType.ATOLL, aD, aDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.ATOLL.ordinal(), aT);

            // Desert
            Drawable dD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_03, c.getTheme());
            Drawable dDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_03, c.getTheme());
            Terrain dT = new Terrain(Terrain.TerrainType.DESERT, dD, dDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.DESERT.ordinal(), dT);

            // Flood
            Drawable fD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_04, c.getTheme());
            Drawable fDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_04, c.getTheme());
            Terrain fT = new Terrain(Terrain.TerrainType.FLOOD, fD, fDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.FLOOD.ordinal(), fT);

            // Forest
            Drawable foD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_05, c.getTheme());
            Drawable foDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_05, c.getTheme());
            Terrain foT = new Terrain(Terrain.TerrainType.FOREST, foD, foDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.FOREST.ordinal(), foT);

            // Grassland
            Drawable gD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_06, c.getTheme());
            Drawable gDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_06, c.getTheme());
            Terrain gT = new Terrain(Terrain.TerrainType.GRASSLAND, gD, gDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.GRASSLAND.ordinal(), gT);

            // Grassland
            Drawable hD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_07, c.getTheme());
            Drawable hDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_07, c.getTheme());
            Terrain hT = new Terrain(Terrain.TerrainType.HILL, hD, hDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.HILL.ordinal(), hT);

            // Ice
            Drawable iD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_08, c.getTheme());
            Drawable iDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_08, c.getTheme());
            Terrain iT = new Terrain(Terrain.TerrainType.ICE, iD, iDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.ICE.ordinal(), iT);

            // Jungle
            Drawable jD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_09, c.getTheme());
            Drawable jDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_09, c.getTheme());
            Terrain jT = new Terrain(Terrain.TerrainType.JUNGLE, jD, jDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.JUNGLE.ordinal(), jT);

            // Lakes
            Drawable lD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_10, c.getTheme());
            Drawable lDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_10, c.getTheme());
            Terrain lT = new Terrain(Terrain.TerrainType.LAKES, lD, lDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.LAKES.ordinal(), lT);

            // Marsh
            Drawable mD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_11, c.getTheme());
            Drawable mDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_11, c.getTheme());
            Terrain mT = new Terrain(Terrain.TerrainType.MARSH, mD, mDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.MARSH.ordinal(), mT);

            // Mountain
            Drawable moD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_12, c.getTheme());
            Drawable moDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_12, c.getTheme());
            Terrain moT = new Terrain(Terrain.TerrainType.MOUNTAIN, moD, moDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.MOUNTAIN.ordinal(), moT);

            // Oasis
            Drawable oD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_13, c.getTheme());
            Drawable oDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_13, c.getTheme());
            Terrain oT = new Terrain(Terrain.TerrainType.OASIS, oD, oDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.OASIS.ordinal(), oT);

            // Ocean
            Drawable ocD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_13, c.getTheme());
            Drawable ocDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_13, c.getTheme());
            Terrain ocT = new Terrain(Terrain.TerrainType.OCEAN, ocD, ocDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.OCEAN.ordinal(), ocT);

            // Plains
            Drawable pD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_14, c.getTheme());
            Drawable pDH = c.getResources().getDrawable(R.drawable.tex_he_terrain_14, c.getTheme());
            Terrain pT = new Terrain(Terrain.TerrainType.PLAINS, pD, pDH, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.PLAINS.ordinal(), pT);

        }
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