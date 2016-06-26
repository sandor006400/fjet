package org.avv.fjet.graph;

import org.avv.fjet.core.board.Cell;
import org.avv.fjet.core.board.ICoords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexander Vorobiev on 25/06/16.
 * --------------------------------------------
 * Class based on examples of this site:
 * http://www.redblobgames.com/pathfinding/a-star/implementation.html
 */
public class FJetCellsGraph {

    // region - Constants

    // endregion - Constants

    // region - Fields

   private HashMap<String, Cell> cells;
   private HashMap<String, List<Cell>> edges;

    // endregion - Fields

    // region - Constructors

   public FJetCellsGraph(){
      this.cells = new HashMap<>();
      this.edges = new HashMap<>();
   }

   public FJetCellsGraph(List<Cell> cells){
      this.cells = new HashMap<>();
      this.edges = new HashMap<>();
      initWithCellsList(cells);
   }

    // endregion - Constructors

    // region - Getters and Setters

   public void addCell(Cell cell){

      if (!this.cells.containsKey(cell.getId())){
         this.cells.put(cell.getId(), cell);
         addCellNeighbors(cell);
      }
   }

   public List<Cell> getNeightbors(Cell cell){
      return this.edges.get(cell.getId());
   }

   public Map<String, Cell> getCells(){
      return this.cells;
   }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

   private void initWithCellsList(List<Cell> cells){
      for (Cell c : cells){
         addCell(c);
      }
   }

   private void addCellNeighbors(Cell cell){
      List<Cell> neighbors = new ArrayList<>();

      for (ICoords coords : cell.getCoords().getNeighborsArray()){
         for (Cell c : this.cells.values()){
            if (coords.equals(c.getCoords())){

               // Add a neighbor cell
               neighbors.add(c);

               // Set itself as a neighbor
               this.edges.get(c.getId()).add(cell);
            }
         }
      }
      this.edges.put(cell.getId(), neighbors);
   }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
