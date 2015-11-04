package net.coderodde.graph.pathfinding;

import java.util.ArrayList;
import java.util.List;
import static net.coderodde.graph.pathfinding.Utils.checkNodeIndex;
import static net.coderodde.graph.pathfinding.Utils.checkNumberOfNodes;

/**
 * This class implements a data structure for querying parents on shortest
 * paths. All shortest path queries run in linear time with regard to the number
 * of edges in the queried path.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Nov 3, 2015)
 */
public final class ParentMatrix {
    
    /**
     * This sentinel value denotes the situation where there is no parent node
     * for the query.
     */
    public static final int NIL = -1;
    
    private final int[][] matrix;
    
    ParentMatrix(int numberOfNodes) {
        checkNumberOfNodes(numberOfNodes);
        this.matrix = new int[numberOfNodes][numberOfNodes];
        
        for (int y = 0; y < numberOfNodes; ++y) {
            for (int x = 0; x < numberOfNodes; ++x) {
                matrix[y][x] = NIL;
            }
        }
    }
    
    public int getNumberOfNodes() {
        return matrix.length;
    }
    
    /**
     * Returns the parent node of the node {@code currentNodeIndex} on a 
     * shortest path from {@code sourceNodeIndex} to {@code currentNodeIndex}.
     * 
     * @param sourceNodeIndex  the index of the source node of a shortest path.
     * @param currentNodeIndex the index of the query node.
     * @return the index of the parent node of {@code currentNodeIndex} on a
     *         shortest path from {@code sourceNodeIndex} to 
     *         {@code currentNodeIndex} or {@link NIL} if there is no such.
     */
    public int getParent(int sourceNodeIndex, int currentNodeIndex) {
        checkNodeIndex(sourceNodeIndex, matrix.length);
        checkNodeIndex(currentNodeIndex, matrix.length);
        return matrix[sourceNodeIndex][currentNodeIndex];
    }
    
    /**
     * Constructs a shortest path from the node {@code sourceNodeIndex} to the 
     * node {@code targetNodeIndex}. If the target node is unreachable from the
     * source, an empty array is returned. Otherwise an array of integers is
     * returned, listing the node indices from the source node to the target 
     * node along a shortest path.
     * 
     * @param sourceNodeIndex the index of the source node.
     * @param targetNodeIndex the index of the target node.
     * @return an array of integers listing the node indices on a shortest path
     *         or an empty array if the target node is not reachable from the 
     *         source node.
     */
    public int[] getShortestPath(int sourceNodeIndex, int targetNodeIndex) {
        checkNodeIndex(sourceNodeIndex, matrix.length);
        checkNodeIndex(targetNodeIndex, matrix.length);
        
        if (sourceNodeIndex == targetNodeIndex) {
            return new int[]{sourceNodeIndex};
        }
        
        if (matrix[sourceNodeIndex][targetNodeIndex] == NIL) {
            return new int[0];
        }
        
        List<Integer> nodeIndexList = new ArrayList<>();
        
        nodeIndexList.add(targetNodeIndex);
        
        while (sourceNodeIndex != targetNodeIndex) {
            targetNodeIndex = matrix[sourceNodeIndex][targetNodeIndex];
            nodeIndexList.add(targetNodeIndex);
        }
        
        int[] path = new int[nodeIndexList.size()];
        
        // The path in 'nodeIndexList' is in reversed order.
        for (int i = 0; i < path.length; ++i) {
            path[i] = nodeIndexList.get(nodeIndexList.size() - 1 - i);
        }
        
        return path;
    }
    
    @Override
    public String toString() {
        int n = matrix.length;
        int maximumFieldLength = 0;
        StringBuilder sb = new StringBuilder();
        
        // Find out how long fields we need in order to print the contents of 
        // the matrix neatly.
        for (int y = 0; y < n; ++y) {
            for (int x = 0; x < n; ++x) {
                sb.append(matrix[y][x]);
                
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
                sb.append(String.format("%" + maximumFieldLength 
                                            + "d", matrix[y][x]));
                
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
    
    void setParent(int sourceNodeIndex, 
                   int currentNodeIndex, 
                   int parentNodeIndex) {
        matrix[sourceNodeIndex][currentNodeIndex] = parentNodeIndex;
    }
}
