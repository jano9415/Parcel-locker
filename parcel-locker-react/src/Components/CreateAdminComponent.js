import React, { useEffect, useState } from 'react';
import AuthService from '../Service/AuthService';
import { Link, useNavigate } from 'react-router-dom';
import PhoneInput from 'react-phone-input-2';
import { TextField, Button, Typography, Box } from '@mui/material';


const CreateAdminComponent = () => {
    const [emailAddress, setEmailAddress] = useState("")
    const [password, setPassword] = useState("")
    const [passwordAgain, setPasswordAgain] = useState("")

    const [passwordMessage, setPasswordMessage] = useState("")
    const [passwordErrorMessage, setPasswordErrorMessage] = useState("")
    const [emailAddressErrorMessage, setEmailAddressErrorMessage] = useState("")
    const [emailAlredyExistMessage, setEmailAlredyExistMessage] = useState("");

    const [isPasswordValid, setIsPasswordValid] = useState(false)
    const [isEmailValid, setIsEmailValid] = useState(false)
    const [isEveryValid, setIsEveryValid] = useState(false)
    const [everyInputMessage, setEveryInputMessage] = useState("")
    const [sendButtonEnable, setSendButtonEnable] = useState(true)


    let navigate = useNavigate();

    useEffect(() => {


    }, [])

    const signUp = (e) => {
        e.preventDefault();

        if (formValidaton()) {
            AuthService.createAdmin(emailAddress, password).then((response) => {
                if (response.data.message === "emailExists") {
                    setEmailAlredyExistMessage("Ez az admin email cím már regisztrálva van")

                }
                if (response.data.message === "successAdminCreation") {
                    window.location.reload();
                }

            },
                (error) => {

                })
        }
        else {
            setEveryInputMessage("Minden mező kitöltése kötelező.")

        }

    }

    //Jelszó validációja, a jelszónak erősnek kell lennie és a két jelszónak meg kell egyeznie
    const passwordValidation = (e) => {
        e.preventDefault();

        // Validáció
        const passwordPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

        if (!passwordPattern.test(password)) {
            setIsPasswordValid(false)
            setPasswordErrorMessage('A jelszónak legalább 8 karakterből kell állnia, tartalmaznia kell nagybetűt, kisbetűt, számot és speciális karaktert.');
            setPasswordMessage("")
            return;
        }

        if (password !== passwordAgain) {
            setIsPasswordValid(false)
            setPasswordErrorMessage('A két jelszó nem egyezik meg.');
            setPasswordMessage("")
            return;
        }

        // Sikeres ellenőrzés
        setIsPasswordValid(true)
        setPasswordErrorMessage("")
        setPasswordMessage('A jelszó megfelelő.');
        showSendButton()
    };

    //Email formátum validációja. Az email-nek valósnak kell lennie
    const emailAddressValidation = (e) => {
        e.preventDefault()

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        //Email nem megfelelő formátumú
        if (!emailRegex.test(emailAddress)) {
            setIsEmailValid(false)
            setEmailAddressErrorMessage('Nem valódi email cím.')
            return

        }

        //Sikeres ellenőrzés
        setIsEmailValid(true)
        setEmailAddressErrorMessage("")
    }



    //Minden mező kitöltése kötelező
    const formValidaton = () => {

        const isEmailNotEmpty = emailAddress.length > 0;
        const isPasswordNotEmpty = password.length > 0;



        if (
            isEmailValid &&
            isPasswordValid &&
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

    //OnBlur esemény
    const showSendButton = () => {
        setSendButtonEnable(false)
    }

    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', padding: '16px' }}>
            <Typography sx={{ marginBottom: '16px', fontSize: 40 }}>Új admin hozzáadása</Typography>
            <TextField
                type="email"
                label="Email cím"
                value={emailAddress}
                onChange={(e) => setEmailAddress(e.target.value)}
                onBlur={emailAddressValidation}
                sx={{ marginBottom: '16px' }}
            />
            {emailAddressErrorMessage && <Typography sx={{ color: 'red', marginBottom: '16px' }}>{emailAddressErrorMessage}</Typography>}
            <TextField
                type="password"
                label="Jelszó"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                sx={{ marginBottom: '16px' }}
            />
            <TextField
                type="password"
                label="Jelszó megerősítése"
                value={passwordAgain}
                onBlur={passwordValidation}
                onChange={(e) => setPasswordAgain(e.target.value)}
                sx={{ marginBottom: '16px' }}
            />
            {passwordErrorMessage && <Typography sx={{ color: 'red', marginBottom: '16px' }}>{passwordErrorMessage}</Typography>}
            {passwordMessage && <Typography sx={{ color: 'green', marginBottom: '16px' }}>{passwordMessage}</Typography>}
            <Button disabled={sendButtonEnable} variant="contained" onClick={(e) => signUp(e)}>Küldés</Button>
            {everyInputMessage && <Typography sx={{ color: 'red', marginBottom: '16px' }}>{everyInputMessage}</Typography>}
            {emailAlredyExistMessage && <Typography sx={{ color: 'red', marginBottom: '16px' }}>{emailAlredyExistMessage}</Typography>}
        </Box>
    );
}

export default CreateAdminComponent;