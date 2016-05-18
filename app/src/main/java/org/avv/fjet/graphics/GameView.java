package org.avv.fjet.graphics;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Alexander Vorobiev on 11/05/16.
 */
public class GameView extends SurfaceView
        implements SurfaceHolder.Callback {

    // region - Constants

    // endregion - Constants

    // region - Fields

    // endregion - Fields

    // region - Constructors

    public GameView(Context context, AttributeSet attrs){
        super(context, attrs);

        init();
    }

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public void init(){
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
