package org.avv.fjet.graph;

import android.util.Log;

import org.avv.fjet.core.board.Board;
import org.avv.fjet.core.board.Cell;
import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.ICoords;
import org.avv.fjet.core.board.SquareCoords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Created by Alexander Vorobiev on 22/05/16.
 */
public class UtilPathFindingAlgorithms {

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

    public static List<Cell> dijkstra(Cell origin, Cell destination, Board board){

        // TODO : see http://rosettacode.org/wiki/Dijkstra%27s_algorithm#Java

        return null;
    }

    /**
     * This algorithm implementation is based on A* implementations from:
     * http://www.redblobgames.com/pathfinding/a-star/implementation.html
     * @param origin
     * @param destination
     * @param cellsGraph
     * @return
     */
    public static List<Cell> aStar(Cell origin, Cell destination, FJetCellsGraph cellsGraph){
        Log.d("UtilPathFindingAlgorithms", "aStar, nodes: " + cellsGraph.getCells().size());

        Map<String, String> cameFrom = new HashMap<String, String>();
        Map<String, Double> costSoFar = new HashMap<String, Double>();

        PriorityQueue<Cell> frontier = new PriorityQueue<>();
        frontier.enqueue(origin, 0.0d);

        cameFrom.put(origin.getId(), origin.getId());
        costSoFar.put(origin.getId(), 0.0);

        while(frontier.getElements().size() > 0) {
            Cell current = frontier.dequeue();

            if (current.equals(destination)){
                break;
            }

            for (Cell next : cellsGraph.getNeightbors(current)) {
                double newCost = costSoFar.get(current.getId()) + next.getTerrain().getMovementCost();

                if (!costSoFar.containsKey(next.getId())
                        || newCost < costSoFar.get(next.getId())){
                    costSoFar.put(next.getId(), newCost);

                    double priority = newCost + heuristic(next.getCoords(), destination.getCoords());
                    frontier.enqueue(next, priority);
                    cameFrom.put(next.getId(), current.getId());
                }
            }
        }

        if (cameFrom.size() > 0) {
            List<Cell> auxPath = new ArrayList<>();
            List<Cell> path = new ArrayList<>();
            Cell prevCell = cellsGraph.getCells().get(destination.getId());
            auxPath.add(destination);

            while (prevCell != null
                    && !prevCell.getId().equals(origin.getId())) {
                auxPath.add(prevCell);
                prevCell = cellsGraph.getCells().get(cameFrom.get(prevCell.getId()));
            }
            auxPath.add(origin);

            // Inverting a path order
            for (int i = auxPath.size() - 1; i >= 0; i--){
                path.add(auxPath.get(i));
            }
            return path;
        }
        return null;
    }

    public static double heuristic(ICoords a, ICoords b){
        if (a instanceof HexCoords && b instanceof HexCoords){
            return Math.abs(((HexCoords) a).getQ() - ((HexCoords) b).getQ())
                    + Math.abs(((HexCoords) a).getR() - ((HexCoords) b).getR());

        } else if (a instanceof SquareCoords && b instanceof SquareCoords){
            return Math.abs(((SquareCoords) a).getX() - ((SquareCoords) b).getX())
                    + Math.abs(((SquareCoords) a).getY() - ((SquareCoords) b).getY());
        }
        return 0;
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
