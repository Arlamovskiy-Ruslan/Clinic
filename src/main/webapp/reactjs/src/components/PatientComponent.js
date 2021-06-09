import React from "react";
import axios from "axios";

class PatientComponent extends React.Component {

   constructor(props) {
       super(props);
       this.state = {
           patients:[]
       }
   }

   componentDidMount() {
       axios.get("http://localhost:8080")
           .then(res => res.data)
           .then((data) => {
               this.setState({patients: data});
           });
   }

    render() {
        return (
            <div>
                {
                    this.state.patients.map(
                        patient =>
                            <div key={patient.id} className="Patients">
                                <hr className="hr1"/>

                                <a>{patient.firstName}</a>
                                <a>{patient.lastName}</a>
                                <p>{patient.dateOfBirth}</p>

                            </div>
                    )
                }
            </div>
            
        )
    }

}

export default PatientComponent