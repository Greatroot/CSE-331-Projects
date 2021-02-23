/*
 * Copyright (C) 2021 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CampusMap implements ModelAPI {
    private Graph<Point, Double> campusGraph; // Could use shortName instead of Point, but Point makes it much easier.
    private List<CampusBuilding> campusBuildings;

    /**
     *
     */
    public CampusMap()
    {
        campusGraph = new Graph<Point, Double>();
        campusBuildings = CampusPathsParser.parseCampusBuildings("campus_buildings.tsv");
        List<CampusPath> campusPaths = CampusPathsParser.parseCampusPaths("campus_paths.tsv");

        //Building the graph with Point(x1, y1)(nodes) (since we know they are a unique identifier
        // of a building) from campusBuildings and distances(edge labels) from
        // campusPaths.

        // First adds nodes.
        for(CampusBuilding campusBuilding : campusBuildings)
        {
            campusGraph.addNode(new Point(campusBuilding.getX(), campusBuilding.getY()));
        }

        //TODO: Remove this after testing.
//        System.out.println("In the constructor: ");
//        System.out.println("campusPath: " + campusPaths);

        //TODO: Remove after testing; I'm going to check what state my graph is in.
        System.out.println("Nodes: ");
        for(Point node : campusGraph.getAllNodes())
        {
            System.out.println("\t" + node);
        }

        // Then add all the edges.
        for(CampusPath campusPath : campusPaths)
        {
            //TODO: Remove after testing.
            System.out.println(campusPath);
            campusGraph.addEdge(campusPath.getDistance(), new Point(campusPath.getX1(), campusPath.getY1()),
                    new Point(campusPath.getX2(), campusPath.getY2()));
        }
    }

    @Override
    public boolean shortNameExists(String shortName) {
        for(CampusBuilding campusBuilding : campusBuildings)
        {
            if(campusBuilding.getShortName().equals(shortName))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @param shortName The short name of a building to look up.
     * @return The long name of the building corresponding to the provided short name. Will return
     * null only if shortName exists but for some reason isn't attached to a longName.
     * @throws IllegalArgumentException if the short name provided does not exist.
     */
    @Override
    public String longNameForShort(String shortName) {
        if(!this.shortNameExists(shortName)) // Uses the shortNameExists() implemented above.
        {
            throw new IllegalArgumentException("That short name does not exist within campus_buildings.tsv.");
        }

        for(CampusBuilding campusBuilding : campusBuildings)
        {
            if(campusBuilding.getShortName().equals(shortName))
            {
                return campusBuilding.getLongName();
            }
        }
        return null; // This should never be reached because of the IllegalArgumentException.
    }

    @Override
    public Map<String, String> buildingNames() {
        Map<String, String> names = new HashMap<String, String>();
        for(CampusBuilding campusBuilding : campusBuildings)
        {
            names.put(campusBuilding.getShortName(), campusBuilding.getLongName());
        }

        return names;
    }

    // Does assume that campusBuildings have unique x and y values.
    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        if(!this.shortNameExists(startShortName) || !this.shortNameExists(endShortName)
                || startShortName == null || endShortName == null)
        {
            throw new IllegalArgumentException();
        }

        //TODO: Remove after testing; I'm going to check what state my graph is in.
        System.out.println("Nodes: ");
        for(Point node : campusGraph.getAllNodes())
        {
            System.out.println("\t" + node);
            for(Graph.Edge<Double, Point> edge : campusGraph.getChildrenEdges(node))
            {
                System.out.println("\t" + edge.getParent() + " to " + edge.getChild() + " with weight " + edge.getLabel());
            }
        }

        // Convert startShortName into a Point
        Point startPoint = null;
        Point endPoint = null;
        for(CampusBuilding campusBuilding : campusBuildings)
        {
            if(campusBuilding.getShortName().equals(startShortName)) // If starting building is found.
            {
                startPoint = new Point(campusBuilding.getX(), campusBuilding.getY());
            } else if(campusBuilding.getShortName().equals(endShortName)) // If ending point is found.
            {
                endPoint = new Point(campusBuilding.getX(), campusBuilding.getY());
            }
        }

        Path<Point> path = FindPath.findShortestPath(campusGraph, startPoint, endPoint);
        // TODO: Remove this after testing
        assert(path != null);
        for(Path<Point>.Segment segment : path)
        {
            System.out.println(segment.getStart() + " to " + segment.getEnd() + " with weight " + segment.getCost());
        }

        return path;
    }

}
