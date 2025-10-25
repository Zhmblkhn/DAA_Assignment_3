import model.*;
import algorithms.*;
import io.JsonIO;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String inputPath = "src/main/resources/data/input.json";
        String outputKruskal = "src/main/resources/data/output_kruskal.json";
        String outputPrim = "src/main/resources/data/output_prim.json";

        Map<String, Graph> graphs = JsonIO.readGraphs(inputPath);
        Map<String, MSTResult> kruskalResults = new HashMap<>();
        Map<String, MSTResult> primResults = new HashMap<>();

        for (Map.Entry<String, Graph> entry : graphs.entrySet()) {
            String id = entry.getKey();
            Graph g = entry.getValue();

            kruskalResults.put(id, KruskalAlgorithm.findMST(g));
            primResults.put(id, PrimAlgorithm.findMST(g));
        }

        JsonIO.writeResults(outputKruskal, kruskalResults);
        JsonIO.writeResults(outputPrim, primResults);

        System.out.println("Results written to output_kruskal.json and output_prim.json");
    }
}
