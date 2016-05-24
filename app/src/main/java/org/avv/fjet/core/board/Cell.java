package org.avv.fjet.core.board;

import org.avv.fjet.core.unit.Unit;
import org.avv.fjet.serialization.JsonSerializer;

import java.util.UUID;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/03/2016
 */
public class Cell {

    // region - Constants

    enum Attributes {

        ID("id"),
        UNIT("unit"),
        COORDS("coords"),
        TERRAIN("terrain");

        private final String name;

        Attributes(final String name){
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // endregion - Constants

    // region - Fields

    private String id;
    private Unit unit;
    private ICoords coords;
    private Terrain terrain;

    // endregion - Fields

    // region - Constructors

    public Cell(ICoords coords, Terrain terrain){
        this.coords = coords;
        this.terrain = terrain;
        this.id = generateID();
    }

    public Cell(ICoords coords, Terrain terrain, String id){
        this.coords = coords;
        this.terrain = terrain;
        this.id = id;
    }

    // endregion - Constructors

    // region - Getters and Setters

    public void setUnit(Unit unit){
        this.unit = unit;
    }

    public void removeUnit(){
        this.unit = null;
    }

    public Unit getUnit(){
        return this.unit;
    }

    public ICoords getCoords(){
        return this.coords;
    }

    public String getId(){
        return this.id;
    }

    public Terrain getTerrain(){
        return this.terrain;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    private String generateID(){
        return UUID.randomUUID().toString();
    }

    /*public static String toJsonString(Cell cell){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put(Attributes.ID.toString(), cell.coords.toJsonString());
            jsonObj.put(Attributes.UNIT.toString(), cell.unit.toJsonString());
            jsonObj.put(Attributes.COORDS, cell.coords.toJsonString());
            jsonObj.put(Attributes.TERRAIN, cell.terrain.toJsonString());
        } catch (JSONException e){

        }
    }*/

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
