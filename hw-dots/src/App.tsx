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

// TODO: Remove edgeText?

import React, {Component} from 'react';
import EdgeList from "./EdgeList";
import Grid from "./Grid";
import GridSizePicker from "./GridSizePicker";

// Allows us to write CSS styles inside App.css, any any styles will apply to all components inside <App />
import "./App.css";

interface AppState {
    gridSize: number;  // size of the grid to display
    // edgeText: string; // The text that the user types into the EdgeList text box.
    edges: [[number, number], [number, number], string][] // All edges the user wants to draw in
                                                      // the format: x1, y1, x2, y2, COLOR
    incorrect_error_message: string; // An error message common to multiple components.
}

class App extends Component<{}, AppState> { // <- {} means no props.

    constructor(props: any) {
        super(props);
        this.state = {
            gridSize: 4,
            edges: [],
            incorrect_error_message: "There was an error with some of your line input."
                + "\nFor reference, the correct form for each line should be: x1,y1 x2,y2 color",
        };
    }

    updateGridSize = (newSize: number) => {
        this.setState({
            gridSize: newSize,
        });
    };

    updateEdges = (newEdges: [[number, number], [number, number], string][]) => {
        this.setState( {
            edges: newEdges,
        });
    }

    render() {
        const canvas_size = 500;
        return (
            <div>
                <p id="app-title">Connect the Dots!</p>
                <GridSizePicker value={this.state.gridSize.toString()} onChange={this.updateGridSize}/>
                <Grid incorrect_input_message={this.state.incorrect_error_message} size={this.state.gridSize} width={canvas_size} height={canvas_size} edges={this.state.edges}/>
                <EdgeList incorrect_error_message={this.state.incorrect_error_message} onChange={this.updateEdges}/>
            </div>

        );
    }

}
// <EdgeList value={this.state.edgeText} onChange={(value) => {console.log("EdgeList onChange", value)}}/>


export default App;
