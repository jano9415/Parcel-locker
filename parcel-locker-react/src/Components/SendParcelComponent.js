import React, { useEffect, useState } from 'react';
import { Form, useFormik } from 'formik';
import { Box, Button, FormControl, FormControlLabel, FormLabel, InputLabel, MenuItem, Radio, RadioGroup, Select, TextField, Typography } from '@mui/material';
import * as Yup from 'yup';
import ParcelLockerService from '../Service/ParcelLockerService';
import AuthService from '../Service/AuthService';
import ParcelService from '../Service/ParcelService';
import image7 from '../image7.png';
import image8 from '../image8.png';




const SendParcelComponent = () => {

    const [parcelLockers, setParcelLockers] = useState([{}]);
    const [senderParcelLockerFull, setSenderParcelLockerFull] = useState(false);
    const [smallBoxesFull, setSmallBoxesFull] = useState(false);
    const [mediumBoxesFull, setMediumBoxesFull] = useState(false);
    const [largeBoxesFull, setLargeBoxesFull] = useState(false);
    const [sendingMessage, setSendingMessage] = useState("");



    useEffect(() => {
        //Csomag automaták lekérése
        ParcelLockerService.getParcelLockersForChoice().then(
            (response) => {
                setParcelLockers(response.data);
            },
            (error) => {

            }
        )
    }, [])

    //Formik objektum és validáció
    const formik = useFormik({
        initialValues: {
            price: 0,
            size: '',
            parcelLockerFromId: '',
            parcelLockerToId: '',
            receiverName: '',
            receiverEmailAddress: '',
            receiverPhoneNumber: '',
            //Ezt nem a felhasználótól kérem be
            //Bejelentkezett felhasználó email címe
            senderEmailAddress: AuthService.getCurrentUser().emailAddress,
        },
        validationSchema: Yup.object({
            price: Yup.number()
                .min(0, 'Nullánál nagyobb számot adj meg'),
            size: Yup.string()
                .required("Válassz csomag méretet"),
            parcelLockerFromId: Yup.string()
                .required("Válassz feladási automatát")
                //Ellenőrzöm, hogy a feladási automata tele van-e
                //Saját validáció
                .test(
                    'isFull',
                    'A feladási automata tele van',
                    function (parcelLockerFromId) {
                        //Feladási automata tele van?
                        ParcelLockerService.isParcelLockerFull(parcelLockerFromId).then(
                            (response) => {
                                if (response.data.message === "full") {
                                    setSenderParcelLockerFull(true);
                                }
                                if (response.data.message === "notfull") {
                                    setSenderParcelLockerFull(false);
                                }
                                //Ha az automata nincs tele, akkor ellenőrzöm a kis, közepes és nagy rekeszek telítettségét.
                                if (senderParcelLockerFull === false) {
                                    //Rekeszek tele vannak? Kicsi, közepes, nagy rekeszek ellenőrzése.
                                    ParcelLockerService.areBoxesFull(parcelLockerFromId).then(
                                        (response) => {
                                            //Kicsi rekeszek
                                            if (response.data[0].message === "full") {
                                                setSmallBoxesFull(true);
                                            }
                                            if (response.data[0].message === "notfull") {
                                                setSmallBoxesFull(false);
                                            }
                                            //Közepes rekeszek
                                            if (response.data[1].message === "full") {
                                                setMediumBoxesFull(true);
                                            }
                                            if (response.data[1].message === "notfull") {
                                                setMediumBoxesFull(false);
                                            }
                                            //Nagy rekeszek
                                            if (response.data[2].message === "full") {
                                                setLargeBoxesFull(true);
                                            }
                                            if (response.data[2].message === "notfull") {
                                                setLargeBoxesFull(false);
                                            }
                                        },
                                        (error) => {

                                        }
                                    )

                                }
                            },
                            (error) => {

                            }
                        )

                        //Ha a feladási automata tele van, akkor true
                        //Viszont a formik validátor akkor ad hibát, ha a függvény false-al tér vissza
                        //Ezért meg kell negálni az értéket
                        return !senderParcelLockerFull;
                    }
                ),
            parcelLockerToId: Yup.string()
                .required("Válassz érkezési automatát")
                //A feladási és az érkezési automata nem egyezhet meg
                //Saját validáció
                .test(
                    'not-same-parcel-locker',
                    'A feladási és érkezési automata nem lehet azonos',
                    function (value) {
                        const parcelLockerFromId = this.resolve(Yup.ref('parcelLockerFromId'));
                        return value !== parcelLockerFromId;
                    }
                ),
            receiverName: Yup.string()
                .required("Add meg az átvevő nevét"),
            receiverEmailAddress: Yup.string()
                .required("Add meg az átvevő email címét")
                .email("Valódi email címet adj meg"),
            receiverPhoneNumber: Yup.string()
                .required("Add meg az átvevő telefonszámát")
        }),
        onSubmit: (values) => {
            //Csomagfeladás
            ParcelService.sendParcelWithCodeFromWebpage(values).then(
                (respone) => {
                    if (respone.data.message === "successSending") {
                        setSendingMessage("Sikeres előzetes csomagfeladás. A feladási kódodat megtalálod az email értesítőben" +
                            " és a csomagjaim meüpontban.");
                    }

                },
                (error) => {

                }
            )
        }
    })

    return (
        <div>
            <form onSubmit={formik.handleSubmit}>
                <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                    <Box>
                        <InputLabel>Feladási automata</InputLabel>
                        <Select
                            id='parcelLockerFromId'
                            name='parcelLockerFromId'
                            value={formik.parcelLockerFromId}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            fullWidth
                        >
                            {
                                parcelLockers.map((parcelLocker) => (
                                    <MenuItem key={parcelLocker.id} value={parcelLocker.id}>{parcelLocker.postCode + " "
                                        + parcelLocker.city + " " + parcelLocker.street}</MenuItem>
                                ))
                            }
                        </Select>
                        {
                            formik.touched.parcelLockerFromId && formik.errors.parcelLockerFromId && (
                                <Typography sx={{ color: 'red' }}>{formik.errors.parcelLockerFromId}</Typography>
                            )
                        }

                        <InputLabel>Érkezési automata</InputLabel>
                        <Select
                            id='parcelLockerToId'
                            name='parcelLockerToId'
                            value={formik.parcelLockerToId}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            fullWidth
                        >
                            {
                                parcelLockers.map((parcelLocker) => (
                                    <MenuItem key={parcelLocker.id} value={parcelLocker.id}>{parcelLocker.postCode + " "
                                        + parcelLocker.city + " " + parcelLocker.street}</MenuItem>
                                ))
                            }
                        </Select>
                        {
                            formik.touched.parcelLockerToId && formik.errors.parcelLockerToId && (
                                <Typography sx={{ color: 'red' }}>{formik.errors.parcelLockerToId}</Typography>
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
                            {
                                smallBoxesFull === false && (
                                    <FormControlLabel value={"small"} label="Kicsi" control={<Radio />} />
                                )
                            }
                            {
                                mediumBoxesFull === false && (
                                    <FormControlLabel value={"medium"} label="Közepes" control={<Radio />} />
                                )
                            }
                            {
                                largeBoxesFull === false && (
                                    <FormControlLabel value={"large"} label="Nagy" control={<Radio />} />
                                )
                            }
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
                        {
                            senderParcelLockerFull ? (
                                <Box>
                                    <Typography sx={{ color: 'red' }}>Ez a feladási automata jelenleg tele van. Nem tudsz csomagot feladni</Typography>
                                </Box>
                            ) :
                                (
                                    <Box>
                                        <Button disabled={!formik.isValid} type='submit'>Küldés</Button>
                                    </Box>
                                )
                        }
                        <Box>
                            <Typography>{sendingMessage}</Typography>
                        </Box>

                    </Box>
                </Box>
            </form >

            <Box className="d-flex justify-content-center">
                <Box>
                    <Typography sx={{ fontSize: 40 }}>Csomagfeladás webes vagy mobilos alkalmazásból</Typography>
                </Box>
            </Box>

            <Box className="d-flex justify-content-center">
                <Box>
                    <img src={image7} alt="Logo" height="470" />
                </Box>
            </Box>

            <Box className="d-flex justify-content-center">
                <Box>
                    <img src={image8} alt="Logo" height="470" />
                </Box>
            </Box>



        </div >
    );
}

export default SendParcelComponent;