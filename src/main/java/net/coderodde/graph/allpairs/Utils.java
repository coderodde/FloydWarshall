package net.coderodde.graph.allpairs;

/**
 * This class contains miscellaneous utility methods for the entire library.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Nov 3, 2015)
 */
class Utils {
    
    static void checkNumberOfNodes(int numberOfNodes) {
        if (numberOfNodes < 0) {
            throw new IllegalArgumentException(
                    "The number of nodes is negative: " + numberOfNodes);
        }
    }
    
    static void checkNodeIndex(int nodeIndex, int numberOfNodes) {
        if (nodeIndex < 0) {
            throw new IllegalArgumentException(
                    "The node index is negative: " + nodeIndex);
        }
        
        if (nodeIndex >= numberOfNodes) {
            throw new IllegalArgumentException(
                    "The node index is too large: " + nodeIndex + ", the " +
                    "amount of nodes in the implicit graph is " + 
                    numberOfNodes + ".");
        }
    }
    
    static void checkArcCost(double cost) {
        if (Double.isNaN(cost)) {
            throw new IllegalArgumentException("The arc cost is NaN.");
        }
    }
}
