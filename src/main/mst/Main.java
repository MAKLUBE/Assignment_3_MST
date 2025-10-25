package mst;

import com.google.gson.*;
import java.io.FileWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Graph> graphs = JsonReader.loadGraphs("src/main/resources/input.json");

        JsonArray results = new JsonArray();

        for (Graph g : graphs) {
            MSTResult prim = PrimAlgorithm.run(g);
            MSTResult kruskal = KruskalAlgorithm.run(g);

            JsonObject graphResult = new JsonObject();
            graphResult.addProperty("graph_id", g.id);

            JsonObject inputStats = new JsonObject();
            inputStats.addProperty("vertices", g.getVertexCount());
            inputStats.addProperty("edges", g.getEdgeCount());
            graphResult.add("input_stats", inputStats);

            graphResult.add("prim", toJsonResult(prim));
            graphResult.add("kruskal", toJsonResult(kruskal));

            results.add(graphResult);
        }

        JsonObject outputRoot = new JsonObject();
        outputRoot.add("results", results);

        try (FileWriter writer = new FileWriter("src/main/resources/output.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(outputRoot, writer);
        }

        System.out.println("Results are in output.json");

        try (FileWriter csvWriter = new FileWriter("src/main/resources/results.csv")) {
            csvWriter.write("Graph ID,Vertices,Edges,Prim Cost,Prim Time (ms),Kruskal Cost,Kruskal Time (ms),Prim Ops,Kruskal Ops\n");

            for (Graph g : graphs) {
                MSTResult prim = PrimAlgorithm.run(g);
                MSTResult kruskal = KruskalAlgorithm.run(g);

                csvWriter.write(String.format(
                        "%d,%d,%d,%d,%.2f,%d,%.2f,%d,%d\n",
                        g.id, g.getVertexCount(), g.getEdgeCount(),
                        prim.totalCost, prim.timeMs,
                        kruskal.totalCost, kruskal.timeMs,
                        prim.operations, kruskal.operations
                ));
            }
        }

        System.out.println("Results are in results.csv");
    }

    private static JsonObject toJsonResult(MSTResult result) {
        JsonObject obj = new JsonObject();

        JsonArray edgesArray = new JsonArray();
        for (Edge e : result.edges) {
            JsonObject edgeObj = new JsonObject();
            edgeObj.addProperty("from", e.source);
            edgeObj.addProperty("to", e.destination);
            edgeObj.addProperty("weight", e.weight);
            edgesArray.add(edgeObj);
        }
        obj.add("mst_edges", edgesArray);
        obj.addProperty("total_cost", result.totalCost);
        obj.addProperty("operations_count", result.operations);
        obj.addProperty("execution_time_ms", Math.round(result.timeMs * 100.0) / 100.0);
        return obj;
    }


}


