package marvel;

import graph.EdgeStringCompare;
import graph.Graph;

import java.util.*;

/**
 * MarvelPaths contains methods useful to generating a Graph based on data in a TSV file and performing
 * path finding operations on a Graph.
 */
public final class MarvelPaths {

    /**
     * Loads a graph from an empty one using the TSV data within the file passed in.
     *
     * @param filename the name of the TSV file whose data we want to generate a Graph from.
     * @return a graph generated or loaded from the data from the TSV file passed in.
     * @throws IllegalArgumentException if the filename cannot be found within the file system.
     * @throws NullPointerException if the filename passed in is null.
     */
    public static Graph loadGraph(String filename) throws IllegalArgumentException, NullPointerException
    {
        if(filename == null)
        {
            throw new NullPointerException();
        }
        Graph graph = new Graph();
        Iterator<HeroModel> heroModelIterator = MarvelParser.parseData(filename);
        Map<String, List<String>> marvelBooks = new HashMap<String, List<String>>();

        // First add all of the heroes as nodes while building up my map.
        while(heroModelIterator.hasNext())
        {
            HeroModel currentHero = heroModelIterator.next();
            graph.addNode(currentHero.getHero());

            // Store the character as a value corresponding to all the books (each one a key)
            // that it is present for.
            if(!marvelBooks.containsKey(currentHero.getBook()))
            {
                List<String> heroesInBook = new ArrayList<String>();
                heroesInBook.add(currentHero.getHero());
                marvelBooks.put(currentHero.getBook(), heroesInBook);
            } else
            {
                List<String> heroesInBook = marvelBooks.get(currentHero.getBook());
                heroesInBook.add(currentHero.getHero());
                marvelBooks.put(currentHero.getBook(), heroesInBook);
            }
        }

        // Now create all of the edges now that we have all of the nodes in place
        // using the organized map we created.
        // For every list of heroes within a book,
        for(Map.Entry<String, List<String>> book : marvelBooks.entrySet())
        {
            String bookTitle = book.getKey();
            List<String> heroesInBook = book.getValue();
            for(int i = 0; i != heroesInBook.size(); i++)
            {
                for(int j = 0; j != heroesInBook.size(); j++)
                {
                    if(!heroesInBook.get(i).equals(heroesInBook.get(j))) // To avoid pointing a hero to itself and duplicates.
                    {
                        graph.addEdge(bookTitle, heroesInBook.get(i), heroesInBook.get(j));
                    }
                }
            }
        }
        return graph;
    }

    /**
     * Returns the shortest path of heroes that connects hero_a to hero_b.
     *
     * @param marvelGraph the graph we want to perform the BFS search on.
     * @param hero_a the hero we want to start the search at.
     * @param hero_b the hero we want to find the shortest path TO from hero_a.
     * @throws IllegalArgumentException if hero_a or hero_b are not within marvelGraph.
     * @return the shortest path of books and heroes (stored in Edges) that connects hero_a
     * to hero_b in the provided graph. If hero_a.equals(hero_b), then just return an empty path.
     * If there is no path that exists between the two heroes, return null.
     */
    public static List<Graph.Edge> findPath(Graph marvelGraph, String hero_a, String hero_b)
            throws IllegalArgumentException
    {
        if(!marvelGraph.containsNode(hero_a) || !marvelGraph.containsNode(hero_b))
        {
            throw new IllegalArgumentException();
        }

        // visited node -> edges traveled.
        Map<String, List<Graph.Edge>> paths = new HashMap<String, List<Graph.Edge>>();
        Queue<String> nodesToVisit = new LinkedList<String>();

        nodesToVisit.add(hero_a);
        paths.put(hero_a, new ArrayList<Graph.Edge>());
        while(!nodesToVisit.isEmpty())
        {
            String currentHero = nodesToVisit.remove();
            if(currentHero.equals(hero_b))
            {
                return paths.get(currentHero); // Make sure this is right.
            }

            List<Graph.Edge> nextConnectedHeroes = marvelGraph.getChildrenEdges(currentHero);
            EdgeStringCompare edgeStringCompare = new EdgeStringCompare();
            nextConnectedHeroes.sort(edgeStringCompare); // Ensures child nodes are in searched in lexicographical order.

            for(Graph.Edge nextConnectedHero : nextConnectedHeroes) // This is the list of child nodes.
            {
                if(!paths.containsKey(nextConnectedHero.getChild()))
                {
                    List<Graph.Edge> pathToNextHero = new ArrayList<Graph.Edge>(paths.get(currentHero));
                    pathToNextHero.add(nextConnectedHero); // Because our path is just the path of heroes/nodes from a to b.
                    paths.put(nextConnectedHero.getChild(), pathToNextHero);
                    nodesToVisit.add(nextConnectedHero.getChild());
                }
            }
        }
        // The loop terminated and there is no path that exists from start to dest.
        return null;
    }

    /**
     * An interactable terminal solution for MarvelPaths' functionality.
     *
      * @param args arguments passed.
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Input the file you want to search: ");
        String filename = scan.nextLine();

        System.out.println("Input the starting node: ");
        String node_a = scan.nextLine();

        System.out.println("Input the ending node: ");
        String node_b = scan.nextLine();

        System.out.println("path from " + node_a + " to " + node_b + ":");
        Graph graph = MarvelPaths.loadGraph(filename);
        if(!graph.containsNode(node_a)) // If node_a DNE within the graph
        {
            System.out.println("unknown node: " + node_a);
        }
        if(!graph.containsNode(node_b)) // If node_b DNE within the graph
        {
            System.out.println("unknown node: " + node_a);
        }

        if(graph.containsNode(node_a) && graph.containsNode(node_b))
        {
            List<Graph.Edge> path = MarvelPaths.findPath(graph, node_a, node_b);
            if(path != null) // If there is a path
            {
                for(Graph.Edge edge : path)
                {
                    System.out.println(edge.getParent() + " to " + edge.getChild() + " via " + edge.getLabel());
                }
            } else // If there's no paths found
            {
                System.out.println("No path found");
            }
        }
    }

}
