package mst;

import java.util.*;

public class KruskalAlgorithm {

    static class UnionFind {
        private Map<String, String> parent = new HashMap<>();

        public void makeSet(String vertex) {
            parent.put(vertex, vertex);
        }

        public String find(String vertex) {
            if (!parent.get(vertex).equals(vertex)) {
                parent.put(vertex, find(parent.get(vertex)));
            }
            return parent.get(vertex);
        }

        public void union(String v1, String v2) {
            String root1 = find(v1);
            String root2 = find(v2);
            if (!root1.equals(root2))
                parent.put(root1, root2);
        }
    }

    public static MSTResult run(Graph graph) {
        long start = System.nanoTime();
        List<Edge> mstEdges = new ArrayList<>();
        int operations = 0;
        int totalCost = 0;

        UnionFind uf = new UnionFind();
        for (String v : graph.vertices) uf.makeSet(v);

        List<Edge> sortedEdges = new ArrayList<>(graph.edges);
        Collections.sort(sortedEdges);

        for (Edge e : sortedEdges) {
            operations++;
            String root1 = uf.find(e.source);
            String root2 = uf.find(e.destination);
            if (!root1.equals(root2)) {
                mstEdges.add(e);
                totalCost += e.weight;
                uf.union(e.source, e.destination);
            }
            if (mstEdges.size() == graph.getVertexCount() - 1) break;
        }

        long end = System.nanoTime();
        return new MSTResult("Kruskal", mstEdges, totalCost, operations, (end - start) / 1_000_000.0);
    }
}
