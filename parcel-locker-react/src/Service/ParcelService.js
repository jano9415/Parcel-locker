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



const ParcelService = {
    sendParcelWithCodeFromWebpage,

};

export default ParcelService;