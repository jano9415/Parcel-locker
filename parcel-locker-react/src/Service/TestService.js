import axios from 'axios';
import authHeader from './AuthHeader';

const API_URL = "http://localhost:8080/notification/";

//Autentikációt igényel
const test1 = () => {
    return axios.get("http://localhost:8080/statistics/test1", {headers: authHeader()})
}

//Autentikációt igényel
const test2 = () => {
    return axios.get("http://localhost:8080/statistics/test2" , {headers: authHeader()})
}

//Nem igényel autentikációt
const test3 = () => {
    return axios.get("http://localhost:8080/notification/test1", {headers: authHeader()})
}

//Autentikációt igényel
const test4 = () => {
    return axios.get("http://localhost:8080/notification/test2" , {headers: authHeader()})
}



const TestService = {
    test1,
    test2,
    test3,
    test4

};

export default TestService;