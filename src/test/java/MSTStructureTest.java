import algorithms.KruskalAlgorithm;
import algorithms.PrimAlgorithm;
import model.Edge;
import model.Graph;
import model.MSTResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MSTStructureTest {

    @Test
    public void testMSTHasVminusOneEdges_Kruskal() {
        Graph g = TestUtils.createSampleGraph();
        MSTResult kr = KruskalAlgorithm.findMST(g);
        assertEquals(g.getVertices() - 1, kr.getEdges().size(),
                "Kruskal MST should have V-1 edges");
    }

    @Test
    public void testMSTHasVminusOneEdges_Prim() {
        Graph g = TestUtils.createSampleGraph();
        MSTResult pr = PrimAlgorithm.findMST(g);
        assertEquals(g.getVertices() - 1, pr.getEdges().size(),
                "Prim MST should have V-1 edges");
    }

    @Test
    public void testMSTAcyclic_Kruskal() {
        Graph g = TestUtils.createSampleGraph();
        MSTResult kr = KruskalAlgorithm.findMST(g);
        assertFalse(hasCycle(kr.getEdges(), g.getVertices()), "Kruskal MST must be acyclic");
    }

    @Test
    public void testMSTAcyclic_Prim() {
        Graph g = TestUtils.createSampleGraph();
        MSTResult pr = PrimAlgorithm.findMST(g);
        assertFalse(hasCycle(pr.getEdges(), g.getVertices()), "Prim MST must be acyclic");
    }

    private boolean hasCycle(List<Edge> edges, int vertexCount) {
        int[] parent = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++) parent[i] = i;

        for (Edge e : edges) {
            int u = find(parent, e.src);
            int v = find(parent, e.dest);
            if (u == v) return true;
            parent[u] = v;
        }
        return false;
    }

    private int find(int[] parent, int x) {
        while (parent[x] != x) x = parent[x];
        return x;
    }
}
