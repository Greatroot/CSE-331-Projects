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

import java.util.*;

/**
 * A CampusMap represents an immutable map of the University of Washington's Seattle campus.
 *
 * <p> A CampusMap object consists of a series of points or intersections on a 2d map and a series of
 *  paths between those points that allow you to get from one point on the map to any other point on the
 *  map.
 *
 *  <p> More specifically, a <b>point</b> is a cartesian point with an x and y value that can represent
 *  a building or natural intersection on a 2d map of the University of Washington Seattle.
 *
 *  <p> As for a <b>path</b>, it represents a physical path between two points on the 2d CampusMap
 *      with the physical real-world distance between the two points.
 *
 *  <p> Every <b>building</b> on this CampusMap has a short name and long name associated with it
 *      along with the normal attributes of a point on a map.
 */
public class CampusMap implements ModelAPI {
    private final Graph<Point, Double> campusGraph; // Could use shortName instead of Point, but Point makes it much easier.
    private final List<CampusBuilding> campusBuildings;

    private final boolean MEDIUM_DEBUG = true;
    private final boolean HEAVY_DEBUG = true;

    // AF(this) =
    //      A point on the CampusMap => campusGraph.key()
    //      A path between two points on the CampusMap => campusGraph.value()
    //      All of the buildings on the CampusMap (which are just special points with a short name
    //      and long name) => SOME points in campusGraph.keySet() OR => every element in campusBuilding
    //

    // Rep Invariant:
    //  campusGraph != null
    //  campusBuildings != null
    //
    //  for every CampusBuilding cb in campusBuildings:
    //      cb.getShortName() != null
    //      cb.getLongName() != null
    //      cb.getX() != null
    //      cb.getY() != null
    //      every cb must be unique according to CampusBuilding.equals()
    //
    //  for every Edge e within campusGraph:
    //      e.getLabel() >= 0
    //  for every Point p within campusGraph:
    //      p != null (This is upheld by Graph's Rep Inv)

    /**
     * Constructs a new CampusMap using campus_buildings.tsv and campus_paths.tsv to compile all the
     * necessary points, buildings, and paths between all of those points/buildings.
     */
    public CampusMap()
    {
        campusGraph = new Graph<Point, Double>();
        campusBuildings = CampusPathsParser.parseCampusBuildings("campus_buildings.tsv");
        List<CampusPath> campusPaths = CampusPathsParser.parseCampusPaths("campus_paths.tsv");

        //Building the graph with Point(x1, y1)(nodes) (since we know they are a unique identifier
        // of a building) from campusPaths (since campusBuildings does not include all nodes) and
        // distances(edge labels) from campusPaths.

        // First adds nodes.
        for(CampusPath campusPath : campusPaths)
        {
            campusGraph.addNode(new Point(campusPath.getX1(), campusPath.getY1()));
            campusGraph.addNode(new Point(campusPath.getX2(), campusPath.getY2()));
        }

        // Then add all the edges.
        for(CampusPath campusPath : campusPaths)
        {
            campusGraph.addEdge(campusPath.getDistance(), new Point(campusPath.getX1(), campusPath.getY1()),
                    new Point(campusPath.getX2(), campusPath.getY2()));
        }
        this.checkRep();
    }

    /**
     * @param shortName The short name of a building to query.
     * @return {@literal true} iff the short name provided exists in this campus map.
     */
    @Override
    public boolean shortNameExists(String shortName) {
        this.checkRep();
        for(CampusBuilding campusBuilding : campusBuildings)
        {
            if(campusBuilding.getShortName().equals(shortName))
            {
                this.checkRep();
                return true;
            }
        }
        this.checkRep();
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
        this.checkRep();
        if(!this.shortNameExists(shortName)) // Uses the shortNameExists() implemented above.
        {
            throw new IllegalArgumentException("That short name does not exist within campus_buildings.tsv.");
        }

        for(CampusBuilding campusBuilding : campusBuildings)
        {
            if(campusBuilding.getShortName().equals(shortName))
            {
                this.checkRep();
                return campusBuilding.getLongName();
            }
        }
        this.checkRep();
        return null; // This should never be reached because of the IllegalArgumentException, unless
                    // shortName isn't for some reason attached to longName.
    }

    /**
     * @return A mapping from all the buildings' short names to their long names in this campus map. If buildingNames()
     *  is empty, then it returns an empty Map.
     */
    @Override
    public Map<String, String> buildingNames() {
        this.checkRep();
        Map<String, String> names = new HashMap<String, String>();
        for(CampusBuilding campusBuilding : campusBuildings)
        {
            names.put(campusBuilding.getShortName(), campusBuilding.getLongName());
        }

        this.checkRep();
        return names;
    }

    /**
     * Finds the shortest path, by distance, between the two provided buildings.
     *
     * @param startShortName The short name of the building at the beginning of this path.
     * @param endShortName   The short name of the building at the end of this path.
     * @return A path between {@code startBuilding} and {@code endBuilding}, or {@literal null}
     * if none exists.
     * @throws IllegalArgumentException if {@code startBuilding} or {@code endBuilding} are
     *                                  {@literal null}, or not valid short names of buildings in
     *                                  this campus map.
     */
    // Does assume that campusBuildings have unique x and y values.
    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        this.checkRep();
        if(!this.shortNameExists(startShortName) || !this.shortNameExists(endShortName)
                || startShortName == null || endShortName == null)
        {
            throw new IllegalArgumentException();
        }

        // Convert startShortName into a Point
        Point startPoint = null;
        Point endPoint = null;
        for(CampusBuilding campusBuilding : campusBuildings)
        {
            if(campusBuilding.getShortName().equals(startShortName)) // If starting building is found.
            {
                startPoint = new Point(campusBuilding.getX(), campusBuilding.getY());
            }
            // I decided NOT to use an else if to reduce processing to cover the case that both points
            // are the same node.
            if(campusBuilding.getShortName().equals(endShortName)) // If ending point is found.
            {
                endPoint = new Point(campusBuilding.getX(), campusBuilding.getY());
            }
        }

        this.checkRep();
        return FindPath.findShortestPath(campusGraph, startPoint, endPoint);
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        //  campusGraph != null
        //  campusBuildings != null
        assert(campusGraph != null);
        assert(campusBuildings != null);

        //  for every CampusBuilding cb in campusBuildings:
        //      cb.getShortName() != null
        //      cb.getLongName() != null
        //      every cb must be unique according to CampusBuilding.equals()
        if(MEDIUM_DEBUG)
        {
            for(CampusBuilding cb : campusBuildings)
            {
                assert(cb.getShortName() != null);
                assert(cb.getLongName() != null);
            }
        }

        if(HEAVY_DEBUG)
        {
            for(Point p : campusGraph.getAllNodes())
            {
                List<Graph.Edge<Double, Point>> childEdges = campusGraph.getChildrenEdges(p);
                for(Graph.Edge<Double, Point> childEdge : childEdges)
                {
                    assert(childEdge.getLabel() >= 0);
                }
            }
        }
    }

}
