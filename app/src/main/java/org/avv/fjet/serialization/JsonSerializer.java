package org.avv.fjet.serialization;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by Alexander Vorobiev on 24/05/16.
 */
public class JsonSerializer {

    // region - Constants

    // endregion - Constants

    // region - Fields

    // endregion - Fields

    // region - Constructors

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    /**
     * Method that serielizes an object to JSON format string. Source: http://www.rgagnon.com/javadetails/java-get-fields-and-values-from-an-object.html
     * @param object
     * @return
     */
    public static String toJsonString(final Object object){
        Class<? extends Object> objectClass = object.getClass();
        Field[] fields = objectClass.getDeclaredFields();
        JSONObject jsonObject = new JSONObject();

        //Log.d("JsonSerializer", "class name: " + String.valueOf(objectClass.getName()) + " fields: " + String.valueOf(fields.length));
        try {
            for(int i = 0; i < fields.length; i++){

                // See: http://stackoverflow.com/questions/36549129/android-java-objmodelclass-getclass-getdeclaredfields-returns-change-as-o
                if (!fields[i].isSynthetic()
                        && !fields[i].isEnumConstant()
                        && !Modifier.isFinal(fields[i].getModifiers())){
                    Log.d("attribute ->", fields[i].getName());
                    fields[i].setAccessible(true);
                    Object value = fields[i].get(object);
                    Log.d("value ->", value.toString());
                    jsonObject.put(
                            fields[i].getName(),
                            value.toString());
                }
            }

        } catch (IllegalAccessException e) {
            return null;

        } catch (JSONException e) {
            return null;
        }
        return jsonObject.toString();
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
