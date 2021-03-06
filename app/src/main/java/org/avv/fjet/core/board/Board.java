package org.avv.fjet.core.board;

import org.avv.fjet.core.GameEntity;
import org.avv.fjet.core.board.util.UtilCoordinates;
import org.avv.fjet.core.unit.Unit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    private Cell [][] cellsMatrix;
    private Map<String, Unit> unitsMap;

    private List<Cell> selectedCells;  // The selected cells

    private int width = 0;
    private int height = 0;

    // endregion - Fields

    // region - Constructors

    public Board(BoardType type, int width, int height){
        init(height, width);

        this.width = width;
        this.height = height;
        this.type = type;
    }

    /**
     * Creates a Board using json with Board data
     * @param data
     */
    public Board(BoardData data){
        init(data.height, data.width);
        initWithData(data);
    }

    // endregion - Constructors

    // region - Getters and Setters

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public BoardType getBoardType(){
        return this.type;
    }

    /*public Cell getCellWithCoords(ICoords coords){

        if (this.cellsMap != null){
            return this.cellsMap.get(coords.toShortString());
        }
        return null;
    }*/

    public Cell getCellWithCoords(ICoords coords){
        if (coords instanceof HexCoords) {
            int y = ((HexCoords) coords).getR();
            int x = ((HexCoords) coords).getQ() + UtilCoordinates.getCoordLeftPaddingInMatrix(y);

            if (y >= 0 && x >= 0 && y < this.cellsMatrix.length && x < this.cellsMatrix[0].length){
                return this.cellsMatrix[y][x];
            }

        } else if (coords instanceof SquareCoords){
            int y = ((SquareCoords) coords).getY();
            int x = ((SquareCoords) coords).getX();

            if (y >= 0 && x >= 0 && y < this.cellsMatrix.length && x < this.cellsMatrix[0].length){
                return this.cellsMatrix[y][x];
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

        for (int i = 0; i < coords.length; i++) {
            Cell c = getCellWithCoords(coords[i]);

            if (c != null) {
                cells.add(c);
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

        for (Cell cell: getCells()){
            boolean found = false;

            for (int i = 0; i < ids.length && !found; i++) {
                if (cell.getId().equals(ids[i])) {
                    cells.add(cell);
                    found = true;
                }
            }
        }
        return cells;
    }

    public void setCellAndCoords(ICoords coords, Cell cell){
        if (coords instanceof HexCoords) {
            int row = ((HexCoords) coords).getR();
            int col = ((HexCoords) coords).getQ() + UtilCoordinates.getCoordLeftPaddingInMatrix(row);

            if (row >= 0 && col >= 0 && row < this.cellsMatrix.length && col < this.cellsMatrix[0].length){
                this.cellsMatrix[row][col] = cell;
            }

        } else if (coords instanceof SquareCoords){
            int row = ((SquareCoords) coords).getY();
            int col = ((SquareCoords) coords).getX();

            if (row >= 0 && col >= 0 && row < this.cellsMatrix.length && col < this.cellsMatrix[0].length){
                this.cellsMatrix[row][col] = cell;
            }
        }
    }

    public List<Cell> getCells(){
        List<Cell> cells = new ArrayList<>();

        for (int j = 0;  j < this.cellsMatrix.length; j++){
            for (int i = 0; i < this.cellsMatrix[0].length; i++){
                if (this.cellsMatrix[j][i] != null){
                    cells.add(this.cellsMatrix[j][i]);
                }
            }
        }
        return cells;
    }

    public List<Cell> getSelectedCells(){
        return this.selectedCells;
    }

    public Map<String, Unit> getUnits(){
        return this.unitsMap;
    }

    public BoardType getType(){
        return this.type;
    }

    public List<Unit> getSelectedUnits(){
        List<Unit> units = new ArrayList<>();

        for (Cell cell : this.selectedCells){
            Unit u = cell.getUnit();

            if (u != null){
                units.add(u);
            }
        }
        return units;
    }

    /**
     * Adds unit to units map and links unit with associated Cell.
     * @param unit
     * @param cell
     */
    public void addUnit(Unit unit, Cell cell){
        if (getCellWithCoords(cell.getCoords()) != null) {
            unit.setCell(cell);
            cell.setUnit(unit);
            this.unitsMap.put(unit.getId(), unit);
        }
    }

    /**
     * Removes unit from units map and units reference from associated cell.
     * @param unit
     */
    public void removeUnit(Unit unit){

        if (this.unitsMap.containsKey(unit.getId())) {
            unit.getCell().setUnit(null);
            this.unitsMap.remove(unit.getId());
        }
    }

    /**
     * Removes all units from units map and units reference from associated cell.
     */
    public void removeAllUnits(){

        for (Unit u : this.unitsMap.values()) {
            u.getCell().setUnit(null);
        }
        this.unitsMap.clear();
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public String toString() {
        return "Board(" + this.type + ")";
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    private void init(int height, int width){
        //this.cellsMap = new HashMap<>();
        this.unitsMap = new HashMap<>();
        this.selectedCells = new ArrayList<>();
        this.cellsMatrix = new Cell[height][width];
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

    public void selectCells(List<Cell> cells){
        Cell selectedCell;
        List<String> ids = new ArrayList<>();

        for (Cell cell : cells) {
            ids.add(cell.getId());
        }
        selectCells(Arrays.copyOf(ids.toArray(), ids.size(), String[].class));
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
        for (Cell c : this.getCells()){
            String unitId = c.getUnitId();

            if (unitId != null && !unitId.equals("")){
                Unit unit = this.unitsMap.get(unitId);
                c.setUnit(unit);
                unit.setCell(c);
            }
        }
    }

    public BoardData getBoardData(){
        BoardData data = new BoardData();
        data.type = this.type;

        List<Cell> cellsList = new ArrayList<>();
        for (Cell c: getCells()){
            cellsList.add(c);
        }
        data.cells = Arrays.copyOf(cellsList.toArray(), cellsList.size(), Cell[].class);
        List<Unit> unitsList = new ArrayList<>();
        for (Unit u: this.unitsMap.values()){
            unitsList.add(u);
        }
        data.units = Arrays.copyOf(unitsList.toArray(), unitsList.size(), Unit[].class);
        data.width = this.width;
        data.height = this.height;
        return data;
    }

    private void initWithData(BoardData data){
        this.type = data.type;

        for (int i = 0; i < data.cells.length; i++){
            setCellAndCoords(data.cells[i].getCoords(), data.cells[i]);
        }

        for (int i = 0; i < data.units.length; i++){
            this.unitsMap.put(data.units[i].getId(), data.units[i]);
        }
        this.width = data.width;
        this.height = data.height;
        assignUnitsToCells();
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    /**
     * Memento pattern implementation for Board class
     */
    static public class BoardData extends GameEntity {

        private BoardType type;
        private Cell[] cells;
        private Unit[] units;
        private int width;
        private int height;

        public BoardData(){
            super();
        }

        public BoardData(JSONObject json){
            super();

            initWithJson(json);
        }

        @Override
        protected JSONObject initJSONObject() {
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
                jsonObject.put("width", this.width);
                jsonObject.put("height", this.height);

            } catch (JSONException e) {

            }
            return jsonObject;
        }

        @Override
        public void initWithJson(JSONObject json) {
            super.initWithJson(json);

            if (json != null) {
                try {
                    this.type = BoardType.valueOf(json.getString("type"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray cellsArray = json.getJSONArray("cells");
                    this.cells = new Cell[cellsArray.length()];

                    for (int i = 0; i < cellsArray.length(); i++) {
                        Cell cellData = new Cell(new JSONObject(cellsArray.getString(i)));
                        this.cells[i] = cellData;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray unitsArray = json.getJSONArray("units");
                    this.units = new Unit[unitsArray.length()];

                    for (int i = 0; i < unitsArray.length(); i++) {
                        Unit unitData = new Unit(new JSONObject(unitsArray.getString(i)));
                        this.units[i] = unitData;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    this.width = json.getInt("width");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    this.height = json.getInt("height");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // endregion - Inner and Anonymous Classes

}
