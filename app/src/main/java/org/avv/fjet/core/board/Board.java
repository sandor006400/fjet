package org.avv.fjet.core.board;

import android.content.Context;

import org.avv.fjet.core.unit.Unit;
import org.avv.fjet.serialization.JsonSerializable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/03/2016
 */
public class Board {

    // region - Constants

    enum Attributes {

        TYPE("type"),
        CELLS("cells"),
        UNITS("units");

        private final String name;

        Attributes(final String name){
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum BoardType {
        HEX_CELLS,
        SQUARE_CELLS
    }

    // endregion - Constants

    // region - Fields

    private BoardType type;

    private Map<ICoords, Cell> cellsMap;
    private Map<String, Unit> unitsMap;

    private List<Cell> selectedCells;  // The selected cells

    // endregion - Fields

    // region - Constructors

    public Board(BoardType type){
        init();

        this.type = type;
    }

    /**
     * Creates a Board using json with Board data
     * @param jsonString
     */
    public Board(String jsonString, Context c){
        initWithJsonString(jsonString, c);
    }

    // endregion - Constructors

    // region - Getters and Setters

    public Cell getCellWithCoords(ICoords coords){
        for (Map.Entry<ICoords, Cell> entry : this.cellsMap.entrySet()){
            if (entry.getKey().equals(coords)){
                return entry.getValue();
            }
        }
        return null;
    }

    public void setCellAndCoords(ICoords coords, Cell cell){
        this.cellsMap.put(coords, cell);
    }

    public Map getCells(){
        return this.cellsMap;
    }

    public List<Cell> getSelectedCells(){
        return this.selectedCells;
    }

    public Map getUnits(){
        return this.unitsMap;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces


    @Override
    public String toString() {
        return this.toJsonString();
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    private void init(){
        this.cellsMap = new HashMap<>();
        this.unitsMap = new HashMap<>();
        this.selectedCells = new ArrayList<>();
    }

    public boolean anyCellIsSelected(){
        return !this.selectedCells.isEmpty();
    }

    public void selectCells(ICoords [] coords){
        Cell selectedCell;

        for (ICoords c : coords){
            selectedCell = getCellWithCoords(c);

            if (selectedCell != null) {
                this.selectedCells.add(selectedCell);
            }
        }
    }

    public void deselectCell(){
        this.selectedCells.clear();
    }

    public String toJsonString() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put(Attributes.TYPE.toString(), this.type);

            String [] cells = new String[this.cellsMap.values().size()];
            int i = 0;

            // Serilizing cells
            for (Cell c : this.cellsMap.values()){
                cells[i++] = c.toString();
            }
            jsonObj.put(
                    Attributes.CELLS.toString(),
                    new JSONArray(cells));

            // Srializing units
            String [] units = new String[this.unitsMap.values().size()];
            i = 0;

            for (Unit u : this.unitsMap.values()){
                units[i++] = u.toString();
            }
            jsonObj.put(
                    Attributes.UNITS.toString(),
                    new JSONArray(units));

        } catch (JSONException e){

        }
        return jsonObj.toString();
    }

    private void initWithJsonString(String jsonString, Context c){
        init();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);

        } catch (JSONException e) {

        }

        if (jsonObject != null) {

            try {
                this.type = BoardType.valueOf(
                        jsonObject.getString(Attributes.TYPE.toString()));

            } catch (JSONException e) {

            }

            if (this.type != null) {
                try {
                    JSONArray jsonCellsArray = jsonObject.getJSONArray(Attributes.CELLS.toString());;

                    for (int i = 0; i < jsonCellsArray.length(); i++) {
                        String jsonCell = jsonCellsArray.getString(i);
                        Cell cell = new Cell(jsonCell, c, this.type);
                        this.cellsMap.put(
                                cell.getCoords(),
                                cell);
                    }

                } catch (JSONException e) {

                }

                try {
                    JSONObject jsonUnitsMap = jsonObject.getJSONObject(Attributes.UNITS.toString());
                    Iterator<String> i = jsonUnitsMap.keys();

                    while (i.hasNext()) {
                        String jsonUnitId = i.next();
                        String jsonUnit = jsonUnitsMap.getString(jsonUnitId);
                        this.unitsMap.put(
                                jsonUnitId,
                                new Unit(jsonUnit));
                    }

                } catch (JSONException e) {

                }

                // Associates cells and units
                assignUnitsToCells();
            }
        }
    }

    /**
     * Used while json deserialization takes place to assign Units with Cells
     */
    private void assignUnitsToCells(){
        for (Cell c : this.cellsMap.values()){
            String unitId = c.getUnitId();

            if (unitId != null && !unitId.equals("")){
                c.setUnit(this.unitsMap.get(unitId));
            }
        }
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
