package pathfinder.junitTests;

import graph.Graph;
import marvel.MarvelPaths;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import pathfinder.FindPath;
import pathfinder.datastructures.Path;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class contains a set of test cases that can be used to test the utility methods of MarvelPaths.
 *
 */
public final class FindPathTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested.

    private final String EXPECTED_TRUE = "failure - expected true but was false";
    private final String EXPECTED_FALSE = "failure - expected false but was true";


    Graph<String, Double> graph;
    Path<String> path;

    @Before
    public void setUp()
    {
        path = new Path<String>("n1");

        graph = new Graph<String, Double>();
        graph.addNode("n1");
        graph.addNode("n2");
        graph.addNode("n3");
        graph.addNode("n4");
        graph.addNode("n5");

        graph.addEdge(6.0, "n1", "n2");
        graph.addEdge(10.0, "n2", "n3");
        graph.addEdge(4.0, "n3", "n4");

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  findSmallestPath()
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void testFindShortestPathThrowsIllegalArgumentExceptionWhenNodeA_DNE()
    {
        FindPath.findShortestPath(graph, "DNE", "n4");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindPathThrowsIllegalArgumentExceptionWhenNodeB_DNE()
    {
        FindPath.findShortestPath(graph, "n1", "DNE");
    }

    @Test
    public void testFindPathBetweenSameNode()
    {
        assertEquals(EXPECTED_TRUE, path, FindPath.findShortestPath(graph, "n1", "n1"));
    }

    @Test
    public void testFindPathThatDNE()
    {
        assertNull(FindPath.findShortestPath(graph, "n1", "n5"));
    }


}