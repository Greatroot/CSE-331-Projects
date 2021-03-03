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

// TODO: Remove value?
import React, {Component} from 'react';

interface EdgeListState {
    edgeText: string;
    isFirstTime: boolean;
}

interface EdgeListProps {
    onChange(edges: [[number, number], [number, number], string][]): void;  // called when a new edge list is ready
    // once you decide how you want to communicate the edges to the App, you should
    // change the type of edges so it isn't `any`
    incorrect_error_message: string;
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {

    constructor(props: EdgeListProps) {
        super(props);
        this.state = {
            edgeText: "Enter edges here: ",
            isFirstTime: true,
        }
    }

    //TODO: Do I need validation???
    onTextAreaChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        this.setState({
            edgeText: event.target.value,
        });
    }

    // Make the default text come back when the box is empty and you click away???
    onTextAreaClick = () => {
        if(this.state.isFirstTime)
        {
            this.setState( {
                edgeText: "",
                isFirstTime: false,
            });
        }
    }

    // TODO: This is probably the place for user validation. ADD VALIDATION!!!
    onDrawButtonClick = () => { // Parses the current edgeText and sends that edge information to App.
        let edges: [[number, number], [number, number], string][] = [];
        let parsedByNewLines: string[] = this.state.edgeText.split("\n");
        let parsedEachLine: [string, string, string][] = [];
        for(let line of parsedByNewLines) { // First parse each line into a [P1, P2, COLOR] tuple
            let lineArray: string[] = line.split(" ");
            let parsedLine: [string, string, string] = [lineArray[0], lineArray[1], lineArray[2]];
            parsedEachLine.push(parsedLine);
        }

        let lineNum = 1;
        for(let parsedLine of parsedEachLine) // Now parse each line into a [[x1,y1], [x2,y2], COLOR] tuple
        {
            if(parsedLine[0] === undefined || parsedLine[1] === undefined)
            {
                alert(this.props.incorrect_error_message + "\n\nLine " + lineNum + ": You're either missing a "
                    + "portion of the line or missing a space.");
            } else {
                let point1: string[] = parsedLine[0].split(","); // ["x1", "y1"]
                let point2: string[] = parsedLine[1].split(","); // ["y2", "y2"]

                let p1: [number, number] = [parseInt(point1[0]), parseInt(point1[1])] // [x1, y1]
                let p2: [number, number] = [parseInt(point2[0]), parseInt(point2[1])] // [x2, y2]

                // Validation on the two points and color that I'm putting into my edges.
                if(isNaN(p1[0]) || isNaN(p1[1]) || isNaN(p2[0]) || isNaN(p2[1]))
                {
                    alert(this.props.incorrect_error_message + "\n\nLine " + lineNum + ": Coordinate(s) contain "
                    + "non-integer value(s).");
                } else
                {
                    edges.push([p1, p2, parsedLine[2]]);
                    lineNum++;
                }
            }


        }

        if(edges === [])
        {
            alert(this.props.incorrect_error_message + "\n\nLine " + lineNum + ": You're either missing a "
                + "portion of the line or missing a space.");
        }

        this.props.onChange(edges);
    }




    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onClick={this.onTextAreaClick}
                    onChange={this.onTextAreaChange}
                    value={this.state.edgeText}
                /> <br/>
                <button onClick={this.onDrawButtonClick}>Draw</button>
                <button onClick={() => {console.log('Clear onClick was called');}}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;
