package algorithms;

import model.Edge;
import model.Graph;
import model.MSTResult;

import java.util.*;

public class PrimAlgorithm {

    public static MSTResult findMST(Graph graph) {
        long start = System.nanoTime();

        int V = graph.getVertices();
        boolean[] visited = new boolean[V];
        List<Edge> mst = new ArrayList<>();
        int totalWeight = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        visited[0] = true;

        for (Edge e : graph.getEdges()) {
            if (e.src == 0 || e.dest == 0) pq.offer(e);
        }

        while (!pq.isEmpty() && mst.size() < V - 1) {
            Edge edge = pq.poll();
            int next = visited[edge.src] ? edge.dest : edge.src;

            if (!visited[next]) {
                visited[next] = true;
                mst.add(edge);
                totalWeight += edge.weight;

                for (Edge e : graph.getEdges()) {
                    if ((e.src == next && !visited[e.dest]) || (e.dest == next && !visited[e.src])) {
                        pq.offer(e);
                    }
                }
            }
        }

        long end = System.nanoTime();
        return new MSTResult("Prim", mst, totalWeight, end - start);
    }
}
