//package graph;
//
//import java.util.List;
//
///**
// * <b>Graph</b> represents a <b>mutable</b> directed labeled graph, which is a collection of
// * nodes and edges. A node is simply made up of a String value while an edge consists of a String label
// * and a parent node and a child node (starting point and ending point).
// *
// * <p> Each node can be connected one directionally via an edge. For example, node A
// * can be connected to node B by edge E and B is directly reachable from A (A, B). Because of one-way
// * directionality, A cannot be accessed by B using edge E. A new edge must be made for this to happen.
// *
// * <p> For every instance of Graph, there can be any number of edges (zero, one, or more) between a pair of nodes
// * and any number of nodes and edges altogether.
// *
// * <p> Each node's value is unique within an instance of a Graph. As for edges, multiple edges within one
// * instance of Graph can have the same label, with the exception that all labels for edges between the SAME
// * pair of nodes MUST be unique.
// *
// * Specification fields:
// *
// * @spec.specfield parent : Node // If an edge points from node A to node B, A is said to be a parent of node B.
// *
// * @spec.specfield child : Node // If an edge points from node A to node B, B is said to be a child of node A.
// *
// * @spec.specfield relationship : Between Two Nodes // A relationship refers generally to the relationship
// * between two nodes within a graph instance (and only within a graph instance) usually in terms of
// * parent and child (A.K.A. how they are connected via an edge/edges).
// *
// * @spec.specfield neighbor : Node // a neighbor refers to a node that is either a child or parent of another node.
// *  So if node A had node B and node C as parents (edges connected from (B, A) and (C, A)) and node D as
// *  a child (edge connected from (A, D)), then nodes B, C, and D are all neighbors of A.
// *
// * @spec.specfield endpoint : Node // Either node that makes up an edge. If edge A has the parent node B
// * and the child node C, then both nodes B and C are endpoints of A.
// *
// */
//public class Graph {
//
//    /**
//     * @spec.effects Constructs a new Graph with no nodes.
//     */
//    public Graph()
//    {
//        // TODO: Fill in this method, then remove the RuntimeException
//        throw new RuntimeException("Graph(Node node) constructor is not yet implemented");
//    }
//
//    /**
//     * @param nodeValue the value of the single node the new Graph is constructed with.
//     * @spec.requires {@code nodeValue != null}
//     * @spec.effects Constructs a new Graph with a single node with value "nodeValue." If nodeValue.isEmpty(),
//     * simply constructs a new Graph with a single node with an empty value (this counts as being unique).
//     */
//    public Graph(String nodeValue)
//    {
//        // TODO: Fill in this method, then remove the RuntimeException
//        throw new RuntimeException("Graph constructor is not yet implemented");
//    }
//
//    /**
//     * @param node the single node the new Graph is constructed with.
//     * @spec.requires {@code node != null}
//     * @spec.effects Constructs a new Graph with the given "node" node.
//     */
//    public Graph(Node node)
//    {
//        // TODO: Fill in this method, then remove the RuntimeException
//        throw new RuntimeException("Graph(Node node) constructor is not yet implemented");
//    }
//
////    /**
////     * @param nodes the collection of Nodes the new Graph is constructed with.
////     * @spec.requires {@code nodes != null}
////     * @spec.effects Constructs a new Graph with the given nodes. If nodes.isEmpty(), then generate
////     * a new Graph with no nodes.
////     */
////    public Graph(List<Node> nodes)
////    {
////        // TODO: Fill in this method, then remove the RuntimeException
////        throw new RuntimeException("Graph(List<Node> nodes) constructor is not yet implemented");
////    }
//
//    /**
//     * Returns whether the parent node is connected to the child node via an edge.
//     *
//     * @param parent the parent node to test
//     * @param child the child node to test
//     * @spec.requires {@code parent != null && child != null} and both Nodes must exists in the Graph.
//     * @return true if and only if parent node is connected to a child node via an edge.
//     */
//    public boolean isAdjacent(Node parent, Node child)
//    {
//        // TODO: Fill in this method, then remove the RuntimeException
//        throw new RuntimeException("Graph.isAdjacent() constructor is not yet implemented");
//    }
//
//    /**
//     * Adds a Node with value "nodeValue" to this graph if it isn't already there.
//     *
//     * @param nodeValue value of Node being added.
//     * @spec.requires {@code nodeValue != null}
//     * @return true if and only if the node is successfully added &and;&and; there was NOT a node with that value
//     * already present in this graph.
//     * @spec.modifies this
//     * @spec.effects our collection of Nodes
//     */
//    public boolean addNode(String nodeValue)
//    {
//        // TODO: Fill in this method, then remove the RuntimeException
//        throw new RuntimeException("Graph.addNode(String nodeValue) is not yet implemented");
//    }
//
//    /**
//     * Adds a Node with value "nodeValue" to this graph if it isn't already there.
//     *
//     * @param node the Node being added.
//     * @spec.requires {@code node != null}
//     * @return true if and only if the node is successfully added &and;&and; there was NOT a node with that value
//     * already present in this graph.
//     * @spec.modifies this
//     * @spec.effects our collection of Nodes
//     */
//    public boolean addNode(Node node)
//    {
//        // TODO: Fill in this method, then remove the RuntimeException
//        throw new RuntimeException("Graph.addNode(Node node) is not yet implemented");
//    }
//
//    // THIS IS BEING SAVED FOR POTENTIAL FUTURE DEVELOPMENT
////    /**
////     * Removes the Node "node" from this graph if it is already inside the graph. Also removes all edges
////     * that connect to or from "node" and modifies the relationship status between all neighbors of "node."
////     *
////     * @param node node to be removed from this graph.
////     * @spec.requires {@code node != null}
////     * @return true if and only if "node" is removed &and;&and; if "node" was already present to begin with
////     * &and;&and; if all neighbors and edges are modified accordingly.
////     * @spec.modifies this
////     * @spec.effects the collection of Nodes in this.
////     */
////    public boolean removeNode(Node node)
////    {
////        // TODO: Fill in this method, then remove the RuntimeException
////        throw new RuntimeException("Graph.removeNode() is not yet implemented");
////    }
//
////    public boolean removeNode(String nodeValue) // Should work because each node has a unique value
////    {
////        throw new RuntimeException("Graph constructor is not yet implemented");
////    }
//
////    public boolean addEdge(String label, Node parentNode, Node childNode) // Uses Edge.equals() to check if labels and nodes are the same.
////    {
////        throw new RuntimeException("Graph constructor is not yet implemented");
////    }
//    /**
//     * Adds the edge "edge" to this graph if there are no duplicates and both endpoints exist
//     * within this graph.
//     *
//     * @param edge the edge being added.
//     * @spec.requires {@code edge != null}
//     * @return true if and only if the edge is successfully added &and;&and; there were no duplicate edges.
//     * If the parent and/or child do not exist within the graph, return false and do not add the edge.
//     * @spec.modifies this
//     * @spec.effects our collection of edges
//     */
//    public boolean addEdge(Edge edge) // Uses Edge.equals() to check if labels and nodes are the same.
//    {
//        // TODO: Fill in this method, then remove the RuntimeException
//        throw new RuntimeException("Graph.addEdge() is not yet implemented");
//    }
//
////    public boolean removeEdge(String label, Node parentNode, Node childNode) // Uses Edge.equals() to check if labels and nodes are the same.
////    {
////        // TODO: Fill in this method, then remove the RuntimeException
////        throw new RuntimeException("Graph constructor is not yet implemented");
////    }
//
//    // THIS IS BEING SAVED FOR POTENTIAL FUTURE DEVELOPMENT
////    /**
////     * Removes the Edge "edge" from this graph if it is already inside the graph.
////     *
////     * @param edge Edge to be removed from this graph.
////     * @spec.requires {@code edge != null}
////     * @return true if and only if "edge" is removed &and;&and; if "edge" was already present to begin with
////     * &and;&and; if both endpoints of "edge" are modified accordingly.
////     * @spec.modifies this
////     * @spec.effects the collection of Edges in this.
////     */
////    public boolean removeEdge(Edge edge) // Uses Edge.equals() to check if labels and nodes are the same.
////    {
////        // TODO: Fill in this method, then remove the RuntimeException
////        throw new RuntimeException("Graph.removeEdge() is not yet implemented");
////    }
//
//    /**
//     * Returns the Node with value "nodeValue" within this graph. If the node doesn't exist, returns null.
//     *
//     * @param nodeValue the value of the Node we want to retrieve.
//     * @spec.requires {@code nodeValue != null}
//     * @return the Node with the value of "nodeValue." If no such Node exists, then returns null.
//     */
//    public Node getNode(String nodeValue)
//    {
//        // TODO: Fill in this method, then remove the RuntimeException
//        throw new RuntimeException("Graph.getNode() is not yet implemented");
//    }
//
//    /**
//     * Gives specified node the new value "newValue." If no such node exists, then create a new node with
//     * new value.
//     *
//     * @param newValue the new value of a pre-existing or potentially new node.
//     * @param oldValue the value of the node we want to change.
//     * @throws IllegalArgumentException if oldValue or newValue are null or if newValue
//     * is an empty String.
//     * @spec.modifies this
//     * @spec.effects collection of nodes in this graph if node with value "newValue" doesn't already exist.
//     */
//    public void setNode(String oldValue, String newValue)
//    {
//        // Since my Node class is immutable, I will simply replace the
//        // old node with a new copy with the value newValue and same neighbors.
//        this.checkRep();
//        if(oldValue == null || newValue == null)
//        {
//            throw new IllegalArgumentException();
//        }
//
//        Node oldNode = new Node(oldValue);
//        Node newNode = new Node(newValue);
//        if(this.nodes.contains(oldNode)) // Ask if using contains to search instead of just doing the for loop is double the work.
//        {
//            Iterator<Node> i = this.nodes.iterator();
//            while(i.hasNext())
//            {
//                Node node = i.next();
//                if(node.equals(oldNode))
//                {
//
//                }
//            }
//        }
//    }
//
//    /**
//     * Returns a collection of nodes that connect parentNode to childNode. If no such edges exist, returns
//     * an empty list.
//     *
//     * @param parentNode the parent Node of the edges we want.
//     * @param childNode the child Node of the edges we want.
//     * @spec.requires {@code parentNode != null && childNode != null} and both parentNode and
//     * childNode must exist within this graph.
//     * @return a list of edges with the specified parentNode and childNode. If no edges with those endpoints
//     * exist, then return an empty list.
//     */
//    public List<Edge> getEdge(Node parentNode, Node childNode)
//    {
//        // TODO: Fill in this method, then remove the RuntimeException
//        throw new RuntimeException("Graph.getEdge() is not yet implemented");
//    }
//
//    /**
//     * Gets and returns all nodes in this graph.
//     *
//     * @return a copy of the list of all nodes in this graph.
//     */
//    public List<Node> getAllNodes()
//    {
//        // TODO: Fill in this method, then remove the RuntimeException
//        throw new RuntimeException("Graph.getAllNodes() is not yet implemented");
//    }
//
//    /**
//     * Gets and returns all edges in this graph.
//     *
//     * @return a copy of the list of all edges in this graph.
//     */
//    public List<Edge> getAllEdges()
//    {
//        // TODO: Fill in this method, then remove the RuntimeException
//        throw new RuntimeException("Graph.getAllEdges() is not yet implemented");
//    }
//
//    /**
//     * Returns a string representation of this graph.
//     *
//     * @return a String representation of this graph in the form of the String representation of
//     * every individual Nodes within this graph followed by the String representation of every
//     * individual edge within this graph.
//     *<p> There is a new blank line between each Node String and each edge String.
//     */
//    @Override
//    public String toString() {
//        // TODO: Fill in this method, then remove the RuntimeException
//        throw new RuntimeException("Graph.getEdge() is not yet implemented");
//    }
//
////    /**
////     * Standard equality operation.
////     *
////     * @param obj the object to be compared for equality
////     * @return true if and only if 'obj' is an instance of a Graph and 'this' and 'obj' represent
////     * the same graph with the same Nodes and Edges.
////     */
////    @Override
////    public boolean equals(Object obj) {
////        // TODO: Fill in this method, then remove the RuntimeException
////        throw new RuntimeException("Graph.hashCode() is not yet implemented");
////    }
////
////    /**
////     * Standard hashCode function.
////     *
////     * @return an int that all objects equal to this will also return
////     */
////    @Override
////    public int hashCode() {
////        // TODO: Fill in this method, then remove the RuntimeException
////        throw new RuntimeException("Graph.hashCode() is not yet implemented");
////    }
//
//
//    /**
//     * A <b>Node</b> represents a simple <b>immutable</b> data point on our Graph, and is made up of
//     * a single String value and connections (list of Nodes) to their parent and children nodes.
//     *
//     * <p> Each node can be connected one directionally via an edge. For example, node "A"
//     * can be connected to node "B" by edge "E" and "B" is directly reachable from "A" (A, B). Because of one-way
//     * directionality, "A" cannot be accessed by "B" using edge "E." A new edge must be made for this to happen.
//     *
//     *<p> This "one directionality" of how nodes are connected by edges is mainly kept track of by the
//     * Edge class and it instances itself, but nodes do keep track of these relationships as well
//     *
//     * <p> For the purposes of simplicity, it is valid for a Node to have an empty String as a value.
//     * An empty String also counts as a unique value for the purposes of comparing Nodes.
//     *
//     */
//    private class Node {
//
//        /**
//         * @param value the value the Node is constructed with.
//         * @spec.requires {@code value != null}
//         * @spec.effects Constructs a new Node instance with the value "value."
//         */
//        public Node(String value)
//        {
//            // TODO: Fill in this method, then remove the RuntimeException
//            throw new RuntimeException("Node constructor is not yet implemented");
//        }
//
//        /**
//         * Adds a parent node to this node's list of parents.
//         *
//         * @param newParent the parent node to be added.
//         * @spec.requires {@code newParent != null}
//         * @return true if and only if the new parent node is successfully added &and;&and; didn't previously
//         * exist before.
//         */
//        private boolean addParent(Node newParent)
//        {
//            // TODO: Fill in this method, then remove the RuntimeException
//            throw new RuntimeException("Node.addParent() is not yet implemented");
//        }
//
//        // THIS IS BEING SAVED FOR POTENTIAL FUTURE DEVELOPMENT
////        /**
////         * Removes a parent node from this node's list of parents.
////         *
////         * @param parent the parent node to be removed.
////         * @spec.requires {@code parent != null}
////         * @return true if and only if parent was already present &and;&and; was successfully removed.
////         */
////        private boolean removeParent(Node parent)
////        {
////            // TODO: Fill in this method, then remove the RuntimeException
////            throw new RuntimeException("Node.removeParent() is not yet implemented");
////        }
//
//        /**
//         * Adds a child node to this node's list of children.
//         *
//         * @param newChild the child node to be added.
//         * @spec.requires {@code newChild != null}
//         * @return true if and only if the new child node is successfully added &and;&and; didn't previously
//         * exist before.
//         */
//        private boolean addChild(Node newChild)
//        {
//            // TODO: Fill in this method, then remove the RuntimeException
//            throw new RuntimeException("Node.addChild() is not yet implemented");
//        }
//
//        // THIS IS BEING SAVED FOR POTENTIAL FUTURE DEVELOPMENT
////        /**
////         * Removes a child node from this node's list of children.
////         *
////         * @param child the parent node to be removed.
////         * @spec.requires {@code child != null}
////         * @return true if and only if parent was already present &and;&and; was successfully removed.
////         */
////        private boolean removeChild(Node child)
////        {
////            // TODO: Fill in this method, then remove the RuntimeException
////            throw new RuntimeException("Node.removeChild() is not yet implemented");
////        }
//
//        /**
//         * Returns a copy of this Node's value.
//         *
//         * @return a completely separate copy in memory of this Node's value.
//         */
//        public String getValue()
//        {
//            // Make sure to return a completely separate copy to keep this class immutable.
//            // TODO: Fill in this method, then remove the RuntimeException
//            throw new RuntimeException("Node.getValue() is not yet implemented");
//        }
//
//        /**
//         * Returns all parents of this.
//         *
//         * @return the list of Nodes parent to this. If there are no parents, returns empty list.
//         */
//        public List<Node> getParents()
//        {
//            //Should not break the immutability of Node since the underlying elements of List<Node>
//            // are immutable themselves.
//            // TODO: Fill in this method, then remove the RuntimeException
//            throw new RuntimeException("Node.getParents() is not yet implemented");
//        }
//
//        /**
//         * returns all children of this.
//         *
//         * @return a list of nodes child to this. If there are no children, returns empty list.
//         */
//        public List<Node> getChildren()
//        {
//            //Should not break the immutability of Node since the underlying elements of List<Node>
//            // are immutable themselves
//            // TODO: Fill in this method, then remove the RuntimeException
//            throw new RuntimeException("Node.getChildren() is not yet implemented");
//        }
//
//        /**
//         * Returns a string representation of this Node.
//         *
//         * @return a String representation of this Node by printing out the value and the parent and children
//         * lists.
//         */
//        @Override
//        public String toString() {
//            // TODO: Fill in this method, then remove the RuntimeException
//            throw new RuntimeException("Node.toString() is not yet implemented");
//        }
//
//        /**
//         * Standard equality operation.
//         *
//         * @param obj the object to be compared for equality
//         * @return true if and only if 'obj' is an instance of a Node and 'this' and 'obj' represent
//         * the same Node with the same value.
//         */
//        @Override
//        public boolean equals(Object obj) {
//            // In this current version of Graph, Node.equals() should only be used to prevent duplicates.
//            // TODO: Fill in this method, then remove the RuntimeException
//            throw new RuntimeException("Node.equals() is not yet implemented");
//        }
//
//        /**
//         * Standard hashCode function.
//         *
//         * @return an int that all objects equal to this will also return
//         */
//        @Override
//        public int hashCode() {
//            // TODO: Fill in this method, then remove the RuntimeException
//            throw new RuntimeException("Node.hashCode() is not yet implemented");
//        }
//    }
//
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
//    private class Edge {
//
//        /**
//         * Constructs an Edge with the specified "label," "parent," and "child."
//         *
//         * @param label the label for this Edge
//         * @param parent the parent Node that this Edge starts at.
//         * @param child the child Node that this Edge ends at.
//         * @spec.requires {@code label != null && parent != null && child != null}
//         *
//         * Note: an edge can start at a node and point to itself.
//         */
//        public Edge(String label, Node parent, Node child)
//        {
//            // TODO: Fill in this method, then remove the RuntimeException
//            throw new RuntimeException("Edge constructor is not yet implemented");
//        }
//
//        /**
//         * Returns this Edge's label
//         *
//         * @return a separate copy of this Edge's label.
//         */
//        private String getLabel()
//        {
//            // Returns a copy to maintain immutability.
//            // TODO: Fill in this method, then remove the RuntimeException
//            throw new RuntimeException("Edge.getLabel() is not yet implemented");
//        }
//
//        /**
//         * Returns the parent Node.
//         *
//         * @return a separate copy of this Edge's parent Node.
//         */
//        private Node getParent()
//        {
//            // Returns a copy to maintain immutability.
//            // TODO: Fill in this method, then remove the RuntimeException
//            throw new RuntimeException("Edge.getParent() is not yet implemented");
//        }
//
//        /**
//         * Returns the child Node.
//         *
//         * @return a separate copy of this Edge's child Node.
//         */
//        private Node getChild()
//        {
//            // Returns a copy to maintain immutability.
//            // TODO: Fill in this method, then remove the RuntimeException
//            throw new RuntimeException("Edge.getChild() is not yet implemented");
//        }
//
//        /**
//         * Returns a string representation of this Edge.
//         *
//         * @return a String representation of this Node by printing out the label and the parent and children
//         * Nodes.
//         */
//        @Override
//        public String toString() {
//            // TODO: Fill in this method, then remove the RuntimeException
//            throw new RuntimeException("Edge.toString() is not yet implemented");
//        }
//
//        /**
//         * Standard equality operation.
//         *
//         * @param obj the object to be compared for equality
//         * @return true if and only if 'obj' is an instance of an Edge and 'this' and 'obj' represent
//         * the same Edges with the same label and endpoints.
//         */
//        @Override
//        public boolean equals(Object obj) {
//            // TODO: Fill in this method, then remove the RuntimeException
//            throw new RuntimeException("Edge.toString() is not yet implemented");
//        }
//
//        /**
//         * Standard hashCode function.
//         *
//         * @return an int that all objects equal to this will also return
//         */
//        @Override
//        public int hashCode() {
//            // TODO: Fill in this method, then remove the RuntimeException
//            throw new RuntimeException("Edge.toString() is not yet implemented");
//        }
//    }
//
//}
