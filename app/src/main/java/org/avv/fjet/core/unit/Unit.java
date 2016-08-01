package org.avv.fjet.core.unit;

import org.avv.fjet.core.GameEntity;
import org.avv.fjet.core.board.Cell;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/02/2016
 */
public class Unit extends GameEntity {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private String id;
    private String playerId;
    private Cell cell;
    private String unitType;

    // endregion - Fields

    // region - Constructors

    public Unit(){
        super();

        this.id = generateID();
    }

    /**
     * Creates a Unit using json with json
     * @param json
     */
    public Unit(JSONObject json){
        super(json);

        initWithJson(json);
    }

    // endregion - Constructors

    // region - Getters and Setters

    public void setCell(Cell cell){
        this.cell = cell;
    }

    public void setPlayerId(String playerId){
        this.playerId = playerId;
    }

    public Cell getCell(){
        return this.cell;
    }

    public String getId(){
        return this.id;
    }

    public String getPlayerId(){
        return this.playerId;
    }

    public String getUnitType(){
        return this.unitType;
    }

    public void setUnitType(String unitType){
        this.unitType = unitType;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

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
                this.unitType = json.getString("type");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                this.playerId = json.getString("playerId");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected JSONObject initJSONObject() {
        JSONObject jsonObject = super.toJson();
        try {
            jsonObject.put("id", this.id);
            jsonObject.put("type", this.unitType);
            jsonObject.put("playerId", this.playerId);

        } catch (JSONException e) {

        }
        return jsonObject;
    }

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

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
