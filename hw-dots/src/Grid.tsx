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
    edges: [[number, number], [number, number], string][]; // information for edges we want the canvas
                        // to draw in format [[x1, y1], [x2, y2] "COLOR"].
    incorrect_input_message: string; // An error message for incorrect user input that is
                                    // common to multiple components.
    inputIsIncorrect: boolean; // Is true if there were any issues with the user's input in the Edge
                                // TextArea. Allows me to both avoid drawing when one of the edges is
                                // incorrect and avoid printing the same error message multiple times.
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

    canvasReference: React.RefObject<HTMLCanvasElement>

    constructor(props: GridProps) {
        super(props);
        this.state = {
            backgroundImage: null  // An image object to render into the canvas.
        };
        this.canvasReference = React.createRef();
    }

    componentDidMount() {
        // Since we're saving the image in the state and re-using it any time we
        // redraw the canvas, we only need to load it once, when our component first mounts.
        this.fetchAndSaveImage();
        this.redraw();
    }

    componentDidUpdate() {
        this.redraw()
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

        let lineNum = 1;
        for(let edge of this.props.edges)
        {
            let x1: number = edge[0][0];
            let y1: number = edge[0][1];
            let x2: number = edge[1][0];
            let y2: number = edge[1][1];
            let color: string = edge[2];
            const scalar = 500 / (this.props.size + 1); // The scale of the grid of dots

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
                alert("Line " + lineNum + ": Cannot draw edges, grid must be at least size " + requiredSize);
            } else if(x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0) {
                alert(this.props.incorrect_input_message + "\n\nLine " + lineNum + ": Coordinate(s) contain " +
                    "negative value(s)");
            } else if(!this.props.inputIsIncorrect) { // If there weren't any errors with user input found in EdgeList...
                ctx.beginPath();
                ctx.lineWidth = 2.5;
                ctx.strokeStyle = color;
                ctx.moveTo((x1+1)*scalar, (y1+1)*scalar);
                ctx.lineTo((x2+1)*scalar, (y2+1)*scalar);
                ctx.stroke();
            }
            lineNum++;
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
