package model;

import java.util.List;

public class MSTResult {
    private String algorithm;
    private List<Edge> edges;
    private int totalWeight;
    private long executionTime;
    private long operationCount; // 🔹 новое поле

    public MSTResult(String algorithm, List<Edge> edges, int totalWeight, long executionTime, long operationCount) {
        this.algorithm = algorithm;
        this.edges = edges;
        this.totalWeight = totalWeight;
        this.executionTime = executionTime;
        this.operationCount = operationCount;
    }

    public String getAlgorithm() { return algorithm; }
    public List<Edge> getEdges() { return edges; }
    public int getTotalWeight() { return totalWeight; }
    public long getExecutionTime() { return executionTime; }
    public long getOperationCount() { return operationCount; }
}
