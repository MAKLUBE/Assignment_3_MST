package mst;

public class Edge implements Comparable<Edge> {
    public String source;
    public String destination;
    public int weight;

    public Edge(String source, String destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return source + " - " + destination + " : " + weight;
    }
}

