package org.avv.fjet;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.avv.fjet.core.Game;
import org.avv.fjet.core.action.Action;
import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.ICoordinates;
import org.avv.fjet.core.board.SquareCoords;

import java.util.Arrays;

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

        /*Game g = new Game();
        TestThread t1 = new TestThread(g, " Thread_1 ");
        TestThread t2 = new TestThread(g, " Thread_2 ");
        TestUnRedoThread urT = new TestUnRedoThread(g, "UnRedo_Thread_1");

        t1.start();
        t2.start();
        urT.start();*/
        HexCoords sC = new HexCoords(3,4);
        ICoordinates[] results = sC.getCoordsInRangeArray(2);

        for (ICoordinates c : results) {
            Log.d("Resultado", c.toString());
        }
    }

    private class TestAction extends Action {

        private String tag;

        public TestAction(String tag){
            this.tag = tag;
        }

        @Override
        protected ActionResult getExecutionResult() {
            return null;
        }

        @Override
        protected ActionUndoResult getExecutionUndoResult() {
            return null;
        }

        @Override
        public void execute() {
            Log.d("Accion", ">>>>>>>>> - " + tag);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("Accion", "<<<<<<<<< - " + tag);
        }

        @Override
        public void undo() {
            Log.d("FUUUU", "Undo -> " + tag);
        }
    }

    private class TestThread extends Thread {

        private Game g;
        private String name;

        TestThread(Game game, String name){
            this.g = game;
            this.name = name;
        }

        @Override
        public void run() {
            for(int i = 0; i < 10; i++){
                TestAction a = new TestAction(String.valueOf(i) + this.name);
                this.g.processAction(a);
            }
        }
    }

    private class TestUnRedoThread extends Thread {

        private Game g;
        private String name;

        TestUnRedoThread(Game game, String name){
            this.g = game;
            this.name = name;
        }

        @Override
        public void run() {
            for(int i = 0; i < 10; i++){
                this.g.undoLastAction();
                try {
                    sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for(int i = 0; i < 10; i++){
                this.g.redoLastAction();
                try {
                    sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
