package org.avv.fjet.core.board;

import android.content.Context;

import org.avv.fjet.core.action.Action;
import org.avv.fjet.core.unit.Unit;
import org.avv.fjet.serialization.JsonSerializable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
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
     * @param data
     * @param c
     */
    public Board(BoardData data, Context c){
        init();
        initWithData(data, c);
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

    /**
     * Returns the cells with coords
     * @param coords
     * @return
     */
    public List<Cell> getCellsWithCoords(ICoords [] coords){
        List<Cell> cells = new ArrayList<>();

        for (Map.Entry<ICoords, Cell> entry : this.cellsMap.entrySet()){
            boolean found = false;

            for (int i = 0; i < coords.length && !found; i++) {
                if (entry.getKey().equals(coords[i])) {
                    cells.add(entry.getValue());
                    found = true;
                }
            }
        }
        return cells;
    }

    /**
     * Returns the cells with ids
     * @param ids
     * @return
     */
    public List<Cell> getCellsWithIds(String [] ids){
        List<Cell> cells = new ArrayList<>();

        for (Map.Entry<ICoords, Cell> entry : this.cellsMap.entrySet()){
            boolean found = false;

            for (int i = 0; i < ids.length && !found; i++) {
                if (entry.getValue().getId().equals(ids[i])) {
                    cells.add(entry.getValue());
                    found = true;
                }
            }
        }
        return cells;
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

    public BoardType getType(){
        return this.type;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public String toString() {
        return "Board(" + this.type + ")";
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

    public void selectCells(String [] cellIds){
        List<Cell> selectedCells = getCellsWithIds(cellIds);

        if (selectedCells != null) {
            this.selectedCells.addAll(selectedCells);
        }
    }

    public void deselectCell(){
        this.selectedCells.clear();
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

    public BoardData getBoardData(){
        BoardData data = new BoardData();
        data.type = this.type;

        List<Cell.CellData> cellsList = new ArrayList<>();
        for (Cell c: this.cellsMap.values()){
            cellsList.add(c.getCellData());
        }
        data.cells = Arrays.copyOf(cellsList.toArray(), cellsList.size(), Cell.CellData[].class);
        List<Unit.UnitData> unitsList = new ArrayList<>();
        for (Unit u: this.unitsMap.values()){
            unitsList.add(u.getUnitData());
        }
        data.units = Arrays.copyOf(unitsList.toArray(), unitsList.size(), Unit.UnitData[].class);
        return data;
    }

    private void initWithData(BoardData data, Context c){
        this.type = data.type;

        for (int i = 0; i < data.cells.length; i++){
            Cell cell = new Cell(data.cells[i], c);
            this.cellsMap.put(cell.getCoords(), cell);
        }

        for (int i = 0; i < data.units.length; i++){
            Unit unit = new Unit(data.units[i]);
            this.unitsMap.put(unit.getId(), unit);
        }
        assignUnitsToCells();
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    static public class BoardData {

        private BoardType type;
        private Cell.CellData[] cells;
        private Unit.UnitData[] units;

        public BoardData(){

        }

        public BoardData(String json){
            initWithJson(json);
        }

        public String toJson(){
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("type", this.type);

                JSONArray jsonArrayCells = new JSONArray();
                for (int i = 0; i < this.cells.length; i++) {
                    jsonArrayCells.put(this.cells[i].toJson());
                }
                jsonObject.put("cells", jsonArrayCells);

                JSONArray jsonArrayUnits = new JSONArray();
                for (int i = 0; i < this.units.length; i++) {
                    jsonArrayUnits.put(this.units[i].toJson());
                }
                jsonObject.put("units", jsonArrayUnits);

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
                    this.type = BoardType.valueOf(jsonObject.getString("type"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray cellsArray = jsonObject.getJSONArray("cells");
                    this.cells = new Cell.CellData[cellsArray.length()];

                    for (int i = 0; i < cellsArray.length(); i++) {
                        Cell.CellData cellData = new Cell.CellData(cellsArray.getString(i));
                        this.cells[i] = cellData;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray unitsArray = jsonObject.getJSONArray("units");
                    this.units = new Unit.UnitData[unitsArray.length()];

                    for (int i = 0; i < unitsArray.length(); i++) {
                        Unit.UnitData unitData = new Unit.UnitData(unitsArray.getString(i));
                        this.units[i] = unitData;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // endregion - Inner and Anonymous Classes

}
