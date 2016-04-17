package org.avv.fjet.core.board;

import org.avv.fjet.core.unit.Unit;

import java.util.HashMap;
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

    // endregion - Fields

    // region - Constructors

    public Board(){
        this.cellsMap = new HashMap<>();
        this.unitsMap = new HashMap<>();
    }

    // endregion - Constructors

    // region - Getters and Setters

    public Cell getCellWithCoords(ICoords coords){
        return this.cellsMap.get(coords);
    }

    public void setCellAndCoords(ICoords coords, Cell cell){
        this.cellsMap.put(coords, cell);
    }

    public Map getCells(){
        return this.cellsMap;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    interface IBoardState {

    }

    // endregion - Inner and Anonymous Classes

}
