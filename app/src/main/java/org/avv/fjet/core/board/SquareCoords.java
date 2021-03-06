package org.avv.fjet.core.board;

import org.avv.fjet.core.board.util.UtilCoordinates;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/03/2016
 * -------------------------------------------
 * Square cell coordinates
 */
public class SquareCoords implements ICoords {

    // region - Constants

    enum Attributes {

        X("x"),
        Y("y");

        private final String name;

        Attributes(final String name){
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static final SquareCoords [] NEIGHBORS = {
            new SquareCoords(0, -1),
            new SquareCoords(-1, 0), new SquareCoords(1, 0),
            new SquareCoords(0, 1)};

    // endregion - Constants

    // region - Fields

    private int x;
    private int y;

    // endregion - Fields

    // region - Constructors

    public SquareCoords(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a SquareCoords using json with SquareCoords data
     * @param jsonString
     */
    public SquareCoords(String jsonString){
        initWithJson(jsonString);
    }

    // endregion - Constructors

    // region - Getters and Setters

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public ICoords[] getNeighborsArray() {
        SquareCoords [] neighbors = new SquareCoords[NEIGHBORS.length];

        for (int i = 0; i < NEIGHBORS.length; i++){
            neighbors[i] = UtilCoordinates.addSquareCoords(this, NEIGHBORS[i]);
        }
        return neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SquareCoords){
            SquareCoords coords = (SquareCoords) o;
            return this.x == coords.x
                    && this.y == coords.y;
        }
        return false;
    }

    @Override
    public ICoords[] getCoordsInRangeArray(int n) {
        List<SquareCoords> neighbors = new ArrayList<>();

        for (int dy = -n; dy <= n; dy++){               // -N ≤ dy ≤ N
            int minX = Math.max(-dy - n, dy - n);
            int maxX = Math.min(-dy + n, dy + n);

            for (int dx = minX; dx <= maxX; dx++){      // -dy-N ≤ dy ≤ -dy+N
                neighbors.add(UtilCoordinates.addSquareCoords(this, new SquareCoords(dx, dy)));
            }
        }
        return neighbors.toArray(new SquareCoords[neighbors.size()]);
    }

    @Override
    public String toString() {
        //return "SquareCoords(" + this.x + "," + this.y + ")";
        //return JsonSerializer.toJson(this);
        return this.toJson();
    }

    @Override
    public ICoords getCopy() {
        return new SquareCoords(this.x, this.y);
    }

    @Override
    public String toShortString() {
        return "(" + this.x + "," + this.y + ")";
    }

    public void initWithJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            this.x = jsonObject.getInt("x");
            this.y = jsonObject.getInt("y");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public String toJson() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put(Attributes.X.toString(), this.x);
            jsonObj.put(Attributes.Y.toString(), this.y);
        } catch (JSONException e){

        }
        return jsonObj.toString();
    }

    private void initWithJsonString(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            this.x = jsonObject.getInt(Attributes.X.toString());
            this.y = jsonObject.getInt(Attributes.Y.toString());

        } catch (JSONException e) {

        }
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
