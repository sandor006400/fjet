package org.avv.fjet.graphics;

import android.content.Context;
import android.util.Log;
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

    private IGameViewObserver observer;

    // endregion - Fields

    // region - Constructors

    public GameView(Context context){
        super(context);

        init();
    }

    // endregion - Constructors

    // region - Getters and Setters

    public void setObserver(IGameViewObserver observer){
        this.observer = observer;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Log.d("GameView", "surfaceCreated");
        if (this.observer != null){
            this.observer.onSurfaceCreated();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        Log.d("GameView", "surfaceDestroyed");
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public void init(){
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    public interface IGameViewObserver {

        void onSurfaceCreated();

    }

    // endregion - Inner and Anonymous Classes

}
