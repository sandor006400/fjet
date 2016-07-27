package org.avv.fjet.core.player;

import org.avv.fjet.core.GameEntity;
import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.SquareCoords;
import org.avv.fjet.core.board.Terrain;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/02/2016
 */
public class Player extends GameEntity {

    // region - Constants

    private static String DEFAULT_NAME = "Unknown";

    // endregion - Constants

    // region - Fields

    private String id;
    private String name;

    // endregion - Fields

    // region - Constructors

    public Player(){
        super();
        this.id = generateId();
        this.name = DEFAULT_NAME;
    }

    public Player(JSONObject json){
        super();

        initWithJson(json);
    }

    // endregion - Constructors

    // region - Getters and Setters

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public boolean equals(Object o) {
        if (o == null){
            return false;

        } else if (o instanceof Player) {
            return ((Player)o).name.equals(this.name)
                    && ((Player)o).id.equals(this.id);
        } else {
            return false;
        }
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    private String generateId(){
        return UUID.randomUUID().toString().replace(" ", "");
    }

    @Override
    protected JSONObject initJSONObject() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", this.id);
            jsonObject.put("name", this.name);

        } catch (JSONException e) {

        }
        return jsonObject;
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
                this.name = json.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
