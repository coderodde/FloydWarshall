package net.coderodde.graph.allpairs;

import static net.coderodde.graph.allpairs.Utils.checkArcCost;
import static net.coderodde.graph.allpairs.Utils.checkNumberOfNodes;
import static net.coderodde.graph.allpairs.Utils.checkNodeIndex;

/**
 * This class implements implicitly a directed graph by means of adjacency 
 * matrix. After construction, the cost of each possible arc is set to positive
 * infinity in order to denote the fact that the arc is not present in the
 * graph.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Nov 3, 2015)
 */
public final class AdjacencyMatrix {

    private final double[][] matrix;
    
    public AdjacencyMatrix(int numberOfNodes) {
        checkNumberOfNodes(numberOfNodes);
        this.matrix = new double[numberOfNodes][numberOfNodes];
        
        for (int y = 0; y < numberOfNodes; ++y) {
            for (int x = 0; x < numberOfNodes; ++x) {
                matrix[y][x] = Double.POSITIVE_INFINITY;
            }
        }
        
        for (int i = 0; i < numberOfNodes; ++i) {
            // The distance from a node to itself is always zero.
            matrix[i][i] = 0.0;
        }
    }
    
    public int getNumberOfNodes() {
        return matrix.length;
    }
    
    /**
     * Reads the cost of the arc from {@code tailNodeIndex} to 
     * {@code headNodeIndex}.
     * 
     * @param tailNodeIndex the index of the tail node.
     * @param headNodeIndex the index of the head node.
     * @return the current cost of the arc.
     */
    public double getArcCost(int tailNodeIndex, int headNodeIndex) {
        checkNodeIndex(tailNodeIndex, matrix.length);
        checkNodeIndex(headNodeIndex, matrix.length);
        return matrix[headNodeIndex][tailNodeIndex];
    }
    
    /**
     * Sets the cost of the arc from {@code tailNodeIndex} to
     * {@code headNodeIndex}.
     * 
     * @param tailNodeIndex the index of the tail node.
     * @param headNodeIndex the index of the head node.
     * @param cost the cost of the arc to set.
     */
    public void setArcCost(int tailNodeIndex, int headNodeIndex, double cost) {
        checkNodeIndex(tailNodeIndex, matrix.length);
        checkNodeIndex(headNodeIndex, matrix.length);
        checkArcCost(cost);
        
        // Do not update cost from a node to itself, or namely, do not introduce
        // self-loops.
        if (tailNodeIndex != headNodeIndex) {
            matrix[headNodeIndex][tailNodeIndex] = cost;
        }
    }
}
