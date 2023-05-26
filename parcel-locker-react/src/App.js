import { useEffect, useState } from 'react';
import './App.css';
import AuthService from './Service/AuthService';
import EventBus from './Service/EventBus';
import { BrowserRouter as Router, Link, Route, Routes } from 'react-router-dom';
import HomeComponent from './Components/HomeComponent';
import LoginComponent from './Components/LoginComponent';
import SignUpComponent from './Components/SignUpComponent';
import LoginCourierComponent from './Components/LoginCourierComponent';
import postlogo from './postlogo.png';
import parcellockerimage from './parcellockerimage.jpg'
import CreateCourierComponent from './Components/CreateCourierComponent';
import CreateAdminComponent from './Components/CreateAdminComponent';
import UserMenuComponent from './Components/UserMenuComponent';


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

        <div style={styles.menu1}>
          <div style={styles.logo}>
            <Link to={"/"} className='nav-link'>
              <img src={postlogo} alt="Logo" height="40" />
            </Link>
          </div>
          <div style={styles.loginButton}>
            {currentUser ? (
              <Link to={"/login"} className='nav-link' onClick={logOut}>
                <span>Kijelentkezés</span>
              </Link>
            ) :
              (
                <Link to={"/login"} className='nav-link'>
                  <span>Bejelentkezés</span>
                </Link>
              )
            }
          </div>
          <div>
            {!currentUser && (
              <Link to={"/signup"} className='nav-link'>
                <span>Regisztráció</span>
              </Link>
            )}
          </div>
        </div>

        <UserMenuComponent />

        <div style={styles.menu2}>

          <div style={styles.menuItem}>
            {!currentUser && (
              <Link to={"/logincourier"} className='nav-link'>
                <span>Futár bejelentkezés</span>
              </Link>
            )}

          </div>
          <div style={styles.menuItem}>
            <Link to={"/logincourier"} className='nav-link'>
              <span>Csomagküldés</span>
            </Link>

          </div>
          <div style={styles.menuItem}>
            <Link to={"/"} className='nav-link'>
              <span>Csomagkövetés</span>
            </Link>

          </div>
          <div style={styles.menuItem}>
            <Link to={"/createadmin"} className='nav-link'>
              <span>Csomagautomaták</span>
            </Link>

          </div>
          <div style={styles.menuItem}>
            <Link to={"/createcourier"} className='nav-link'>
              <span>Futár hozzáadása</span>
            </Link>
          </div>
          <div style={styles.menuItem}>
            <Link to={"/createadmin"} className='nav-link'>
              <span>Admin hozzáadása</span>
            </Link>
          </div>
        </div>

        <nav className='navbar navbar-expand navbar-light bg-light'>
          <div className='navbar-nav mr-auto'>
            {!currentUser && (
              <li>
                <Link to={"/signup"} className='nav-link'>
                  <span>Regisztráció</span>
                </Link>
              </li>
            )}
            {!currentUser && (
              <li>
                <Link to={"/logincourier"} className='nav-link'>
                  <span>Futár bejelentkezés</span>
                </Link>
              </li>
            )}
            <li>
              <Link to={"/logincourier"} className='nav-link'>
                <span>Csomagküldés</span>
              </Link>
            </li>
            <li>
              <Link to={"/"} className='nav-link'>
                <span>Csomagkövetés</span>
              </Link>
            </li>
            <li>
              <Link to={"/createadmin"} className='nav-link'>
                <span>Csomagautomaták</span>
              </Link>
            </li>
            <li>
              <Link to={"/createcourier"} className='nav-link'>
                <span>Futár hozzáadása</span>
              </Link>
            </li>
            <li>
              <Link to={"/createadmin"} className='nav-link'>
                <span>Admin hozzáadása</span>
              </Link>
            </li>

          </div>
        </nav>


        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', backgroundColor: '#decf02' }}>
          <img style={{ width: '40%' }} src={parcellockerimage} alt="" />
          <img style={{ width: '40%' }} src={parcellockerimage} alt="" />
        </div>

        <div className='container'>
          <Routes>
            <Route exact path='/' element={<HomeComponent />} />
            <Route path='/login' element={<LoginComponent />} />
            <Route path='/login/:signUpActivationCode' element={<LoginComponent />} />
            <Route path='signup' element={<SignUpComponent />} />
            <Route path='/logincourier' element={<LoginCourierComponent />} />
            <Route path='/createcourier' element={<CreateCourierComponent />} />
            <Route path='/createadmin' element={<CreateAdminComponent />} />

          </Routes>
        </div>

        <footer>
          <nav>
            <div className='navbar-nav mr-auto'>
              <li>
                <Link to={"/logincourier"} className='nav-link'>
                  <span>Futár bejelentkezés</span>
                </Link>
              </li>
            </div>
          </nav>
        </footer>

      </Router>

    </div>
  );
}

const styles = {
  menu1: {
    backgroundColor: '#0b07f0',
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    height: '80px',
    padding: '0 20px',
    color: 'white',
  },
  logo: {
    marginRight: 'auto',
    cursor: 'pointer',
  },
  loginButton: {
    marginLeft: 'auto',
    marginRight: '9px',
    cursor: 'pointer',
  },
  menu2: {
    backgroundColor: '#eeedfa',
    display: 'flex',
    justifyContent: 'space-around',
    alignItems: 'center',
    height: '50px',
    padding: '0 20px',
  },
  menuItem: {
    color: 'black',
    cursor: 'pointer',
    fontFamily: 'Arial, sans-serif',
    fontWeight: 'bold',
    fontSize: '16px',
    padding: '5px 10px',
    borderRadius: '5px',
  },
};

export default App;
