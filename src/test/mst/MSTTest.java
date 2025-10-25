package mst;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class MSTTest {

    @Test
    public void testSameTotalCost() {
        Graph g = createSampleGraph();
        MSTResult prim = PrimAlgorithm.run(g);
        MSTResult kruskal = KruskalAlgorithm.run(g);
        assertEquals(prim.totalCost, kruskal.totalCost,
                "Total cost must be identical for both algorithms");
    }

    @Test
    public void testEdgeCountEqualsVMinusOne() {
        Graph g = createSampleGraph();
        MSTResult prim = PrimAlgorithm.run(g);
        assertEquals(g.getVertexCount() - 1, prim.edges.size(),
                "MST must have V-1 edges");
    }

    @Test
    public void testMSTAcyclic() {
        Graph g = createSampleGraph();
        MSTResult prim = PrimAlgorithm.run(g);
        assertTrue(isAcyclic(prim.edges),
                "MST must be acyclic (no cycles)");
    }

    @Test
    public void testMSTConnected() {
        Graph g = createSampleGraph();
        MSTResult prim = PrimAlgorithm.run(g);
        assertTrue(isConnected(g.vertices, prim.edges),
                "MST must connect all vertices");
    }

    @Test
    public void testDisconnectedGraphHandled() {
        List<String> vertices = Arrays.asList("A", "B", "C");
        List<Edge> edges = Arrays.asList(new Edge("A", "B", 1)); // 'C' disconnected
        Graph g = new Graph(vertices, edges);

        MSTResult prim = PrimAlgorithm.run(g);
        assertTrue(prim.edges.size() <= g.getVertexCount() - 1,
                "Algorithm should handle disconnected graphs gracefully");
    }

    @Test
    public void testExecutionTimeNonNegative() {
        Graph g = createSampleGraph();
        MSTResult prim = PrimAlgorithm.run(g);
        MSTResult kruskal = KruskalAlgorithm.run(g);

        assertTrue(prim.timeMs >= 0, "Execution time must be non-negative");
        assertTrue(kruskal.timeMs >= 0, "Execution time must be non-negative");
    }

    @Test
    public void testOperationCountPositive() {
        Graph g = createSampleGraph();
        MSTResult prim = PrimAlgorithm.run(g);
        MSTResult kruskal = KruskalAlgorithm.run(g);

        assertTrue(prim.operations >= 0, "Operation count must be non-negative");
        assertTrue(kruskal.operations >= 0, "Operation count must be non-negative");
    }

    @Test
    public void testResultsReproducible() {
        Graph g = createSampleGraph();
        MSTResult firstRun = PrimAlgorithm.run(g);
        MSTResult secondRun = PrimAlgorithm.run(g);
        assertEquals(firstRun.totalCost, secondRun.totalCost,
                "Results must be reproducible for the same dataset");
    }


    private Graph createSampleGraph() {
        List<String> vertices = Arrays.asList("A", "B", "C", "D", "E");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 4),
                new Edge("A", "C", 3),
                new Edge("B", "C", 2),
                new Edge("B", "D", 5),
                new Edge("C", "D", 7),
                new Edge("C", "E", 8),
                new Edge("D", "E", 6)
        );
        return new Graph(vertices, edges);
    }

    private boolean isAcyclic(List<Edge> edges) {
        Map<String, String> parent = new HashMap<>();
        for (Edge e : edges) {
            String a = e.source, b = e.destination;
            String pa = find(a, parent);
            String pb = find(b, parent);
            if (pa.equals(pb)) return false; // cycle found
            parent.put(pa, pb);
        }
        return true;
    }

    private boolean isConnected(List<String> vertices, List<Edge> edges) {
        Map<String, String> parent = new HashMap<>();
        for (String v : vertices) parent.put(v, v);
        for (Edge e : edges) {
            String pa = find(e.source, parent);
            String pb = find(e.destination, parent);
            parent.put(pa, pb);
        }

        String root = find(vertices.get(0), parent);
        for (String v : vertices) {
            if (!find(v, parent).equals(root)) return false;
        }
        return true;
    }

    private String find(String x, Map<String, String> p) {
        if (!p.containsKey(x)) p.put(x, x);
        if (!p.get(x).equals(x)) p.put(x, find(p.get(x), p));
        return p.get(x);
    }
}
