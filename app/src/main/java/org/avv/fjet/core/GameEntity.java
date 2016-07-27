package org.avv.fjet.core;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Alexander Vorobiev on 26/07/16.
 */
public abstract class GameEntity {

    // region - Constants

    // endregion - Constants

    // region - Fields

    protected Map<String, String> attributes;

    // endregion - Fields

    // region - Constructors

    public GameEntity(){
        this.attributes = new HashMap<>();
    }

    public GameEntity(Map<String, String> attributesMap){
        this.attributes = attributesMap;
    }

    public GameEntity(JSONObject json){
        this.attributes = new HashMap<>();
        initWithJson(json);
    }

    // endregion - Constructors

    // region - Getters and Setters

    public void setAttributes(Map<String, String> attributes){
        this.attributes = attributes;
    }

    public Map<String,String> getAttributes(){
        return this.attributes;
    }

    public void setAttribute(String attribute, String value){
        this.attributes.put(attribute,value);
    }

    public String getAttribute(String attribute){
        return this.attributes.get(attribute);
    }

    public JSONObject toJson(){
        JSONObject json = initJSONObject();

        for (Map.Entry<String, String> entry : this.attributes.entrySet()){
            try {
                json.put(entry.getKey(), entry.getValue());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    /**
     * Method that subclasses must implement initializing JSONObject with
     * personalized attributes not contained in attributes map
     * @return
     */
    abstract protected JSONObject initJSONObject();

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public void initWithJson(JSONObject json){
        Iterator<String> i = json.keys();
        String key;

        while (i.hasNext()){
            key = i.next();

            try {
                this.attributes.put(key, json.getString(key));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
