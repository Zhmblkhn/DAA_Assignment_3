import model.*;
import algorithms.*;
import io.JsonIO;

import java.util.*;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        String[] inputFiles = {
                "src/main/resources/input_small.json",
                "src/main/resources/input_medium.json",
                "src/main/resources/input_large.json",
                "src/main/resources/input_extralarge.json"
        };

        String outputCSV = "src/main/resources/output_table.csv";
        List<String[]> results = new ArrayList<>();
        results.add(new String[]{"File", "GraphID", "Vertices", "Kruskal(ms)", "Prim(ms)", "MST Cost"});

        for (String inputPath : inputFiles) {
            Map<String, Graph> graphs = new TreeMap<>(
                    Comparator.comparingInt(key -> Integer.parseInt(key)));
            graphs.putAll(JsonIO.readGraphs(inputPath));

            for (Map.Entry<String, Graph> entry : graphs.entrySet()) {
                String id = entry.getKey();
                Graph g = entry.getValue();

                MSTResult kruskal = KruskalAlgorithm.findMST(g);
                MSTResult prim = PrimAlgorithm.findMST(g);

                results.add(new String[]{
                        inputPath.substring(inputPath.lastIndexOf("/") + 1),
                        id,
                        String.valueOf(g.getVertices()),
                        String.valueOf(kruskal.getExecutionTime() / 1_000_000.0),
                        String.valueOf(prim.getExecutionTime() / 1_000_000.0),
                        String.valueOf(kruskal.getTotalWeight())
                });
            }
        }

        try (FileWriter writer = new FileWriter(outputCSV)) {
            for (String[] row : results) {
                writer.write(String.join(",", row) + "\n");
            }
            writer.flush();
            System.out.println("Results saved to " + outputCSV);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}