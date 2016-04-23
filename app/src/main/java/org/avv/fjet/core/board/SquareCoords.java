package org.avv.fjet.core.board;

import org.avv.fjet.core.board.util.UtilCoordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/03/2016
 * -------------------------------------------
 * Square cell coordinates
 */
public class SquareCoords implements ICoords {

    // region - Constants

    private static final SquareCoords [] NEIGHBORS = {
            new SquareCoords(0, -1),
            new SquareCoords(-1, 0), new SquareCoords(1, 0),
            new SquareCoords(0, 1)};

    // endregion - Constants

    // region - Fields

    private int x;
    private int y;

    // endregion - Fields

    // region - Constructors

    public SquareCoords(int x, int y){
        this.x = x;
        this.y = y;
    }

    // endregion - Constructors

    // region - Getters and Setters

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    public ICoords[] getNeighborsArray() {
        SquareCoords [] neighbors = new SquareCoords[NEIGHBORS.length];

        for (int i = 0; i < NEIGHBORS.length; i++){
            neighbors[i] = UtilCoordinates.addSquareCoords(this, NEIGHBORS[i]);
        }
        return neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SquareCoords){
            SquareCoords coords = (SquareCoords) o;
            return this.x == coords.x
                    && this.y == coords.y;
        }
        return false;
    }

    @Override
    public ICoords[] getCoordsInRangeArray(int n) {
        List<SquareCoords> neighbors = new ArrayList<>();

        for (int dy = -n; dy <= n; dy++){               // -N ≤ dy ≤ N
            int minX = Math.max(-dy - n, dy - n);
            int maxX = Math.min(-dy + n, dy + n);

            for (int dx = minX; dx <= maxX; dx++){      // -dy-N ≤ dy ≤ -dy+N
                neighbors.add(UtilCoordinates.addSquareCoords(this, new SquareCoords(dx, dy)));
            }
        }
        return neighbors.toArray(new SquareCoords[neighbors.size()]);
    }

    @Override
    public String toString() {
        return "SquareCoords(" + this.x + "," + this.y + ")";
    }

    @Override
    public ICoords getCopy() {
        return new SquareCoords(this.x, this.y);
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
