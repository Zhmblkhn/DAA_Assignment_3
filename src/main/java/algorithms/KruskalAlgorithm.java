package algorithms;

import model.Edge;
import model.Graph;
import model.MSTResult;

import java.util.*;

public class KruskalAlgorithm {

    private static class UnionFind {
        int[] parent, rank;
        long operations = 0;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
            operations += n; // инициализация
        }

        int find(int x) {
            operations++;
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        void union(int x, int y) {
            operations++;
            int rootX = find(x), rootY = find(y);
            if (rootX != rootY) {
                if (rank[rootX] < rank[rootY]) parent[rootX] = rootY;
                else if (rank[rootX] > rank[rootY]) parent[rootY] = rootX;
                else { parent[rootY] = rootX; rank[rootX]++; }
            }
        }

        long getOperations() {
            return operations;
        }
    }

    public static MSTResult findMST(Graph graph) {
        long start = System.nanoTime();
        long operationCount = 0;

        List<Edge> edges = new ArrayList<>(graph.getEdges());
        Collections.sort(edges);
        operationCount += edges.size(); // сортировка — n лог n сравнений примерно

        UnionFind uf = new UnionFind(graph.getVertices());
        List<Edge> mst = new ArrayList<>();
        int totalWeight = 0;

        for (Edge edge : edges) {
            operationCount++;
            int root1 = uf.find(edge.src);
            int root2 = uf.find(edge.dest);
            if (root1 != root2) {
                mst.add(edge);
                totalWeight += edge.weight;
                uf.union(root1, root2);
                operationCount += 3;
            }
        }

        operationCount += uf.getOperations();
        long end = System.nanoTime();

        return new MSTResult("Kruskal", mst, totalWeight, end - start, operationCount);
    }
}
