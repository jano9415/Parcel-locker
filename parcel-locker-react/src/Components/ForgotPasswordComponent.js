import { Box, Button, FormControlLabel, Switch, TextField, Typography } from "@mui/material";
import MyProfileMenuComponent from "./MyProfileMenuComponent";
import { useFormik } from "formik";
import { useEffect, useState } from "react";
import * as Yup from 'yup';
import AuthService from "../Service/AuthService";

const ForgotPasswordComponent = () => {

    const [responseMessage, setResponseMessage] = useState("");

    useEffect(() => {


    }, []);

    const formik = useFormik({
        initialValues: {
            emailAddress: '',
        },
        validationSchema: Yup.object({
            emailAddress: Yup.string()
                .required("Add meg az email címed"),


        }),
        onSubmit: (values) => {

            AuthService.forgotPassword(values.emailAddress).then(
                (response) => {

                    if (response.data.message === "newPasswordCreated") {
                        setResponseMessage("Az új jelszódat elküldtük email-ben. Bejelentkezés után változtasd meg");
                    }

                },
                (error) => {

                    if (error.response.data.message === "notFound") {
                        setResponseMessage("Ez az email cím nincs regisztrálva.");
                    }

                }
            )


        }
    });

    return (
        <Box>
            <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                <Box>
                    <Typography sx={{ fontSize: 20 }}>Add meg az email címed</Typography>
                </Box>
            </Box>

            <form onSubmit={formik.handleSubmit}>
                <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                    <Box>
                        <Box className='mt-2'>
                            <TextField
                                id='emailAddress'
                                name='emailAddress'
                                type="email"
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
                    </Box>
                </Box>

                <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                    <Box>
                        <Button disabled={!formik.isValid} type='submit'>Küldés</Button>
                        {responseMessage && <Typography sx={{ color: 'red', marginBottom: '16px' }}>{responseMessage}</Typography>}
                    </Box>
                </Box>
            </form>

        </Box>
    );
}

export default ForgotPasswordComponent;