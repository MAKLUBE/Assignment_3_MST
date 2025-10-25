package mst;

import java.util.List;

public class MSTResult {
    public String algorithm;
    public List<Edge> edges;
    public int totalCost;
    public int operations;
    public double timeMs;

    public MSTResult(String algorithm, List<Edge> edges, int totalCost, int operations, double timeMs) {
        this.algorithm = algorithm;
        this.edges = edges;
        this.totalCost = totalCost;
        this.operations = operations;
        this.timeMs = timeMs;
    }
}
