package org.avv.fjet.core.board;

import android.content.Context;

import org.avv.fjet.core.unit.Unit;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/03/2016
 */
public class Cell {

    // region - Constants

    enum Attributes {

        ID("id"),
        UNIT_ID("unitId"),
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
    private String unitId;
    private ICoords coords;
    private Terrain terrain;

    // endregion - Fields

    // region - Constructors

    public Cell(ICoords coords, Terrain terrain){
        this.coords = coords;
        this.terrain = terrain;
        this.id = generateID();
        this.unitId = null;
    }

    public Cell(ICoords coords, Terrain terrain, String id){
        this.coords = coords;
        this.terrain = terrain;
        this.id = id;
        this.unitId = null;
    }

    /**
     * Creates a Cell using json with Cell data
     * @param jsonString
     */
    public Cell(String jsonString, Context c, Board.BoardType t){
        initWithJsonString(jsonString, c, t);
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

    public String getUnitId(){
        return this.unitId;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces


    @Override
    public boolean equals(Object o) {

        if (o == null){
            return false;

        } else if (o instanceof Cell){
            return ((Cell)o).coords.equals(this.coords)
                    && ((Cell)o).id.equals(this.id);

        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.toJsonString();
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    private String generateID(){
        return UUID.randomUUID().toString();
    }

    public String toJsonString() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put(Attributes.ID.toString(), this.id);

            if (this.unit != null) {
                jsonObj.put(Attributes.UNIT_ID.toString(), this.unit.getId());
            }
            jsonObj.put(Attributes.COORDS.toString(), this.coords);
            jsonObj.put(Attributes.TERRAIN.toString(), this.terrain.getType().name());

        } catch (JSONException e){

        }
        return jsonObj.toString();
    }

    private void initWithJsonString(String jsonString, Context c, Board.BoardType t){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);

        } catch (JSONException e) {

        }

        if (jsonObject != null) {
            try {
                this.id = jsonObject.getString(Attributes.ID.toString());

            } catch (JSONException e) {
                this.id = generateID();
            }

            try {
                this.unitId = jsonObject.getString(Attributes.UNIT_ID.toString());

            } catch (JSONException e) {
                this.unitId = null;
            }

            try {
                if (t == Board.BoardType.HEX_CELLS) {
                    this.coords = new HexCoords(jsonObject.getString(Attributes.COORDS.toString()));

                } else if (t == Board.BoardType.SQUARE_CELLS){
                    this.coords = new SquareCoords(jsonObject.getString(Attributes.COORDS.toString()));
                }

            } catch (JSONException e) {
                this.unitId = null;
            }

            try {
                this.terrain = TerrainFactory.getInstance(c).getTerrain(
                        Terrain.TerrainType.valueOf(
                                jsonObject.getString(Attributes.TERRAIN.toString())));

            } catch (JSONException e) {
                this.terrain = null;
            }
        }
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
