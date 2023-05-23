import { useEffect, useState } from 'react';
import './App.css';
import AuthService from './Service/AuthService';
import EventBus from './Service/EventBus';
import { BrowserRouter as Router, Link, Route, Routes } from 'react-router-dom';
import HomeComponent from './Components/HomeComponent';
import LoginComponent from './Components/LoginComponent';
import SignUpComponent from './Components/SignUpComponent';
import LoginCourierComponent from './Components/LoginCourierComponent';

const App = () => {


  const [currentUser, setCurrentUser] = useState(undefined);

  useEffect(() => {

    //Aktuálisan bejelentkezett felhasználó lekérése.
    const user = AuthService.getCurrentUser();

    if (user) {
      setCurrentUser(user);
    }

    EventBus.on("logout", () => {
      logOut();
    });

    return () => {
      EventBus.remove("logout");
    };
  }, []);

  const logOut = () => {
    AuthService.logOut();
    setCurrentUser(undefined);
  };

  return (
    <div>

      <Router>
        <nav className='navbar navbar-expand navbar-dark bg-dark'>
          <div className='navbar-nav mr-auto'>
            <li>
              <Link to={"/"} className='nav-link'>
                <span>Kezdőlap</span>
              </Link>
            </li>
            {!currentUser && (
              <li>
                <Link to={"/signup"} className='nav-link'>
                  <span>Regisztráció</span>
                </Link>
              </li>
            )}

            {currentUser ? (
              <li>
                <Link to={"/login"} className='nav-link' onClick={logOut}>
                  <span>Kijelentkezés</span>
                </Link>
              </li>

            ) :
              (
                <li>
                  <Link to={"/login"} className='nav-link'>
                    <span>Bejelentkezés</span>
                  </Link>
                </li>
              )
            }
            {!currentUser && (
              <li>
                <Link to={"/logincourier"} className='nav-link'>
                  <span>Futár bejelentkezés</span>
                </Link>
              </li>

            )}

          </div>
        </nav>

        <div className='container'>
          <Routes>
            <Route exact path='/' element={<HomeComponent />} />
            <Route path='/login' element={<LoginComponent />} />
            <Route path='/login/:signUpActivationCode' element={<LoginComponent />} />
            <Route path='signup' element={<SignUpComponent />} />
            <Route path='logincourier' element={<LoginCourierComponent />} />
          </Routes>
        </div>

      </Router>



    </div>
  );
}

export default App;
