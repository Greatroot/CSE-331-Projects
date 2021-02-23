/*
 * Copyright (C) 2021 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder.scriptTestRunner;

import graph.EdgeStringCompare;
import graph.Graph;
import marvel.MarvelPaths;
import pathfinder.FindPath;
import pathfinder.datastructures.Path;
import pathfinder.textInterface.Pathfinder;

import java.io.*;
import java.util.*;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver {

    // *********************************
    // ***  Interactive Test Driver  ***
    // *********************************

    public static void main(String[] args) {
        try {
            if (args.length > 1) {
                printUsage();
                return;
            }

            PathfinderTestDriver td;

            if (args.length == 0) {
                td = new PathfinderTestDriver(new InputStreamReader(System.in), new OutputStreamWriter(System.out));
                System.out.println("Running in interactive mode.");
                System.out.println("Type a line in the script testing language to see the output.");
            } else {
                String fileName = args[0];
                File tests = new File(fileName);

                System.out.println("Reading from the provided file.");
                System.out.println("Writing the output from running those tests to standard out.");
                if (tests.exists() || tests.canRead()) {
                    td = new PathfinderTestDriver(new FileReader(tests), new OutputStreamWriter(System.out));
                } else {
                    System.err.println("Cannot read from " + tests.toString());
                    printUsage();
                    return;
                }
            }

            td.runTests();

        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        }
    }

    private static void printUsage() {
        System.err.println("Usage:");
        System.err.println("  Run the gradle 'build' task");
        System.err.println("  Open a terminal at hw-graph/build/classes/java/test");
        System.err.println("  To read from a file: java graph.scriptTestRunner.GraphTestDriver <name of input script>");
        System.err.println("  To read from standard in (interactive): java graph.scriptTestRunner.GraphTestDriver");
    }

    // ***************************
    // ***  JUnit Test Driver  ***
    // ***************************

    /**
     * String -> Graph: maps the names of graphs to the actual graph
     **/
    // TODO: Change Graph type to MarvelPaths type.
    // TODO: MarvelPaths should give us the funcitonality to write the LoadGraph and FindPath tests,
    // and so this test driver still stores and uses Graphs, not MarvelPaths.
    private final Map<String, Graph<String, Double>> graphs = new HashMap<String, Graph<String, Double>>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @spec.requires r != null && w != null
     * @spec.effects Creates a new PathfinderTestDriver which reads command from
     * {@code r} and writes results to {@code w}
     **/
    // Leave this constructor public
    public PathfinderTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @spec.effects Executes the commands read from the input and writes results to the output
     **/
    // Leave this method public
    public void runTests()
            throws IOException {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch (command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch (Exception e) {
            output.println("Exception: " + e.toString());
        }
    }

    private void createGraph(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {

        Graph<String, Double> graph = new Graph<String, Double>();
        graphs.put(graphName, graph);
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {

        Graph<String, Double> graph = graphs.get(graphName);
        graph.addNode(nodeName);
        if(graph.containsNode(nodeName))
        {
            output.println("added node " + nodeName + " to " + graphName);
        } else
        {
            output.println("added node notCorrectNode to " + graphName);
        }
    }

    private void addEdge(List<String> arguments) {
        if (arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, Double.parseDouble(edgeLabel));
    }

    private void addEdge(String graphName, String parentName, String childName,
                         Double edgeLabel) {

        Graph<String, Double> graph = graphs.get(graphName);
        graph.addEdge(edgeLabel, parentName, childName);
        if(graph.containsEdge(edgeLabel, parentName, childName))
        {
            output.println("added edge " + String.format("%.3f", edgeLabel) + " from " + parentName + " to "
                    + childName + " in " + graphName);
        } else
        {
            output.println("The edge " + String.format("%.3f", edgeLabel) + " from " + parentName + " to "
                    + childName + " in " + graphName + " was not added");
        }
    }

    private void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {

        Graph<String, Double> graph = graphs.get(graphName);
        List<String> nodes = graph.getAllNodes();
        Collections.sort(nodes);

        StringBuilder result = new StringBuilder(graphName + " contains:");
        for(String node : nodes)
        {
            result.append(" ").append(node);
        }
        output.println(result);
    }

    private void listChildren(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {

        Graph<String, Double> graph = graphs.get(graphName);
        List<Graph.Edge<Double, String>> childrenEdges = graph.getChildrenEdges(parentName);
        childrenEdges.sort(new EdgeDoubleCompare());

        StringBuilder result = new StringBuilder("the children of " + parentName + " in " + graphName + " are:");
        for(Graph.Edge<Double, String> childEdge : childrenEdges)
        {
            result.append(" ").append(childEdge.getChild()).append("(")

                    .append(String.format("%.3f", childEdge.getLabel()))
                    .append(")");
        }
        output.println(result);
    }

    private void findPath(List<String> arguments)
    {
        if(arguments.size() != 3)
        {
            throw new CommandException("Bad arguments to findPath: " + arguments);
        }

        String graphName = arguments.get(0);
        String node_a = arguments.get(1);
        String node_b = arguments.get(2);
        findPath(graphName, node_a, node_b);
    }

    // If there are two minimum-cost paths between node_a and node_b, it is undefined which one
    // is printed.
    private void findPath(String graphName, String node_a, String node_b)
    {
        Graph<String, Double> graph = this.graphs.get(graphName);
        node_a = node_a.replaceAll("_", " ");
        node_b = node_b.replaceAll("_", " ");

        if(!graph.containsNode(node_a)) // If node_a isn't within the graph.
        {
            output.println("unknown node " + node_a);
        }
        if(!graph.containsNode(node_b)) // If node_b isn't within the graph.
        {
            output.println("unknown node " + node_b);
        }

        if(graph.containsNode(node_a) && graph.containsNode(node_b)) // If both nodes passed are within the graph.
        {
            System.out.println("Nodes: ");
            System.out.println(graph.getAllNodes());

            Path<String> path = FindPath.findShortestPath(graph, node_a, node_b);

            output.println("path from " + node_a + " to " + node_b + ":");
            if(path == null) // If there was no path found.
            {
                output.println("no path found");
            } else { // A path was found
                for (Path<String>.Segment segment : path) {
                    output.println(segment.getStart() + " to " + segment.getEnd() + " with weight "
                            + String.format("%.3f", segment.getCost()));
                }
                output.println("total cost: " + String.format("%.3f", path.getCost()));
            }
        }
    }


    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }

    //TODO: Perhaps I should make one version of EdgeCompare that is generic?
    /** Comparator that compares Edges by their child nodes first and then their edge labels second.
     */
    public static class EdgeDoubleCompare implements Comparator<Graph.Edge<Double, String>> {

        /**
         * Compares two Edges lexicographically by their children nodes first and then alphabetically
         * by their edge labels as a "tie breaker" if the children nodes are the same.
         *
         * @param e1 the first Edge that is being compared
         * @param e2 the second Edge being compared.
         * @return a negative int if e1 lexicographically/alphabetically precedes the Edge e2, a positive
         * int if e1 lexicographically/alphabetically comes after e2, and 0 if both are defined by
         * Graph.Edge.equals() to be equal.
         */
        public int compare(Graph.Edge<Double, String> e1, Graph.Edge<Double, String> e2)
        {
            int result = e1.getChild().compareTo(e2.getChild());
            if(result == 0)
            {
                result = e1.getLabel().compareTo(e2.getLabel());
            }

            return result;
        }
    }
}
