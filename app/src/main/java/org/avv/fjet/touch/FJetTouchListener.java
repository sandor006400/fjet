package org.avv.fjet.touch;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Alexander Vorobiev on 31/05/16.
 */
public class FJetTouchListener implements View.OnTouchListener {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private IFjetTouchListenerDelegate delegate;

    // endregion - Fields

    // region - Constructors

    public FJetTouchListener(IFjetTouchListenerDelegate delegate){
        this.delegate = delegate;
    }

    // endregion - Constructors

    // region - Getters and Setters

    public void setDelegate(IFjetTouchListenerDelegate delegate){
        this.delegate = delegate;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (this.delegate != null) {
            return this.delegate.processTouchEvent(event);

        } else {
            return false;
        }
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    public interface IFjetTouchListenerDelegate{

        boolean processTouchEvent(MotionEvent event);
    }

    // endregion - Inner and Anonymous Classes

}
