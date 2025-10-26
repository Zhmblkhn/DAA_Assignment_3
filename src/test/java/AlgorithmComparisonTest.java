import algorithms.KruskalAlgorithm;
import algorithms.PrimAlgorithm;
import model.Graph;
import model.MSTResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmComparisonTest {

    @Test
    public void testMSTAlgorithmsGiveSameCost() {
        Graph g = TestUtils.createSampleGraph();

        MSTResult kr = KruskalAlgorithm.findMST(g);
        MSTResult pr = PrimAlgorithm.findMST(g);

        assertNotNull(kr, "Kruskal result should not be null");
        assertNotNull(pr, "Prim result should not be null");

        assertEquals(kr.getTotalWeight(), pr.getTotalWeight(),
                "Kruskal and Prim must produce equal total MST weight");
    }
}
