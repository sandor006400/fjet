package org.avv.fjet.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Vorobiev on 26/06/16.
 */
public class PriorityQueue<T> {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private List<Tuple<T, Double>> elements = new ArrayList<Tuple<T, Double>>();

    // endregion - Fields

    // region - Constructors

    // endregion - Constructors

    // region - Getters and Setters

    public List<Tuple<T, Double>> getElements(){
        return this.elements;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public void enqueue(T item, Double priority) {
        elements.add(new Tuple<T, Double>(item, priority));
    }

    public T dequeue() {
        int bestIndex = 0;

        for (int i = 0; i < elements.size(); i++) {
            if (this.elements.get(i).y < this.elements.get(bestIndex).y) {
                bestIndex = i;
            }
        }

        T bestItem = this.elements.get(bestIndex).x;
        elements.remove(bestIndex);
        return bestItem;
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    /**
     * Source: http://stackoverflow.com/questions/2670982/using-pairs-or-2-tuples-in-java
     * @param <X>
     * @param <Y>
     */
    public class Tuple<X, Y> {

        public final X x;
        public final Y y;

        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }

    // endregion - Inner and Anonymous Classes

}
