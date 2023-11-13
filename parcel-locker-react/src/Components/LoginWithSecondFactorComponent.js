import { Box, Button, TextField, Typography } from "@mui/material";
import { useFormik } from "formik";
import { useEffect, useState } from "react";
import * as Yup from 'yup';
import AuthService from "../Service/AuthService";
import { useNavigate } from "react-router-dom";

const LoginWithSecondFactorComponent = () => {

    const [errorMessage, setErrorMessage] = useState("");

    let navigate = useNavigate();

    useEffect(() => {


    }, []);

    const formik = useFormik({
        initialValues: {
            secondFactorCode: '',

        },
        validationSchema: Yup.object({
            secondFactorCode: Yup.string()
                .required("Add meg az emailben kapott kódot"),


        }),
        onSubmit: (values) => {


            AuthService.loginWithSecondFactor(values).then(
                (response) => {
                    if (response.emailAddress) {
                        navigate("/");
                        window.location.reload();
                    }

                },
                (error) => {
                    if (error.response.data.message === "notFound") {
                        setErrorMessage("Hibás kódot adtál meg");
                    }
                }
            )

        }
    });

    return (
        <Box>
            <form onSubmit={formik.handleSubmit}>
                <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                    <Box>
                        <Typography sx={{ fontSize: 40 }}>Bejelentkezés</Typography>
                        <Typography>Írd be az email-ben kapott kódot</Typography>
                        <Box className='mt-2'>
                            <TextField
                                id='secondFactorCode'
                                name='secondFactorCode'
                                type="text"
                                label="Megerősítő kód"
                                value={formik.secondFactorCode}
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                            />
                        </Box>
                        {formik.touched.secondFactorCode && (
                            <Typography sx={{ color: 'red' }}>{formik.errors.secondFactorCode}</Typography>
                        )
                        }
                        <Box>
                            <Button disabled={!formik.isValid} type='submit'>Küldés</Button>
                        </Box>
                        {
                            errorMessage && (
                                <Typography sx={{ color: 'red', marginBottom: '16px' }} >{errorMessage}</Typography>
                            )
                        }
                    </Box>
                </Box>
            </form>

        </Box>
    );
}

export default LoginWithSecondFactorComponent;