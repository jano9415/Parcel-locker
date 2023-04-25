import axios from 'axios';

const API_URL = "http://localhost:8080/auth/";


//Regisztráció
const signUp = (emailAddress, password, firstName, lastName) => {
  return axios.post(API_URL + "signup", {
    emailAddress,
    password,
    firstName,
    lastName
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


//Aktuálisan bejelentkezett felhasználó lekérése a local storage-ból key szerint.
const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

//Kijelentkezés
const logOut = () => {
  localStorage.removeItem("user");
}



const AuthService = {
  signUp,
  logIn,
  courierLogin,
  logOut,
  getCurrentUser


};

export default AuthService;