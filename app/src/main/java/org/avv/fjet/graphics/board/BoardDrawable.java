package org.avv.fjet.graphics.board;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.Log;

import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.ICoords;
import org.avv.fjet.core.board.SquareCoords;
import org.avv.fjet.core.board.util.UtilCoordinates;
import org.avv.fjet.core.geometry.FJetPoint;
import org.avv.fjet.core.geometry.FJetRect;
import org.avv.fjet.core.geometry.util.UtilGeometry;
import org.avv.fjet.graphics.unit.UnitDrawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexander Vorobiev on 22/05/16.
 */
public class BoardDrawable {

    // region - Constants

    public enum Alignment {
        TOP_LEFT, TOP_CENTER, TOP_RIGHT,
        CENTER_LEFT, CENTER, CENTER_RIGHT,
        BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT
    }

    private static final float DEFAULT_SCALE = 1.0f;
    private static final float MIN_SCALE = 0.1f;
    private static final float MAX_SCALE = 2.0f;

    // endregion - Constants

    // region - Fields

    private float minScale = MIN_SCALE;
    private float maxScale = MAX_SCALE;

    private FJetRect boardBounds;   // Board bounds in screen coordinates
    private float scale;            // BoardDrawable current scale
    private int edgeSize;

    private Map<String, CellDrawable> cellDrawablesMap;
    private Map<String, UnitDrawable> unitDrawablesMap;

    // endregion - Fields

    // region - Constructors

    public BoardDrawable(int edgeSize){
        init();

        this.edgeSize = edgeSize;
    }

    // endregion - Constructors

    // region - Getters and Setters

    public void setMinScale(float scale){
        if (scale >= MIN_SCALE && scale <= this.maxScale) {
            this.minScale = scale;
        }
    }

    public void setMaxScale(float scale){
        if (scale >= this.minScale && scale <= MAX_SCALE) {
            this.maxScale = scale;
        }
    }

    public BoardDrawable setBoardBounds(FJetRect bounds){
        this.boardBounds = bounds;
        return this;
    }

    public void setScale(float scale){
        if (scale >= this.minScale && scale <= this.maxScale) {
            this.scale = scale;
        }
    }

    public int getEdgeSize(){
        return this.edgeSize;
    }

    public float getScale(){
        return this.scale;
    }

    public void addCellDrawable(String cellId, CellDrawable cellDrawable){
        this.cellDrawablesMap.put(cellId, cellDrawable);
    }

    public void addUnitDrawable(String unitId, UnitDrawable unitDrawable){
        this.unitDrawablesMap.put(unitId, unitDrawable);
    }

    public FJetPoint getOffset(){
        return new FJetPoint(this.boardBounds.getX(), this.boardBounds.getY());
    }

    public UnitDrawable getUnitDrawable(String unitId){
        return this.unitDrawablesMap.get(unitId);
    }

    /**
     * Aligns BoardDrawable bounds
     * @param alignment
     * @param viewWidth
     * @param viewHeight
     */
    public void setAlignment(Alignment alignment, int viewWidth, int viewHeight){

        if (Alignment.TOP_LEFT.ordinal() == alignment.ordinal()) {
            this.boardBounds.offsetTo(0, 0);

        } else if (Alignment.TOP_CENTER.ordinal() == alignment.ordinal()) {
            this.boardBounds.offsetTo((viewWidth / 2) - (this.boardBounds.getWidth() / 2), 0);

        } else if (Alignment.TOP_RIGHT.ordinal() == alignment.ordinal()) {
            this.boardBounds.offsetTo(viewWidth - this.boardBounds.getWidth(), 0);

        } else if (Alignment.CENTER_LEFT.ordinal() == alignment.ordinal()) {
            this.boardBounds.offsetTo(0, (viewHeight / 2) - (this.boardBounds.getHeight() / 2));

        } else if (Alignment.CENTER.ordinal() == alignment.ordinal()) {
            this.boardBounds.offsetTo(
                    (viewWidth / 2) - (this.boardBounds.getWidth() / 2),
                    (viewHeight / 2) - (this.boardBounds.getHeight() / 2));

        } else if (Alignment.CENTER_RIGHT.ordinal() == alignment.ordinal()) {
            this.boardBounds.offsetTo(
                    viewWidth - this.boardBounds.getWidth(),
                    (viewHeight / 2) - (this.boardBounds.getHeight() / 2));

        } else if (Alignment.BOTTOM_LEFT.ordinal() == alignment.ordinal()) {
            this.boardBounds.offsetTo(0, viewHeight - this.boardBounds.getHeight());

        } else if (Alignment.BOTTOM_CENTER.ordinal() == alignment.ordinal()) {
            this.boardBounds.offsetTo(
                    (viewWidth / 2) - (this.boardBounds.getWidth() / 2),
                    viewHeight - this.boardBounds.getHeight());

        } else if (Alignment.BOTTOM_RIGHT.ordinal() == alignment.ordinal()) {
            this.boardBounds.offsetTo(
                    viewWidth - this.boardBounds.getWidth(),
                    viewHeight - this.boardBounds.getHeight());
        }

    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    /**
     * Draw CellDrawables and UnitDrawables using canvas
     * @param c
     */
    public void draw(Canvas c) {
        FJetRect scaledBounds = getCurrentBounds();

        // Drawing cell drawables
        for (CellDrawable cD : this.cellDrawablesMap.values()){
            cD.draw(scaledBounds, this.scale, c);
        }

        // Drawing unit drawables
        for (UnitDrawable uD : this.unitDrawablesMap.values()){
            uD.draw(scaledBounds, this.scale, c);
        }
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public float getCurrentEdgeSize(){
        return this.scale * this.edgeSize;
    }

    public FJetRect getCurrentBounds(){
        return UtilGeometry.getFJetRectScalingAroundPoint(this.boardBounds, getCenter(), this.scale);
    }

    private FJetPoint getCenter(){
        return new FJetPoint(
                this.boardBounds.getX() + (this.boardBounds.getWidth() / 2),
                this.boardBounds.getY() + (this.boardBounds.getHeight() / 2));
    }

    public FJetPoint getPositionWithCoords(ICoords coords){
        if (coords instanceof HexCoords){
            return UtilCoordinates.hexCoordsToPixel(this.edgeSize, (HexCoords)coords);

        } else if (coords instanceof SquareCoords){
            return UtilCoordinates.squareCoordsToPixel(edgeSize, (SquareCoords) coords);
        }
        return null;
    }

    private void init(){
        this.scale = DEFAULT_SCALE;
        this.boardBounds = new FJetRect(0, 0, 0, 0);
        this.cellDrawablesMap = new HashMap<>();
        this.unitDrawablesMap = new HashMap<>();
    }

    public void setOffsetTo(int x, int y){
        this.boardBounds.offsetTo(x, y);
    }

    public void setOffsetTo(FJetPoint offset){
        this.boardBounds.offsetTo(offset);
    }

    public void setOffset(int x, int y){
        this.boardBounds.offsetBy(x, y);
    }

    public void setOffset(FJetPoint offset){
        this.boardBounds.offsetBy(offset);
    }

    public void removeUnitDrawables(){
        this.unitDrawablesMap.clear();
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
