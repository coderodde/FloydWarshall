package net.coderodde.graph.pathfinding;

import static net.coderodde.graph.pathfinding.Utils.checkNodeIndex;
import static net.coderodde.graph.pathfinding.Utils.checkNumberOfNodes;

/**
 * This class implements a read-only data structure for querying the shortest
 * path costs between two nodes. All queries run in constant time.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Nov 3, 2015)
 */
public class ShortestPathCostMatrix {
    
    private final double[][] matrix;
    
    ShortestPathCostMatrix(int numberOfNodes) {
        checkNumberOfNodes(numberOfNodes);
        this.matrix = new double[numberOfNodes][numberOfNodes];
        
        for (int y = 0; y < numberOfNodes; ++y) {
            for (int x = 0; x < numberOfNodes; ++x) {
                matrix[y][x] = Double.POSITIVE_INFINITY;
            }
        }
    }
    
    public int getNumberOfNodes() {
        return matrix.length;
    }
    
    public double getShortestPathCost(int sourceNodeIndex, 
                                      int targetNodeIndex) {
        checkNodeIndex(sourceNodeIndex, matrix.length);
        checkNodeIndex(targetNodeIndex, matrix.length);
        return matrix[sourceNodeIndex][targetNodeIndex];
    }
    
    @Override
    public String toString() {
        int n = matrix.length;
        int maximumFieldLength = 0;
        StringBuilder sb = new StringBuilder();
        
        for (int y = 0; y < n; ++y) {
            for (int x = 0; x < n; ++x) {
                sb.append((int) matrix[y][x]);
                
                int currentFieldLength = sb.toString().length();
                
                if (maximumFieldLength < currentFieldLength) {
                    maximumFieldLength = currentFieldLength;
                }
                
                // Clear the StringBuilder.
                sb.delete(0, sb.length());
            }
        }
        
        for (int y = 0; y < n; ++y) {
            for (int x = 0; x < n; ++x) {
                sb.append(String.format("%+" + maximumFieldLength + ".2f", 
                                        matrix[y][x]));
                
                if (x < n - 1) {
                    sb.append(' ');
                }
            }
            
            if (y < n - 1) {
                sb.append('\n');
            }
        }
        
        return sb.toString();
    }
    
    void setShortestPathCost(int sourceNodeIndex,
                             int targetNodeIndex,
                             double cost) {
        matrix[sourceNodeIndex][targetNodeIndex] = cost;
    }
}
