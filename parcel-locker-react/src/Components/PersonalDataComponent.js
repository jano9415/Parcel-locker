import { Box, Button, FormControlLabel, Switch, TextField, Typography } from "@mui/material";
import MyProfileMenuComponent from "./MyProfileMenuComponent";
import { useFormik } from "formik";
import { useEffect, useState } from "react";
import * as Yup from 'yup';
import AuthService from "../Service/AuthService";
import { CheckBox } from "@mui/icons-material";
import UserService from "../Service/ParcelHandler/UserService";


const PersonalDataComponent = () => {


    const [errorMessage, setErrorMessage] = useState("");

    useEffect(() => {

        //Személyes adatok lekérése az authentication service-ből
        AuthService.getPersonalData(AuthService.getCurrentUser().emailAddress).then(
            (response) => {
                /*
                formik.setValues({
                    emailAddress: AuthService.getCurrentUser().emailAddress || '',
                    isTwoFactorAuthentication: response.data.twoFactorAuthentication,
                });
                */
                formik.setFieldValue("emailAddress", AuthService.getCurrentUser().emailAddress);
                formik.setFieldValue("isTwoFactorAuthentication", response.data.twoFactorAuthentication);

            },
            (error) => {

            }
        )

        //Személyes adatok lekérése a parcel handler service-ből
        UserService.getPersonalData(AuthService.getCurrentUser().emailAddress).then(
            (response) => {

                /*
                formik.setValues({
                    lastName: response.data.lastName || '',
                    firstName: response.data.firstName || '',
                    phoneNumber: response.data.phoneNumber || '',
                });
                */

                formik.setFieldValue("id", response.data.id);
                formik.setFieldValue("lastName", response.data.lastName);
                formik.setFieldValue("firstName", response.data.firstName);
                formik.setFieldValue("phoneNumber", response.data.phoneNumber);

            },
            (error) => {

            }
        )


    }, []);

    const formik = useFormik({
        initialValues: {
            id: '',
            emailAddress: '',
            firstName: '',
            lastName: '',
            phoneNumber: '',
            isTwoFactorAuthentication: false,

        },
        validationSchema: Yup.object({
            emailAddress: Yup.string()
                .required("Add meg az email címet")
                .email("Nem valódi email cím"),
            lastName: Yup.string()
                .required("Add meg a vezetéknevet"),
            firstName: Yup.string()
                .required("Add meg a keresztnevet"),
            phoneNumber: Yup.string()
                .required("Add meg a telefonszámot"),


        }),
        onSubmit: (values) => {

            //Személyes adatok módosítása
            UserService.updateUser(values).then(
                (response) => {

                    if(response.data.message === "successfulUpdating"){
                        console.log("sikeres");
                    }

                },
                (error) => {
                    if(error.response.data.message === "notFound"){

                    }
                    if(error.response.data.message === "emailAddressExists"){
                        setErrorMessage("Ez az email cím már regisztrálva van");
                    }

                }
            )
        }
    });

    return (
        <Box>
            <MyProfileMenuComponent></MyProfileMenuComponent>

            <form onSubmit={formik.handleSubmit}>
                <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                    <Box>
                        <Typography sx={{ fontSize: 40 }}>Személyes adatok</Typography>
                        <Box className='mt-2'>
                            <TextField
                                id='emailAddress'
                                name='emailAddress'
                                type="text"
                                label="Email cím"
                                value={formik.values.emailAddress}
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                            />
                        </Box>
                        {formik.touched.emailAddress && formik.errors.emailAddress && (
                            <Typography sx={{ color: 'red' }}>{formik.errors.emailAddress}</Typography>
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
                        <Box className='mt-2'>
                            <TextField
                                id='phoneNumber'
                                name='phoneNumber'
                                type="text"
                                label="Telefonszám"
                                value={formik.values.phoneNumber}
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                            />
                        </Box>
                        {formik.touched.phoneNumber && formik.errors.phoneNumber && (
                            <Typography sx={{ color: 'red' }}>{formik.errors.phoneNumber}</Typography>
                        )
                        }
                        <FormControlLabel control={<Switch id='isTwoFactorAuthentication'
                            name="isTwoFactorAuthentication"
                            value={formik.values.isTwoFactorAuthentication}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                        />} label="Kétfaktoros bejelentkezés">
                        </FormControlLabel>

                        <Box>
                            <Button disabled={!formik.isValid} type='submit'>Küldés</Button>
                        </Box>
                        {errorMessage && <Typography sx={{ color: 'red', marginBottom: '16px' }}>{errorMessage}</Typography>}
                    </Box>
                </Box>
            </form>
        </Box>
    );
}

export default PersonalDataComponent;