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

interface GridProps {
    size: number;    // size of the grid to display
    width: number;   // width of the canvas on which to draw
    height: number;  // height of the canvas on which to draw
    edgeText: string; // information for edges we want the canvas
                        // to draw in format [[x1, y1], [x2, y2] "COLOR"].
}

interface GridState {
    backgroundImage: any,  // image object rendered into the canvas (once loaded)
}

/**
 *  A simple grid with a variable size
 *
 *  Most of the assignment involves changes to this class
 */
class Grid extends Component<GridProps, GridState> {

    // firstTime: boolean // True if this is the first time the component has called componentDidUpdate()
    incorrectInputMessage: string // A common error message for incorrect user input.
    canvasReference: React.RefObject<HTMLCanvasElement>

    constructor(props: GridProps) {
        super(props);
        this.state = {
            backgroundImage: null,  // An image object to render into the canvas.
        }; // TODO: Remove firstTime
        // this.firstTime = true; // True if this is the first time the component has called componentDidUpdate()
        this.incorrectInputMessage = "There was an error with some of your line input."
            + "\nFor reference, the correct form for each line should be: x1,y1 x2,y2 color";
        this.canvasReference = React.createRef();
    }

    componentDidMount() {
        // Since we're saving the image in the state and re-using it any time we
        // redraw the canvas, we only need to load it once, when our component first mounts.
        this.fetchAndSaveImage();
        this.redraw();
    }

    // It seems that our background can't load in time and needs to call redraw twice to finish "mounting"
    componentDidUpdate(prevProps: GridProps) { //TODO Remove!
        // if(this.props.size !== prevProps.size) // Only to deal with the component mounting for the first time.
        // {
        //     this.redraw();
        //
        // } else if(this.props.edgeText !== prevProps.edgeText) { // If its another prop that changes (i.e. edges),
        //     this.redraw();                                      // then drawEdges and leave grid alone.
        //     this.drawEdges();
        // }

            this.redraw();

            if(this.props.size === prevProps.size) { // If its another prop that changes (i.e. edges),
                this.drawEdges()                     // then drawEdges and leave grid alone.
        }
    }

    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        const background = new Image();
        background.onload = () => {
            this.setState({
                backgroundImage: background
            });
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./image.jpg";
    }

    redraw = () => {
        console.log("I'm starting a 'redraw' cycle")
        if (this.canvasReference.current === null) {
            throw new Error("Unable to access canvas.");
        }
        const ctx = this.canvasReference.current.getContext('2d');
        if (ctx === null) {
            throw new Error("Unable to create canvas drawing context.");
        }

        // First, let's clear the existing drawing so we can start fresh:
        ctx.clearRect(0, 0, this.props.width, this.props.height);

        // Once the image is done loading, it'll be saved inside our state, and we can draw it.
        // Otherwise, we can't draw the image, so skip it.
        if (this.state.backgroundImage !== null) {
            ctx.drawImage(this.state.backgroundImage, 0, 0);
        }

        // Draw all the dots.
        const coordinates = this.getCoordinates();
        for (let coordinate of coordinates) {
            this.drawCircle(ctx, coordinate);
        }
    };

    /**
     * Sets up and draws the edges.
     */
    drawEdges = () =>
    {
        if (this.canvasReference.current === null) {
            throw new Error("Unable to access canvas.");
        }
        const ctx = this.canvasReference.current.getContext('2d');
        if (ctx === null) {
            throw new Error("Unable to create canvas drawing context.");
        }

        if(this.props.edgeText !== "" && this.props.edgeText !== "Enter edges here: ")
        {
            let edges: [[number, number], [number, number], string][] = [];
            let parsedByNewLines: string[] = this.props.edgeText.split("\n");
            let parsedEachLine: [string, string, string][] = [];
            let errorMessages = []; // A place to keep all of the user's input errors
            // while parsing their input.

            let lineNum = 1;
            for(let line of parsedByNewLines) { // First parse each line into a [P1, P2, COLOR] tuple
                let lineArray: string[] = line.split(" ");

                if(lineArray.length > 3) { // Error if the user put too many arguments into a line.
                    errorMessages.push("Line " + lineNum + ": There are extra "
                        + "portions or an extra space somewhere on this line.");
                }

                let parsedLine: [string, string, string] = [lineArray[0], lineArray[1], lineArray[2]];
                parsedEachLine.push(parsedLine);
            }

            lineNum = 1; // To keep track of each line of Edge user input we parse through.
            for(let parsedLine of parsedEachLine) // Now parse each line into a [[x1,y1], [x2,y2], COLOR] tuple
            {
                // If either point1, point2, or color are missing or the spaces/commas are incorrectly formatted.
                if (parsedLine[0] === undefined || parsedLine[1] === undefined || parsedLine[2] === undefined) {
                    errorMessages.push("Line " + lineNum + ": You're either missing a "
                        + "portion of the line or missing a space.");
                }

                let point1: string[] = parsedLine[0].split(","); // ["x1", "y1"]
                let point2: string[] = parsedLine[1].split(","); // ["y2", "y2"]

                let p1: [number, number] = [parseInt(point1[0]), parseInt(point1[1])] // [x1, y1]
                let p2: [number, number] = [parseInt(point2[0]), parseInt(point2[1])] // [x2, y2]

                // Error if one or both of the two points of an edge are NaN.
                if (isNaN(p1[0]) || isNaN(p1[1]) || isNaN(p2[0]) || isNaN(p2[1])) {
                    errorMessages.push("Line " + lineNum + ": Coordinate(s) contain "
                        + "non-integer value(s).");
                }
                edges.push([p1, p2, parsedLine[2]]);

                // For every edge, we want to do some validation:

                let x1: number = p1[0];
                let y1: number = p1[1];
                let x2: number = p2[0];
                let y2: number = p2[1];
                let color: string = parsedLine[2];

                //validation for if coordinates don't fit the grid.
                if(x1 >= this.props.size || y1 >= this.props.size || x2 >= this.props.size || y2 >= this.props.size)
                {
                    let requiredSize = this.props.size;
                    if(x1 >= this.props.size)
                    {
                        requiredSize = x1 + 1;
                    }
                    if(y1 >= this.props.size)
                    {
                        requiredSize = y1 + 1;
                    }
                    if(x2 >= this.props.size)
                    {
                        requiredSize = x2 + 1;
                    }
                    if(y2 >= this.props.size)
                    {
                        requiredSize = y2 + 1;
                    }
                    errorMessages.push("Line " + lineNum + ": Cannot draw edges, grid must be " +
                        "at least size " + requiredSize);
                }

                if(x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0) { // Validation for if there are negative numbers.
                    errorMessages.push("Line " + lineNum + ": Coordinate(s) contain " +
                        "negative value(s)");
                }
                lineNum++;
            }

            // for(let edge of edges)
            // {
            //     //validation for if coordinates don't fit the grid.
            //     if(x1 >= this.props.size || y1 >= this.props.size || x2 >= this.props.size || y2 >= this.props.size)
            //     {
            //         let requiredSize = this.props.size;
            //         if(x1 >= this.props.size)
            //         {
            //             requiredSize = x1 + 1;
            //         }
            //         if(y1 >= this.props.size)
            //         {
            //             requiredSize = y1 + 1;
            //         }
            //         if(x2 >= this.props.size)
            //         {
            //             requiredSize = x2 + 1;
            //         }
            //         if(y2 >= this.props.size)
            //         {
            //             requiredSize = y2 + 1;
            //         }
            //         errorMessages.push("Line " + lineNum + ": Cannot draw edges, grid must be " +
            //             "at least size " + requiredSize);
            //     } else if(x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0) {
            //         errorMessages.push("Line " + lineNum + ": Coordinate(s) contain " +
            //             "negative value(s)");
            //     }
            // }

            if(errorMessages.length === 0) { // If there weren't any errors with user input found in EdgeList...
                for (let edge of edges) {
                    let x1: number = edge[0][0];
                    let y1: number = edge[0][1];
                    let x2: number = edge[1][0];
                    let y2: number = edge[1][1];
                    let color: string = edge[2];
                    const scalar = 500 / (this.props.size + 1); // The scale of the grid of dots

                    ctx.beginPath();
                    ctx.lineWidth = 2.5;
                    ctx.strokeStyle = color;
                    ctx.moveTo((x1 + 1) * scalar, (y1 + 1) * scalar);
                    ctx.lineTo((x2 + 1) * scalar, (y2 + 1) * scalar);
                    ctx.stroke();

                }
            }

            // If there were errors inside wrongInputs, then print them all out in one error message.
            if(errorMessages.length !== 0)
            {
                let fullErrorMessage: string = this.incorrectInputMessage + "\n\n";
                for(let errorMessage of errorMessages)
                {
                    fullErrorMessage += "\n" + errorMessage;
                }
                alert(fullErrorMessage);
            }
        }
    };

    /**
     * Returns an array of coordinate pairs that represent all the points where grid dots should
     * be drawn.
     */
    getCoordinates = (): [number, number][] => {

        let coordinates: [number, number][] = [];
        const changeInWidth= 500 / (this.props.size + 1);
        for (let i = 1; i <= this.props.size; i++)
        {
            for (let j = 1; j <= this.props.size; j++)
            {
                coordinates.push([i*changeInWidth, j*changeInWidth]);
            }
        }
        return coordinates;
    };

    drawCircle = (ctx: CanvasRenderingContext2D, coordinate: [number, number]) => {
        ctx.fillStyle = "white";
        // Generally use a radius of 4, but when there are lots of dots on the grid (> 50)
        // we slowly scale the radius down so they'll all fit next to each other.
        const radius = Math.min(4, 100 / this.props.size);
        ctx.beginPath();
        ctx.arc(coordinate[0], coordinate[1], radius, 0, 2 * Math.PI);
        ctx.fill();
    };

    render() {
        return (
            <div id="grid">
                <canvas ref={this.canvasReference} width={this.props.width} height={this.props.height}/>
                <p>Current Grid Size: {this.props.size}</p>
            </div>
        );
    }
}

export default Grid;
