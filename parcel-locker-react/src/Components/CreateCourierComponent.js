import React, { useEffect, useState } from 'react';
import AuthService from '../Service/AuthService';
import { Link, useNavigate } from 'react-router-dom';
import PhoneInput from 'react-phone-input-2';
import { TextField, Button, Typography, Box } from '@mui/material';

const CreateCourierComponent = () => {
    const [uniqueCourierId, setUniqueCourierId] = useState("")
    const [firstName, setFirstName] = useState("")
    const [lastName, setLastName] = useState("")


    const [emailAlredyExistMessage, setEmailAlredyExistMessage] = useState("");


    const [isEveryValid, setIsEveryValid] = useState(false)
    const [everyInputMessage, setEveryInputMessage] = useState("")
    const [sendButtonEnable, setSendButtonEnable] = useState(true)


    let navigate = useNavigate();

    useEffect(() => {

    }, [])

    const signUp = (e) => {
        e.preventDefault();

        if (formValidaton()) {
            AuthService.createCourier(uniqueCourierId, firstName, lastName).then((response) => {
                navigate("/login");
                window.location.reload();

            },
                (error) => {
                    setEmailAlredyExistMessage(error.response.data)
                })
        }
        else{
            setEveryInputMessage("Minden mező kitöltése kötelező.")

        }

    }

  

    //Minden mező kitöltése kötelező
    const formValidaton = () => {

        const isFirstNameNotEmpty = firstName.length > 0;
        const isLastNameNotEmpty = lastName.length > 0;
        const isUniqueCourierIdNotEmpty = uniqueCourierId.length > 0;

        if (
            isFirstNameNotEmpty &&
            isLastNameNotEmpty &&
            isUniqueCourierIdNotEmpty
        ) {
            setIsEveryValid(true);
            return true;
        } else {
            setIsEveryValid(false);
            return false;
        }
    };

    const showSendButton = () => {
        if(formValidaton()){
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
                sx={{ marginBottom: '16px' }}
                onBlur={showSendButton}
            />
            <Button disabled={sendButtonEnable} variant="contained" onClick={(e) => signUp(e)}>Küldés</Button>
            {everyInputMessage && <Typography sx={{ color: 'red', marginBottom: '16px' }}>{everyInputMessage}</Typography>}
            {emailAlredyExistMessage && (
                <div className="form-group">
                    <div className="alert alert-danger" role="alert">
                        {emailAlredyExistMessage}
                    </div>
                </div>
            )}
        </Box>
    );
}

export default CreateCourierComponent;