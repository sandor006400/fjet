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
            Terrain cT = new Terrain(Terrain.TerrainType.COAST, cD, cD, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.COAST.ordinal(), cT);

            // Atoll
            Drawable aD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_02, c.getTheme());
            Terrain aT = new Terrain(Terrain.TerrainType.ATOLL, aD, aD, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.ATOLL.ordinal(), aT);

            // Desert
            Drawable dD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_03, c.getTheme());
            Terrain dT = new Terrain(Terrain.TerrainType.DESERT, dD, dD, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.DESERT.ordinal(), dT);

            // Flood
            Drawable fD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_04, c.getTheme());
            Terrain fT = new Terrain(Terrain.TerrainType.FLOOD, fD, fD, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.FLOOD.ordinal(), fT);

            // Forest
            Drawable foD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_05, c.getTheme());
            Terrain foT = new Terrain(Terrain.TerrainType.FOREST, foD, foD, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.FOREST.ordinal(), foT);

            // Grassland
            Drawable gD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_06, c.getTheme());
            Terrain gT = new Terrain(Terrain.TerrainType.GRASSLAND, gD, gD, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.GRASSLAND.ordinal(), gT);

            // Grassland
            Drawable hD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_07, c.getTheme());
            Terrain hT = new Terrain(Terrain.TerrainType.HILL, hD, hD, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.HILL.ordinal(), hT);

            // Ice
            Drawable iD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_08, c.getTheme());
            Terrain iT = new Terrain(Terrain.TerrainType.ICE, iD, iD, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.ICE.ordinal(), iT);

            // Jungle
            Drawable jD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_09, c.getTheme());
            Terrain jT = new Terrain(Terrain.TerrainType.JUNGLE, jD, jD, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.JUNGLE.ordinal(), jT);

            // Lakes
            Drawable lD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_10, c.getTheme());
            Terrain lT = new Terrain(Terrain.TerrainType.LAKES, lD, lD, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.LAKES.ordinal(), lT);

            // Marsh
            Drawable mD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_11, c.getTheme());
            Terrain mT = new Terrain(Terrain.TerrainType.MARSH, mD, mD, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.MARSH.ordinal(), mT);

            // Mountain
            Drawable moD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_12, c.getTheme());
            Terrain moT = new Terrain(Terrain.TerrainType.MOUNTAIN, moD, moD, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.MOUNTAIN.ordinal(), moT);

            // Oasis
            Drawable oD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_13, c.getTheme());
            Terrain oT = new Terrain(Terrain.TerrainType.OASIS, oD, oD, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.OASIS.ordinal(), oT);

            // Ocean
            Drawable ocD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_13, c.getTheme());
            Terrain ocT = new Terrain(Terrain.TerrainType.OCEAN, ocD, ocD, 1.0f, 1.0f);
            this.terrainsMap.put(Terrain.TerrainType.OCEAN.ordinal(), ocT);

            // Plains
            Drawable pD = c.getResources().getDrawable(R.drawable.tex_sq_terrain_14, c.getTheme());
            Terrain pT = new Terrain(Terrain.TerrainType.PLAINS, pD, pD, 1.0f, 1.0f);
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