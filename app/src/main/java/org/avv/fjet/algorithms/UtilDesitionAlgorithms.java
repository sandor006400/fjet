package org.avv.fjet.algorithms;

/**
 * Created by Alexander Vorobiev on 29/07/16.
 */
public class UtilDesitionAlgorithms {

    // region - Constants

    // endregion - Constants

    // region - Fields

    // endregion - Fields

    // region - Constructors

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    /**
     * MiniMax algorithm implementation. Source: <a href="https://en.wikipedia.org/wiki/Minimax">Minimax</a>.
     * <b>Pseudocode</b>:
     * <pre><i>
     * function minimax(node, depth, maximizingPlayer)
     *     if depth = 0 or node is a terminal node
     *         return the heuristic value of node
     *     if maximizingPlayer
     *         bestValue := −∞
     *         for each child of node
     *             v := minimax(child, depth − 1, FALSE)
     *             bestValue := max(bestValue, v)
     *         return bestValue
     *     else    (* minimizing player *)
     *         bestValue := +∞
     *         for each child of node
     *             v := minimax(child, depth − 1, TRUE)
     *             bestValue := min(bestValue, v)
     *         return bestValue
     * </i></pre>
     * @param gameState
     * @param depth
     * @param maximazingPlayer
     * @return
     */
    public static float minimax(IGameState gameState, int depth, boolean maximazingPlayer) {

        if (depth == 0 || gameState.isFinalState()){
            return gameState.getStateScore();
        }
        float bestValue = Float.MIN_VALUE;
        float auxValue;

        if (maximazingPlayer){

            for(IGameState state : gameState.getNextPossibleStates()){
                auxValue = minimax(state, depth - 1, false);
                bestValue = Math.max(auxValue, bestValue);
            }

        } else {
            for(IGameState state : gameState.getNextPossibleStates()){
                auxValue = minimax(state, depth - 1, true);
                bestValue = Math.min(auxValue, bestValue);
            }
        }
        return bestValue;
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
