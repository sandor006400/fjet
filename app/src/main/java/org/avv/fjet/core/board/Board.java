package org.avv.fjet.core.board;

import org.avv.fjet.core.unit.Unit;

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

    // endregion - Constants

    // region - Fields

    private Map<ICoords, Cell> cellsMap;
    private Map<ICoords, Unit> unitsMap;

    private List<Cell> selectedCells;  // The selected cells

    // endregion - Fields

    // region - Constructors

    public Board(){
        this.cellsMap = new HashMap<>();
        this.unitsMap = new HashMap<>();
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

    // endregion - Methods

    // region - Inner and Anonymous Classes

    interface IBoardState {

    }

    // endregion - Inner and Anonymous Classes

}
