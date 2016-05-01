package org.avv.fjet.core.board;

import org.avv.fjet.core.unit.Unit;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/03/2016
 */
public class Cell {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private Unit unit;
    private ICoords coords;

    // endregion - Fields

    // region - Constructors

    public Cell(ICoords coords){
        this.coords = coords;
    }

    // endregion - Constructors

    // region - Getters and Setters

    public void setUnit(Unit unit){
        this.unit = unit;
    }

    public void removeUnit(){
        this.unit = null;
    }

    public Unit getUnit(){
        return this.unit;
    }

    public ICoords getCoords(){
        return this.coords;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
