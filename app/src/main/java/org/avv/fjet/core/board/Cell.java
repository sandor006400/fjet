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
        this.coords = coords;
        this.terrain = terrain;
        this.id = generateID();
        this.unitId = null;
        this.passable = false;
    }

    public Cell(ICoords coords, Terrain terrain, String id){
        this.coords = coords;
        this.terrain = terrain;
        this.id = id;
        this.unitId = null;
        this.passable = false;
    }

    /**
     * Creates a Cell using json with Cell data
     * @param data
     * @param c
     */
    public Cell(CellData data, Context c){
        this.id = data.id;
        this.unitId = data.unitId;
        this.terrain = TerrainFactory.getInstance(c).getTerrain(data.terrainType);
        this.coords = data.coords;
        this.passable = data.passable;
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

    public CellData getCellData(){
        CellData data = new CellData();
        data.id = this.id;
        data.coords = this.coords;
        if (this.coords instanceof HexCoords){
            data.coordsType = CoordsType.H;

        } else if (this.coords instanceof SquareCoords){
            data.coordsType = CoordsType.S;
        }
        data.terrainType = this.terrain.getType();
        if (this.unit != null) {
            data.unitId = this.unit.getId();
        }
        data.passable = this.passable;
        return data;
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

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    private String generateID(){
        return UUID.randomUUID().toString();
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    public static class CellData {

        public String id;
        public String unitId;
        public ICoords coords;
        public CoordsType coordsType;
        public String terrainType;
        public boolean passable;

        public CellData(){

        }

        public CellData(String json){
            initWithJson(json);
        }

        public String toJson(){
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("id", this.id);
                if (this.unitId != null) {
                    jsonObject.put("unitId", this.unitId);
                }
                jsonObject.put("coords", this.coords.toJson());
                jsonObject.put("coordsType", this.coordsType);
                jsonObject.put("terrainType", this.terrainType);
                jsonObject.put("passable", this.passable);

            } catch (JSONException e) {

            }
            return jsonObject.toString();
        }

        public void initWithJson(String json) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(json);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jsonObject != null) {
                try {
                    this.id = jsonObject.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    this.unitId = jsonObject.getString("unitId");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    this.coordsType = CoordsType.valueOf(jsonObject.getString("coordsType"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if (coordsType == CoordsType.H) {
                        this.coords = new HexCoords(jsonObject.getString("coords"));

                    } else if (coordsType == CoordsType.S) {
                        this.coords = new SquareCoords(jsonObject.getString("coords"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    this.terrainType = jsonObject.getString("terrainType");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    this.passable = jsonObject.getBoolean("passable");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o == null){
                return false;

            } else if (o instanceof CellData) {
                return ((CellData)o).coords == this.coords
                        && ((CellData)o).id.equals(this.id)
                        && ((CellData)o).terrainType.equals(this.terrainType)
                        && ((CellData)o).unitId.equals(this.unitId)
                        && ((CellData)o).coordsType == this.coordsType
                        && ((CellData)o).passable == this.passable;
            } else {
                return false;
            }
        }
    }

    // endregion - Inner and Anonymous Classes

}
