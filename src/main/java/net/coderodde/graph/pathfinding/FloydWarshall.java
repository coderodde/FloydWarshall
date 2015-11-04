package net.coderodde.graph.pathfinding;

import java.util.Objects;

/**
 * This class implements the Floyd-Warshall algorithm for all-pairs shortest 
 * path problem.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Nov 3, 2015)
 */
public final class FloydWarshall {

    public ShortestPathData compute(AdjacencyMatrix adjacencyMatrix) {
        Objects.requireNonNull(adjacencyMatrix,
                               "The adjacency matrix is null.");
        int n = adjacencyMatrix.getNumberOfNodes();
        ParentMatrix parentMatrix  = new ParentMatrix(n);
        ShortestPathCostMatrix shortestPathCostMatrix = 
                new ShortestPathCostMatrix(n);
        
        preprocess(parentMatrix, shortestPathCostMatrix, adjacencyMatrix);
        
        for (int k = 0; k < n; ++k) {
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    double currentCost = shortestPathCostMatrix
                                         .getShortestPathCost(i, j);
                    
                    double tentativeCost = 
                            shortestPathCostMatrix.getShortestPathCost(i, k) +
                            shortestPathCostMatrix.getShortestPathCost(k, j);
                    
                    if (currentCost > tentativeCost) {
                        shortestPathCostMatrix
                                .setShortestPathCost(i, j, tentativeCost);
                        
                        // Update the parents book-keeping.
                        parentMatrix
                                .setParent(i, 
                                           j, 
                                           parentMatrix.getParent(k, j));
                    }
                }
            }
        }
        
        for (int i = 0; i < n; ++i) {
            parentMatrix.setParent(i, i, ParentMatrix.NIL);
        }
        
        return new ShortestPathData(shortestPathCostMatrix, 
                                    parentMatrix);
    }
    
    // Initializes the parent and shortest path cost matrices.
    private void preprocess(ParentMatrix parentMatrix,
                            ShortestPathCostMatrix shortestPathCostMatrix,
                            AdjacencyMatrix adjacencyMatrix) {
        int n = adjacencyMatrix.getNumberOfNodes();
        
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                double cost = adjacencyMatrix.getArcCost(i, j);
                shortestPathCostMatrix.setShortestPathCost(i, j, cost);
                
                if (i != j && !Double.isInfinite(cost)) {
                    parentMatrix.setParent(i, j, i);
                }
            }
        }
    }
}
