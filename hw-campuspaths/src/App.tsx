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

/**
 * The main component of the app.
 */
interface AppState {
    buildings: Record<string, string>; // A mapping of all buildings from their short names to their long names.
    firstBuildingIndex: number; // Number index of the first building object inside this.buildings
    secondBuildingIndex: number; // Number index of the second building object inside this.buildings
}

class App extends Component<{}, AppState> {

    constructor(props: {}) {
        super(props);
        this.state = {
            buildings: {}, // A mapping of all buildings from their short names to their long names.
            firstBuildingIndex: 0, // Number index of the first building object inside this.buildings
            secondBuildingIndex: 0, // Number index of the second building object inside this.buildings
        };
    }

    /**
     *
     */
    componentDidMount() {
        this.makeRequestForBuildings();
    }

    makeRequestForBuildings = async () => {
        try {
            let response = await fetch("http://localhost:4567/get-building-names");
            if(!response.ok) {
                alert("The status is wrong! Expected: 200, was: " + response.status);
                return;
            }
            const buildings = (await response.json());
            // console.log(Object.keys(buildings)); // TODO: Remove these when done!!!
            // console.log(Object.values(buildings));

            this.setState({
                buildings: buildings,
            });
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    };

    onFirstSelectChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        const buildingLongNames = Object.values(this.state.buildings);
        const index = buildingLongNames.indexOf(event.target.value);
        console.log("Index: " + index)
        this.setState({
            firstBuildingIndex: index,
        });
    }

    onSecondSelectChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        const buildingLongNames = Object.values(this.state.buildings);
        const index = buildingLongNames.indexOf(event.target.value);
        console.log("Index: " + index);
        this.setState({
            secondBuildingIndex: index,
        });
    }

    resetApp = () => {
        this.setState({
            firstBuildingIndex: 0,
            secondBuildingIndex: 0,
        });
    }

    render() {
        const buildingLongNames = Object.values(this.state.buildings);
        return (
            <div>
                <p>Here's the beginning of your AMAZING CampusPaths GUI!</p>
                <CampusMap buildings={this.state.buildings} firstBuildingIndex={this.state.firstBuildingIndex} secondBuildingIndex={this.state.secondBuildingIndex}/>
                <p>Source</p>
                <select onChange={this.onFirstSelectChange} value={buildingLongNames[this.state.firstBuildingIndex]}>
                    {buildingLongNames.map((buildingLongName, index) =>
                        <option key={index}>
                            {buildingLongName}
                        </option>)
                    }
                </select>
                <p>Destination</p>
                <select onChange={this.onSecondSelectChange} value={buildingLongNames[this.state.secondBuildingIndex]}>
                    {buildingLongNames.map((buildingLongName, index) =>
                        <option key={index}>
                            {buildingLongName}
                        </option>)
                    }
                </select>
                <button onClick={this.resetApp}>Clear</button>
            </div>
        );
    }

}

export default App;
