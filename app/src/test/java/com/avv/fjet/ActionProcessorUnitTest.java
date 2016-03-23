package com.avv.fjet;

import com.avv.fjet.core.action.Action;
import com.avv.fjet.core.action.ActionExecutor;

import org.junit.Test;

import java.lang.Override;
import java.lang.System;
import java.lang.Thread;

public class ActionProcessorUnitTest {
    @Test
    public void test_processAction() throws Exception {
        ActionExecutor aP = new ActionExecutor();

        Thread t1 = new Thread(aP);
        t1.run();
        aP.start();

        for(int i = 0; i < 100; i++){
            TestAction a = new TestAction(String.valueOf(i));
            aP.executeAction(a);

            /*if (i % 10 == 0){
                aP.undoLastAction();
            }

            if (i % 20 == 0){
                aP.redoLastAction();
            }*/
            System.out.println("Terminado el ciclo :" + i);
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
            System.out.println("Execute -> " + tag);
        }

        @Override
        public void undo() {
            System.out.println("Undo -> " + tag);
        }
    }
}