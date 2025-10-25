package mst;

import java.util.List;

public class Graph {
    public int id;
    public List<String> vertices;
    public List<Edge> edges;

    public Graph(List<String> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getEdgeCount() {
        return edges.size();
    }
}
