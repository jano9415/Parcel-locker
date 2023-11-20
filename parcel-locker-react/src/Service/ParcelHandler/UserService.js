import axios from 'axios';
import Cookies from 'js-cookie';
import authHeader from '../AuthHeader';

const API_URL = "http://localhost:8080/parcelhandler/user/";



//Személyes adatok lekérése
const getPersonalData = (emailAddress) => {
    return axios.get(API_URL + "getpersonaldata/" + emailAddress,
        { headers: authHeader() });
};


const UserService = {
    getPersonalData,

};

export default UserService;