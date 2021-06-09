import React, {Component} from "react";
import axios from "axios";
import {Button, Card} from "react-bootstrap";

export default class AddPatient extends Component {

    constructor(props) {
        super(props);
        this.state = this.initialState;
        this.submitPatient = this.submitPatient.bind(this);
    }

    initialState = {
        firstName: '', lastName: '', age: '', dateOfBirth:'', sex: '', country: '', state: '', address: ''
    }

    submitPatient = event => {
        event.preventDefault();

        const patient = {
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            age: this.state.age,
            dateOfBirth: this.state.dateOfBirth,
            sex: this.state.sex,
            country: this.state.country,
            state: this.state.state,
            address: this.state.address
        };
        axios.post("http://localhost:8080/patient/create", patient)
            .then(res => {
                if (res.data != null){
                    this.setState(this.initialState)
                    alert("Patient Saved")
                }
            });
    }

    render() {
        return (
            <Card>

            </Card>
        )
    }
}

export default AddPatient