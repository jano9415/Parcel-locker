import './App.css';
import { BrowserRouter as Router, Link, Route, Routes } from 'react-router-dom';
import HomeComponent from './Components/HomeComponent';
import LoginComponent from './Components/LoginComponent';
import SignUpComponent from './Components/SignUpComponent';
import LoginCourierComponent from './Components/LoginCourierComponent';
import CreateCourierComponent from './Components/CreateCourierComponent';
import CreateAdminComponent from './Components/CreateAdminComponent';
import UserMenuComponent from './Components/UserMenuComponent';
import AdminMenuComponent from './Components/AdminMenuComponent';
import ProfileMenuComponent from './Components/ProfileMenuComponent';
import { useEffect, useState } from 'react';
import AuthService from './Service/AuthService';
import EventBus from './Service/EventBus';
import SendParcelComponent from './Components/SendParcelComponent';
import FollowParcelComponent from './Components/FollowParcelComponent';
import ParcelLockersComponent from './Components/ParcelLockersComponent';
import MyParcelsComponent from './Components/MyParcelsComponent';
import FooterMenuComponent from './Components/FooterMenuComponent';
import PricesComponent from './Components/PricesComponent';
import StatisticsComponent from './Components/StatisticsComponent';


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

        <ProfileMenuComponent />
        <UserMenuComponent />
        {
          currentUser && currentUser.roles.includes("admin") && (
            <AdminMenuComponent />
          )
        }




        <div className='container'>
          <Routes>
            <Route exact path='/' element={<HomeComponent />} />
            <Route path='/login' element={<LoginComponent />} />
            <Route path='/login/:signUpActivationCode' element={<LoginComponent />} />
            <Route path='signup' element={<SignUpComponent />} />
            <Route path='/logincourier' element={<LoginCourierComponent />} />
            <Route path='/createcourier' element={<CreateCourierComponent />} />
            <Route path='/createadmin' element={<CreateAdminComponent />} />
            <Route path='/parcelsending' element={<SendParcelComponent />} />
            <Route path='/followparcel' element={<FollowParcelComponent />} />
            <Route path='/parcellockers' element={<ParcelLockersComponent />} />
            <Route path='/myparcels' element={<MyParcelsComponent />} />
            <Route path='/prices' element={<PricesComponent />} />
            <Route path='/statistics' element={<StatisticsComponent />} />

          </Routes>
        </div>

        <footer>
          <FooterMenuComponent />
        </footer>

      </Router>

    </div>
  );
}



export default App;

