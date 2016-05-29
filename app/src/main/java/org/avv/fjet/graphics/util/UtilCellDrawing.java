package org.avv.fjet.graphics.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.Log;

import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.geometry.FJetPoint;
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
        FJetPoint p = UtilCoordinates.hexCoordsToPixel(currentEdgeSize, coords);
        int halfWidth = Math.round(UtilCoordinates.SQRT_OF_3 * currentEdgeSize / 2);

        ShapeDrawable d = getHexDrawable(edgeSize, scale);
        d.setBounds(
                p.getX() - halfWidth,
                p.getY() - (int)currentEdgeSize,
                p.getX() + halfWidth,
                p.getY() + (int)currentEdgeSize);
        d.getPaint().setColor(color);
        d.getPaint().setStrokeWidth(strokeWidth * scale);
        d.getPaint().setStyle(Paint.Style.STROKE);
        d.draw(c);
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
        FJetPoint p = UtilCoordinates.squareCoordsToPixel(currentEdgeSize, coords);

        ShapeDrawable d = getSquareDrawable(edgeSize, scale);
        d.setBounds(
                p.getX() - Math.round(currentEdgeSize / 2),
                p.getY() - Math.round(currentEdgeSize / 2),
                p.getX() + Math.round(currentEdgeSize / 2),
                p.getY() + Math.round(currentEdgeSize / 2));
        d.getPaint().setColor(color);
        d.getPaint().setStrokeWidth(strokeWidth * scale);
        d.getPaint().setStyle(Paint.Style.STROKE);
        d.draw(c);
    }

    public static ShapeDrawable getHexDrawable(float edgeSize, float scale){
        float currentEdgeSize = edgeSize * scale;
        Path path = createHexCellPath(edgeSize, scale);
        float width = UtilCoordinates.SQRT_OF_3 * currentEdgeSize;
        PathShape shape = new PathShape(path, width, currentEdgeSize * 2);
        return new ShapeDrawable(shape);
    }

    public static Path createHexCellPath(float edgeSize, float scale){
        float currentEdgeSize = edgeSize * scale;
        Path path = new Path();
        float width = UtilCoordinates.SQRT_OF_3 * currentEdgeSize;

        path.moveTo(width / 2, 0);
        path.lineTo(width, currentEdgeSize * 0.5f);
        path.lineTo(width, currentEdgeSize * 1.5f);
        path.lineTo(width / 2, currentEdgeSize * 2.0f);
        path.lineTo(0, currentEdgeSize * 1.5f);
        path.lineTo(0, currentEdgeSize * 0.5f);
        path.close();

        return path;
    }

    public static ShapeDrawable getSquareDrawable(float edgeSize, float scale){
        float currentEdgeSize = edgeSize * scale;
        Path path = createSquareCellPath(edgeSize, scale);

        PathShape shape = new PathShape(path, currentEdgeSize, currentEdgeSize);
        return new ShapeDrawable(shape);
    }

    public static Path createSquareCellPath(float edgeSize, float scale){
        float currentEdgeSize = edgeSize * scale;
        Path path = new Path();

        path.moveTo(0, 0);
        path.lineTo(currentEdgeSize, 0);
        path.lineTo(currentEdgeSize, currentEdgeSize);
        path.lineTo(0, currentEdgeSize);
        path.close();

        return path;
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
