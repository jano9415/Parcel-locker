import axios from 'axios';
import authHeader from './AuthHeader';
import Cookies from 'js-cookie';

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
        Cookies.set('user', JSON.stringify(response.data));
      }

      return response.data;
    });
};

//Bejelentkezés a második faktorral
const loginWithSecondFactor = async (formValues) => {
  return axios
    .post(API_URL + "loginwithsecondfactor", formValues
    )
    .then((response) => {
      if (response.data.token) {
        Cookies.set('user', JSON.stringify(response.data));
      }

      return response.data;
    });
};


//Futár bejelentkezés
//Nem fog kelleni
/*
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
*/

//Regisztráció aktiválása
const signUpActivation = (signUpActivationCode) => {
  return axios.get(API_URL + "activation/" + signUpActivationCode)

}



//Aktuálisan bejelentkezett felhasználó lekérése cookie-ből key szerint.
const getCurrentUser = () => {
  try {
    return JSON.parse(Cookies.get('user'));
  } catch (error) {

  }
};

//Kijelentkezés
const logOut = () => {
  Cookies.remove('user');
}

//Új futár létrehozása
//Admin szerepkör szükséges
const createCourier = (formValues) => {
  return axios.post(API_URL + "createcourier", formValues,
    { headers: authHeader() });
};

//Új admin létrehozása
//Admin szerepkör szükséges
const createAdmin = (emailAddress, password) => {
  return axios.post(API_URL + "createadmin", {
    emailAddress,
    password,
  },
    { headers: authHeader() });
};

//Személyes adatok lekérése
//User szerepkör szükséges
const getPersonalData = (emailAddress) => {
  return axios.get(API_URL + "getpersonaldata/" + emailAddress,
    { headers: authHeader() });
};


//Jelszó módosítása
//User szerepkör szükséges
const updateUserPassword = (formValues) => {
  return axios.put(API_URL + "updateuserpassword", formValues,
    { headers: authHeader() });
}

//Elfelejtett jelszó. Új jelszó küldése email-ben
//Nem szükséges jwt token
const forgotPassword = (emailAddress) => {
  return axios.put(API_URL + "forgotpassword/" + emailAddress)
}


const AuthService = {
  signUp,
  logIn,
  //courierLogin,
  logOut,
  getCurrentUser,
  signUpActivation,
  createCourier,
  createAdmin,
  loginWithSecondFactor,
  getPersonalData,
  updateUserPassword,
  forgotPassword,

};

export default AuthService;