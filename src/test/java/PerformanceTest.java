import algorithms.KruskalAlgorithm;
import algorithms.PrimAlgorithm;
import model.Graph;
import model.MSTResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PerformanceTest {

    @Test
    public void testExecutionTimeAndWeightsNonNegative() {
        Graph g = TestUtils.createSampleGraph();

        MSTResult kr = KruskalAlgorithm.findMST(g);
        MSTResult pr = PrimAlgorithm.findMST(g);

        assertTrue(kr.getExecutionTime() >= 0, "Kruskal execution time should be non-negative");
        assertTrue(pr.getExecutionTime() >= 0, "Prim execution time should be non-negative");

        assertTrue(kr.getTotalWeight() >= 0, "Kruskal total weight should be non-negative");
        assertTrue(pr.getTotalWeight() >= 0, "Prim total weight should be non-negative");
    }
}
