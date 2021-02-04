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

    Graph emptyGraph;

    @Before
    public void setUp()
    {
        emptyGraph = new Graph();
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

}