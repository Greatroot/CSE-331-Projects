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
        Graph.Edge<String, String> edge = new Graph.Edge<String, String>("e1", "n1", "n2");
        assertEquals(EXPECTED_TRUE, "e1", edge.getLabel());
        assertEquals(EXPECTED_TRUE, "n1", edge.getParent());
        assertEquals(EXPECTED_TRUE, "n2", edge.getChild());
    }

    @Test
    public void testConstructingEdgeWithEmptyLabel()
    {
        Graph.Edge<String, String> edge = new Graph.Edge<String, String>("", "n1", "n2");
        assertEquals(EXPECTED_TRUE, "", edge.getLabel());
        assertEquals(EXPECTED_TRUE, "n1", edge.getParent());
        assertEquals(EXPECTED_TRUE, "n2", edge.getChild());
    }

    @Test
    public void testConstructingEdgeWithEmptyParent()
    {
        Graph.Edge<String, String> edge = new Graph.Edge<String, String>("e1", "", "n2");
        assertEquals(EXPECTED_TRUE, "e1", edge.getLabel());
        assertEquals(EXPECTED_TRUE, "", edge.getParent());
        assertEquals(EXPECTED_TRUE, "n2", edge.getChild());
    }

    @Test
    public void testConstructingEdgeWithEmptyChild()
    {
        Graph.Edge<String, String> edge = new Graph.Edge<String, String>("e1", "n1", "");
        assertEquals(EXPECTED_TRUE, "e1", edge.getLabel());
        assertEquals(EXPECTED_TRUE, "n1", edge.getParent());
        assertEquals(EXPECTED_TRUE, "", edge.getChild());
    }

    @Test
    public void testConstructingEdgeBetweenSameNode()
    {
        Graph.Edge<String, String> edge = new Graph.Edge<String, String>("e1", "n1", "n1");
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
        Graph.Edge<String, String> edge1 = new Graph.Edge<String, String>("e1", "n1", "n2");
        Graph.Edge<String, String> edge2 = new Graph.Edge<String, String>("e1", "n1", "n2");
        Graph.Edge<String, String> edge3 = new Graph.Edge<String, String>("e2", "n1", "n2");
        Graph.Edge<String, String> edge4 = new Graph.Edge<String, String>("e1", "n3", "n2");
        Graph.Edge<String, String> edge5 = new Graph.Edge<String, String>("e1", "n1", "n3");

        assertEquals(EXPECTED_TRUE, edge1, edge1);
        assertEquals(EXPECTED_TRUE, edge1, edge2);
        assertNotEquals(EXPECTED_TRUE, edge1, edge3);
        assertNotEquals(EXPECTED_TRUE, edge1, edge4);
        assertNotEquals(EXPECTED_TRUE, edge1, edge5);
    }

    @Test
    public void testEmptyEdgeEquality()
    {
        Graph.Edge<String, String> edge1 = new Graph.Edge<String, String>("", "n1", "n2");
        Graph.Edge<String, String> edge2 = new Graph.Edge<String, String>("", "n1", "n2");
        Graph.Edge<String, String> edge3 = new Graph.Edge<String, String>("e2", "", "n2");
        Graph.Edge<String, String> edge4 = new Graph.Edge<String, String>("e1", "n3", "");
        Graph.Edge<String, String> edge5 = new Graph.Edge<String, String>("e1", "n1", "n3");
        Graph.Edge<String, String> edge6 = new Graph.Edge<String, String>("", "", "");
        Graph.Edge<String, String> edge7 = new Graph.Edge<String, String>("", "", "");

        assertEquals(EXPECTED_TRUE, edge1, edge1);
        assertEquals(EXPECTED_TRUE, edge1, edge2);
        assertEquals(EXPECTED_TRUE, edge6, edge7);

        assertNotEquals(EXPECTED_TRUE, edge1, edge3);
        assertNotEquals(EXPECTED_TRUE, edge1, edge4);
        assertNotEquals(EXPECTED_TRUE, edge1, edge5);
    }
}
