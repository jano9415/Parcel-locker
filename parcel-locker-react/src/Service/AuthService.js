import axios from 'axios';
import authHeader from './AuthHeader';

const API_URL = "http://localhost:8080/auth/";


//Regisztráció
const signUp = (emailAddress, password, firstName, lastName, phoneNumber) => {
  return axios.post(API_URL + "signup", {
    emailAddress,
    password,
    firstName,
    lastName,
    phoneNumber
  });
};


//Bejelentkezés
const logIn = async (emailAddress, password) => {
  return axios
    .post(API_URL + "login", {
      emailAddress,
      password,
    })
    .then((response) => {
      if (response.data.token) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }

      return response.data;
    });
};


//Futár bejelentkezés
const courierLogin = async (uniqueCourierId) => {
  return axios
    .post(API_URL + "courierlogin", {

    },
      {
        params: {
          uniqueCourierId: uniqueCourierId
        },
      })
    .then((response) => {
      if (response.data.token) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }

      return response.data;
    });
};

//Regisztráció aktiválása
const signUpActivation = (signUpActivationCode) => {
  return axios.get(API_URL + "activation/" + signUpActivationCode)

}



//Aktuálisan bejelentkezett felhasználó lekérése a local storage-ból key szerint.
const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

//Kijelentkezés
const logOut = () => {
  localStorage.removeItem("user");
}

//Új futár létrehozása
const createCourier = (uniqueCourierId, password, firstName, lastName) => {
  return axios.post(API_URL + "createcourier", {
    uniqueCourierId,
    password,
    firstName,
    lastName,
  },
  {headers: authHeader()});
};

//Új admin létrehozása
const createAdmin = (emailAddress, password) => {
  return axios.post(API_URL + "createadmin", {
    emailAddress,
    password,
  },
  {headers: authHeader()});
};


const AuthService = {
  signUp,
  logIn,
  courierLogin,
  logOut,
  getCurrentUser,
  signUpActivation,
  createCourier,
  createAdmin


};

export default AuthService;