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
    backgroundImage: HTMLImageElement | null;
    path: Path;
}

interface CampusMapProps {
    buildings: Record<string, string>;
    firstBuildingIndex: number;
    secondBuildingIndex: number;
}

interface Path {
    cost: number;
    start: {
        x: number,
        y: number,
    },
    segment: Segment[],
}

interface Segment {
    cost: number,
    start: {
        x: number,
        y: number,
    },
    end: {
        x: number,
        y: number,
    }
}

class CampusMap extends Component<CampusMapProps, CampusMapState> {

    // NOTE:
    // This component is a suggestion for you to use, if you would like to.
    // It has some skeleton code that helps set up some of the more difficult parts
    // of getting <canvas> elements to display nicely with large images.
    //
    // If you don't want to use this component, you're free to delete it.

    canvas: React.RefObject<HTMLCanvasElement>;

    constructor(props: CampusMapProps) {
        super(props);
        this.state = {
            backgroundImage: null,
            path: {
                cost: 0,
                start: {
                    x: 0,
                    y: 0
                },
                segment: []
            },
        };
        this.canvas = React.createRef();
    }

    componentDidMount() {
        this.fetchAndSaveImage();
        this.drawBackgroundImage();
    }

    // TODO: use componentDidUpdate() to solve my "background-not-loading-fast-enough-while-mounting-issue"
    //  for now, but ask a TA if there's a better way.
    componentDidUpdate(prevProps: CampusMapProps, prevState: CampusMapState) {
        this.drawBackgroundImage();

        if((prevProps.firstBuildingIndex !== this.props.firstBuildingIndex
            || prevProps.secondBuildingIndex !== this.props.secondBuildingIndex)
            && prevState.path !== this.state.path)
        {
            console.log("Hello!")
            this.makeRequestForPath();
            console.log("I made it past makeRequestForPath()!")
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
            ctx.drawImage(this.state.backgroundImage, 0, 0);
        }
    }

    drawPath = () => {
        let canvas = this.canvas.current;
        if (canvas === null) throw Error("Unable to draw, no canvas ref.");
        let ctx = canvas.getContext("2d");
        if (ctx === null) throw Error("Unable to draw, no valid graphics context.");

        const segments: Segment[] = this.state.path.segment;
        for(let segment in segments)
        {
            const x1: number = segments[segment].start.x;
            const y1: number = segments[segment].start.y;
            const x2: number = segments[segment].end.x;
            const y2: number = segments[segment].end.y;

            ctx.beginPath();
            ctx.lineWidth = 50;
            ctx.strokeStyle = "red";
            ctx.moveTo(x1, y1);
            ctx.lineTo(x2, y2);
            ctx.stroke();
        }
}

    makeRequestForPath = async () => {
        try {
            const buildingShortNames = Object.keys(this.props.buildings);
            const firstBuilding = buildingShortNames[this.props.firstBuildingIndex];
            const secondBuilding = buildingShortNames[this.props.secondBuildingIndex];
            console.log(firstBuilding);
            console.log(secondBuilding);
            // let response = await fetch("http://localhost:4567/find-shortest-path?start="
            //     + firstBuilding + "&end=" + secondBuilding);
            let response = await fetch("http://localhost:4567/find-shortest-path?start=CSE&end=KNE");
            if(!response.ok) {
                alert("The status is wrong! Expected: 200, was: " + response.status);
                return;
            }
            const path: Path = await response.json();
            this.setState({
                path: path,
            });

        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
}

    render() {
        return (
            <div>
                <canvas ref={this.canvas}/>
            </div>
        )
    }
}

export default CampusMap;
