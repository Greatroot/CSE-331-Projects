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
import CampusMap from "./CampusMap";

import "./App.css";
import Header from "./components/Header/Header";
import Footer from "./components/Footer/Footer";

/**
 * The main component of the app.
 */
interface AppState {
    buildings: Record<string, string>; // A mapping of all buildings from their short names to their long names.
    firstBuildingIndex: number; // Number index of the first building object inside this.buildings
    secondBuildingIndex: number; // Number index of the second building object inside this.buildings
    offSetX: number; // How much the x value of top right corner of the map background image is offset
    // from the left-most drawing boundary of the canvas.
    offSetY: number; // How much the x value of top right corner of the map background image is offset
                          // from the left-most drawing boundary of the canvas.
}

class App extends Component<{}, AppState> {

    constructor(props: {}) {
        super(props);
        this.state = {
            buildings: {}, // A mapping of all buildings from their short names to their long names.
            firstBuildingIndex: 0, // Numbered index of the starting building inside this.buildings that is on our path.
            secondBuildingIndex: 0, // Numbered index of the second building inside this.buildings that is on our path.
            offSetX: 0, // How much the x value of top right corner of the map background image is offset
                            // from the left-most drawing boundary of the canvas.
            offSetY: 0, // How much the x value of top right corner of the map background image is offset
                            // from the left-most drawing boundary of the canvas.
        };
    }

    componentDidMount() {
        this.makeRequestForBuildings();
    }

    /**
     * Calls our Java Spark server and requests a mapping of all of the buildings in the Campus Map.
     *
     * Stores the mapping of short building names to long building names in our state object.
     */
    makeRequestForBuildings = async () => {
        try {
            let response = await fetch("http://localhost:4567/get-building-names");
            if(!response.ok) {
                alert("The status is wrong! Expected: 200, was: " + response.status);
                return;
            }
            const buildings = (await response.json());

            this.setState({
                buildings: buildings,
            });
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    };

    /**
     * Takes the new building that the user picked in the drop down menu and stores in the state
     *  the index of where to find that new building in our campus buildings mapping.
     *
     * @param event | The state of our first building drop down menu.
     */
    onFirstSelectChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        const buildingLongNames = Object.values(this.state.buildings);
        const index = buildingLongNames.indexOf(event.target.value);
        console.log("Index: " + index)
        this.setState({
            firstBuildingIndex: index,
        });
    }

    /**
     * Takes the new building that the user picked in the drop down menu and stores in the state
     *  the index of where to find that new building in our campus buildings mapping.
     *
     * @param event | The state of our second building drop down menu.
     */
    onSecondSelectChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        const buildingLongNames = Object.values(this.state.buildings);
        const index = buildingLongNames.indexOf(event.target.value);
        console.log("Index: " + index);
        this.setState({
            secondBuildingIndex: index,
        });
    }

    offSet = (newOffSetX: number, newOffSetY: number) => {
        this.setState({
            offSetX: newOffSetX,
            offSetY: newOffSetY,
        })
    }

    /**
     * Resets the app back to the state it was in when the user first opened the app.
     *
     * More specifically, the campus map canvas gets wiped of any paths and the starting and ending
     * buildings get put back to their default values.
     */
    resetApp = () => {
        this.setState({
            firstBuildingIndex: 0,
            secondBuildingIndex: 0,
            offSetX: 0,
            offSetY: 0,
        });
    }

    render() {
        const buildingLongNames = Object.values(this.state.buildings);
        return (
            <div className="container">
                <Header bottomBorder={true}/>
                <div className="campus-map">
                    <CampusMap buildings={this.state.buildings}
                               firstBuildingIndex={this.state.firstBuildingIndex}
                               secondBuildingIndex={this.state.secondBuildingIndex}
                               offSetX={this.state.offSetX}
                               offSetY={this.state.offSetY}
                               onOffSet={this.offSet}
                    />
                </div>
                <div className="drop-down">
                    <h3>Source</h3>
                    <select onChange={this.onFirstSelectChange} value={buildingLongNames[this.state.firstBuildingIndex]}>
                        {buildingLongNames.map((buildingLongName, index) =>
                            <option key={index}>
                                {buildingLongName}
                            </option>)
                        }
                    </select>
                    <h3>Destination</h3>
                    <select onChange={this.onSecondSelectChange} value={buildingLongNames[this.state.secondBuildingIndex]}>
                        {buildingLongNames.map((buildingLongName, index) =>
                            <option key={index}>
                                {buildingLongName}
                            </option>)
                        }
                    </select>
                    <br/>
                    <button id="clear-button" onClick={this.resetApp}>Clear</button>
                </div>
                <Footer />
            </div>
        );
    }

}

export default App;
