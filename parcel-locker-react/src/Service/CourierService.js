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

//Futár lekérése id alapján
//Jwt token szükséges
//Admin szerepkör szükséges
const findCourierById = (courierId) => {
    return axios.get(API_URL + "findcourierbyid/" + courierId,
        { headers: authHeader() });
}

//Futár valamely adatának módosítása
//Jwt token szükséges
//Admin szerepkör szükséges
const updateCourier = (formValues) => {
    return axios.put(API_URL + "updatecourier", formValues,
        { headers: authHeader() });
}


const CourierService = {
    getCouriers,
    findCourierById,
    updateCourier

};

export default CourierService;