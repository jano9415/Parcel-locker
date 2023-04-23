import axios from 'axios';

const API_URL = "http://localhost:8080/notification/";

//Kijelentkezés
const test = () => {
    return axios.get(API_URL + "test1")
}



const TestService = {
    test

};

export default TestService;