package graph;

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
 * <p> For the purposes of simplification, node values nor edge labels can be an empty String.
 *
 * Specification fields:
 *
 * @spec.specfield <b>node</b> : String // A node in a graph that is made up of a value.
 *
 * @spec.specfield <b>edge</b> : An edge consists of a label, the node it starts at, and the node that it ends at.
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

    // TODO: Add Exceptions to methods to replace @requires in my Graph methods.

    // Abstraction Function:
    // A Graph is made up of Nodes and Edges:
    //      node in the graph => each Node in the nodes Set.
    //      edge in the graph => each Node in the nodes Set stores all of its incident Edges.

    // Representation Invariant for each Graph g:
    //  g.nodes != null
    //  g.nodes cannot contain duplicates.

    //I use a set here to satisfy the RI.
    private Set<Node> nodes;

    /**
     * @spec.effects Constructs a new Graph with no nodes.
     */
    public Graph()
    {
        this.nodes = new HashSet<Node>();
        checkRep();
    }

    /**
     * @param nodeValue the value of the single node the new Graph is constructed with.
     * @spec.requires {@code nodeValue != null && !nodeValue.isEmpty()}
     * @spec.effects Constructs a new Graph with a single node with value "nodeValue."
     */
    public Graph(String nodeValue)
    {
        this.nodes = new HashSet<Node>();
        this.nodes.add(new Node(nodeValue));
        checkRep();
    }

    /**
     * Returns whether the parent node is connected to the child node via an edge.
     *
     * @param parent the parent node to test
     * @param child the child node to test
     * @spec.requires {@code parent != null && child != null} and both Nodes must exist in the Graph.
     * @return true if and only if parent node is connected to a child node via an edge.
     */
    public boolean isAdjacent(String parent, String child)
    {
        this.checkRep();
        Node parentNode = new Node(parent);
        Edge childEdge = new Edge(child, parentNode, new Node(child));
        // The parent node keeps track of its children through a Set of Edges.

        // I can assume that both nodes do exist within the graph.
        for(Node node : this.nodes)
        {
            if(node.equals(parentNode))
            {
                if(node.getChildren().contains(childEdge))
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
     * @return true if and only if the node is successfully added &and;&and; there was NOT a node with that value
     * already present in this graph.
     * @throws IllegalArgumentException if nodeValue is null or an empty string.
     * @spec.modifies this
     * @spec.effects our collection of Nodes
     */
    public boolean addNode(String nodeValue) throws IllegalArgumentException
    {
        this.checkRep();
        if(nodeValue == null || nodeValue.isEmpty())
        {
            throw new IllegalArgumentException();
        }

        Node newNode = new Node(nodeValue);
        if(this.nodes.contains(newNode))
        {
            return false;
        }

        this.nodes.add(newNode);
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
     * @throws IllegalArgumentException if label, parentNode, or childNode are null or label is
     * an empty String.
     * @spec.modifies this
     * @spec.effects our collection of edges
     */
    public boolean addEdge(String label, String parentNode, String childNode)
            throws IllegalArgumentException
    {
        // Uses Edge.equals() to check if labels and nodes are the same.
        if(label == null || parentNode == null || childNode == null || label.isEmpty())
        {
            throw new IllegalArgumentException();
        }

        Node parent = new Node(parentNode);
        Node child = new Node(childNode);
        // Check if the endpoints exist within the graph
        if(this.nodes.contains(parent) && this.nodes.contains(child))
        {
            // If so, we can go through nodes to find the parent node to add a new edge that points to child.
            for(Node node : this.nodes)
            {
                if(node.equals(parent))
                {
                    node.addChild(new Edge(label, parent, child));
                    this.checkRep();
                    return true;
                }
            }
        }
        this.checkRep();
        return false;
    }

    /**
     * Checks to see if this graph contains the specified node.
     *
     * @param nodeValue the value of the node we want to check for.
     * @return true if and only if a node with nodeValue exists within the Graph. If it doesn't,
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

        for(Node node : this.nodes)
        {
            if(node.getValue().equals(nodeValue))
            {
                this.checkRep();
                return true;
            }
        }
        this.checkRep();
        return false;
    }

    /**
     * Returns a collection of edges that connect parentNode to childNode. If no such edges exist, returns
     * an empty list.
     *
     * @param parentNode the parent node of the edges we want.
     * @param childNode the child node of the edges we want.
     * @spec.requires {@code parentNode != null && childNode != null} and both parentNode and
     * childNode must exist within this graph.
     * @return a list of edge labels with the specified parentNode and childNode.
     * If no edges with those endpoints exist, then return an empty list.
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

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        //  g.nodes != null
        assert (this.nodes != null);
    }

    // Questions:
    //  AF, how exactly should I go about it based off of Hal's answer?
    //  Are these comments ok to have for me?
    //  Does .contains() use the overridden equals? // Yes
    //  String is immutable, right? // Yes
    //  Make sure to use UnmodifiableSet.

    // New Questions:
    // Does overriding toString, equals, and hashCode within Node and Edge cause rep exposure?

//    /**
//     * A <b>Node</b> represents a simple <b>immutable</b> data point on our Graph, and is made up of
//     * a single String value and all of the edges pointing to and away from this node.
//     *
//     * <p> Each node can be connected one directionally via an edge. For example, node "A"
//     * can be connected to node "B" by edge "E" and "B" is directly reachable from "A" (A, B).
//     * Because of one-way directionality, "A" cannot be accessed by "B" using edge "E."
//     * A new edge must be made for this to happen.
//     *
//     *<p> This "one directionality" of how nodes are connected by edges is mainly kept track of by the
//     * Edge class and it instances itself, but nodes do keep track of these relationships as well
//     *
//     * <p> For the purposes of simplicity, it is invalid for a Node to have an empty String as a value.
//     *
//     */
    private class Node {

        // Abstract Function:
        //
        //  value => Node.value
        //  edges that point to parent nodes => Node.parents; Set<Edge>
        //  edges that point to child nodes => Node.children; Set<Edge>

        // Representation Invariant for every Node n:
        //  n.value && n.parent && n.children != null
        //  n.value != empty string
        //  There can't exist duplicate edges in n.parents.
        //  There can't exist duplicate edges in n.children.



        // I use sets to satisfy the RI.
        private final String value; // The value of the node
        private Set<Edge> parents; // All of the edges that point to this node.
        private Set<Edge> children; // All of the edges that point away from this edge.

//        /**
//         * @param value the value the Node is constructed with.
//         * @spec.requires {@code value != null}
//         * @spec.effects Constructs a new Node instance with the value "value."
//         */
        public Node(String value)
        {
            this.value = value;
            this.parents = new HashSet<Edge>();
            this.children = new HashSet<Edge>();
            this.checkRep();
        }

//        /**
//         * Adds the edge that connects from the parent node to this node.
//         *
//         * @param newParent the edge to be added
//         * @spec.requires {@code newParent != null}
//         * @return true if and only if the new parent edge is successfully added &and;&and; didn't previously
//         * exist before.
//         */
        private boolean addParent(Edge newParent)
        {
            this.checkRep();
            boolean didAdd = this.parents.add(newParent);
            this.checkRep();

            return didAdd;
        }

//        /**
//         * Adds the edge that connects from this node to its child node.
//         *
//         * @param newChild the child edge to be added.
//         * @spec.requires {@code newChild != null}
//         * @return true if and only if the new edge is successfully added &and;&and; didn't previously
//         * exist before.
//         */
        private boolean addChild(Edge newChild)
        {
            this.checkRep();
            boolean didAdd = this.parents.add(newChild);
            this.checkRep();

            return didAdd;
        }

//        /**
//         * Returns this Node's value.
//         *
//         * @return this Node's String value.
//         */
        private String getValue()
        {
            // String is immutable, so this should maintain immutability.
            this.checkRep();
            return this.value;
        }

//        /**
//         * Returns all parents of this.
//         *
//         * @return the list of Edges parent to this. If there are no parents, returns empty list.
//         */
        private Set<Edge> getParents()
        {
            //Should not break the immutability of Node since the underlying elements of List<Node>
            // are immutable themselves.
            this.checkRep();
            Set<Edge> unmodset = Collections.unmodifiableSet(this.parents);
            this.checkRep();

            return unmodset;
        }

//        /**
//         * returns all children of this.
//         *
//         * @return a list of Edges child to this. If there are no children, returns empty list.
//         */
        private Set<Edge> getChildren()
        {
            //Should not break the immutability of Node since the underlying elements of List<Node>
            // are immutable themselves
            this.checkRep();
            Set<Edge> unmodset = Collections.unmodifiableSet(this.children);
            this.checkRep();

            return unmodset;
        }

//        /**
//         * Returns a string representation of this Node.
//         *
//         * @return a String representation of this Node by printing out the value
//         * and the number of parent and children edges.
//         */
        @Override
        public String toString() {
            return "This Node " + this.value + " has " + this.parents.size() + " parent edges and "
                    + this.children.size() + " children edges.";
        }

//        /**
//         * Standard equality operation.
//         *
//         * @param obj the object to be compared for equality
//         * @return true if and only if 'obj' is an instance of a Node and 'this' and 'obj' represent
//         * the same Node with the same value.
//         */
        @Override
        public boolean equals(Object obj) {
            if(obj == this)
            {
                return true;
            }
            if(!(obj instanceof Node))
            {
                return false;
            }

            Node other = (Node) obj;

            return this.value.equals(other.value);
        }

//        /**
//         * Standard hashCode function.
//         *
//         * @return an int that all objects equal to this will also return
//         */
        @Override
        public int hashCode() {
            return this.value.hashCode();
        }

//        /**
//         * Throws an exception if the representation invariant is violated.
//         */
        private void checkRep() {
            // n.value && n.parent && n.children != null
            assert (this.value != null);
            assert (this.parents != null);
            assert (this.children != null);

            // n.value != empty string
            assert (!this.value.isEmpty());
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
//     * For the purposes of simplicity, it is valid for an Edge to have an empty String as a label.
//     * An empty String also counts as a unique value for the purposes of comparing two Edges with the
//     * same parent and child nodes.
//     *
//     * @spec.specfield endpoint : Node // Either Node that makes up an edge. If Edge A has the parent Node B
//     * and the child Node C, then both Nodes B and C are endpoints of A.
//     */
    private class Edge {

        // Abstract Function:
        //
        // label => String Edge.label
        // parent node => Edge.parent; Node
        // child node => Edge.child; Node

        // Representation Invariant for every Edge e:
        //  e.label && e.parent && e.child != null
        //  e.label != empty string


        private final String label;
        private final Node parent;
        private final Node child;

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
        public Edge(String label, Node parent, Node child)
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
        private Node getParent()
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
        private Node getChild()
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
            return "This Node " + this.label + " starts at " + this.parent.getValue() + " and ends at "
                    + this.child.getValue();
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
            if(!(obj instanceof Node))
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

            //  e.label != empty string
            assert (!this.label.isEmpty());
        }
    }
}
