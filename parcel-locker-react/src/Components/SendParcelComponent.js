import React, { useState } from 'react';
import { Form, useFormik } from 'formik';
import { Box, Button, FormControl, FormControlLabel, FormLabel, InputLabel, MenuItem, Radio, RadioGroup, Select, TextField, Typography } from '@mui/material';
import * as Yup from 'yup';




const SendParcelComponent = () => {

    const formik = useFormik({
        initialValues: {
            price: 0,
            size: '',
            parcelLockerFrom: '',
            parcelLockerTo: '',
            receiverName: '',
            receiverEmailAddress: '',
            receiverPhoneNumber: '',
        },
        validationSchema: Yup.object({
            price: Yup.number()
                .min(0, 'Nullánál nagyobb számot adj meg'),
            size: Yup.string()
                .required("Válassz csomag méretet"),
            parcelLockerFrom: Yup.string()
                .required("Válassz feladási automatát"),
            parcelLockerTo: Yup.string()
                .required("Válassz érkezési automatát"),
            receiverName: Yup.string()
                .required("Add meg az átvevő nevét"),
            receiverEmailAddress: Yup.string()
                .required("Add meg az átvevő email címét")
                .email("Valódi email címet adj meg"),
            receiverPhoneNumber: Yup.string()
                .required("Add meg az átvevő telefonszámát")
        }),
        onSubmit: values => {
            console.log(values);
        }
    })

    //Csomag automaták lekérése
    const parcelLockers = [
        {
            label: "Várpalota",
            value: "1",
        },
        {
            label: "Veszprém",
            value: "2",
        }
    ];




    return (
        <div>
            <form onSubmit={formik.handleSubmit}>
                <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                    <Box>
                        <InputLabel>Feladási automata</InputLabel>
                        <Select
                            id='parcelLockerFrom'
                            name='parcelLockerFrom'
                            value={formik.parcelLockerFrom}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            fullWidth
                        >
                            {
                                parcelLockers.map((parcelLocker) => (
                                    <MenuItem value={parcelLocker.value}>{parcelLocker.label}</MenuItem>
                                ))
                            }
                        </Select>
                        {
                            formik.touched.parcelLockerFrom && formik.errors.parcelLockerFrom && (
                                <Typography sx={{ color: 'red' }}>{formik.errors.parcelLockerFrom}</Typography>
                            )
                        }

                        <InputLabel>Érkezési automata</InputLabel>
                        <Select
                            id='parcelLockerTo'
                            name='parcelLockerTo'
                            value={formik.parcelLockerTo}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            fullWidth
                        >
                            {
                                parcelLockers.map((parcelLocker) => (
                                    <MenuItem value={parcelLocker.value}>{parcelLocker.label}</MenuItem>
                                ))
                            }
                        </Select>
                        {
                            formik.touched.parcelLockerTo && formik.errors.parcelLockerTo && (
                                <Typography sx={{ color: 'red' }}>{formik.errors.parcelLockerTo}</Typography>
                            )
                        }
                        <Typography>A csomag adatai</Typography>
                        <FormLabel>Csomagméret kiválasztása</FormLabel>
                        <RadioGroup
                            row
                            defaultValue={"small"}
                            id='size'
                            name='size'
                            value={formik.size}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                        >
                            <FormControlLabel value={"small"} label="Kicsi" control={<Radio />} />
                            <FormControlLabel value={"medium"} label="Közepes" control={<Radio />} />
                            <FormControlLabel value={"large"} label="Nagy" control={<Radio />} />

                        </RadioGroup>
                        {
                            formik.touched.size && formik.errors.size && (
                                <Typography sx={{ color: 'red' }}>{formik.errors.size}</Typography>
                            )
                        }
                        <Box className='mt-2'>
                            <TextField
                                defaultValue={0}
                                id='price'
                                name='price'
                                type="number"
                                label="Csomag ára"
                                value={formik.price}
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                            />
                        </Box>
                        {formik.errors.price && (
                            <Typography sx={{ color: 'red' }}>{formik.errors.price}</Typography>
                        )
                        }
                        <Box className='mt-2'>
                            <TextField
                                id='receiverName'
                                name='receiverName'
                                type="text"
                                label="Átvevő neve"
                                value={formik.receiverName}
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                            />
                        </Box>
                        {formik.touched.receiverName && formik.errors.receiverName && (
                            <Typography sx={{ color: 'red' }}>{formik.errors.receiverName}</Typography>
                        )
                        }
                        <Box className='mt-2'>
                            <TextField
                                id='receiverEmailAddress'
                                name='receiverEmailAddress'
                                type="email"
                                label="Átvevő email címe"
                                value={formik.receiverEmailAddress}
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                            />
                        </Box>
                        {formik.touched.receiverEmailAddress && formik.errors.receiverEmailAddress && (
                            <Typography sx={{ color: 'red' }}>{formik.errors.receiverEmailAddress}</Typography>
                        )
                        }
                        <Box className='mt-2'>
                            <TextField
                                id='receiverPhoneNumber'
                                name='receiverPhoneNumber'
                                type="text"
                                label="Átvevő telefonszáma"
                                value={formik.receiverPhoneNumber}
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                            />
                        </Box>
                        {formik.touched.receiverPhoneNumber && formik.errors.receiverPhoneNumber && (
                            <Typography sx={{ color: 'red' }}>{formik.errors.receiverPhoneNumber}</Typography>
                        )
                        }
                        <Box>
                            <Button disabled={!formik.isValid} type='submit'>Küldés</Button>
                        </Box>
                    </Box>
                </Box>
            </form>



        </div>
    );
}

export default SendParcelComponent;