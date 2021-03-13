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

import React, {Component} from 'react';
import "./CampusMap.css";

interface CampusMapState {
    backgroundImage: HTMLImageElement | null; // The campus map image.
    path: Path; // The path of points on our campus map from the starting building to the ending building.
}

interface CampusMapProps {
    buildings: Record<string, string>; // A mapping of all buildings from their short names to their long names.
    firstBuildingIndex: number; // Numbered index of the starting building inside this.buildings that is on our path.
    secondBuildingIndex: number; // Numbered index of the second building inside this.buildings that is on our path.
    offSetX: number; // How much the x value of top right corner of the map background image is offset
                        // from the left-most drawing boundary of the canvas.
    offSetY: number; // How much the x value of top right corner of the map background image is offset
                        // from the left-most drawing boundary of the canvas.
    onOffSet(newOffSetX: number, newOffSetY: number): void; // handles offSetX and offSetY getting changed.
}

/**
 * An interface that describes the path object between the start building and end building that is
 *  given to us by our Java Spark server.
 *
 *  The start and end points of a segment are cartesian coordinates on our
 *  campus map with a distance cost of feet in between. A path is a series of connected segments that
 *  take us from one point (i.e. a building) to another on our campus, with the total distance in feet
 *  of all those segments (i.e. the distance someone has to travel) being the path's total cost.
 */
interface Path {
    cost: number; // The total distance in feet the path is.
    start: { // The starting point of our path (i.e. a building)
        x: number, // The x-coordinate of our starting point.
        y: number, // The y-coordinate of our starting point.
    },
    path: Segment[], // A connected series of segments that make up our overall path.
}

/**
 * An interface that describes one segment in a whole string of connected segments that make up a path
 *  from one point to another. The start and end points of a segment are cartesian coordinates on our
 *  campus map with a distance cost of feet in between. A path is a series of connected segments that
 *  take us from one point (i.e. a building) to another on our campus.
 */
interface Segment {
    cost: number, // The total distance in feet the segment is.
    start: { // The starting point of our path (i.e. a building, side walk, cross walk, etc.)
        x: number, // The x-coordinate of the starting point of this segment.
        y: number, // The y-coordinate of the starting point of this segment.
    },
    end: {
        x: number, // The x-coordinate of the ending point of this segment.
        y: number, // The y-coordinate of the ending point of this segment.
    }
}

/**
 *  A map of the University of Washington's campus that can draw visible
 *  paths between two buildings.
 */
class CampusMap extends Component<CampusMapProps, CampusMapState> {

    canvas: React.RefObject<HTMLCanvasElement>; // The canvas where the map and paths are drawn in.
    firstTime: boolean; // A flag that denotes if this is the first time componentDidUpdate() has been run.
    MIN_SCALE: number;
    MAX_SCALE: number;
    isMouseMoving: boolean; // true if the mouse is clicking down over the canvas and being moved.

    constructor(props: CampusMapProps) {
        super(props);
        this.state = {
            backgroundImage: null, // The canvas where the map and paths are drawn in.
            path: {
                cost: 0, // The total distance in feet the path is.
                start: { // The starting point of our path (i.e. a building)
                    x: 0, // The x-coordinate of our starting point.
                    y: 0 // The y-coordinate of our starting point.
                },
                path: [] // A connected series of segments that make up our overall path.
            },
        };
        this.canvas = React.createRef(); // The canvas where the map and paths are drawn in.
        this.firstTime = true; // A flag that denotes if this is the first time componentDidUpdate() has been run.
        this.MIN_SCALE = 0.5;
        this.MAX_SCALE = 3;
        this.isMouseMoving = false;
    }

    componentDidMount() {
        this.fetchAndSaveImage();
        this.drawBackgroundImage();
    }

    componentDidUpdate(prevProps: CampusMapProps, prevState: CampusMapState) {
        this.drawBackgroundImage();

        if((prevProps.firstBuildingIndex !== this.props.firstBuildingIndex
            || prevProps.secondBuildingIndex !== this.props.secondBuildingIndex)
            || prevProps.buildings !== this.props.buildings) // Takes care of buildings loading asynchronously
        {
            this.firstTime = false;
            this.makeRequestForPath();
        }
        if(prevState.path !== this.state.path || prevProps.offSetX != this.props.offSetX
            || prevProps.offSetY != this.props.offSetY)
        {
            this.drawPath();
        }
    }

    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        let background: HTMLImageElement = new Image();
        background.onload = () => {
            this.setState({
                backgroundImage: background
            });
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./campus_map.jpg";
    }

    drawBackgroundImage() {
        let canvas = this.canvas.current;
        if (canvas === null) throw Error("Unable to draw, no canvas ref.");
        let ctx = canvas.getContext("2d");
        if (ctx === null) throw Error("Unable to draw, no valid graphics context.");
        //
        if (this.state.backgroundImage !== null) { // This means the image has been loaded.
            // Sets the internal "drawing space" of the canvas to have the correct size.
            // This helps the canvas not be blurry.
            canvas.width = this.state.backgroundImage.width;
            canvas.height = this.state.backgroundImage.height;
            ctx.drawImage(this.state.backgroundImage, this.props.offSetX, this.props.offSetY);
        }
    }

    /**
     * Draws the path between the starting building and the ending building the user has selected.
     */
    drawPath = () => {
        let canvas = this.canvas.current;
        if (canvas === null) throw Error("Unable to draw, no canvas ref.");
        let ctx = canvas.getContext("2d");
        if (ctx === null) throw Error("Unable to draw, no valid graphics context.");

        const segments: Segment[] = this.state.path.path;

        // Loop through all the segments in our path from the beginning of our path to the end of our path
        // and draw an edge for each segment.
        for(let segment in segments)
        {
            const x1: number = segments[segment].start.x + this.props.offSetX;
            const y1: number = segments[segment].start.y + this.props.offSetY;
            const x2: number = segments[segment].end.x + this.props.offSetX;
            const y2: number = segments[segment].end.y + this.props.offSetY;

            ctx.beginPath();
            ctx.lineWidth = 10;
            ctx.strokeStyle = "red";
            ctx.moveTo(x1, y1);
            ctx.lineTo(x2, y2);
            ctx.stroke();

        }
    }

    /**
     * Request the path from the starting building to the ending building from our Java Spark server.
     */
    makeRequestForPath = async () => {
        try {
            const buildingShortNames = Object.keys(this.props.buildings);
            const firstBuilding = buildingShortNames[this.props.firstBuildingIndex];
            const secondBuilding = buildingShortNames[this.props.secondBuildingIndex];
            let response = await fetch("http://localhost:4567/find-shortest-path?start="
                + firstBuilding + "&end=" + secondBuilding);
            if(!response.ok) {
                alert("The status is wrong! Expected: 200, was: " + response.status);
                return;
            }
            const path: Path = await response.json(); // This takes too long.
            this.setState({
                path: path,
            });

        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    }

    /**
     * Activates panning on the canvas when the user clicks down on the mouse over the canvas.
     */
    onMouseDown = () => {
        this.isMouseMoving = true;
    }

    /**
     * Deactivates panning on the canvas when the user "unclicks" the mouse over the canvas.
     */
    onMouseUp = () => {
        this.isMouseMoving = false;
    }

    /**
     * When the user is clicking down on the mouse and moving the mouse, calculates how offset the
     * x and y cooridnates of where the background map needs to be drawn.
     *
     * @param event that captures the properties of the mouse while the mouse is moving across the screen.
     */
    onMousePan = (event: React.MouseEvent<HTMLCanvasElement>) => {
        if(this.isMouseMoving) {
            const newOffSetX = this.props.offSetX + (event.movementX * 5);
            const newOffSetY = this.props.offSetY + (event.movementY * 5);

            this.props.onOffSet(newOffSetX, newOffSetY);
        }
    }

    render() {
        return (
            <div>
                <canvas ref={this.canvas}
                        onMouseDown={this.onMouseDown} onMouseMove={this.onMousePan}
                        onMouseUp={this.onMouseUp}/>
            </div>
        )
    }
}

export default CampusMap;
