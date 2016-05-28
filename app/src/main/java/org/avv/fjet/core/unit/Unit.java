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

    enum Attributes {

        ID("id"),
        CELL("cell");

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
    private Cell cell;

    // endregion - Fields

    // region - Constructors

    public Unit(){
        this.id = generateID();
    }

    /**
     * Creates a Unit using json with Unit data
     * @param jsonString
     */
    public Unit(String jsonString){
        initWithJsonString(jsonString);
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
        return this.toJsonString();
    }

    public String toJsonString() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put(Attributes.ID.toString(), this.id);
            jsonObj.put(Attributes.CELL.toString(), this.cell);
        } catch (JSONException e){

        }
        return jsonObj.toString();
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    private String generateID(){
        return UUID.randomUUID().toString();
    }

    private void initWithJsonString(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            this.id = jsonObject.getString(Attributes.ID.toString());

        } catch (JSONException e) {

        }
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes


    public class UnitData extends JsonSerializable {

        public String id;

        @Override
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
