package com.avv.fjet;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.avv.fjet.core.action.Action;
import com.avv.fjet.core.action.ActionProcessor;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("FUUUU", "arrancando...");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("FUUUU", "comienza lo bueno...");
        daleChicha();
    }

    private void daleChicha(){

        ActionProcessor aP = new ActionProcessor();

        Thread t1 = new Thread(aP);
        t1.run();
        aP.start();

        for(int i = 0; i < 100; i++){
            Log.d("FUUUU", "ciclo -> " + i);
            TestAction a = new TestAction(String.valueOf(i));
            aP.processAction(a);

                if (i % 10 == 0){
                    aP.undoLastAction();
                }

                if (i % 20 == 0){
                    aP.redoLastAction();
                }
        }
        aP.stop();
        assert (true);
    }

    private class TestAction extends Action {

        private String tag;

        public TestAction(String tag){
            this.tag = tag;
        }

        @Override
        public void execute() {
            Log.d("FUUUU", "Execute -> " + tag);
        }

        @Override
        public void undo() {
            Log.d("FUUUU", "Undo -> " + tag);
        }
    }

}
