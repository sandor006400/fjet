package org.avv.fjet.core.player;

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
public class Player {

    // region - Constants

    private String DEFAULT_NAME = "Unknown";

    // endregion - Constants

    // region - Fields

    private String id;
    private String name;

    // endregion - Fields

    // region - Constructors

    public Player(){
        this.id = generateId();
        this.name = DEFAULT_NAME;
    }

    public Player(String json){
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

    public String toJson(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", this.id);
            jsonObject.put("name", this.name);

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
                this.name = jsonObject.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
