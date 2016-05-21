package org.avv.fjet.graphics.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.Point;
import org.avv.fjet.core.board.SquareCoords;
import org.avv.fjet.core.board.util.UtilCoordinates;

/**
 * Created by Alexander Vorobiev on 21/05/16.
 */
public class UtilCellDrawing {

    // region - Constants

    // endregion - Constants

    // region - Fields

    // endregion - Fields

    // region - Constructors

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    /**
     * Draws the edge of hexagonal cell with coords in canvas
     * @param c
     * @param edgeSize
     * @param scale
     * @param coords
     * @param color
     * @param strokeWidth
     */
    public static void drawHexCellEdge(Canvas c, float edgeSize, float scale, HexCoords coords, int color, float strokeWidth){
        float currentEdgeSize = edgeSize * scale;
        Paint paint = new Paint();
        paint.setStrokeWidth(strokeWidth * scale);
        paint.setColor(color);
        Point p = UtilCoordinates.hexCoordsToPixel(currentEdgeSize, coords);

        c.drawLine(p.getX(), p.getY() - currentEdgeSize,
                p.getX() + (UtilCoordinates.SQRT_OF_3 * currentEdgeSize / 2), p.getY() - (currentEdgeSize * 0.5f), paint);
        c.drawLine(p.getX() + (UtilCoordinates.SQRT_OF_3 * currentEdgeSize / 2), p.getY() - (currentEdgeSize * 0.5f),
                p.getX() + (UtilCoordinates.SQRT_OF_3 * currentEdgeSize / 2), p.getY() + (currentEdgeSize * 0.5f), paint);
        c.drawLine(p.getX() + (UtilCoordinates.SQRT_OF_3 * currentEdgeSize / 2), p.getY() + (currentEdgeSize * 0.5f),
                p.getX(), p.getY() + currentEdgeSize, paint);
        c.drawLine(p.getX(), p.getY() + currentEdgeSize,
                p.getX() - (UtilCoordinates.SQRT_OF_3 * currentEdgeSize / 2), p.getY() + (currentEdgeSize * 0.5f), paint);
        c.drawLine(p.getX() - (UtilCoordinates.SQRT_OF_3 * currentEdgeSize / 2), p.getY() + (currentEdgeSize * 0.5f),
                p.getX() - (UtilCoordinates.SQRT_OF_3 * currentEdgeSize / 2), p.getY() - (currentEdgeSize * 0.5f), paint);
        c.drawLine(p.getX() - (UtilCoordinates.SQRT_OF_3 * currentEdgeSize / 2), p.getY() - (currentEdgeSize * 0.5f),
                p.getX(), p.getY() - currentEdgeSize, paint);
    }

    /**
     * Draws the edge of square cell with coords in canvas
     * @param c
     * @param edgeSize
     * @param scale
     * @param coords
     * @param color
     * @param strokeWidth
     */
    public static void drawSquareCellEdge(Canvas c, float edgeSize, float scale, SquareCoords coords, int color, float strokeWidth){
        float currentEdgeSize = edgeSize * scale;
        Paint paint = new Paint();
        paint.setStrokeWidth(strokeWidth * scale);
        paint.setColor(color);
        Point p = UtilCoordinates.squareCoordsToPixel(currentEdgeSize, coords);

        float x1 = p.getX() - currentEdgeSize / 2;
        float y1 = p.getY() - currentEdgeSize / 2;
        float x2 = p.getX() + currentEdgeSize / 2;
        float y2 = p.getY() + currentEdgeSize / 2;

        c.drawRect(x1, y1, x2, y2, paint);
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
