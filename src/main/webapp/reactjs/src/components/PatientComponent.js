import React from "react";
import axios from "axios";
import {Button, Card} from "react-bootstrap";

class PatientComponent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            patients: []
        }
    }

    componentDidMount() {
       this.findAllPatients();
    }

    findAllPatients(){
        axios.get("http://localhost:8080")
            .then(res => res.data)
            .then((data) => {
                this.setState({patients: data});
            });
    }

    render() {
        return (
            <Card>
                <div className="Search">
                    <input className="search_user" type="text" size="15"/>
                    <Button className="add_button">New Patient</Button>
                </div>
                <div className="Patients">
                    {
                        this.state.patients.map(
                            patient =>
                                <div key={patient.id}>
                                    <hr className="hr1"/>

                                    <a>{patient.firstName}</a>
                                    <a>{patient.lastName}</a>
                                    <p>{patient.dateOfBirth}</p>

                                </div>
                        )
                    }
                </div>
                <div className="vl"></div>
            </Card>
        )
    }

}

export default PatientComponent