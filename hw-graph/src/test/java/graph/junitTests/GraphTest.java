package graph.junitTests;

import graph.Graph;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.w3c.dom.Node;

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class contains a set of test cases that can be used to test the implementation of the
 * Graph class.
 *
 * <p>
 */
public final class GraphTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested.

    Graph emptyGraph, emptyStringedGraph, oneNodeGraph, twoNodeOneEdgeGraph;

    @Before
    public void setUp()
    {
        emptyGraph = new Graph();

        emptyStringedGraph = new Graph("");

        oneNodeGraph = new Graph("someValue");

        twoNodeOneEdgeGraph = new Graph("n1");
        twoNodeOneEdgeGraph.addNode("n2");
        twoNodeOneEdgeGraph.addEdge();

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Constructor
    ///////////////////////////////////////////////////////////////////////////////////////

    //  Test to see if building an empty graph results in no nodes and no edges.
    @Test
    public void testEmptyGraph()
    {
        assertTrue("failure - graph is not empty, this graph has nodes.",emptyGraph.getAllNodes().isEmpty());
        assertTrue("failure - graph is not empty, this graph has edges.",emptyGraph.getAllEdges().isEmpty());
    }

    @Test void testGraphWithEmptyStringedNode()
    {
        assertTrue("failure - graph doesn't have one Node.", emptyStringedGraph.getAllEdges().size() == 1);
        assertTrue("failure - graph's node isn not empty stringed.", emptyStringedGraph.getAllNodes().get(0).getValue().equals(""));
    }

    @Test void testConstructingGraphWithOneNode()
    {
        assertTrue("failure - graph doesn't have one Node.", oneNodeGraph.getAllEdges().size() == 1);
        assertTrue("failure - graph's node isn not empty stringed.", oneNodeGraph.getAllNodes().get(0).getValue().equals("someValue"));
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  isAdjacent()
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testAreTwoNodesAdjacent()
    {

    }
}