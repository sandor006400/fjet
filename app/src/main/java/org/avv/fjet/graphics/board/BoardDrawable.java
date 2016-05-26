package org.avv.fjet.graphics.board;

import android.graphics.Canvas;

import org.avv.fjet.core.geometry.FJetRect;
import org.avv.fjet.graphics.unit.UnitDrawable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander Vorobiev on 22/05/16.
 */
public class BoardDrawable {

    // region - Constants

    private static final float DEFAULT_SCALE = 1.0f;
    private static final float MIN_SCALE = 0.5f;
    private static final float MAX_SCALE = 2.0f;

    // endregion - Constants

    // region - Fields

    private FJetRect boardBounds;   // Board bounds in screen coordinates
    private FJetRect viewBounds;    // View bounds in screen coordinates
    private float scale;            // BoardDrawable current scale

    private Map<String, CellDrawable> cellDrawablesMap;
    private Map<String, UnitDrawable> unitDrawablesMap;

    // endregion - Fields

    // region - Constructors

    public BoardDrawable(){
        init();

    }

    // endregion - Constructors

    // region - Getters and Setters

    public BoardDrawable setBoardBounds(FJetRect bounds){
        this.boardBounds = bounds;
        return this;
    }

    public BoardDrawable setViewBounds(FJetRect bounds){
        this.viewBounds = bounds;
        return this;
    }

    public void setScale(float scale){
        if (scale >= MIN_SCALE && scale <= MAX_SCALE) {
            this.scale = scale;
        }
    }

    public void addCellDrawable(String cellId, CellDrawable cellDrawable){
        this.cellDrawablesMap.put(cellId, cellDrawable);
    }

    public void addUnitDrawable(String unitId, UnitDrawable unitDrawable){
        this.unitDrawablesMap.put(unitId, unitDrawable);
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    /**
     * Draw CellDrawables and UnitDrawables using canvas
     * @param c
     */
    public void draw(Canvas c) {

        // Drawing cell drawables
        for (CellDrawable cD : this.cellDrawablesMap.values()){
            cD.draw(this.boardBounds, this.viewBounds, this.scale, c);
        }

        // Drawing unit drawables
        for (UnitDrawable uD : this.unitDrawablesMap.values()){
            uD.draw(this.boardBounds, this.viewBounds, this.scale, c);
        }

    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    private void init(){
        this.scale = DEFAULT_SCALE;
        this.viewBounds = new FJetRect(0, 0, 0, 0);
        this.boardBounds = new FJetRect(0, 0, 0, 0);
        this.cellDrawablesMap = new HashMap<>();
        this.unitDrawablesMap = new HashMap<>();
    }

    public void setOffset(int x, int y){
        this.boardBounds.offsetTo(x, y);
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}