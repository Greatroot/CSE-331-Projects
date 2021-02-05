package graph;

import java.util.List;
import java.util.Set;

/**
 * <b>Graph</b> represents a <b>mutable</b> directed labeled graph, which is a collection of
 * nodes and edges. A node is simply made up of a String value while an edge consists of a String label
 * and a parent node and a child node (starting point and ending point).
 *
 * <p> Each node can be connected one directionally via an edge. For example, node A
 * can be connected to node B by edge E and B is directly reachable from A (A, B). Because of one-way
 * directionality, A cannot be accessed by B using edge E. A new edge must be made for this to happen.
 *
 * <p> For every instance of Graph, there can be any number of edges (zero, one, or more) between a pair of nodes
 * and any number of nodes and edges altogether.
 *
 * <p> Each node's value is unique within an instance of a Graph. As for edges, multiple edges within one
 * instance of Graph can have the same label, with the exception that all labels for edges between the SAME
 * pair of nodes MUST be unique.
 *
 * <p> For the purposes of simplification, node values nor edge labels can be an empty String.
 *
 * Specification fields:
 *
 * @spec.specfield <b>node</b> : String // A node in a graph that is is both identified and purely exists by a String value.
 *
 * @spec.specfield <b>edge</b> : An edge consists of a String label, the parent node it starts at, and the child node it ends at.
 *
 * @spec.specfield <b>parent</b> : Node // If an edge points from node A to node B, A is said to be a parent of node B.
 *
 * @spec.specfield <b>child</b> : Node // If an edge points from node A to node B, B is said to be a child of node A.
 *
 * @spec.specfield <b>relationship</b> : Between Two Nodes // A relationship refers generally to the relationship
 * between two nodes within a graph instance (and only within a graph instance) usually in terms of
 * parent and child (A.K.A. how they are connected via an edge/edges).
 *
 * @spec.specfield <b>neighbor</b> : Node // a neighbor refers to a node that is either a child or parent of another node.
 *  So if node A had node B and node C as parents (edges connected from (B, A) and (C, A)) and node D as
 *  a child (edge connected from (A, D)), then nodes B, C, and D are all neighbors of A.
 *
 * @spec.specfield <b>endpoint</b> : Node // Either node that makes up an edge. If edge A has the parent node B
 * and the child node C, then both nodes B and C are endpoints of A.
 *
 */
public class Graph {

    /**
     * @spec.effects Constructs a new Graph with no nodes.
     */
    public Graph()
    {
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Graph(Node node) constructor is not yet implemented");
    }

    /**
     * @param nodeValue the value of the single node the new Graph is constructed with.
     * @spec.requires {@code nodeValue != null && !nodeValue.isEmpty()}
     * @spec.effects Constructs a new Graph with a single node with value "nodeValue."
     */
    public Graph(String nodeValue)
    {
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Graph constructor is not yet implemented");
    }

    /**
     * Returns whether the parent node is connected to the child node via an edge.
     *
     * @param parent the parent node to test
     * @param child the child node to test
     * @spec.requires {@code parent != null && child != null} and both Nodes must exists in the Graph.
     * @return true if and only if parent node is connected to a child node via an edge.
     */
    public boolean isAdjacent(String parent, String child)
    {
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Graph.isAdjacent() constructor is not yet implemented");
    }

    /**
     * Adds a Node with value "nodeValue" to this graph if it isn't already there.
     *
     * @param nodeValue value of Node being added.
     * @spec.requires {@code nodeValue != null && !nodeValue.isEmpty()}
     * @return true if and only if the node is successfully added &and;&and; there was NOT a node with that value
     * already present in this graph.
     * @spec.modifies this
     * @spec.effects our collection of Nodes
     */
    public boolean addNode(String nodeValue)
    {
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Graph.addNode(String nodeValue) is not yet implemented");
    }

    /**
     * Adds the edge "edge" to this graph if there are no duplicates and both endpoints exist
     * within this graph.
     *
     * @param label the label of the edge being added.
     * @param parentNode the node where the edge starts
     * @param childNode the node where the edge ends.
     * @spec.requires {@code label != null && parentNode != null && childNode != null && !label.isEmpty()}
     * @return true if and only if the edge is successfully added &and;&and; there were no duplicate edges.
     * If the parent and/or child do not exist within the graph, return false and do not add the edge.
     * @spec.modifies this
     * @spec.effects our collection of edges
     */
    public boolean addEdge(String label, String parentNode, String childNode)
    {
        // Uses Edge.equals() to check if labels and nodes are the same.
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Graph.addEdge() is not yet implemented");
    }

    /**
     * Returns the node with value "nodeValue" within this graph. If the node doesn't exist, returns null.
     *
     * @param nodeValue the value of the node we want to retrieve.
     * @spec.requires {@code nodeValue != null && !nodeValue.isEmpty()}
     * @return the node with the value of "nodeValue." If no such node exists, then returns null.
     */
    public String getNode(String nodeValue)
    {
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Graph.getNode() is not yet implemented");
    }

    /**
     * Gives specified node the new value "newValue." If no such node exists, then create a new node with
     * new value.
     *
     * @param newValue the new value of a pre-existing or potentially new node.
     * @param oldValue the value of the node we want to change.
     * @spec.requires {@code newValue != null && oldValue != null && !newValue.isEmpty()}
     * @spec.modifies this
     * @spec.effects collection of nodes in this graph if node with value "newValue" doesn't already exist.
     */
    public void setNode(String oldValue, String newValue)
    {
        // Since my Node class doesn't have a way to change its label, I will simply replace the
        // old node with a new copy with the value newValue and same neighbors.
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Graph.setNode() is not yet implemented");
    }

    /**
     * Returns a collection of edges that connect parentNode to childNode. If no such edges exist, returns
     * an empty list.
     *
     * @param parentNode the parent node of the edges we want.
     * @param childNode the child node of the edges we want.
     * @spec.requires {@code parentNode != null && childNode != null} and both parentNode and
     * childNode must exist within this graph.
     * @return a list of edge labels with the specified parentNode and childNode. If no edges with those endpoints
     * exist, then return an empty list.
     */
    public List<String> getEdge(String parentNode, String childNode)
    {
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Graph.getEdge() is not yet implemented");
    }

    /**
     * Gets and returns all nodes in this graph.
     *
     * @return a copy of the list of all nodes in this graph.
     */
    public List<String> getAllNodes()
    {
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Graph.getAllNodes() is not yet implemented");
    }

    /**
     * Gets and returns all edges in this graph.
     *
     * @return a copy of the list of all edge labels in this graph.
     */
    public List<String> getAllEdges()
    {
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Graph.getAllEdges() is not yet implemented");
    }

    /**
     * Returns a string representation of this graph.
     *
     * @return a String representation of this graph in the form of the String representation of
     * every individual node within this graph followed by the String representation of every
     * individual edge within this graph.
     *<p> There is a new blank line between each node String and each edge String.
     */
    @Override
    public String toString() {
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Graph.getEdge() is not yet implemented");
    }
}


    // ***************** I PLAN ON IMPLEMENTING A PRIVATE NODE CLASS *******************


    // ***************** I PLAN ON IMPLEMENTING A PRIVATE EDGE CLASS *******************
