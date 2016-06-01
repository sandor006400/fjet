package org.avv.fjet.core.action;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 02/04/2016
 */
public class ActionFactory {

    // region - Constants

    // Movement Actions 0-9999
    public static final int MOVE_TO_ACTION = 0;

    // Attack Actions 10000-19999
    public static final int ATTACK_ACTION = 10000;

    // Selection Actions 20000-29999
    public static final int SELECT_CELL_ACTION = 20000;

    // Other Actions 30000-39999
    public static final int SCROLL_BOARD_VIEW_ACTION = 30000;
    public static final int SCALE_BOARD_VIEW_ACTION = 30001;

    // endregion - Constants

    // region - Fields

    // endregion - Fields

    // region - Constructors

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    /**
     * Override this method to create new actions
     * @param action
     * @return
     */
    private Action createOtherAction(int action){
        return null;
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public static Action createAction(int action){

        switch (action){

            case MOVE_TO_ACTION:
                return new MoveUnitAction();

            case ATTACK_ACTION:
                return null; // TODO

            case SELECT_CELL_ACTION:
                return new SelectCellAction();

            case SCROLL_BOARD_VIEW_ACTION:
                return new ScrollBoardViewAction();

            case SCALE_BOARD_VIEW_ACTION:
                return new ScaleViewAction();

            default:
                return createAction(action);
        }
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
