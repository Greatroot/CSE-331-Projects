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

interface EdgeListState {
    isFirstTime: boolean; // Keeps track of if this is the first time within the current lifecycle
                            // that the TextArea has been clicked.
    edgeText: string; // The text the user should see in the TextArea "client-side-wise."
}

interface EdgeListProps {
    edgeText: string; // The entire app's copy of the text within the TextArea.
    onChange(edgeText: string): void;  // called when a new edge list is ready
    // once you decide how you want to communicate the edges to the App, you should
    // change the type of edges so it isn't `any`
    // incorrect_error_message: string; // An error message for incorrect user input that is
    //                                 // common to multiple components.
    // onIncorrectInput(wrongInput: string): void; // Updates App on if there was some incorrect
    //                             // user input within the TextArea
    // onChangeReset(): void; // Resets App.edges and App.wrongInputs. TODO: Remove
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {

    constructor(props: EdgeListProps) {
        super(props);
        this.state = {
            isFirstTime: true, // Keeps track of if this is the first time within the current lifecycle
                                // that the TextArea has been clicked.
            edgeText: this.props.edgeText, // The text the user should see in
                                            // the TextArea "client-side-wise."
        }
    }

    /**
     * Keeps track of the text in the TextArea everytime it is changed.
     * Stores it locally in this component's state.
     *
     * @param event | Information on the state of the TextArea when it changes.
     */
        // I only want the edgeText to be locally changed for now, since only the draw button should be the
        // only trigger to drawing out the edges in the text box.
    onTextAreaChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        this.setState({
            edgeText: event.target.value,
        });
    }

    /**
     * Removes the default text on the TexaArea when the user clicks on it for the
     * FIRST and first time only.
     */
    onTextAreaClick = () => {
        if(this.state.isFirstTime)
        {
            this.setState( {
                isFirstTime: false,
                edgeText: "",
            });
            // this.props.onChange(""); TODO: Remove
        }
    }

    /**
     *  Parses the current edgeText and sends that edge information to App to be sent to Grid and drawn.
     */
    onDrawButtonClick = () => { // Parses the current edgeText and sends that edge information to App.

        if(this.props.edgeText !== "") // If there is text within the TextArea, then parse.
        {
            this.props.onChange(this.state.edgeText);
        }
    }

    /**
     * Clears the text currently in TextArea.
     */
    onClearButtonClick = () => {
        this.setState({
           edgeText: "",
        });
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
                <button onClick={this.onClearButtonClick}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;
