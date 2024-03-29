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
import QuestionsComponent from './Components/QuestionsComponent';
import GetCouriersComponent from './Components/GetCouriersComponent';
import UpdateCourierComponent from './Components/UpdateCourierComponent';
import GetParcelsOfStoreComponent from './Components/GetParcelsOfStoreComponent';
import GetParcelsOfCourierComponent from './Components/GetParcelsOfCourierComponent';
import GetParcelsOfParcelLockerComponent from './Components/GetParcelsOfParcelLockerComponent';
import LoginWithSecondFactorComponent from './Components/LoginWithSecondFactorComponent';
import MyProfileSettingsComponent from './Components/MyProfileSettingsComponent';
import PersonalDataComponent from './Components/PersonalDataComponent';
import UpdateUserPasswordComponent from './Components/UpdateUserPasswordComponent';
import ForgotPasswordComponent from './Components/ForgotPasswordComponent';


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
            <Route path='/questions' element={<QuestionsComponent />} />
            <Route path='/getcouriers' element={<GetCouriersComponent />} />
            <Route path='/updatecourier/:courierId' element={<UpdateCourierComponent />} />
            <Route path='/getparcelsofstore' element={<GetParcelsOfStoreComponent />} />
            <Route path='/getparcelsofcourier' element={ <GetParcelsOfCourierComponent /> } />
            <Route path='/getparcelsofparcellocker' element={ <GetParcelsOfParcelLockerComponent /> } />
            <Route path='/loginwithsecondfactor' element={ <LoginWithSecondFactorComponent /> } />
            <Route path='/myprofilesettings' element={ <MyProfileSettingsComponent /> } />
            <Route path='/personaldata' element={ <PersonalDataComponent /> } />
            <Route path='/updatepassword' element={ <UpdateUserPasswordComponent /> } />
            <Route path='/forgotpassword' element={ <ForgotPasswordComponent /> } />
            

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

