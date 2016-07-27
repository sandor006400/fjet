package org.avv.fjet.core.board;

import android.content.Context;

import org.avv.fjet.core.GameEntity;
import org.avv.fjet.core.unit.Unit;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.UUID;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/03/2016
 */
public class Cell extends GameEntity {

    // region - Constants

    enum CoordsType {
        H, S
    }

    // endregion - Constants

    // region - Fields

    private String id;
    private Unit unit;
    private String unitId;
    private ICoords coords;
    private Terrain terrain;
    private boolean passable;

    // endregion - Fields

    // region - Constructors

    public Cell(ICoords coords, Terrain terrain){
        super();
        this.coords = coords;
        this.terrain = terrain;
        this.id = generateID();
        this.unitId = null;
        this.passable = false;
    }

    public Cell(ICoords coords, Terrain terrain, String id){
        super();
        this.coords = coords;
        this.terrain = terrain;
        this.id = id;
        this.unitId = null;
        this.passable = false;
    }

    /**
     * Creates a Cell using json with json
     * @param json
     */
    public Cell(JSONObject json){
        super(json);

        initWithJson(json);
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

    public boolean isPassable(){
        return this.passable;
    }

    public void setPassable(boolean passable){
        this.passable = passable;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    protected JSONObject initJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", this.id);
            if (this.unitId != null) {
                jsonObject.put("unitId", this.unitId);
            }
            jsonObject.put("coords", this.coords.toJson());

            if (this.coords instanceof HexCoords) {
                jsonObject.put("coordsType", CoordsType.H);

            } else {
                jsonObject.put("coordsType", CoordsType.S);
            }
            jsonObject.put("terrainType", this.terrain.getType());
            jsonObject.put("passable", this.passable);

        } catch (JSONException e) {

        }
        return jsonObject;
    }

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
        return this.id;
    }

    @Override
    public void initWithJson(JSONObject json) {
        super.initWithJson(json);

        if (json != null) {
            try {
                this.id = json.getString("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                this.unitId = json.getString("unitId");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            CoordsType coordsType = CoordsType.S;
            try {
                coordsType = CoordsType.valueOf(json.getString("coordsType"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                if (coordsType == CoordsType.H) {
                    this.coords = new HexCoords(json.getString("coords"));

                } else if (coordsType == CoordsType.S) {
                    this.coords = new SquareCoords(json.getString("coords"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                this.terrain = TerrainFactory.getInstance().getTerrain(json.getString("terrainType"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                this.passable = json.getBoolean("passable");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    private String generateID(){
        return UUID.randomUUID().toString();
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
