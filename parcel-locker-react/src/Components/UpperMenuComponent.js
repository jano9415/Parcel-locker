import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import postlogo from '../postlogo.png'
import AuthService from '../Service/AuthService';
import EventBus from '../Service/EventBus';
import ProfileMenuComponent from './ProfileMenuComponent';
import { Box, Tab, Tabs } from '@mui/material';

const UpperMenuComponent = () => {

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




    const [value, setValue] = useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };



    

    return (
        <div>
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
                <ProfileMenuComponent />
            </div>


            <Box sx={{ width: '100%', height: '30%', bgcolor: 'blue' }}>
            <Tabs value={value} onChange={handleChange} centered >

                <Tab label={<Link to={"/logincourier"} className='nav-link'>Bejelentkezés</Link>} />
                <Tab label={<Link to={"/"} sx={{ color: 'white' }} className='nav-link'>Regisztráció</Link>} />
            </Tabs>
        </Box>
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
};

export default UpperMenuComponent;