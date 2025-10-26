package visualizer;

import model.Edge;
import model.Graph;
import model.MSTResult;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Random;

public class GraphVisualizer extends JPanel {
    private Graph graph;
    private MSTResult mstResult;
    private static final int NODE_SIZE = 18;

    public GraphVisualizer(Graph graph, MSTResult mstResult) {
        this.graph = graph;
        this.mstResult = mstResult;
        setPreferredSize(new Dimension(600, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        List<Edge> edges = graph.getEdges();
        Set<String> mstEdges = new HashSet<>();

        for (Edge e : mstResult.getEdges()) {
            mstEdges.add(e.src + "-" + e.dest);
            mstEdges.add(e.dest + "-" + e.src);
        }

        int n = graph.getVertices();
        Point[] positions = new Point[n];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            int x = rand.nextInt(getWidth() - 100) + 50;
            int y = rand.nextInt(getHeight() - 100) + 50;
            positions[i] = new Point(x, y);
        }

        for (Edge e : edges) {
            Point p1 = positions[e.src];
            Point p2 = positions[e.dest];
            g.setColor(mstEdges.contains(e.src + "-" + e.dest) ? Color.RED : Color.GRAY);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        g.setColor(Color.BLACK);
        for (int i = 0; i < n; i++) {
            Point p = positions[i];
            g.fillOval(p.x - NODE_SIZE / 2, p.y - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);
            g.drawString(String.valueOf(i), p.x - 4, p.y - 10);
        }
    }

    public static void visualize(Graph graph, MSTResult mstResult, String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new GraphVisualizer(graph, mstResult));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
