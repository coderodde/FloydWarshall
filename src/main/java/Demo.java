import java.util.Random;
import net.coderodde.graph.allpairs.AdjacencyMatrix;
import net.coderodde.graph.allpairs.FloydWarshall;
import net.coderodde.graph.allpairs.ShortestPathData;

class Demo {

    private static final int NODES = 1_000;
    private static final int ARCS = 150_000;

    public static void main(String[] args) {
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        AdjacencyMatrix m = getRandomAdjacencyMatrix(NODES,
                                                     ARCS,
                                                     0.0,
                                                     4.0,
                                                     random);

        System.out.println("Seed: " + seed);

        long startTime = System.currentTimeMillis();
        ShortestPathData data = new FloydWarshall().compute(m);
        long endTime = System.currentTimeMillis();

        System.out.println("Time elapsed: " + (endTime - startTime) 
                                            + " milliseconds.");
    }

    /**
     * Creates a random adjacency matrix over {@code nodes} nodes with
     * <b>approximately</b> {@code arcs} arcs. The weight of each arc varies 
     * between {@code minWeight} and {@code maxWeight}.
     * 
     * @param nodes     the number of nodes.
     * @param arcs      the requested number of arcs.
     * @param minWeight the minimum arc weight.
     * @param maxWeight the maximum arc weight.
     * @param random    the random number generator.
     * @return an adjacency matrix representing the graph with requested 
     *         parameters.
     */
    private static AdjacencyMatrix getRandomAdjacencyMatrix(int nodes,
                                                            int arcs,
                                                            double minWeight,
                                                            double maxWeight,
                                                            Random random) {
        AdjacencyMatrix m = new AdjacencyMatrix(nodes);

        while (arcs > 0) {
            int sourceNodeIndex = random.nextInt(nodes);
            int targetNodeIndex = random.nextInt(nodes);

            double weight = (maxWeight - minWeight) * random.nextDouble()
                                                    + minWeight;

            m.setArcCost(sourceNodeIndex, targetNodeIndex, weight);
            --arcs;
        }

        return m;
    }
}
