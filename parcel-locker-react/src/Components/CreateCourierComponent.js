import React, { useEffect, useState } from 'react';
import AuthService from '../Service/AuthService';
import { Link, useNavigate } from 'react-router-dom';
import PhoneInput from 'react-phone-input-2';
import { TextField, Button, Typography, Box } from '@mui/material';

const CreateCourierComponent = () => {
    const [uniqueCourierId, setUniqueCourierId] = useState("");
    const [password, setPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");


    const [emailOrPasswordAlredyExistMessage, setEmailOrPasswordAlredyExistMessage] = useState("");


    const [isEveryValid, setIsEveryValid] = useState(false)
    const [everyInputMessage, setEveryInputMessage] = useState("")
    const [sendButtonEnable, setSendButtonEnable] = useState(true)


    let navigate = useNavigate();

    useEffect(() => {

    }, [])


    //Új futár létrehozása
    const signUp = (e) => {
        e.preventDefault();

        if (formValidaton()) {
            AuthService.createCourier(uniqueCourierId, password, firstName, lastName).then((response) => {
                if (response.data.message === "uidExists") {
                    setEmailOrPasswordAlredyExistMessage("Ez a futár azonosító már regisztrálva van");
                }
                if (response.data.message === "passwordExists") {
                    setEmailOrPasswordAlredyExistMessage("Ez a jelszó már regisztrálva van. A jelszó egyben a futár rfid azonosítója is");
                }
                if (response.data.message === "successCourierCreation") {

                    navigate("/login");
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



    //Minden mező kitöltése kötelező
    const formValidaton = () => {

        const isFirstNameNotEmpty = firstName.length > 0;
        const isLastNameNotEmpty = lastName.length > 0;
        const isUniqueCourierIdNotEmpty = uniqueCourierId.length > 0;
        const isPasswordNotEmpty = password.length > 0;

        if (
            isFirstNameNotEmpty &&
            isLastNameNotEmpty &&
            isUniqueCourierIdNotEmpty &&
            isPasswordNotEmpty
        ) {
            setIsEveryValid(true);
            return true;
        } else {
            setIsEveryValid(false);
            return false;
        }
    };

    const showSendButton = () => {
        if (formValidaton()) {
            setSendButtonEnable(false)
        }
    }


    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', padding: '16px' }}>
            <Typography variant="h4" sx={{ marginBottom: '16px' }}>Új futár hozzáadása</Typography>
            <TextField
                type="text"
                label="Futár azonosító"
                value={uniqueCourierId}
                onChange={(e) => setUniqueCourierId(e.target.value)}
                required
                sx={{ marginBottom: '16px' }}
            />
            <TextField
                type="password"
                label="Jelszó"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
                sx={{ marginBottom: '16px' }}
            />
            <TextField
                type="text"
                label="Vezetéknév"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
                required
                sx={{ marginBottom: '16px' }}
            />
            <TextField
                type="text"
                label="Keresztnév"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
                required
                sx={{ marginBottom: '16px' }}
                onBlur={showSendButton}
            />
            <Button disabled={sendButtonEnable} variant="contained" onClick={(e) => signUp(e)}>Küldés</Button>
            {everyInputMessage && <Typography sx={{ color: 'red', marginBottom: '16px' }}>{everyInputMessage}</Typography>}
            {emailOrPasswordAlredyExistMessage && <Typography sx={{ color: 'red', marginBottom: '16px' }}>{emailOrPasswordAlredyExistMessage}</Typography>}
        </Box>
    );
}

export default CreateCourierComponent;