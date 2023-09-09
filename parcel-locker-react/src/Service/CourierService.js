import axios from "axios";
import authHeader from "./AuthHeader";


const API_URL = "http://localhost:8080/parcelhandler/courier/";

//Összes futár lekérése
//Jwt token szükséges
//Admin szerepkör szükséges
const getCouriers = () => {
    return axios.get(API_URL + "getcouriers",
        { headers: authHeader() });
}


const CourierService = {
    getCouriers,

};

export default CourierService;