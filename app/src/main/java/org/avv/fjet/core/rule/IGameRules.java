package org.avv.fjet.core.rule;

import org.avv.fjet.core.action.Action;
import org.avv.fjet.core.board.ICoords;

/**
 * Creado por Alexander Vladimirovich Vorobiev / 27/02/2016
 */
public interface IGameRules {

    Action getActionWithOnTapDown(ICoords coords);

    Action getActionWithOnTapUp(ICoords coords);

    Action getActionWithOnLongPress(ICoords coords);

}
