package graph;

import java.util.Comparator;

/** Comparator that compares Edges by their child nodes first and then their edge labels second.
 */
public class EdgeCompare implements Comparator<Graph.Edge> {

    /**
     * Compares two Edges lexicographically by their children nodes first and then their
     * edge labels lexicographically as a "tie breaker" if the children nodes are the same.
     *
     * @param e1 the first Edge that is being compared
     * @param e2 the second Edge being compared.
     * @return a negative int if e1 lexicographically precedes the Edge e2, a positive
     * int if e1 lexicographically comes after e2, and 0 if both are defined by Graph.Edge.equals()
     * to be equal.
     */
    public int compare(Graph.Edge e1, Graph.Edge e2)
    {
        int result = e1.getChild().compareTo(e2.getChild());
        if(result == 0)
        {
            result = e1.getLabel().compareTo(e2.getLabel());
        }

        return result;
    }
}