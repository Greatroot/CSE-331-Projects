package graph.junitTests;

import graph.Graph;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    Graph emptyGraph, oneNodeGraph, threeNodesOneEdgeGraph, fourNodesGraph, threeNodesThreeEdgesGraph;
    List<String> edges;
    Set<String> nodes;

    @Before
    public void setUp()
    {
        nodes = new HashSet<String>();
        edges = new ArrayList<String>();

        emptyGraph = new Graph();

        oneNodeGraph = new Graph("n1");

        threeNodesOneEdgeGraph = new Graph("n1");
        threeNodesOneEdgeGraph.addNode("n2");
        threeNodesOneEdgeGraph.addNode("n3");
        threeNodesOneEdgeGraph.addEdge("e1", "n1", "n2");

        fourNodesGraph = new Graph("n1");
        fourNodesGraph.addNode("n2");
        fourNodesGraph.addNode("n3");
        fourNodesGraph.addNode("n4");

        threeNodesThreeEdgesGraph = new Graph("n1");
        threeNodesThreeEdgesGraph.addNode("n2");
        threeNodesThreeEdgesGraph.addNode("n3");
        threeNodesThreeEdgesGraph.addEdge("e1", "n1", "n2");
        threeNodesThreeEdgesGraph.addEdge("e2", "n1", "n2");
        threeNodesThreeEdgesGraph.addEdge("e3", "n1", "n3");
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

    @Test
    public void testConstructingGraphWithOneNode()
    {
        assertTrue("failure - graph doesn't have one Node.", oneNodeGraph.getAllNodes().size() == 1);
        assertTrue("failure - graph's node isn not empty stringed.", oneNodeGraph.getAllNodes().get(0).equals("n1"));
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  isAdjacent()
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testParentChildNodeIsAdjacent()
    {
        assertTrue("failure - neighbors should be adjacent", threeNodesOneEdgeGraph.isAdjacent("n1", "n2"));
        assertFalse("failure - The roles of two neighbors have been incorrectly reversed",
                threeNodesOneEdgeGraph.isAdjacent("n2", "n1"));
        assertTrue("failure - these nodes are not neighbors", !threeNodesOneEdgeGraph.isAdjacent("n3", "n4"));
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  addEdge()
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testAddingEdgeWithParentDNE() // should return false and not add the edge
    {
        assertFalse("failure - expected false but returned " + true,
                oneNodeGraph.addEdge("e1", "DNE", "n1"));
        assertTrue("failure - added an invalid edge", oneNodeGraph.getAllEdges().isEmpty());
    }

    @Test
    public void testAddingEdgeWithChildDNE()
    {
        assertFalse("failure - expected false but returned " + true,
                oneNodeGraph.addEdge("e1", "n1", "DNE"));
        assertTrue("failure - added an invalid edge", oneNodeGraph.getAllEdges().isEmpty());
    }

    @Test
    public void testAddingEdgeToEmptyGraph()
    {
        assertFalse("failure - expected false but returned " + true,
                emptyGraph.addEdge("e1", "n1DNE", "n2DNE"));
        assertTrue("failure - added an invalid edge", oneNodeGraph.getAllEdges().isEmpty());
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  containsNode()
    ///////////////////////////////////////////////////////////////////////////////////////

//    @Test
//    public void testGettingNodeDNE() // Test getting a node that DNE within the graph | should return null
//    {
//        assertNull("failure - expected a null", oneNodeGraph.getNode("DNE"));
//    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  setNode()
    ///////////////////////////////////////////////////////////////////////////////////////

//    @Test // Test setting a node that exists to a new value | should change the node's value to new value.
//    public void testSettingNodeToNewValue()
//    {
//        String failMessage = "failure - setNode() did not correctly change value";
//        oneNodeGraph.setNode("n1", "newValue");
//        assertEquals(failMessage, "newValue", oneNodeGraph.getNode("newValue"));
//    }
//
//    @Test // Test setting a node that exists to a new value | should change the node's value to new value.
//    public void testSettingMultipleNodesNoEdgesToNewValue()
//    {
//        String failMessage = "failure - setNode() did not correctly change multiple values";
//        fourNodesGraph.setNode("n1", "newValue1");
//        fourNodesGraph.setNode("n4", "newValue4");
//        fourNodesGraph.setNode("n2", "newValue2");
//        assertEquals(failMessage, "newValue1", fourNodesGraph.getNode("newValue1"));
//        assertEquals(failMessage, "newValue4", fourNodesGraph.getNode("newValue4"));
//        assertEquals(failMessage, "newValue2", fourNodesGraph.getNode("newValue2"));
//    }
//
//    @Test
//    public void testSettingNodeToNewValueInMultipleGraphs()
//    {
//        String failMessage = "failure - setNode() did not work with multiple graphs.";
//        oneNodeGraph.setNode("n1", "newValue1");
//        fourNodesGraph.setNode("n3", "newValue3");
//        assertEquals(failMessage, "newValue1", oneNodeGraph.getNode("newValue1"));
//        assertEquals(failMessage, "newValue4", fourNodesGraph.getNode("newValue4"));
//    }
//
//    @Test
//    public void testSettingNodeToNewValueWithEdge()
//    {
//        String failMessage = "failure - setNode() did not work with an Edge in the graph.";
//        threeNodesOneEdgeGraph.setNode("n1", "newValue1");
//        assertEquals(failMessage, "newValue1", threeNodesOneEdgeGraph.getNode("newValue1"));
//    }
//
//    @Test
//    public void testSettingMultipleNodesWithEdge()
//    {
//        String failMessage = "failure - setNode() did not correctly change multiple values with edge";
//        threeNodesOneEdgeGraph.setNode("n1", "newValue1");
//        threeNodesOneEdgeGraph.setNode("n3", "newValue3");
//        threeNodesOneEdgeGraph.setNode("n2", "newValue2");
//        assertEquals(failMessage, "newValue1", threeNodesOneEdgeGraph.getNode("newValue1"));
//        assertEquals(failMessage, "newValue3", threeNodesOneEdgeGraph.getNode("newValue3"));
//        assertEquals(failMessage, "newValue2", threeNodesOneEdgeGraph.getNode("newValue2"));
//    }
//
//    @Test
//    public void testSettingNodeThatDNE()
//    {
//        String failMessage = "failure - setNode() didn't create a new node from one that DNE.";
//        oneNodeGraph.setNode("n2", "newValue2");
//        assertEquals(failMessage, "newValue2", oneNodeGraph.getNode("newValue2"));
//    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  getEdge()
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test //Have no edges between the two nodes call getEdge() | should return an empty list.
    public void testNoEdgesBetweenTwoNodes()
    {
        String failMessage = "failure - getEdge() did not return an empty list of edges.";
        assertEquals(failMessage, new ArrayList<>(), fourNodesGraph.getEdge("n1", "n2"));
    }

    @Test
    public void testOneEdgeBetweenTwoNodes()
    {
        edges.add("e1");
        String failMessage = "failure - getEdge() did not return the one edge.";
        assertEquals(failMessage, edges, threeNodesOneEdgeGraph.getEdge("n1", "n2"));
    }

    @Test
    public void testMultipleEdgesBetweenTwoNodes()
    {
        edges.add("e1");
        edges.add("e2");
        String failMessage = "failure - getEdge() did not return the two edges.";
        assertEquals(failMessage, edges, threeNodesThreeEdgesGraph.getEdge("n1", "n2"));
    }
}