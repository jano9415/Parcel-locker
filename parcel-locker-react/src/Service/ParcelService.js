import axios from 'axios';
import authHeader from './AuthHeader';
import Cookies from 'js-cookie';
import AuthService from '../Service/AuthService';

const API_URL = "http://localhost:8080/parcelhandler/parcel/";


//Csomag küldése a weblapról feladási kóddal
//Ez még csak egy előzetes csomagfeladás. A felhasználó megkapja email-ben a csomagfeladási kódot
//A végleges csomagfeladás az automatánál történik
//Jwt token szükséges
const sendParcelWithCodeFromWebpage = (formValues) => {
    return axios.post(API_URL + "sendparcelwithcodefromwebpage", formValues,
        { headers: authHeader() });
};

//Csomag követése
const followParcel = (uniqueParcelId) => {
    return axios.get(API_URL + "followparcel/" + uniqueParcelId);
}

//Felhasználó csomagjainak lekérése
//Típusok:
//all - összes csomag
//reserved - online feladott, automatában még nem elhelyezett csomagok
//notPickedUp - még át nem vett csomagok. Szállítás alatti csomagok
//pickedUp - átvett csomagok. Sikeresen lezárt küldések
//Jwt token szükséges
//User szerepkör szükséges
const getParcelsOfUser = (type) => {
    return axios.get(API_URL + "getparcelsofuser/" + AuthService.getCurrentUser().emailAddress + "/" + type,
        { headers: authHeader() });
}

//Csomag törlése
//Felhasználó kitörli az előzetes csomagfeladást
//Jwt token szükséges
//User szerepkör szükséges
const deleteMyParcel = (parcelId) => {
    return axios.delete(API_URL + "deletemyparcel/" + parcelId,
        { headers: authHeader() });
}


const ParcelService = {
    sendParcelWithCodeFromWebpage,
    followParcel,
    getParcelsOfUser,
    deleteMyParcel

};

export default ParcelService;