import axios from 'axios';
import Cookies from 'js-cookie';
import authHeader from '../AuthHeader';

const API_URL = "http://localhost:8080/parcelhandler/user/";



//Személyes adatok lekérése
//User szerepkör szükséges
const getPersonalData = (emailAddress) => {
    return axios.get(API_URL + "getpersonaldata/" + emailAddress,
        { headers: authHeader() });
};

//Személyes adatok módosítása
//User szerepkör szükséges
const updateUser = (formValues) => {
    return axios.put(API_URL + "updateuser", formValues,
        { headers: authHeader() });
}

//Jelszó módosítása
//User szerepkör szükséges
const updateUserPassword = (formValues) => {
    return axios.put(API_URL + "updateuserpassword", formValues,
    { headers: authHeader() });
}


const UserService = {
    getPersonalData,
    updateUser,

};

export default UserService;