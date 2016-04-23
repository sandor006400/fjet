package org.avv.fjet;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import org.avv.fjet.core.board.Point;

public class MainActivity extends Activity {

    private FJetSurfaceView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.gameView = (FJetSurfaceView) findViewById(R.id.surfaceView);

        Log.d("FUUUU", "arrancando...");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("FUUUU", "comienza lo bueno...");
        daleChicha();
    }

    @Override
    protected void onStop() {
        super.onStop();

        this.gameView.stop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //return super.dispatchTouchEvent(ev);

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            this.gameView.processTouchEvent(new Point((int) ev.getX(), (int) ev.getY()));
        }
        return true;
    }

    private void daleChicha(){

    }

}
