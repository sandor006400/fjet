package org.avv.fjet.touch;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by Alexander Vorobiev on 31/05/16.
 */
public class FJetTouchListener implements View.OnTouchListener {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private int touchesCount = 0;

    private GestureDetector gestureListener;
    private ScaleGestureDetector scaleGestureDetector;

    // endregion - Fields

    // region - Constructors

    public FJetTouchListener(IFjetTouchListenerDelegate delegate, Context c){
        this.gestureListener = new GestureDetector(c, new FJetGestureListener(delegate));
        this.scaleGestureDetector = new ScaleGestureDetector(c, new FJetScaleGestureListener(delegate));
    }

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("------->", "onTouch, num of pointers: " + String.valueOf(this.touchesCount));
        this.gestureListener.onTouchEvent(event);
        this.scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    public interface IFjetTouchListenerDelegate {

        boolean onDown(MotionEvent e);

        void onShowPress(MotionEvent e);

        boolean onSingleTapUp(MotionEvent e);

        boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);

        void onLongPress(MotionEvent e);

        boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);

        boolean onScale(ScaleGestureDetector detector);

    }

    static private class FJetGestureListener implements GestureDetector.OnGestureListener {

        private IFjetTouchListenerDelegate delegate;

        public FJetGestureListener(IFjetTouchListenerDelegate delegate){
            this.delegate = delegate;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return this.delegate != null
                    && this.delegate.onDown(e);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            if (this.delegate != null){
                this.delegate.onShowPress(e);
            }
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return this.delegate != null
                    && this.delegate.onSingleTapUp(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return this.delegate != null
                    && this.delegate.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            if (this.delegate != null) {
                this.delegate.onLongPress(e);
            }
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return this.delegate != null
                    && this.delegate.onFling(e1, e2, velocityX, velocityY);
        }

    }

    static private class FJetScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        private IFjetTouchListenerDelegate delegate;

        public FJetScaleGestureListener(IFjetTouchListenerDelegate delegate){
            this.delegate = delegate;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return this.delegate != null
                    && this.delegate.onScale(detector);
        }
    }

    // endregion - Inner and Anonymous Classes

}
