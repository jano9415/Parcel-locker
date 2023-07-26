import axios from 'axios';
import authHeader from './AuthHeader';
import Cookies from 'js-cookie';

const API_URL = "http://localhost:8080/parcelhandler/parcellocker/";

//Automaták lekérése kiválasztásra
const getParcelLockersForChoice = () => {
    return axios.get(API_URL + "getparcellockersforchoice");
}

//Rekeszek tele vannak? Kicsi, közepes, nagy rekeszek ellenőrzése.
const areBoxesFull = (senderParcelLockerId) => {
    return axios.get(API_URL + "areboxesfull/" + senderParcelLockerId);
}

  //Automata tele van?
  const isParcelLockerFull = senderParcelLockerId => {
    return axios.get(API_URL + "isparcellockerfull/" + senderParcelLockerId);
  }



const ParcelLockerService = {
    getParcelLockersForChoice,
    areBoxesFull,
    isParcelLockerFull
};

export default ParcelLockerService;