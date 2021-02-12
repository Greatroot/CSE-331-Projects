package graph;

import org.w3c.dom.Node;

import java.util.*;

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
 * <p> For the purposes of simplicity, it is valid for a node to have an empty String as a value and
 * for an edge to have an empty String as a label. An empty String also counts as a unique value for
 * the purposes of comparing nodes to nodes and edges to edges.
 *
 *
 * Abstract fields:
 *
 * <p><b>node</b> : String // A node in a graph that is made up of a value.
 *
 * <p> <b>edge</b> : An edge consists of a label, the node it starts at, and the node that it ends at.
 *
 * <p><b>parent</b> : Node // If an edge points from node A to node B, A is said to be a parent of node B.
 *
 * <p><b>child</b> : Node // If an edge points from node A to node B, B is said to be a child of node A.
 *
 * <p><b>relationship</b> : Between Two Nodes // A relationship refers generally to the relationship
 * between two nodes within a graph instance (and only within a graph instance) usually in terms of
 * parent and child (A.K.A. how they are connected via an edge/edges).
 *
 * <p><b>neighbor</b> : Node // a neighbor refers to a node that is either a child or parent of another node.
 *  So if node A had node B and node C as parents (edges connected from (B, A) and (C, A)) and node D as
 *  a child (edge connected from (A, D)), then nodes B, C, and D are all neighbors of A.
 *
 * <p><b>endpoint</b> : Node // Either node that makes up an edge. If edge A has the parent node B
 * and the child node C, then both nodes B and C are endpoints of A.
 *
 *
 *
 */
public class Graph {

    // Abstraction Function:
    // A Graph g is made up of Nodes and Edges:
    //      node collection in the graph => g.nodes.keySet()
    //      for every Node n in the graph:
    //          all edges with n as parent => g.nodes.get(n)

    // Representation Invariant for each Graph g:
    //  g.nodes != null
    //
    //  For every Edge e paired to every Node n within g.nodes:
    //      n != null
    //      e != null
    //      e.parent.equals(n)

    private Map<String, Set<Edge>> nodes; // Each node in the map is paired with all of their child edges.
    private final boolean HEAVY_DEBUG = true;

    /**
     * @spec.effects Constructs a new Graph with no nodes.
     */
    public Graph()
    {
        this.nodes = new HashMap<String, Set<Edge>>();
        checkRep();
    }

    /**
     * @param nodeValue the value of the single node the new Graph is constructed with.
     * @throws IllegalArgumentException if nodeValue is null
     * @spec.effects Constructs a new Graph with a single node with value "nodeValue."
     *
     */
    public Graph(String nodeValue) throws IllegalArgumentException
    {
        if(nodeValue == null)
        {
            throw new IllegalArgumentException();
        }

        this.nodes = new HashMap<String, Set<Edge>>();
        this.nodes.put(nodeValue, new HashSet<Edge>());
        checkRep();
    }

    /**
     * Returns whether the parent node is connected to the child node via an edge.
     *
     * @param parent the parent node to test
     * @param child the child node to test
     * @return true if and only if parent node is connected to a child node via an edge.
     * Returns false otherwise, including if either node passed DNE within the graph.
     * @throws IllegalArgumentException if parent or child are null.
     */
    public boolean isAdjacent(String parent, String child) throws IllegalArgumentException
    {
        this.checkRep();
        if(parent == null || child == null)
        {
            throw new IllegalArgumentException();
        }

        Set<Edge> childEdges = this.nodes.get(parent);
        if(childEdges != null)
        {
            for(Edge childEdge : childEdges)
            {
                if(childEdge.getChild().equals(child))
                {
                    this.checkRep();
                    return true;
                }
            }
        }

        this.checkRep();
        return false;
    }

    /**
     * Adds a Node with value "nodeValue" to this graph if it isn't already there.
     *
     * @param nodeValue value of Node being added.
     * @return true if and only if the node is successfully added &and;&and; there was NOT a node with
     * that value already present in this graph.
     * @throws IllegalArgumentException if nodeValue is null
     * @spec.modifies this
     * @spec.effects our collection of Nodes by adding a node to it.
     */
    public boolean addNode(String nodeValue) throws IllegalArgumentException
    {
        this.checkRep();
        if(nodeValue == null)
        {
            throw new IllegalArgumentException();
        }

        // I know maps will take care of duplicates, but I still need to return false;
        if(this.nodes.containsKey(nodeValue))
        {
            return false;
        }

        this.nodes.put(nodeValue, new HashSet<Edge>());
        this.checkRep();
        return true;
    }

    /**
     * Adds the edge "edge" to this graph if there are no duplicates and both endpoints exist
     * within this graph.
     *
     * @param label the label of the edge being added.
     * @param parentNode the node where the edge starts
     * @param childNode the node where the edge ends.
     * @return true if and only if the edge is successfully added &and;&and; there were
     * no duplicate edges.If the parent and/or child do not exist within the graph, return
     * false and do not add the edge.
     * @throws IllegalArgumentException if label, parentNode, or childNode are null
     * @spec.modifies this
     * @spec.effects our collection of edges by adding an edge to it.
     */
    public boolean addEdge(String label, String parentNode, String childNode)
            throws IllegalArgumentException
    {
        this.checkRep();
        if(label == null || parentNode == null || childNode == null)
        {
            throw new IllegalArgumentException();
        }

        boolean didAdd = false;
        if(this.nodes.containsKey(parentNode) && this.nodes.containsKey(childNode)) {
            Edge newEdge = new Edge(label, parentNode, childNode);
            didAdd = this.nodes.get(parentNode).add(newEdge);
        }

        this.checkRep();
        return didAdd;
    }

    /**
     * Checks to see if this graph contains the specified node.
     *
     * @param nodeValue the value of the node we want to check for.
     * @return true if and only if a node with nodeValue exists within the Graph. If it isn't,
     * returns false.
     * @throws IllegalArgumentException if nodeValue is null
     */
    public boolean containsNode(String nodeValue) throws IllegalArgumentException
    {
        this.checkRep();
        if(nodeValue == null)
        {
            throw new IllegalArgumentException();
        }

        boolean doesContain = this.nodes.containsKey(nodeValue);
        System.out.println("this.nodes.containsKey(nodeValue): " + this.nodes.containsKey(""));

        this.checkRep();
        return doesContain;
    }

    /**
     * Checks to see if this graph contains the specified edge.
     *
     * @param label the label of the edge we want to check exists.
     * @param parent the parent of the edge we want to check exists.
     * @param child the child of the edge we want to check exists.
     * @return true if and only if an edge with the specified label, parent, and child exists within
     * this graph. If it isn't, return false.
     * @throws IllegalArgumentException if label, parent, or child are null
     */
    public boolean containsEdge(String label, String parent, String child) throws IllegalArgumentException
    {
        this.checkRep();
        if(label == null || parent == null || child == null)
        {
            throw new IllegalArgumentException();
        }

        Set<Edge> childEdges = this.nodes.get(parent);
        if(childEdges != null)
        {
            for(Edge edge : childEdges)
            {
                if(edge.getLabel().equals(label) && edge.getChild().equals(child))
                {
                    this.checkRep();
                    return true;
                }
            }
        }

        this.checkRep();
        return false;
    }

    /**
     * Returns a collection of edge labels that connect parentNode to childNode.
     * If no such edges exist, returns an empty list.
     *
     * @param parentNode the parent node of the edges we want.
     * @param childNode the child node of the edges we want.
     * @return a list of edge labels with the specified parentNode and childNode.
     * If no edges with those endpoints exist, then return an empty list.
     * If either edge doesn't exist within the graph, then return null.
     * @throws IllegalArgumentException if either parentNode or childNode are null
     */
    public List<String> getEdge(String parentNode, String childNode) throws IllegalArgumentException
    { // This retrieves edge labels of edges that sit between the two nodes.
        // The client should have access to all three components of the edge: the label, the parent,
        // and the child since they passed in the latter two.
        this.checkRep();
        if(parentNode == null || childNode == null)
        {
            throw new IllegalArgumentException();
        }

        if(!this.nodes.containsKey(parentNode) || !this.nodes.containsKey(childNode))
        {
            return null;
        }

        List<String> edgeLabels = new ArrayList<String>();
        Set<Edge> edges = this.nodes.get(parentNode);
        for(Edge edge : edges)
        {
            if(edge.getChild().equals(childNode))
            {
                edgeLabels.add(edge.getLabel());
            }
        }

        this.checkRep();
        return edgeLabels;
    }

    /**
     * returns all children nodes of the parent node.
     *
     * @param parentNode the value of the parent node whose children we want to retrieve.
     * @return a list of child nodes. If there are no children, returns empty list.
     * If parent doesn't exist within the graph, return null.
     * @throws IllegalArgumentException if the String parent is null.
     */
    public List<String> getChildren(String parentNode) throws IllegalArgumentException
    { // This retrieves child nodes
        this.checkRep();
        if(parentNode == null
        )
        {
            throw new IllegalArgumentException();
        }

        if(!this.nodes.containsKey(parentNode))
        {
            return null;
        }

        List<String> childrenNodes = new ArrayList<String>();
        Set<Edge> edges = this.nodes.get(parentNode);
        for(Edge edge : edges)
        {
            childrenNodes.add(edge.getChild());
        }

        this.checkRep();
        return childrenNodes;
    }

    /**
     * Gets and returns all nodes in this graph.
     *
     * @return a copy of the list of all nodes in this graph.
     */
    public List<String> getAllNodes()
    {
        this.checkRep();

        Set<String> keySet = this.nodes.keySet();
        List<String> nodeValues = new ArrayList<String>(this.nodes.keySet());

        this.checkRep();
        return nodeValues;
    }

    /**
     * Gets and returns number of edges in this graph.
     *
     * @return the number of edges in this graph. If this graph contains more than Integer.MAX_VALUE
     * elements, returns Integer.MAX_VALUE.
     */
    public int getNumOfEdges()
    {
        this.checkRep();
        int size = 0;
        Collection<Set<Edge>> totalEdges = this.nodes.values();
        for(Set<Edge> edges : totalEdges)
        {
            for(Edge edge : edges)
            {
                if(size != Integer.MAX_VALUE)
                {
                    size++;
                }
            }
        }

        return size;
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        //  g.nodes != null
        assert (this.nodes != null);

        //  For every Edge e paired to every Node n within g.nodes: e.parent.equals(n)
        if(HEAVY_DEBUG)
        {
            Iterator<String> allNodes = this.nodes.keySet().iterator();
            for(Set<Edge> edges : this.nodes.values())
            {
                String parentNode = allNodes.next();
                for(Edge edge : edges)
                {
                    assert(edge.getParent().equals(parentNode));
                }
            }
        }
    }

//    /**
//     * A <b>Edge</b> represents an <b>immutable</b> one-directional edge connecting two Nodes on our graph,
//     * and is made up of a single String label and the edge's starting (parent) Node and ending (child) Node.
//     *
//     * <p> Each edge is one directional, meaning it represents a vector pointing FROM one Node TO another
//     * Node. For example, node "A" can be connected to node "B" by edge "E" and "B" is directly
//     * reachable from "A" (A, B). Because of one-way directionality, "A" cannot be accessed
//     * by "B" using edge "E." A new edge must be made for this to happen.
//     *
//     *<p> This "one directionality" of how nodes are connected by edges is mainly kept track of by the
//     * Edge class and it instances itself, but nodes do keep track of these relationships as well.
//     *
//     * For the purposes of simplicity, it is not valid for an Edge to have an empty String as a label.
//     *
//     * endpoint : Node // Either Node that makes up an edge. If Edge A has the parent Node B
//     * and the child Node C, then both Nodes B and C are endpoints of A.
//     */
    private class Edge {

        // Abstract Function:
        //  For every Edge e:
        //      e.label => Edge.label : String
        //      e.parent node => Edge.parent : Node
        //      e.child node => Edge.child :Node

        // Representation Invariant for every Edge e:
        //  e.label && e.parent && e.child != null

        private final String label;
        private final String parent;
        private final String child;

//        /**
//         * Constructs an Edge with the specified "label," "parent," and "child."
//         *
//         * @param label the label for this Edge
//         * @param parent the parent Node that this Edge starts at.
//         * @param child the child Node that this Edge ends at.
//         * @spec.requires {@code label != null && parent != null && child != null && !label.isEmpty()},
//         * which is upheld by Graph.addEdge()'s @requires spec.
//         *
//         * Note: an edge can start at a node and point to itself.
//         */
        public Edge(String label, String parent, String child)
        {
            this.label = label;
            this.parent = parent;
            this.child = child;
            this.checkRep();
        }

//        /**
//         * Returns this Edge's label
//         *
//         * @return a separate copy of this Edge's label.
//         */
        private String getLabel()
        {
            // Retains immutability since String is immutable.
            this.checkRep();
            return this.label;
        }

//        /**
//         * Returns the parent Node.
//         *
//         * @return a separate copy of this Edge's parent Node.
//         */
        private String getParent()
        {
            // Retains immutability since Node is immutable.
            this.checkRep();
            return this.parent;

        }

//        /**
//         * Returns the child Node.
//         *
//         * @return a separate copy of this Edge's child Node.
//         */
        private String getChild()
        {
            // Retains immutability since Node is immutable
            this.checkRep();
            return this.child;
        }

//        /**
//         * Returns a string representation of this Edge.
//         *
//         * @return a String representation of this Node by printing out the label and the parent and child
//         * Nodes.
//         */
        @Override
        public String toString() {
            return "This Node " + this.label + " starts at " + this.parent + " and ends at "
                    + this.child;
        }

//        /**
//         * Standard equality operation.
//         *
//         * @param obj the object to be compared for equality
//         * @return true if and only if 'obj' is an instance of an Edge and 'this' and 'obj' represent
//         * the same Edges with the same label and endpoints.
//         */
        @Override
        public boolean equals(Object obj) {
            if(obj == this)
            {
                return true;
            }
            if(!(obj instanceof Edge))
            {
                return false;
            }

            Edge other = (Edge) obj;

            return this.label.equals(other.label) && this.parent.equals(other.parent)
                    && this.child.equals(other.child);
        }

//        /**
//         * Standard hashCode function.
//         *
//         * @return an int that all objects equal to this will also return
//         */
        @Override
        public int hashCode() {
            int result = this.label.hashCode();
            result = 31 * result + this.parent.hashCode();
            result = 31 * result + this.child.hashCode();

            return result;
        }

//        /**
//         * Throws an exception if the representation invariant is violated.
//         */
        private void checkRep() {
            //  e.label && e.parent && e.child != null
            assert (this.label != null);
            assert (this.parent != null);
            assert (this.child != null);
        }
    }
}
