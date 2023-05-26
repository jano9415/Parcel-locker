import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import AuthService from '../Service/AuthService';
import { TextField, Button, Typography, Box } from '@mui/material';

const LoginComponent = () => {

    let navigate = useNavigate();

    const { signUpActivationCode } = useParams();

    const [emailAddress, setEmailAddress] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const [signUpActivationMessage, setSignUpActivationMessage] = useState("");
    const [signUpActivationErrorMessage, setSignUpActivationErrorMessage] = useState("");



    const [isEveryValid, setIsEveryValid] = useState(false)
    const [everyInputMessage, setEveryInputMessage] = useState("")


    //Bejelentkezés kezelése
    const logIn = (e) => {
        e.preventDefault();

        setErrorMessage("");
        setLoading(true);


        if (formValidaton()) {
            AuthService.logIn(emailAddress, password).then(
                () => {
                    navigate("/");
                    window.location.reload();
                },
                (error) => {
                    setLoading(false);
                    setErrorMessage(error.response.data)

                }
            )

        }
        else {
            setLoading(false)
            setEveryInputMessage("Minden mező kitöltése kötelező.")
        }


    }

    useEffect(() => {
        //Regisztráció aktiválása
        if (signUpActivationCode) {
            AuthService.signUpActivation(signUpActivationCode).then(
                (response) => {
                    setSignUpActivationMessage(response.data)

                },
                (error) => {
                    setSignUpActivationErrorMessage(error.response.data)

                }
            )
        }
    }, []);

    //Sikeres vagy sikertelen regisztráció aktiválása üzenet
    const showSignUpActivationMessage = () => {

        if (signUpActivationMessage) {
            return (
                <div className="form-group">
                    <div className="alert alert-success" role="alert">
                        {signUpActivationMessage}
                    </div>
                </div>
            )
        }

        if (signUpActivationErrorMessage) {
            return (
                <div className="form-group">
                    <div className="alert alert-danger" role="alert">
                        {signUpActivationErrorMessage}
                    </div>
                </div>
            )
        }
    }

    //Minden mező kitöltése kötelező
    const formValidaton = () => {

        const isEmailNotEmpty = emailAddress.length > 0;
        const isPasswordNotEmpty = password.length > 0;

        if (
            isEmailNotEmpty &&
            isPasswordNotEmpty
        ) {
            setIsEveryValid(true);
            return true;
        } else {
            setIsEveryValid(false);
            return false;
        }
    };



    return (
        <div>
            <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', padding: '16px' }}>
                <Typography variant="h4" sx={{ marginBottom: '16px' }}>Bejelentkezés</Typography>
                <TextField
                    type="email"
                    label="Email cím"
                    value={emailAddress}
                    onChange={(e) => setEmailAddress(e.target.value)}
                    sx={{ marginBottom: '16px' }}
                />
                <TextField
                    type="password"
                    label="Jelszó"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    sx={{ marginBottom: '16px' }}
                />
                <Button variant="contained" onClick={(e) => logIn(e)}
                    disabled={loading}>
                    {loading && (
                        <span className="spinner-border spinner-border-sm"></span>
                    )}
                    <span>Bejelentkezés</span>
                </Button>
                {everyInputMessage && <Typography sx={{ color: 'red', marginBottom: '16px' }}>{everyInputMessage}</Typography>}
                {errorMessage && (
                    <div className="form-group">
                        <div className="alert alert-danger" role="alert">
                            {errorMessage}
                        </div>
                    </div>
                )}
                {showSignUpActivationMessage()}
            </Box>
        </div>
    );
}

export default LoginComponent;