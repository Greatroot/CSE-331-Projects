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

/* A simple TextField that only allows numerical input */

import React, {Component} from 'react';

interface GridSizePickerProps {
    value: string;                    // text to display in the text area
    onChange(newSize: number): void;  // called when a new size is picked
}

interface GridSizePickerState {
    displaySize: string;
}

class GridSizePicker extends Component<GridSizePickerProps, GridSizePickerState> {

    constructor(props: GridSizePickerProps) {
        super(props);
        this.state = {
            displaySize: this.props.value,
        }
    }

    onInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        // Every event handler with JS can optionally take a single parameter that
        // is an "event" object - contains information about an event. For mouse clicks,
        // it'll tell you thinks like what x/y coordinates the click was at. For text
        // box updates, it'll tell you the new contents of the text box, like we're using
        // below.
        //
        // TODO - Not currently doing any validation or error handling. Should probably add some...
        let newSize: number = parseInt(event.target.value);
        let newDisplaySize: string = event.target.value;
        if(isNaN(newSize))
        {
            newSize = 0;
        }
        if(0 <= newSize && newSize <= 100)
        {
            this.props.onChange(newSize); // Tell our parent component about the new size.
            this.setState({displaySize: newDisplaySize});
        } else {
            alert("Size must be an integer between 0 and 100 (inclusive).");
        }
    };

    render() {
        return (
            <div id="grid-size-picker">
                <label>
                    Grid Size:
                    <input
                        value={this.state.displaySize}
                        onChange={this.onInputChange}
                        type="number"
                        min={1}
                        max={100}
                    />
                </label>
            </div>
        );
    }
}

export default GridSizePicker;
