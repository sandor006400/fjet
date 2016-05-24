package org.avv.fjet.core.board;

import org.avv.fjet.core.unit.Unit;
import org.avv.fjet.serialization.JsonSerializable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/03/2016
 */
public class Board {

    // region - Constants

    enum Attributes {

        CELLS_MAP("cellsMap"),
        UNITS_LIST("unitsList");

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

    private Map<ICoords, Cell> cellsMap;
    private List<Unit> unitsList;

    private List<Cell> selectedCells;  // The selected cells

    // endregion - Fields

    // region - Constructors

    public Board(){
        this.cellsMap = new HashMap<>();
        this.unitsList = new ArrayList<>();
        this.selectedCells = new ArrayList<>();
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

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces


    @Override
    public String toString() {
        return this.toJsonString();
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

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
            jsonObj.put(Attributes.CELLS_MAP.toString(), this.cellsMap);
            jsonObj.put(Attributes.UNITS_LIST.toString(), this.unitsList);
        } catch (JSONException e){

        }
        return jsonObj.toString();
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    interface IBoardState {

    }

    // endregion - Inner and Anonymous Classes

}
