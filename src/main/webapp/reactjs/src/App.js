import React from "react";
import './App.css';
import PatientComponent from "./components/PatientComponent";
import {render} from "@testing-library/react";

function App() {
        return (
            <div>
                <PatientComponent/>
            </div>
        );
}

export default App;
