package org.avv.fjet.core.unit;

import org.avv.fjet.core.board.Cell;

import java.util.UUID;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/02/2016
 */
public class Unit {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private String id;
    private Cell cell;

    // endregion - Fields

    // region - Constructors

    public Unit(){
        this.id = generateID();
    }

    public Unit(String id){
        this.id = id;
    }

    // endregion - Constructors

    // region - Getters and Setters

    public void setCell(Cell cell){
        this.cell = cell;
    }

    public Cell getCell(){
        return this.cell;
    }

    public String getId(){
        return this.id;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    private String generateID(){
        return UUID.randomUUID().toString();
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
