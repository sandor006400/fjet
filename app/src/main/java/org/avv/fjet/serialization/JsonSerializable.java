package org.avv.fjet.serialization;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Alexander Vorobiev on 24/05/16.
 */
public abstract class JsonSerializable {

    // region - Constants

    // endregion - Constants

    // region - Fields

    // endregion - Fields

    // region - Constructors

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    /**
     * Initialize an object attributes using JSON
     * @param json
     * @return
     */
    abstract public void initWithJson(String json);

    /**
     * Serilizes a object to Strng with JSON format
     * @return
     */
    public String toJson(){
        return toJsonString(this);
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    /**
     * Method that serielizes an object to JSON format string using reflexion to get objects attributes.
     * Source: http://www.rgagnon.com/javadetails/java-get-fields-and-values-from-an-object.html
     * @param object
     * @return
     */
    private static String toJsonString(final Object object){
        Class<? extends Object> objectClass = object.getClass();
        Field[] fields = objectClass.getDeclaredFields();
        JSONObject jsonObject = new JSONObject();
        try {
            for(int i = 0; i < fields.length; i++){

                // See: http://stackoverflow.com/questions/36549129/android-java-objmodelclass-getclass-getdeclaredfields-returns-change-as-o
                if (!fields[i].isSynthetic()
                        && !fields[i].isEnumConstant()
                        && !Modifier.isFinal(fields[i].getModifiers())
                        && !Modifier.isStatic(fields[i].getModifiers())) {

                    fields[i].setAccessible(true);
                    Object value = fields[i].get(object);

                    if (value instanceof Collection) {
                        jsonObject.put(
                                fields[i].getName(),
                                getJSONArrayFromArray(((Collection) value).toArray()));

                    } else if (value instanceof Map) {
                        jsonObject.put(
                                fields[i].getName(),
                                getJSONArrayFromArray(((Map) value).values().toArray()));

                    } else if (value instanceof Object[]){
                        jsonObject.put(
                                fields[i].getName(),
                                getJSONArrayFromArray((Object[]) value));

                    } else {
                        jsonObject.put(
                                fields[i].getName(),
                                toJsonString(value));
                    }
                }
            }

        } catch (IllegalAccessException e) {
            return null;

        } catch (JSONException e) {
            return null;
        }
        return jsonObject.toString();
    }

    static private JSONArray getJSONArrayFromArray(Object [] array){
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < array.length; i++) {
            Object obj = array[i];
            jsonArray.put(toJsonString(obj));
        }
        return jsonArray;
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
