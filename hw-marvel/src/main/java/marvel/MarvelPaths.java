package marvel;

import graph.EdgeCompare;
import graph.Graph;

import java.util.*;

//TODO: As of 10:54 PM on Tuesday night
// TODO: 1.) Ask about Unknown command: FindPath
// TODO: 2.) Try to find why my LoadGraph doesn't scale well and is so slow with marvel.txt. FOUND IT~
// TODO: 3.) Tidy up comments for Graph's getEdge() and getChildren()
// TODO: 4.) Write tests for FindPath and LoadGraph.
// TODO: 5.) Ask in office hours if this, EdgeCompare, and HeroModel count as ADTs
//  (I'm pretty sure about the first two)
// Ask about testing. Should we also test for weird TSV data?

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
    public static Graph loadGraph(String filename)
    {
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
//        System.out.println(marvelBooks); TODO: Remove this

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
                    if(j != i) // To avoid pointing a hero to itself.
                    {
                        graph.addEdge(bookTitle, heroesInBook.get(i), heroesInBook.get(j));
                    }
                }
            }
        }
//        System.out.println(marvelBooks); TODO: Remove this
        return graph;
    }

    /**
     * Returns the shortest path of heroes that connects hero_a to hero_b.
     *
     * @param marvelGraph the graph we want to perform the BFS search on.
     * @param hero_a the hero we want to start the search at.
     * @param hero_b the hero we want to find the shortest path TO from hero_a.
     * @return the shortest path of books and heroes (stored in Edges) that connects hero_a
     * to hero_b in the provided graph. If hero_a.equals(hero_b), then just return an empty path.
     * If there is no path that exists between the two heroes, return null.
     */
    // To make this work lexicographically, I need to make sure I search each child in lexicographical order.
    public static List<Graph.Edge> findPath(Graph marvelGraph, String hero_a, String hero_b)
    {
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
            //TODO: Use new version of getChildren/getEdges.
            List<Graph.Edge> nextConnectedHeroes = marvelGraph.getChildren(currentHero);

            EdgeCompare edgeCompare = new EdgeCompare();
            nextConnectedHeroes.sort(edgeCompare); // Ensures child nodes are in searched in lexicographical order.

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

}
