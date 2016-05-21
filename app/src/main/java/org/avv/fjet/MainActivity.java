package org.avv.fjet;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.avv.fjet.core.action.Action;
import org.avv.fjet.core.action.ScaleViewAction;
import org.avv.fjet.core.board.BoardFactory;
import org.avv.fjet.core.board.Point;

public class MainActivity extends Activity {

    private FJetSurfaceView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.gameView = (FJetSurfaceView) findViewById(R.id.surfaceView);

        Log.d("FUUUU", "arrancando...");

        Button b70 = (Button)findViewById(R.id.button70);
        b70.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("FUUUU", "boton Pinchado...");
                ScaleViewAction a = new ScaleViewAction(Action.Type.INFORMATIVE);
                a.setScale(0.7f);
                gameView.processAction(a);
            }
        });

        Button b100 = (Button)findViewById(R.id.button100);
        b100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScaleViewAction a = new ScaleViewAction(Action.Type.INFORMATIVE);
                a.setScale(1.0f);
                gameView.processAction(a);
            }
        });

        Button b130 = (Button)findViewById(R.id.button130);
        b130.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScaleViewAction a = new ScaleViewAction(Action.Type.INFORMATIVE);
                a.setScale(1.3f);
                gameView.processAction(a);
            }
        });

        Button bH = (Button)findViewById(R.id.buttonHex);
        bH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.setCellType(BoardFactory.BoardType.HEX_CELLS);
                //gameView.initializeThread();
            }
        });

        Button bS = (Button)findViewById(R.id.buttonSquare);
        bS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.setCellType(BoardFactory.BoardType.SQUARE_CELLS);
                //gameView.initializeThread();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("FUUUU", "comienza lo bueno...");
        Log.d("FUUUU", "10 / 3f = " + 10 / 3f);
        Log.d("FUUUU", "10 - 3.3f = " + (10 - 3.3f));
        daleChicha();

        //this.gameView.run();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //this.gameView.
        this.gameView.stop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //return super.dispatchTouchEvent(ev);

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Rect r = new Rect(
                    this.gameView.getLeft(),
                    this.gameView.getTop(),
                    this.gameView.getRight(),
                    this.gameView.getBottom());

            float x = ev.getX() - this.gameView.getLeft();
            float y = ev.getY() - this.gameView.getTop();
            this.gameView.processTouchEvent(new Point((int) x, (int) y));

            if (r.contains((int)ev.getX(), (int)ev.getY())){
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void daleChicha(){
        this.gameView.init();
    }

}
