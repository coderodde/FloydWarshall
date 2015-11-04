package net.coderodde.graph.pathfinding;

import org.junit.Test;
import static org.junit.Assert.*;

public class FloydWarshallTest {

    private static final FloydWarshall ALGO = new FloydWarshall();
    
//    @Test
    public void testFloydWarshallOnEmptyAdjacencyMatrix() {
        AdjacencyMatrix m = new AdjacencyMatrix(0);
        ShortestPathData data = ALGO.compute(m);
        
        assertEquals(0, data.getCostMatrix().getNumberOfNodes());
        assertEquals(0, data.getParentMatrix().getNumberOfNodes());
        
        try {
            data.getCostMatrix().getShortestPathCost(0, 0);
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            
        }
        
        try {
            data.getParentMatrix().getParent(0, 0);
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException ex) {
            
        }
    }    
    
    @Test
    public void testFloydWarshallOnDisconnectedGraph() {
        // (0)     (3)
        //  |       |
        //  | 3.0   | 4.0
        //  |       | 
        // (1)     (4)
        //  |
        //  | 1.5
        //  |
        // (2)
        
        AdjacencyMatrix m = new AdjacencyMatrix(5);
        
        m.setArcCost(0, 1, 3.0);
        m.setArcCost(1, 0, 3.0);
        m.setArcCost(1, 2, 1.5);
        m.setArcCost(2, 1, 1.5);
        m.setArcCost(3, 4, 4.0);
        m.setArcCost(4, 3, 4.0);
        
        ShortestPathData data = ALGO.compute(m);
        ParentMatrix pm = data.getParentMatrix();
        ShortestPathCostMatrix cm = data.getCostMatrix();
        
        assertEquals(3.0, cm.getShortestPathCost(0, 1), 0.0);
        assertEquals(3.0, cm.getShortestPathCost(1, 0), 0.0);
        
        assertEquals(1.5, cm.getShortestPathCost(1, 2), 0.0);
        assertEquals(1.5, cm.getShortestPathCost(2, 1), 0.0);
        
        assertEquals(4.5, cm.getShortestPathCost(0, 2), 0.0);
        assertEquals(4.5, cm.getShortestPathCost(2, 0), 0.0);
        
        assertEquals(4.0, cm.getShortestPathCost(3, 4), 0.0);
        assertEquals(4.0, cm.getShortestPathCost(4, 3), 0.0);
        
        final double INF = Double.POSITIVE_INFINITY;
        
        assertEquals(INF, cm.getShortestPathCost(0, 3), 0.0);
        assertEquals(INF, cm.getShortestPathCost(3, 0), 0.0);

        assertEquals(INF, cm.getShortestPathCost(0, 4), 0.0);
        assertEquals(INF, cm.getShortestPathCost(4, 0), 0.0);

        assertEquals(INF, cm.getShortestPathCost(1, 3), 0.0);
        assertEquals(INF, cm.getShortestPathCost(3, 1), 0.0);

        assertEquals(INF, cm.getShortestPathCost(1, 4), 0.0);
        assertEquals(INF, cm.getShortestPathCost(4, 1), 0.0);

        assertEquals(INF, cm.getShortestPathCost(2, 3), 0.0);
        assertEquals(INF, cm.getShortestPathCost(3, 2), 0.0);

        assertEquals(INF, cm.getShortestPathCost(2, 4), 0.0);
        assertEquals(INF, cm.getShortestPathCost(4, 2), 0.0);
        
        assertEquals(0.0, cm.getShortestPathCost(0, 0), 0.0);
        assertEquals(0.0, cm.getShortestPathCost(1, 1), 0.0);
        assertEquals(0.0, cm.getShortestPathCost(2, 2), 0.0);
        assertEquals(0.0, cm.getShortestPathCost(3, 3), 0.0);
        assertEquals(0.0, cm.getShortestPathCost(4, 4), 0.0);
        
        int[] path = pm.getShortestPath(0, 2);
        System.out.println("yooo");
        
        assertEquals(3, path.length);
        assertEquals(0, path[0]);
        assertEquals(1, path[1]);
        assertEquals(2, path[2]);
        
        path = pm.getShortestPath(2, 0);
        
        assertEquals(3, path.length);
        assertEquals(2, path[0]);
        assertEquals(1, path[1]);
        assertEquals(0, path[2]);
    }
    
//    @Test
    public void testFloydWarshallSelfLoops() {
        AdjacencyMatrix m = new AdjacencyMatrix(2);
        
        m.setArcCost(0, 0, 1.5);
        m.setArcCost(1, 1, 9.6);
        
        ShortestPathData data = ALGO.compute(m);
        
        assertEquals(0.0, data.getCostMatrix().getShortestPathCost(0, 0), 0.0);
        assertEquals(0.0, data.getCostMatrix().getShortestPathCost(1, 1), 0.0);
        
        assertEquals(ParentMatrix.NIL, data.getParentMatrix().getParent(0, 0));
        assertEquals(ParentMatrix.NIL, data.getParentMatrix().getParent(1, 1));
    }
    
//    @Test
    public void testFloydWarshallGraphFromIntroductionToAlgorithms() {
        // The following graph is from "Introduction to Algorithms" 3rd edition,
        // Chapter 25, page 690.
        final int N = 5;
        AdjacencyMatrix m = new AdjacencyMatrix(N);
        
        // 9 arcs follow:
        
        // 1st row
        m.setArcCost(0, 1, 3.0);
        m.setArcCost(0, 2, 8.0);
        m.setArcCost(0, 4, -4.0);
        
        // 2nd row
        m.setArcCost(1, 3, 1.0);
        m.setArcCost(1, 4, 7.0);
        
        // 3rd row
        m.setArcCost(2, 1, 4.0);
        
        // 4th row
        m.setArcCost(3, 0, 2.0);
        m.setArcCost(3, 2, -5.0);
        
        // 5th row
        m.setArcCost(4, 3, 6.0);
        
        ShortestPathData data = ALGO.compute(m);
        ShortestPathCostMatrix cm = data.getCostMatrix();
        ParentMatrix pm = data.getParentMatrix();
        
        for (int i = 0; i < N; ++i) {
            // Diagonal entries must be zero (0.0).
            assertEquals(0.0, cm.getShortestPathCost(i, i), 0.0);
            
            // Diagonal entries must be NIL because for each trivial path
            // i -> i, there is only one node in the path, and for this reason
            // each i has no parent node.
            assertEquals(ParentMatrix.NIL, pm.getParent(i, i));
        }
        
        System.out.println(cm);
        System.out.println(pm);
        
        // Check all the shortest path cost entries.
        assertEquals(+1.0, cm.getShortestPathCost(0, 1), 0.0);
        assertEquals(-3.0, cm.getShortestPathCost(0, 2), 0.0);
        assertEquals(+2.0, cm.getShortestPathCost(0, 3), 0.0);
        assertEquals(-4.0, cm.getShortestPathCost(0, 4), 0.0);
        
        assertEquals(+3.0, cm.getShortestPathCost(1, 0), 0.0);
        assertEquals(-4.0, cm.getShortestPathCost(1, 2), 0.0);
        assertEquals(+1.0, cm.getShortestPathCost(1, 3), 0.0);
        assertEquals(-1.0, cm.getShortestPathCost(1, 4), 0.0);
        
        assertEquals(+7.0, cm.getShortestPathCost(2, 0), 0.0);
        assertEquals(+4.0, cm.getShortestPathCost(2, 1), 0.0);
        assertEquals(+5.0, cm.getShortestPathCost(2, 3), 0.0);
        assertEquals(+3.0, cm.getShortestPathCost(2, 4), 0.0);
        
        assertEquals(+2.0, cm.getShortestPathCost(3, 0), 0.0);
        assertEquals(-1.0, cm.getShortestPathCost(3, 1), 0.0);
        assertEquals(-5.0, cm.getShortestPathCost(3, 2), 0.0);
        assertEquals(-2.0, cm.getShortestPathCost(3, 4), 0.0);
        
        assertEquals(+8.0, cm.getShortestPathCost(4, 0), 0.0);
        assertEquals(+5.0, cm.getShortestPathCost(4, 1), 0.0);
        assertEquals(+1.0, cm.getShortestPathCost(4, 2), 0.0);
        assertEquals(+6.0, cm.getShortestPathCost(4, 3), 0.0);
        
    }
}
