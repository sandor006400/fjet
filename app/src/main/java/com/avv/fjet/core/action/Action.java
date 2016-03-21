package com.avv.fjet.core.action;

/**
 * Creado por Alexander Vladimirovich Vorobiev / 27/02/2016
 */
public abstract class Action {

    public abstract void execute();

    public abstract void undo();

    interface IActionData {

    }

}
