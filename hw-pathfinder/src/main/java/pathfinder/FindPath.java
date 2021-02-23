package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * FindPath contains a utility method for finding the least costing path in a Graph with nodes of any type
 * and edges with Doubles for labels.
 */
public final class FindPath {

    /**
     * Finds the least costing path in a Graph with nodes of any type and edges with Doubles for labels.
     * Uses Dijkstra's algorithm to perform this search.
     *
     * @param graph the graph we want to perform the search on.
     * @param node_a the node we want to start the search at.
     * @param node_b the hero we want to find the shortest path TO from hero_a.
     * @param <N> the type of nodes in the graph passed in.
     * @param <E> the type of labels stored in the edges in the graph passed; must be Double or
     *           some subtype of Double.
     * @spec.requires the graph passed to not have any negative edge weights.
     * @throws IllegalArgumentException if node_a or node_b are not within graph.
     * @return the path from node_a to node_b that costs the smallest amount when adding up all of the
     * Double amounts from each segment of the path. If node_a.equals(node_b), then just return an
     * empty path. If there is no path that exists between the two nodes, return null.
     */
    public static <N, E extends Double> Path<N> findShortestPath(Graph<N, E> graph, N node_a, N node_b)
            throws IllegalArgumentException
    {
        if(!graph.containsNode(node_a) || !graph.containsNode(node_b))
        {
            throw new IllegalArgumentException();
        }

        PriorityQueue<Path<N>> possiblePaths = new PriorityQueue<Path<N>>(new FindPath.PathCompare<N>());
        Set<N> nodesVisited = new HashSet<N>();

        possiblePaths.add(new Path<N>(node_a));
        while(!possiblePaths.isEmpty())
        {
            // minPath is the lowest-cost path in possiblePaths and is our priority in what path to check next.
            Path<N> minPath = possiblePaths.remove();
            N minDest = minPath.getEnd();

            if(minDest.equals(node_b))
            {
                return minPath;
            }
            if(nodesVisited.contains(minDest))
            {
                continue;
            }

            for(Graph.Edge<E, N> childEdge : graph.getChildrenEdges(minDest))
            {
                if(!nodesVisited.contains(childEdge.getChild()))
                {
                    Path<N> newPath = minPath.extend(childEdge.getChild(), childEdge.getLabel());
                    possiblePaths.add(newPath);
                }
            }
            nodesVisited.add(minDest);
        }
        // If the loop terminates, then no path exists from start to dest.
        // The implementation should indicate this to the client.
        return null;
    }

    /** Comparator that compares Paths by their total cost.
     *
     * @param <N> the type of nodes you want the path you're implementing to compare.
     */
    // TODO: Remove the generic typing and replace with wildcard?
    private static class PathCompare<N> implements Comparator<Path<N>> {

        /**
         * Compares two Paths by their total cost.
         *
         * @param p1 the first Path that is being compared
         * @param p2 the second Path being compared.
         * @return a negative int if p1's total cost is smaller than p2's, a positive
         * if p1's total cost is greater than p2's, and 0 if both costs are equal.
         * to be equal.
         */
        @Override
        public int compare(Path<N> p1, Path<N> p2)
        {
            double result = p1.getCost() - p2.getCost();
            if(result < 0)
            {
                return -1;
            } else if(result > 0)
            {
                return 1;
            } else
            {
                return 0;
            }
        }
    }
}
