package net.coderodde.graph.allpairs;

import java.util.Objects;

/**
 * This class implements the Floyd-Warshall algorithm for all-pairs shortest 
 * path problem.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Nov 3, 2015)
 */
public final class FloydWarshall {

    private ShortestPathCostMatrix costMatrix;
    private ParentMatrix parentMatrix;
    
    public FloydWarshall() {
        
    }
    
    private FloydWarshall(ShortestPathCostMatrix costMatrix,
                          ParentMatrix parentMatrix) {
        this.costMatrix = costMatrix;
        this.parentMatrix = parentMatrix;
    }
    
    public ShortestPathData compute(AdjacencyMatrix adjacencyMatrix) {
        Objects.requireNonNull(adjacencyMatrix,
                               "The adjacency matrix is null.");
        int n = adjacencyMatrix.getNumberOfNodes();
        FloydWarshall fw = new FloydWarshall(new ShortestPathCostMatrix(n),
                                             new ParentMatrix(n));
        fw.preprocess(adjacencyMatrix);

        for (int k = 0; k < n; ++k) {
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    fw.attemptImprovement(k, i, j);
                }
            }
        }
        
        boolean containsNegativeWeightCycles = 
                fw.containsNegativeWeightCycle(adjacencyMatrix);
        
        return new ShortestPathData(fw.costMatrix, 
                                    fw.parentMatrix, 
                                    containsNegativeWeightCycles);
    }
    
    private boolean 
        containsNegativeWeightCycle(AdjacencyMatrix adjacencyMatrix) {
        int n = adjacencyMatrix.getNumberOfNodes();
        
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                double arcCost = adjacencyMatrix.getArcCost(j, i);
                
                // Arc (j -> i) exists?
                if (Double.isFinite(arcCost)) {
                    // The cost of the shortest path from i to j.
                    double currentCost = costMatrix.getShortestPathCost(i, j);
                 
                    if (arcCost + currentCost < 0.0) {
                        // We have found a negative weight cycle.
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    private void attemptImprovement(int k, int i, int j) {
        double currentCost = costMatrix.getShortestPathCost(i, j);
        double tentativeCost = costMatrix.getShortestPathCost(i, k) +
                               costMatrix.getShortestPathCost(k, j);

        if (currentCost > tentativeCost) {
            costMatrix.setShortestPathCost(i, j, tentativeCost);
            parentMatrix.setParent(i, j, parentMatrix.getParent(k, j));
        }
    }
    
    // Initializes the parent and shortest path cost matrices.
    private void preprocess(AdjacencyMatrix adjacencyMatrix) {
        int n = adjacencyMatrix.getNumberOfNodes();

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                double cost = adjacencyMatrix.getArcCost(i, j);
                costMatrix.setShortestPathCost(i, j, cost);

                if (i != j && !Double.isInfinite(cost)) {
                    parentMatrix.setParent(i, j, i);
                }
            }
        }
    }
}
