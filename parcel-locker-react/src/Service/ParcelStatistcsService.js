import axios from 'axios';
import authHeader from './AuthHeader';


const API_URL = "http://localhost:8080/statistics/parcel/";

//Összes kézbesített csomagok száma
//Jwt token szükséges
//Admin szerepkör szükséges
const numberOfParcels = () => {
    return axios.get(API_URL + "numberofparcels", {headers: authHeader()});
}

//Leggyakoribb méretű csomagok: kicsi, közepes vagy nagy
//Jwt token szükséges
//Admin szerepkör szükséges
const mostCommonParcelSize = () => {
    return axios.get(API_URL + "mostcommonparcelsize", {headers: authHeader()});
}

//Csomagok száma méret szerint
//Lista első eleme: kicsi csomagok száma
//Lista második eleme: közepes csomagok száma
//Lista harmadik eleme: nagy csomagok száma
//Jwt token szükséges
//Admin szerepkör szükséges
const numberOfParcelsBySize = () => {
    return axios.get(API_URL + "numberofparcelsbysize", {headers: authHeader()});
}

//Összes bevétel a kézbesített csomagokból
//Jwt token szükséges
//Admin szerepkör szükséges
const totalRevenue = () => {
    return axios.get(API_URL + "totalrevenue", {headers: authHeader()});
}

//Csomagok értékének átlaga forintban
//Jwt token szükséges
//Admin szerepkör szükséges
const averageParcelValue = () => {
    return axios.get(API_URL + "averageparcelvalue", {headers: authHeader()});
}

//Feladott csomagok száma aszerint, hogy automatából vagy online adják fel
//Lista első eleme: automatából
//Lista második eleme: online
//Jwt token szükséges
//Admin szerepkör szükséges
const amountOfParcelsFromOnlineAndParcelLocker = () => {
    return axios.get(API_URL + "amountofparcelsfromonlineandparcellocker", {headers: authHeader()});
}

//Honnan adják fel a legtöbb csomagot?
//Jwt token szükséges
//Admin szerepkör szükséges
const mostCommonSendingLocation = () => {
    return axios.get(API_URL + "mostcommonsendinglocation", {headers: authHeader()});
}

//Hova érkezik a legtöbb csomag?
//Jwt token szükséges
//Admin szerepkör szükséges
const mostCommonReceivingLocation = () => {
    return axios.get(API_URL + "mostcommonreceivinglocation", {headers: authHeader()});
}


const ParcelStatistcsService = {
    numberOfParcels,
    mostCommonParcelSize,
    numberOfParcelsBySize,
    totalRevenue,
    averageParcelValue,
    amountOfParcelsFromOnlineAndParcelLocker,
    mostCommonSendingLocation,
    mostCommonReceivingLocation

};

export default ParcelStatistcsService;