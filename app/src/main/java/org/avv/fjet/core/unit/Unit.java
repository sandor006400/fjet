package org.avv.fjet.core.unit;

import org.avv.fjet.core.board.Cell;
import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.ICoords;
import org.avv.fjet.core.board.SquareCoords;
import org.avv.fjet.core.board.Terrain;
import org.avv.fjet.serialization.JsonSerializable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/02/2016
 */
public class Unit {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private String id;
    private Cell cell;

    // endregion - Fields

    // region - Constructors

    public Unit(){
        this.id = generateID();
    }

    /**
     * Creates a Unit using json with Unit data
     * @param data
     */
    public Unit(UnitData data){

    }

    // endregion - Constructors

    // region - Getters and Setters

    public void setCell(Cell cell){
        this.cell = cell;
    }

    public Cell getCell(){
        return this.cell;
    }

    public String getId(){
        return this.id;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces


    @Override
    public boolean equals(Object o) {

        if (o == null){
            return false;

        } else if (o instanceof Unit){
            return ((Unit)o).id.equals(this.id);

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

    public UnitData getUnitData(){
        UnitData data = new UnitData();
        data.id = this.id;
        return data;
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes


    static public class UnitData {

        public String id;

        public UnitData(){

        }

        public UnitData(String json){
            initWithJson(json);
        }

        public String toJson(){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", this.id);
            } catch (JSONException e) {

            }
            return jsonObject.toString();
        }

        public void initWithJson(String json) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                this.id = jsonObject.getString("id");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    // endregion - Inner and Anonymous Classes

}
