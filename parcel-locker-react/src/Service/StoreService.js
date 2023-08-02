import axios from 'axios';
import authHeader from './AuthHeader';
import Cookies from 'js-cookie';

const API_URL = "http://localhost:8080/parcelhandler/store/";


const getStores = () => {
    return axios.get(API_URL + "getstores" , {headers: authHeader()});
}

const StoreService = {
    getStores,

};

export default StoreService;