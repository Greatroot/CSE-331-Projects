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
    edgeText: string // All edges the user wants to draw in
                    // the format: x1, y1, x2, y2, COLOR
}

class App extends Component<{}, AppState> { // <- {} means no props.

    constructor(props: any) {
        super(props);
        this.state = {
            gridSize: 4,
            edgeText: "Enter edges here: ",
        };
    }

    updateGridSize = (newSize: number) => {
        this.setState({
            gridSize: newSize,
        });
    };

    updateEdges = (newEdges: string) => {
        this.setState( {
            edgeText: newEdges,
        });
    }

    // // Updates App's state if there was some incorrect user input within the EdgeList's TextArea
    // incorrectInputWasFound = (wrongInput: string) => {
    //     console.log("I entered incorrectInputWasFound")
    //     let updatedWrongInputs: string[] = [...this.state.wrongInputs];
    //     updatedWrongInputs.push(wrongInput);
    //     this.setState({
    //        wrongInputs: updatedWrongInputs,
    //     });
    //     console.log("wrongInputs: " + this.state.wrongInputs);
    // }

    // /** TODO: Remove this
    //  * Clears all of the user's wrong input error messages and clears all pre-existing edges in this.state.edges.
    //  */
    // partiallyResetState = () => {
    //     console.log("Things should be getting reset now")
    //     this.setState({
    //         wrongInputs: [],
    //         edges: [],
    //     });
    // }

    render() {
        const canvas_size = 500;
        return (
            <div>
                <p id="app-title">Connect the Dots!</p>
                <GridSizePicker value={this.state.gridSize.toString()} onChange={this.updateGridSize}/>
                <Grid size={this.state.gridSize} width={canvas_size} height={canvas_size}
                      edgeText={this.state.edgeText}/>
                <EdgeList edgeText={this.state.edgeText}
                          onChange={this.updateEdges}/>
            </div>

        );
    }

}

export default App;


// Good test inputs for EdgeList
// 2,3 1,1 3,2 3,3
// 1,2 5,6 red
// 2,1 3,-3 blue

// 9,9 6,7 red
// 3,2 5,9 blue
// 0,0 8,3 orange