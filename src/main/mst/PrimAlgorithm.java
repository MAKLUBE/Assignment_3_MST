package mst;

import java.util.*;

public class PrimAlgorithm {

    public static MSTResult run(Graph graph) {
        long start = System.nanoTime();
        Set<String> visited = new HashSet<>();
        List<Edge> mstEdges = new ArrayList<>();
        int operations = 0;
        int totalCost = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        String startVertex = graph.vertices.get(0);
        visited.add(startVertex);

        // add all edges from the start vertex
        for (Edge e : graph.edges) {
            if (e.source.equals(startVertex) || e.destination.equals(startVertex)) {
                pq.add(e);
                operations++;
            }
        }

        while (!pq.isEmpty() && mstEdges.size() < graph.getVertexCount() - 1) {
            Edge edge = pq.poll();
            operations++;

            String next = visited.contains(edge.source) ? edge.destination : edge.source;

            if (visited.contains(next)) continue;

            visited.add(next);
            mstEdges.add(edge);
            totalCost += edge.weight;

            for (Edge e : graph.edges) {
                if (visited.contains(e.source) && !visited.contains(e.destination)
                        || visited.contains(e.destination) && !visited.contains(e.source)) {
                    pq.add(e);
                    operations++;
                }
            }
        }

        long end = System.nanoTime();
        return new MSTResult("Prim", mstEdges, totalCost, operations, (end - start) / 1_000_000.0);
    }
}
