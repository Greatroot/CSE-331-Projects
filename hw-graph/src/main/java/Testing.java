import graph.Graph;

import java.util.Collections;
import java.util.List;

public class Testing {
    public static void main(String[] args) {
        String graphName = "someGraph";
        Graph graph = new Graph();
        graph.addNode("n3");
        graph.addNode("n1");
        graph.addNode("n2");
        List<String> nodes = graph.getAllNodes();
        Collections.sort(nodes);
        StringBuilder nodesResult = new StringBuilder(graphName + " contains:");
        for(String node : nodes)
        {
            nodesResult.append(" ").append(node);
        }
        System.out.println(nodesResult);
    }
}
