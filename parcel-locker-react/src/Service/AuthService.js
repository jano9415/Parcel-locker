import axios from 'axios';

const API_URL = "http://localhost:8080/auth/";

//Regisztráció ideiglenes
const signUp = (emailAddress, password, firstName, lastName) => {
  return axios.get(API_URL + "signup", {
    params : {
      emailAddress : emailAddress,
      password : password,
      firstName : firstName,
      lastName : lastName
    }
  });
};

//Regisztráció
const signUpPost = (emailAddress, password, firstName, lastName) => {
  return axios.post(API_URL + "signuppost", {
    emailAddress,
    password,
    firstName,
    lastName
  });
};

//Bejelentkezés ideiglenes
const logIn = async (emailAddress, password) => {
  return axios
    .get(API_URL + "login", {
      params : {
        emailAddress : emailAddress,
        password : password
      }
    })
    .then((response) => {
      if (response.data.token) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }

      return response.data;
    });
};

//Bejelentkezés
const logInPost = async (emailAddress, password) => {
  return axios
    .post(API_URL + "loginpost", {
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


//Futár bejelentkezés ideiglenes
const courierLogin = async (uniqueCourierId) => {
  return axios
    .get(API_URL + "courierlogin",
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

//Futár bejelentkezés
const courierLoginPost = async (uniqueCourierId) => {
  return axios
    .post(API_URL + "courierloginpost", {

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

const testAuth = () => {
  return axios.get(API_URL + "testauth");
}

const testPost = (number) => {

  return axios.post(API_URL + "testpost", {
    params: {
      number: number
    }
  });
}



const AuthService = {
  signUp,
  signUpPost,
  logIn,
  logInPost,
  courierLogin,
  logOut,
  getCurrentUser,
  testAuth,
  testPost,
  courierLoginPost,


};

export default AuthService;