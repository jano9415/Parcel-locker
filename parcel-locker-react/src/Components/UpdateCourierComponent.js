import React, { useEffect, useState } from 'react';
import { Form, useFormik } from 'formik';
import { Box, Button, FormControl, FormControlLabel, FormLabel, InputLabel, MenuItem, Radio, RadioGroup, Select, TextField, Typography } from '@mui/material';
import * as Yup from 'yup';
import ParcelLockerService from '../Service/ParcelLockerService';
import AuthService from '../Service/AuthService';
import ParcelService from '../Service/ParcelService';
import { useNavigate, useParams } from 'react-router-dom';
import StoreService from '../Service/StoreService';
import CourierService from '../Service/CourierService';

const UpdateCourierComponent = () => {

    const { courierId } = useParams();

    const [stores, setStores] = useState([{}]);
    const [emailOrPasswordAlredyExistMessage, setEmailOrPasswordAlredyExistMessage] = useState("");
    const [courier, setCourier] = useState({});





    let navigate = useNavigate();

    useEffect(() => {


        //Futár lekérése a parcel handler service-ből
        //Fromik változók inicializálása a kérés változóival
        CourierService.findCourierById(courierId).then((response) => {
            setCourier(response.data);

            formik.setValues({
                id: response.data.id,
                uniqueCourierId: response.data.uniqueCourierId || '',
                firstName: response.data.firstName || '',
                lastName: response.data.lastName || '',
                storeId: response.data.storeId
            });

        },
            (error) => {

            })



        //Központi raktárak lekérése
        StoreService.getStores().then((response) => {
            setStores(response.data);
        },
            (error) => {

            })

    }, [])






    const formik = useFormik({
        initialValues: {
            //Ez az id mező a courier válasz objektumból jön
            id: '',
            uniqueCourierId: '',
            password: '',
            firstName: '',
            lastName: '',
            storeId: '',

        },
        validationSchema: Yup.object({
            uniqueCourierId: Yup.string()
                .required("Add meg a futár azonosítóját"),
            firstName: Yup.string()
                .required("Add meg a futár keresztnevét"),
            lastName: Yup.string()
                .required("Add meg a futár vezetéknevét"),
            storeId: Yup.string()
                .required("Add meg a futár körzetét")

        }),
        onSubmit: (values) => {
            //Futár valamely adatának módosítása
            CourierService.updateCourier(values).then((response) => {

                if (response.data.message === "notFound") {

                }

                if (response.data.message === "uidExists") {
                    setEmailOrPasswordAlredyExistMessage("Ez a futár azonosító már regisztrálva van");
                }


                if (response.data.message === "successfulUpdating") {

                    navigate("/getcouriers");
                }

            },
                (error) => {

                    //Tranzakciós hiba a szerver oldalon
                    setEmailOrPasswordAlredyExistMessage("Ez a jelszó már regisztrálva van. A jelszó egyben a futár rfid azonosítója is");


                })

        }
    });


    return (

        <div>
            <form onSubmit={formik.handleSubmit}>
                <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                    <Box>
                        <Typography sx={{ fontSize: 40 }}>Futár módosítása</Typography>
                        <Box className='mt-2'>
                            <TextField
                                id='uniqueCourierId'
                                name='uniqueCourierId'
                                type="text"
                                label="Egyedi futár azonosító"
                                value={formik.values.uniqueCourierId}
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                            />
                        </Box>
                        {formik.touched.uniqueCourierId && formik.errors.uniqueCourierId && (
                            <Typography sx={{ color: 'red' }}>{formik.errors.uniqueCourierId}</Typography>
                        )
                        }
                        <Box className='mt-2'>
                            <TextField
                                id='password'
                                name='password'
                                type="password"
                                label="RFID azonosító"
                                value={formik.password}
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                            />
                        </Box>
                        {formik.touched.password && formik.errors.password && (
                            <Typography sx={{ color: 'red' }}>{formik.errors.password}</Typography>
                        )
                        }
                        <Box className='mt-2'>
                            <TextField
                                id='lastName'
                                name='lastName'
                                type="text"
                                label="Vezetéknév"
                                value={formik.values.lastName}
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                            />
                        </Box>
                        {formik.touched.lastName && formik.errors.lastName && (
                            <Typography sx={{ color: 'red' }}>{formik.errors.lastName}</Typography>
                        )
                        }
                        <Box className='mt-2'>
                            <TextField
                                id='firstName'
                                name='firstName'
                                type="text"
                                label="Keresztnév"
                                value={formik.values.firstName}
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                            />
                        </Box>
                        {formik.touched.firstName && formik.errors.firstName && (
                            <Typography sx={{ color: 'red' }}>{formik.errors.firstName}</Typography>
                        )
                        }
                        <InputLabel>Futár körzete</InputLabel>
                        <Select
                            id='storeId'
                            name='storeId'
                            value={formik.values.storeId}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            fullWidth
                        >
                            {
                                stores.map((store) => (
                                    <MenuItem key={store.id} value={store.id}>{store.county}</MenuItem>
                                ))
                            }
                        </Select>
                        {
                            formik.touched.storeId && formik.errors.storeId && (
                                <Typography sx={{ color: 'red' }}>{formik.errors.storeId}</Typography>
                            )
                        }
                        <Box>
                            <Button disabled={!formik.isValid} type='submit'>Küldés</Button>
                        </Box>
                        {emailOrPasswordAlredyExistMessage && <Typography sx={{ color: 'red', marginBottom: '16px' }}>{emailOrPasswordAlredyExistMessage}</Typography>}
                    </Box>
                </Box>
            </form>




        </div>

    );
}

export default UpdateCourierComponent;