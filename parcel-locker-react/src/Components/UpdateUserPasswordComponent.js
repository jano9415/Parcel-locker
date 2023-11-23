import { Box, Button, FormControlLabel, Switch, TextField, Typography } from "@mui/material";
import MyProfileMenuComponent from "./MyProfileMenuComponent";
import { useFormik } from "formik";
import { useEffect, useState } from "react";
import * as Yup from 'yup';
import AuthService from "../Service/AuthService";
import { CheckBox } from "@mui/icons-material";

const UpdateUserPasswordComponent = () => {

    const [errorMessage, setErrorMessage] = useState("");

    useEffect(() => {


    }, []);

    const formik = useFormik({
        initialValues: {
            emailAddress: AuthService.getCurrentUser().emailAddress,
            password: '',
            newPassword: '',
            newPasswordAgain: '',
        },
        validationSchema: Yup.object({
            password: Yup.string()
                .required("Add meg a jelenlegi jelszót")
                .matches(
                    /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/,
                    'A jelszónak legalább 8 karakterből kell állnia, tartalmaznia kell nagybetűt, kisbetűt, számot és speciális karaktert.'
                ),
            newPassword: Yup.string()
                .required("Add meg az új jelszót")
                .matches(
                    /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/,
                    'A jelszónak legalább 8 karakterből kell állnia, tartalmaznia kell nagybetűt, kisbetűt, számot és speciális karaktert.'
                )
                //Saját validáció
                //A két új jelszó mező nem egyezik meg
                .test(
                    'newPasswordIsTheSame',
                    'Az új jelszó nem lehet azonos az előző jelszóval',
                    function (value) {
                        const password = this.resolve(Yup.ref('password'));
                        return value !== password;
                    }
                ),
            newPasswordAgain: Yup.string()
                .required("Add meg az új jelszót újra")
                .matches(
                    /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/,
                    'A jelszónak legalább 8 karakterből kell állnia, tartalmaznia kell nagybetűt, kisbetűt, számot és speciális karaktert.'
                )
                //Saját validáció
                //A két új jelszó mező nem egyezik meg
                .test(
                    'twoNewPasswordsDiffer',
                    'A két jelszó nem egyezik meg',
                    function (value) {
                        const newPassword = this.resolve(Yup.ref('newPassword'));
                        return value === newPassword;
                    }
                ),

        }),
        onSubmit: (values) => {

            AuthService.updateUserPassword(values).then(
                (response) => {
                    if (response.data.message === "successfulUpdating") {
                        window.location.reload();
                    }

                },
                (error) => {
                    if (error.response.data.message === "wrongPassword") {
                        setErrorMessage("Rossz jelenlegi jelszót adtál meg")
                    }
                }
            )

        }
    });


    return (
        <Box>
            <MyProfileMenuComponent></MyProfileMenuComponent>
            <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                <Box>
                    <Typography sx={{ fontSize: 40 }}>Jelszó módosítása</Typography>
                </Box>
            </Box>

            <form onSubmit={formik.handleSubmit}>
                <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                    <Box>
                        <Box className='mt-2'>
                            <TextField
                                id='password'
                                name='password'
                                type="password"
                                label="Jelenlegi jelszó"
                                value={formik.values.password}
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
                                id='newPassword'
                                name='newPassword'
                                type="password"
                                label="Új jelszó"
                                value={formik.values.newPassword}
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                            />
                        </Box>
                        {formik.touched.newPassword && formik.errors.newPassword && (
                            <Typography sx={{ color: 'red' }}>{formik.errors.newPassword}</Typography>
                        )
                        }
                        <Box className='mt-2'>
                            <TextField
                                id='newPasswordAgain'
                                name='newPasswordAgain'
                                type="password"
                                label="Új jelszó újra"
                                value={formik.values.newPasswordAgain}
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                            />
                        </Box>
                        {formik.touched.newPasswordAgain && formik.errors.newPasswordAgain && (
                            <Typography sx={{ color: 'red' }}>{formik.errors.newPasswordAgain}</Typography>
                        )
                        }
                    </Box>
                </Box>

                <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                    <Box>
                        <Button disabled={!formik.isValid} type='submit'>Küldés</Button>
                        {errorMessage && <Typography sx={{ color: 'red', marginBottom: '16px' }}>{errorMessage}</Typography>}
                    </Box>
                </Box>
            </form>

        </Box>
    );
}

export default UpdateUserPasswordComponent;