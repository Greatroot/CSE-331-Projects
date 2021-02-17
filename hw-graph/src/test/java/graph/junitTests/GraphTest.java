package graph.junitTests;

import graph.Graph;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;
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

    private final String EXPECTED_TRUE = "failure - expected true but was false";
    private final String EXPECTED_FALSE = "failure - expected false but was true";


    Graph emptyGraph, oneNodeGraph, threeNodesOneEdgeGraph, fourNodesGraph, threeNodesThreeEdgesGraph,
            graphWithEmptyStringNode;
    List<String> edges;
    List<String> nodes;

    @Before
    public void setUp()
    {
        nodes = new ArrayList<String>();
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

        graphWithEmptyStringNode = new Graph("");
        graphWithEmptyStringNode.addNode("n1");
        graphWithEmptyStringNode.addNode("n2");
        graphWithEmptyStringNode.addEdge("e1", "", "n1");
        graphWithEmptyStringNode.addEdge("", "n2", "");
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Constructor
    ///////////////////////////////////////////////////////////////////////////////////////

    //  Test to see if building an empty graph results in no nodes and no edges.
    @Test
    public void testEmptyGraph()
    {
        assertTrue("failure - graph is not empty, this graph has nodes, nor edges" +
                ".",emptyGraph.getAllNodes().isEmpty());
        assertTrue("failure - graph is not empty, this graph has edges.",emptyGraph.getNumOfEdges() == 0);
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
        assertTrue("failure - added an invalid edge", oneNodeGraph.getNumOfEdges() == 0);
    }

    @Test
    public void testAddingEdgeWithChildDNE()
    {
        assertFalse("failure - expected false but returned " + true,
                oneNodeGraph.addEdge("e1", "n1", "DNE"));
        assertTrue("failure - added an invalid edge", oneNodeGraph.getNumOfEdges() == 0);
    }

    @Test
    public void testAddingEdgeToEmptyGraph()
    {
        assertFalse("failure - expected false but returned " + true,
                emptyGraph.addEdge("e1", "n1DNE", "n2DNE"));
        assertTrue("failure - added an invalid edge", oneNodeGraph.getNumOfEdges() == 0);
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    ////  getEdge()
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test //Have no edges between the two nodes and call getEdge() | should return an empty list.
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

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Test Adding Valid Input
    ///////////////////////////////////////////////////////////////////////////////////////

    // These are all paired with a script test to create a full suite.

    @Test
    public void testAddingEdgeBetweenOneNode()
    {
        assertTrue(EXPECTED_TRUE, fourNodesGraph.addEdge("e1", "n1", "n1"));
        assertTrue(EXPECTED_TRUE, fourNodesGraph.containsEdge("e1", "n1", "n1"));
    }

    @Test
    public void testAddingOneEdge()
    {
        assertTrue(EXPECTED_TRUE, fourNodesGraph.addEdge("e1", "n1", "n2"));
        assertTrue(EXPECTED_TRUE, fourNodesGraph.containsEdge("e1", "n1", "n2"));
    }

    @Test
    public void testAddingOneNode()
    {
        assertTrue(EXPECTED_TRUE, fourNodesGraph.addNode("n5"));
        assertTrue(EXPECTED_TRUE, fourNodesGraph.containsNode("n5"));
    }

    @Test
    public void testAddingThreeEdgesWithEndpointOverlap()
    {
        assertTrue(EXPECTED_TRUE, fourNodesGraph.addEdge("e1", "n1", "n2"));
        assertTrue(EXPECTED_TRUE, fourNodesGraph.addEdge("e2", "n1", "n3"));
        assertTrue(EXPECTED_TRUE, fourNodesGraph.addEdge("e3", "n3", "n4"));
    }

    @Test
    public void testAddingTwoEdges()
    {
        assertTrue(EXPECTED_TRUE, fourNodesGraph.addEdge("e1", "n1", "n2"));
        assertTrue(EXPECTED_TRUE, fourNodesGraph.addEdge("e2", "n3", "n4"));
    }

    @Test
    public void testAddingTwoEdgesOverlapAtParent()
    {
        assertTrue(EXPECTED_TRUE, fourNodesGraph.addEdge("e1", "n1", "n2"));
        assertTrue(EXPECTED_TRUE, fourNodesGraph.addEdge("e2", "n1", "n3"));
    }

    @Test
    public void testAddingTwoEdgesOverlapAtChild()
    {
        assertTrue(EXPECTED_TRUE, fourNodesGraph.addEdge("e1", "n1", "n2"));
        assertTrue(EXPECTED_TRUE, fourNodesGraph.addEdge("e2", "n2", "n3"));
    }

    @Test
    public void testAddingTwoNodes()
    {
        assertTrue(EXPECTED_TRUE, fourNodesGraph.addNode("n5"));
        assertTrue(EXPECTED_TRUE, fourNodesGraph.addNode("n6"));
    }

    @Test
    public void testAddingTwoOverlappingEdges()
    {
        assertTrue(EXPECTED_TRUE, fourNodesGraph.addEdge("e1", "n1", "n2"));
        assertTrue(EXPECTED_TRUE, fourNodesGraph.addEdge("e2", "n1", "n2"));
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Test For Proper IllegalArgumentException AND Test that nulls are properly being returned
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void testPassingNullToConstructor()
    {
        Graph illegalGraph = new Graph(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPassingNullParentToIsAdjacent()
    {
        threeNodesThreeEdgesGraph.isAdjacent(null, "n2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPassingNullChildToIsAdjacent()
    {
        threeNodesThreeEdgesGraph.isAdjacent("n1", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPassingNullToAddNode()
    {
        threeNodesThreeEdgesGraph.addNode(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPassingNullLabelToAddEdge()
    {
        threeNodesThreeEdgesGraph.addEdge(null, "n1", "n2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPassingNullParentToAddEdge()
    {
        threeNodesThreeEdgesGraph.addEdge("e1", null, "n2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPassingNullChildToAddEdge()
    {
        threeNodesThreeEdgesGraph.addEdge("e1", "n1", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPassingNullToContainsNode()
    {
        threeNodesThreeEdgesGraph.containsNode(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPassingNullLabelToContainsEdge()
    {
        threeNodesThreeEdgesGraph.containsEdge(null, "n1", "n2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPassingNullParentToContainsEdge()
    {
        threeNodesThreeEdgesGraph.containsEdge("e1", null, "n2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPassingNullChildToContainsEdge()
    {
        threeNodesThreeEdgesGraph.containsEdge("e1", "n1", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPassingNullParentToGetEdge()
    {
        threeNodesThreeEdgesGraph.getEdge(null, "n2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPassingNullChildToGetEdge()
    {
        threeNodesThreeEdgesGraph.getEdge("n1", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPassingNullToGetChildren()
    {
        threeNodesThreeEdgesGraph.getChildrenNodes(null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Test All Methods with Empty Graph AND Test that nulls are properly being returned
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testIsAdjacentWithEmptyGraph()
    {
        assertFalse(EXPECTED_FALSE, emptyGraph.isAdjacent("n1", "n2"));
    }

    @Test
    public void testAddNodeWithEmptyGraph()
    {
        assertTrue(EXPECTED_TRUE, emptyGraph.addNode("n1"));
        assertTrue(EXPECTED_TRUE, emptyGraph.containsNode("n1"));
    }

    @Test
    public void testAddEdgeWithEmptyGraph()
    {
        assertFalse(EXPECTED_FALSE, emptyGraph.addEdge("e1", "n1", "n2"));
        assertFalse(EXPECTED_FALSE, emptyGraph.containsEdge("e1", "n1", "n2"));
    }

    @Test
    public void testContainsNodeWithEmptyGraph()
    {
        assertFalse(EXPECTED_FALSE, emptyGraph.containsNode("n1"));
    }

    @Test
    public void testContainsEdgeWithEmptyGraph()
    {
        assertFalse(emptyGraph.containsEdge("e1", "n1", "n2"));
    }

    @Test
    public void testGetEdgeWithEmptyGraph()
    {
        String failMessage = "failure - expected null";
        assertNull(failMessage, emptyGraph.getEdge("n1", "n2"));
    }

    @Test
    public void testGetChildrenWithEmptyGraph()
    {
        String failMessage = "failure - expected null";
        assertNull(failMessage, emptyGraph.getChildrenNodes("n1"));
    }

    @Test
    public void testGetAllNodesWithEmptyGraph()
    {
        String failMessage = "failure - expected empty List<String>";
        assertEquals(failMessage, nodes, emptyGraph.getAllNodes());
    }

    @Test
    public void testGetNumOfEdgesWithEmptyGraph()
    {
        String failMessage = "failure - expected 0";
        assertEquals(failMessage, 0, emptyGraph.getNumOfEdges());
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Test Adding Duplicates
    ///////////////////////////////////////////////////////////////////////////////////////

    // Tests adding two nodes with the same value to the same Graph.
    // Should not add the duplicate node.
    @Test
    public void testAddingDuplicateNodesToGraph()
    {
        assertFalse(EXPECTED_FALSE, threeNodesOneEdgeGraph.addNode("n1"));
        assertFalse(EXPECTED_FALSE, threeNodesOneEdgeGraph.addNode("n1"));
        assertFalse(EXPECTED_FALSE, oneNodeGraph.addNode("n1"));
    }

    @Test
    public void testAddingDuplicateEdgesToGraph()
    {
        assertFalse(EXPECTED_FALSE, threeNodesOneEdgeGraph.addEdge("e1", "n1", "n2"));
        assertFalse(EXPECTED_FALSE, threeNodesOneEdgeGraph.addEdge("e1", "n1", "n2"));
        assertFalse(EXPECTED_FALSE, threeNodesThreeEdgesGraph.addEdge("e1", "n1", "n2"));
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Test each method with an empty string.
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testConstructorWithEmptyString()
    {
        assertTrue(EXPECTED_TRUE, graphWithEmptyStringNode.containsNode(""));
    }

    @Test
    public void testIsAdjacentWithEmptyString()
    {
        assertTrue(EXPECTED_TRUE, graphWithEmptyStringNode.isAdjacent("", "n1"));
    }

    @Test
    public void testAddNodeWithEmptyString()
    {
        assertTrue(EXPECTED_TRUE, emptyGraph.addNode(""));
        assertTrue(EXPECTED_TRUE, emptyGraph.containsNode(""));
    }

    @Test
    public void testAddEdgeWithEmptyString()
    {
        assertTrue(EXPECTED_TRUE, graphWithEmptyStringNode.addEdge("", "", "n1"));
        assertTrue(EXPECTED_TRUE, graphWithEmptyStringNode.containsEdge("", "", "n1"));
    }

    @Test
    public void testContainsNodeWithEmptyString()
    {
        assertTrue(EXPECTED_TRUE, graphWithEmptyStringNode.containsNode(""));
    }

    @Test
    public void testContainsEdgeWithEmptyString()
    {
        assertTrue(EXPECTED_TRUE, graphWithEmptyStringNode.containsEdge("", "n2", ""));
    }

    @Test
    public void testGetEdgeWithEmptyString()
    {
        edges.add("");
        assertEquals(EXPECTED_TRUE, edges, graphWithEmptyStringNode.getEdge("n2", ""));
    }

    @Test
    public void testGetChildrenWithEmptyString()
    {
        edges.add("n1");
        assertEquals(EXPECTED_TRUE, edges, graphWithEmptyStringNode.getChildrenNodes(""));
    }

    @Test
    public void testGetAllNodesWithEmptyString()
    {
        nodes.add("");
        nodes.add("n1");
        nodes.add("n2");
        assertEquals(EXPECTED_TRUE, nodes, graphWithEmptyStringNode.getAllNodes());
    }

    @Test
    public void testGetNumOfEdgesWithEmptyString()
    {
        assertEquals(EXPECTED_TRUE, 2, graphWithEmptyStringNode.getNumOfEdges());
    }
}