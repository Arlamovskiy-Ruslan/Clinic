import React from "react";
import './App.css';
import Patients from "./components/Patients";
import {render} from "@testing-library/react";

function App() {
        return (
            <div>
                <Patients/>
            </div>
        );
}

export default App;
