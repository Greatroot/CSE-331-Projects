package graph.junitTests;

import graph.Graph;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * This class contains a set of test cases that can be used to test the implementation of the
 * Edge class.
 */
public class EdgeTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested.

    private final String EXPECTED_TRUE = "failure - expected true but was false";
    private final String EXPECTED_FALSE = "failure - expected false but was true";


    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Constructor
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testConstructingValidEdge()
    {
        Graph.Edge edge = new Graph.Edge("e1", "n1", "n2");
        assertEquals(EXPECTED_TRUE, "e1", edge.getLabel());
        assertEquals(EXPECTED_TRUE, "n1", edge.getParent());
        assertEquals(EXPECTED_TRUE, "n2", edge.getChild());
    }

    @Test
    public void testConstructingEdgeWithEmptyLabel()
    {
        Graph.Edge edge = new Graph.Edge("", "n1", "n2");
        assertEquals(EXPECTED_TRUE, "", edge.getLabel());
        assertEquals(EXPECTED_TRUE, "n1", edge.getParent());
        assertEquals(EXPECTED_TRUE, "n2", edge.getChild());
    }

    @Test
    public void testConstructingEdgeWithEmptyParent()
    {
        Graph.Edge edge = new Graph.Edge("e1", "", "n2");
        assertEquals(EXPECTED_TRUE, "e1", edge.getLabel());
        assertEquals(EXPECTED_TRUE, "", edge.getParent());
        assertEquals(EXPECTED_TRUE, "n2", edge.getChild());
    }

    @Test
    public void testConstructingEdgeWithEmptyChild()
    {
        Graph.Edge edge = new Graph.Edge("e1", "n1", "");
        assertEquals(EXPECTED_TRUE, "e1", edge.getLabel());
        assertEquals(EXPECTED_TRUE, "n1", edge.getParent());
        assertEquals(EXPECTED_TRUE, "", edge.getChild());
    }

    @Test
    public void testConstructingEdgeBetweenSameNode()
    {
        Graph.Edge edge = new Graph.Edge("e1", "n1", "n1");
        assertEquals(EXPECTED_TRUE, "e1", edge.getLabel());
        assertEquals(EXPECTED_TRUE, "n1", edge.getParent());
        assertEquals(EXPECTED_TRUE, "n1", edge.getChild());
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  equals()
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testNormalEdgeEquality()
    {
        Graph.Edge edge1 = new Graph.Edge("e1", "n1", "n2");
        Graph.Edge edge2 = new Graph.Edge("e1", "n1", "n2");
        Graph.Edge edge3 = new Graph.Edge("e2", "n1", "n2");
        Graph.Edge edge4 = new Graph.Edge("e1", "n3", "n2");
        Graph.Edge edge5 = new Graph.Edge("e1", "n1", "n3");

        assertEquals(EXPECTED_TRUE, edge1, edge1);
        assertEquals(EXPECTED_TRUE, edge1, edge2);
        assertNotEquals(EXPECTED_TRUE, edge1, edge3);
        assertNotEquals(EXPECTED_TRUE, edge1, edge4);
        assertNotEquals(EXPECTED_TRUE, edge1, edge5);
    }

    @Test
    public void testEmptyEdgeEquality()
    {
        Graph.Edge edge1 = new Graph.Edge("", "n1", "n2");
        Graph.Edge edge2 = new Graph.Edge("", "n1", "n2");
        Graph.Edge edge3 = new Graph.Edge("e2", "", "n2");
        Graph.Edge edge4 = new Graph.Edge("e1", "n3", "");
        Graph.Edge edge5 = new Graph.Edge("e1", "n1", "n3");
        Graph.Edge edge6 = new Graph.Edge("", "", "");
        Graph.Edge edge7 = new Graph.Edge("", "", "");

        assertEquals(EXPECTED_TRUE, edge1, edge1);
        assertEquals(EXPECTED_TRUE, edge1, edge2);
        assertNotEquals(EXPECTED_TRUE, edge1, edge3);
        assertNotEquals(EXPECTED_TRUE, edge1, edge4);
        assertNotEquals(EXPECTED_TRUE, edge1, edge5);
    }
}
