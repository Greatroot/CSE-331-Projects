package marvel.junitTests;

import graph.Graph;
import marvel.MarvelPaths;
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
 * This class contains a set of test cases that can be used to test the utility methods of MarvelPaths.
 *
 */
public final class MarvelPathsTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested.

    private final String EXPECTED_TRUE = "failure - expected true but was false";
    private final String EXPECTED_FALSE = "failure - expected false but was true";


    Graph<String, String> marvelGraph, testDataGraph, loadedGraphWithEmptyStringedNodes, loadedGraphWithEmptyStringedEdges;
    List<Graph.Edge<String, String>> path;

    @Before
    public void setUp()
    {
        path = new ArrayList<Graph.Edge<String, String>>();

        marvelGraph = MarvelPaths.loadGraph("marvel.tsv");
        testDataGraph = MarvelPaths.loadGraph("testData.tsv");
        loadedGraphWithEmptyStringedNodes = MarvelPaths.loadGraph("testDataWithEmptyStringedNode.tsv");
        loadedGraphWithEmptyStringedEdges = MarvelPaths.loadGraph("testDataWithEmptyStringedEdges.tsv");
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Test a tsv file with empty strings.
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testLoadingGraphFromTSVWithEmptyNode()
    {
        path.add(new Graph.Edge<String, String>("e13" ,"", "n10"));
        path.add(new Graph.Edge<String, String>("e10" ,"n10", "n6"));
        path.add(new Graph.Edge<String, String>("e5" ,"n6", "n4"));
        path.add(new Graph.Edge<String, String>("e4" ,"n4", "n3"));
        path.add(new Graph.Edge<String, String>("e2" ,"n3", "n1"));
        path.add(new Graph.Edge<String, String>("e1" ,"n1", "n2"));
        path.add(new Graph.Edge<String, String>("e3" ,"n2", "n5"));

        assertEquals(EXPECTED_TRUE, path, MarvelPaths.findPath(loadedGraphWithEmptyStringedNodes, "", "n5"));
    }

    @Test
    public void testLoadingGraphFromTSVWithEmptyEdges()
    {
        path.add(new Graph.Edge<String, String>("" ,"n10", "n5"));

        assertEquals(EXPECTED_TRUE, path, MarvelPaths.findPath(loadedGraphWithEmptyStringedEdges, "n10", "n5"));
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  loadGraph()
    ///////////////////////////////////////////////////////////////////////////////////////

    // Should throw IllegalArgumentException if filename passed DNE
    @Test(expected = IllegalArgumentException.class)
    public void testLoadGraphThrowsIllegalArgumentException()
    {
        MarvelPaths.loadGraph("DNE.tsv");
    }

    // Should throw NullPointerException if filename passed is null.
    @Test(expected = NullPointerException.class)
    public void testLoadGraphThrowsNullPointerException()
    {
        MarvelPaths.loadGraph(null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  FindPath()
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void testFindPathThrowsIllegalArgumentExceptionWhenNodeA_DNE()
    {
        MarvelPaths.findPath(marvelGraph, "DNE", "THANOS");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindPathThrowsIllegalArgumentExceptionWhenNodeB_DNE()
    {
        MarvelPaths.findPath(marvelGraph, "THANOS", "DNE");
    }

    @Test
    public void testFindPathBetweenSameNode()
    {
        assertEquals(EXPECTED_TRUE, path, MarvelPaths.findPath(marvelGraph, "THANOS", "THANOS"));
    }

    @Test
    public void testFindPathThatDNE()
    {
        assertNull(MarvelPaths.findPath(testDataGraph, "n1", "n13"));
    }


}