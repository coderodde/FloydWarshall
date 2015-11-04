package net.coderodde.graph.pathfinding;

/**
 * This class bundles all the information computed in a all-pairs shortest path
 * algorithm.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Nov 3, 2015) 
 */
public class ShortestPathData {
    
    private final ShortestPathCostMatrix costMatrix;
    private final ParentMatrix parentMatrix;
    
    ShortestPathData(ShortestPathCostMatrix costMatrix, 
                     ParentMatrix parentMatrix) {
        this.costMatrix = costMatrix;
        this.parentMatrix = parentMatrix;
    }
    
    public ShortestPathCostMatrix getCostMatrix() {
        return costMatrix;
    }
    
    public ParentMatrix getParentMatrix() {
        return parentMatrix;
    }
}
