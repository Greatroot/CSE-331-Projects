
import React, {Component} from 'react';

interface BuildingsDropDownState {
    buildings: Record<string, string>;
    firstBuilding: string;
    secondBuilding: string;
}

class BuildingsDropDown extends Component<{}, BuildingsDropDownState> {

    constructor(props: {}) {
        super(props);
        this.state = {
            buildings: {},
            firstBuilding: "",
            secondBuilding: "",
        };
    }

    componentDidMount() {
        this.makeRequestForBuildings();
    }

    componentDidUpdate() {

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
        this.setState({
            firstBuilding: event.target.value,
        });
    }

    onSecondSelectChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        this.setState({
            secondBuilding: event.target.value,
        });
    }

    render() {
        const buildingLongNames = Object.values(this.state.buildings);
        return (
            <div>
                <select onChange={this.onFirstSelectChange} value={this.state.firstBuilding}>
                    {buildingLongNames.map((buildingLongName, index) =>
                        <option key={index}>
                            {buildingLongName}
                        </option>)
                    }
                </select>
                <select onChange={this.onSecondSelectChange} value={this.state.secondBuilding}>
                    {buildingLongNames.map((buildingLongName, index) =>
                        <option key={index}>
                            {buildingLongName}
                        </option>)
                    }
                </select>
            </div>
        )
    }
}

export default BuildingsDropDown;

// {['firstName', 'lastName'].map(key => (
//     <select key={key}>
//         {Object.values(this.state.buildings).map(({ [key]: value }) => <option key={value}>{value}</option>)}
//     </select>
// ))}