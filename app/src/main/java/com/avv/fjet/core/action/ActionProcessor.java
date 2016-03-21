package com.avv.fjet.core.action;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Creado por Alexander Vladimirovich Vorobiev / 27/02/2016
 */
public class ActionProcessor implements Runnable {

    // region - Constants

    enum State {
        RUNNING,
        WAITING,
        STOP
    }

    private static final String DEBUD_TAG = "ActionProcessor";
    private static final int CYCLES_PER_SECOND = 30;
    private static final int WATING_TIME = 1000 / CYCLES_PER_SECOND;
    private static final int MAX_BUFFER_SIZE = 100;

    // endregion - Constants

    // region - Fields

    private long lastUpdateTime = 0;

    private State state;

    private List<Action> actionsList = new ArrayList<Action>();
    private int currentAction = -1;
    private Action [] actionsBuffer = new Action [MAX_BUFFER_SIZE];
    private int bufferHead = 0;
    private int bufferTail = 0;

    // endregion - Fields

    // region - Constructors

    public ActionProcessor(){
        this.state = State.WAITING;
    }

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public void run() {

        while(this.state != State.STOP) {
            long currentTime = System.currentTimeMillis();

            if (lastUpdateTime + WATING_TIME < currentTime) {
                //Log.d(DEBUD_TAG, "run -> " + (currentTime - lastUpdateTime) + " millis of waiting");
                this.lastUpdateTime = currentTime;

                if (this.state == State.RUNNING) {

                    if (this.bufferTail != this.bufferHead){
                        Action action = this.actionsBuffer[this.bufferHead];
                        updateActionsList(action);
                        action.execute();
                        this.bufferHead = (this.bufferHead + 1) % MAX_BUFFER_SIZE;
                    }
                }

            } else {
                // TODO -> maybe for lightweight tasks
            }
        }
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public void start(){
        this.state = State.RUNNING;
    }

    public void pause(){
        this.state = State.WAITING;
    }

    public void stop(){
        this.state = State.STOP;
    }

    private void updateActionsList(Action action){
        if (this.currentAction + 1 < this.actionsList.size()){
            this.actionsList.subList(0, this.currentAction);
        }
        this.actionsList.add(action);
        this.currentAction++;
    }

    synchronized public void undoLastAction(){
        if (this.currentAction != -1 && this.currentAction < this.actionsList.size()) {
            this.actionsList.get(this.currentAction).undo();
            this.currentAction--;
        }
    }

    synchronized public void redoLastAction(){
        if (this.currentAction + 1 < this.actionsList.size()) {
            this.currentAction++;
            this.actionsList.get(this.currentAction).execute();
        }
    }

    synchronized public void processAction(Action action){
        if ((this.bufferTail + 1) % MAX_BUFFER_SIZE  != this.bufferHead){
            this.actionsBuffer[this.bufferTail] = action;
            this.bufferTail = (this.bufferTail + 1) % MAX_BUFFER_SIZE;
        }
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
