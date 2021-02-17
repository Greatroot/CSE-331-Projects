package marvel;

import graph.Graph;

import java.util.*;

//TODO: Make the class static???
public final class MarvelPaths {

    //TODO: Remove this if I go through with making this class static.
//    private Graph marvelGraph;

    /**
     * Loads a graph from an empty one using the TSV data within the file passed in.
     * @param filename
     * @return a graph generated or loaded from the data from the TSV file passed in.
     */
    public static Graph LoadGraph(String filename)
    {
        Graph graph = new Graph();
        Iterator<HeroModel> heroModelIterator = MarvelParser.parseData(filename);
        Map<String, List<String>> marvelBooks = new HashMap<String, List<String>>();
        // TODO: If for some reason the map needs to stop duplicates, look here.

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
     * Returns the path of heroes that connects hero_a to hero_b.
     *
     * @param marvelGraph
     * @param hero_a
     * @param hero_b
     * @return
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
            List<Graph.Edge> nextConnectedHeroes = marvelGraph.getChildrenNodes(currentHero);
            Collections.sort(nextConnectedHeroes); // Ensures child nodes are in searched in lexicographical order.
            for(String nextConnectedHero : nextConnectedHeroes) // This is the list of child nodes.
            {
                if(!paths.containsKey(nextConnectedHero))
                {
                    List<String> pathToNextHero = new ArrayList<String>(paths.get(currentHero));
                    pathToNextHero.add(nextConnectedHero); // Because our path is just the path of heroes/nodes from a to b.
                    paths.put(nextConnectedHero, pathToNextHero);
                    nodesToVisit.add(nextConnectedHero);
                }
            }
        }
    }

}
