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
 * Hexagonal cell axial coordinates
 */
public class HexCoords implements ICoords {

    // region - Constants

    enum Attributes {

        Q("q"),
        R("r");

        private final String name;

        Attributes(final String name){
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static final HexCoords [] NEIGHBORS = {
            new HexCoords(0, -1), new HexCoords(1, -1),
            new HexCoords(-1, 0), new HexCoords(1, 0),
            new HexCoords(-1, 1), new HexCoords(0, 1)};

    // endregion - Constants

    // region - Fields

    private int q;
    private int r;

    // endregion - Fields

    // region - Constructors

    public HexCoords(int q, int r){
        this.q = q;
        this.r = r;
    }

    /**
     * Creates a HexCoords using json with HexCoords data
     * @param jsonString
     */
    public HexCoords(String jsonString){
        initWithJson(jsonString);
    }

    // endregion - Constructors

    // region - Getters and Setters

    public int getR(){
        return this.r;
    }

    public int getQ(){
        return this.q;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public ICoords[] getNeighborsArray() {
        HexCoords [] neighbors = new HexCoords[NEIGHBORS.length];

        for (int i = 0; i < NEIGHBORS.length; i++){
            neighbors[i] = UtilCoordinates.addHexCoords(this, NEIGHBORS[i]);
        }
        return neighbors;
    }

    @Override
    public String toString() {
        return this.toJson();
    }

    @Override
    public ICoords getCopy() {
        return new HexCoords(this.q, this.r);
    }

    @Override
    public String toShortString() {
        return "(" + this.q + "," + this.r + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof HexCoords){
            HexCoords coords = (HexCoords) o;
            return this.q == coords.q
                    && this.r == coords.r;
        }
        return false;
    }

    /**
     * Hex coord within N range from current HexCoords. See <a href="http://www.redblobgames.com/grids/hexagons/#range">Coordinate range</a>/>.
     * @param n
     * @return
     */
    @Override
    public ICoords[] getCoordsInRangeArray(int n) {
        List<HexCoords> neighbors = new ArrayList<>();

        for (int dr = -n; dr <= n; dr++){               // -N ≤ dr ≤ N
            int minQ = Math.max(-dr - n, -n);
            int maxQ = Math.min(-dr + n, n);

            for (int dq = minQ; dq <= maxQ; dq++){      // max(-N, -dr-N) ≤ dq ≤ min(N, -dr+N)
                neighbors.add(UtilCoordinates.addHexCoords(this, new HexCoords(dq, dr)));
            }
        }
        return neighbors.toArray(new HexCoords[neighbors.size()]);
    }

    public void initWithJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            this.q = jsonObject.getInt("q");
            this.r = jsonObject.getInt("r");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public String toJson() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put(Attributes.Q.toString(), this.q);
            jsonObj.put(Attributes.R.toString(), this.r);
        } catch (JSONException e){

        }
        return jsonObj.toString();
    }

    private void initWithJsonString(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            this.q = jsonObject.getInt(Attributes.Q.toString());
            this.r = jsonObject.getInt(Attributes.R.toString());

        } catch (JSONException e) {

        }
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
