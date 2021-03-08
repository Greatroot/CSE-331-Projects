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

package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.Map;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        // TODO: Create all the Spark Java routes you need here.
        CampusMap campusMap = new CampusMap();

        // Requires no query string from client.
        Spark.get("/get-building-names", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Map<String, String> buildings = campusMap.buildingNames();
                Gson gson = new Gson();

                return gson.toJson(buildings);
            }
        });

        // With this request there must be a query string with two key/value pairs:
        //  1.) start => value
        //  2.) end => value
        Spark.get("/find-shortest-path", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String start = request.queryParams("start");
                String end = request.queryParams("end");
                if(start == null || end == null) { // If bad request, then send 400 error.
                    Spark.halt(400, "You made a bad request.");
                }

                try {
                    Path<Point> shortestPath = campusMap.findShortestPath(start, end);
                    Gson gson = new Gson();
                    return gson.toJson(shortestPath);
                } catch(IllegalArgumentException e)
                {
                    Spark.halt(400, "Either the start or the end requested don't exist.");
                } // TODO: Make sure this is correct!
                return null; // This should never be reached, since Spark.halt should stop the program?
            }
        });
    }

}
