package org.avv.fjet.core.geometry.util;

import org.avv.fjet.core.geometry.FJetPoint;
import org.avv.fjet.core.geometry.FJetRect;

/**
 * Created by Alexander Vorobiev on 9/07/16.
 */
public class UtilGeometry {

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
     * Scales rect to scale
     * @param rect
     * @param scale
     */
    public static void scaleFJetRect(FJetRect rect, float scale){
        rect.setAttributes(
                Math.round(rect.getX() * scale),
                Math.round(rect.getY() * scale),
                Math.round(rect.getWidth() * scale),
                Math.round(rect.getHeight() * scale));
    }

    /**
     * Scale rect around point p
     * @param rect
     * @param p
     * @param scale
     */
    public static void scaleFJetRectAroundPoint(FJetRect rect, FJetPoint p, float scale){
        rect.offsetBy(-p.getX(), -p.getY());
        scaleFJetRect(rect, scale);
        rect.offsetBy(p.getX(), p.getY());
    }

    /**
     * Creates rect aplying scale to rect around point p
     * @param rect
     * @param p
     * @param scale
     */
    public static FJetRect getFJetRectScalingAroundPoint(FJetRect rect, FJetPoint p, float scale){
        FJetRect newRect = new FJetRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        newRect.offsetBy(-p.getX(), -p.getY());
        scaleFJetRect(newRect, scale);
        newRect.offsetBy(p.getX(), p.getY());
        return newRect;
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
