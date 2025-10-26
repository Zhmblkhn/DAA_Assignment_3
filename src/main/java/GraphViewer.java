import model.*;
import algorithms.*;
import io.JsonIO;
import visualizer.GraphVisualizer;

import java.util.Map;
import java.util.Scanner;

public class GraphViewer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select the file you want to open:");
        System.out.println("1) input_small.json (id contains: 1-5)");
        System.out.println("2) input_medium.json (id contains: 6-15)");
        System.out.println("3) input_large.json (id contains: 16-25)");
        System.out.println("4) input_extralarge.json (id contains: 26-28)");
        System.out.print("Select (1–4): ");
        int choice = scanner.nextInt();

        String inputPath = switch (choice) {
            case 1 -> "src/main/resources/input_small.json";
            case 2 -> "src/main/resources/input_medium.json";
            case 3 -> "src/main/resources/input_large.json";
            case 4 -> "src/main/resources/input_extralarge.json";
            default -> {
                System.out.println("Wrong choice.");
                yield null;
            }
        };

        if (inputPath == null) return;

        System.out.print("Enter the graph ID to visualize: ");
        String graphId = scanner.next();

        Map<String, Graph> graphs = JsonIO.readGraphs(inputPath);
        Graph g = graphs.get(graphId);

        if (g == null) {
            System.out.println("Graph with ID " + graphId + " not found.");
            return;
        }

        MSTResult kruskal = KruskalAlgorithm.findMST(g);
        MSTResult prim = PrimAlgorithm.findMST(g);

        GraphVisualizer.visualize(g, kruskal, "Kruskal MST (Graph " + graphId + ")");
        GraphVisualizer.visualize(g, prim, "Prim MST (Graph " + graphId + ")");
    }
}
