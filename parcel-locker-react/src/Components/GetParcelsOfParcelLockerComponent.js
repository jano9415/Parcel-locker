import { Box, InputLabel, MenuItem, Paper, Select, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import ParcelLockerService from "../Service/ParcelLockerService";
import ParcelService from "../Service/ParcelService";
import UpdateParcelOptionsModalComponent from "./UpdateParcelOptionsModalComponent";

const GetParcelsOfParcelLockerComponent = () => {

    const [parcels, setParcels] = useState([]);
    const [parcelLockers, setParcelLockers] = useState([]);
    const [selectedParcelLockerId, setSelectedParcelLockerId] = useState();


    useEffect(() => {

        //Automaták lekérése
        ParcelLockerService.getParcelLockersForChoice().then((response) => {
            setParcelLockers(response.data);
        },
            (error) => {

            })



    }, [])

    //Automata kiválasztása
    const selectParcelLocker = (event) => {


        const selectedValue = event.target.value;
        setSelectedParcelLockerId(selectedValue);

        //Automata csomagjainak lekérése
        ParcelService.getParcelsOfParcelLocker(selectedValue).then((response) => {
            setParcels(response.data);
        },
            (error) => {

            })


    }

    return (
        <Box>

            <Box className="d-flex justify-content-center m-2">
                <Box sx={{ p: 2, border: '1px dashed grey' }}>
                    <Typography>Itt tudod megtekinteni az automatákban megtalálható csomagokat.</Typography>
                    <Typography>A legördülő listából válaszd ki az automatát.</Typography>
                    <Typography>Ha módosítani szeretnéd a csomag valamelyik adatát, kattints a módosítási</Typography>
                    <Typography>lehetőségek oszlopban a módosítás gombra.</Typography>
                    <Typography>Ezután válaszd ki, hogy mit szeretnél módosítani.</Typography>
                    <Typography>Lehetőségek: csomagátvételi lejárati dátum módosítása,</Typography>
                    <Typography>csomagfeladási lejárati dátum módosítása.</Typography>
                    <Typography>Ezeket az adatokat nem minden esetben tudod módosítani. Csak akkor ha,</Typography>
                    <Typography>megjelenik a "Kattints ide" lehetőség</Typography>
                </Box>
            </Box>

            <Box className="d-flex justify-content-center">
                <Box>
                    <InputLabel>Automaták</InputLabel>
                    <Select
                        id='parcellockerid'
                        name='parcellockerid'
                        onChange={selectParcelLocker}
                        value={selectedParcelLockerId}
                        fullWidth
                    >

                        {
                            parcelLockers.map((parcelLocker) => (
                                <MenuItem key={parcelLocker.id} value={parcelLocker.id}>{parcelLocker.postCode +
                                    " " + parcelLocker.city + " " + parcelLocker.street}</MenuItem>
                            ))
                        }

                    </Select>
                </Box>
            </Box>

            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell align="right">Csomagazonosító</TableCell>
                            <TableCell align="right">Feladási automata</TableCell>
                            <TableCell align="right">Érkezési automata</TableCell>
                            <TableCell align="right">Módosítási lehetőségek</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {parcels.map((parcel) => (
                            <TableRow
                                key={parcel.id}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                                <TableCell align="right">{parcel.uniqueParcelId}</TableCell>
                                <TableCell align="right">
                                    {parcel.shippingFromPostCode + " " + parcel.shippingFromCity + " " + parcel.shippingFromStreet}
                                </TableCell>
                                <TableCell align="right">
                                    {parcel.shippingToPostCode + " " + parcel.shippingToCity + " " + parcel.shippingToStreet}
                                </TableCell>
                                <TableCell align="right">
                                    <UpdateParcelOptionsModalComponent parcel={parcel}></UpdateParcelOptionsModalComponent>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

        </Box>
    );
}

export default GetParcelsOfParcelLockerComponent;