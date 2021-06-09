import axios from "axios";

const PATIENTS_REST_URL = 'http://localhost:8080';

class PatientService {

    getPatients(){
        axios.get(PATIENTS_REST_URL)
    }
}

export default new PatientService();