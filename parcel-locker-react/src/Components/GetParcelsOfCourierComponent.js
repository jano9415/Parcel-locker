import { Box, InputLabel, MenuItem, Paper, Select, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import CourierService from "../Service/CourierService";
import ParcelService from "../Service/ParcelService";

const GetParcelsOfCourierComponent = () => {

    const [parcels, setParcels] = useState([]);
    const [couriers, setCouriers] = useState([]);
    const [selectedCourierId, setSelectedCourierId] = useState();


    useEffect(() => {

        //Futárok lekérése
        CourierService.getCouriers().then((response) => {
            setCouriers(response.data);
        },
            (error) => {

            })



    }, [])

    //Futár kiválasztása
    const selectCourier = (event) => {


        const selectedValue = event.target.value;
        setSelectedCourierId(selectedValue);

        //Futár csomagjainak lekérése
        ParcelService.getParcelsOfCourier(selectedValue).then((response) => {
            setParcels(response.data);
            console.log(response.data);
        },
            (error) => {

            })


    }

    return (
        <Box>

            <Box className="d-flex justify-content-center m-2">
                <Box sx={{ p: 2, border: '1px dashed grey' }}>
                    <Typography>Itt tudod megtekinteni a futároknál megtalálható csomagokat.</Typography>
                    <Typography>A legördülő listából válaszd ki a futárt.</Typography>
                </Box>
            </Box>

            <Box className="d-flex justify-content-center">
                <Box>
                    <InputLabel>Futárok</InputLabel>
                    <Select
                        id='courierid'
                        name='courierid'
                        onChange={selectCourier}
                        value={selectedCourierId}
                        fullWidth
                    >

                        {
                            couriers.map((courier) => (
                                <MenuItem key={courier.id} value={courier.id}>{courier.lastName + " " + courier.firstName
                                    + " " + "(" + courier.uniqueCourierId + ")"}</MenuItem>
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
                                    {parcel.shippingFromCounty + " megye" + " " + parcel.shippingFromPostCode + " " +
                                        parcel.shippingFromCity + " " + parcel.shippingFromStreet}
                                </TableCell>
                                <TableCell align="right">
                                    {parcel.shippingToCounty + " megye" + " " + parcel.shippingToPostCode + " " +
                                        parcel.shippingToCity + " " + parcel.shippingToStreet}
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

        </Box>
    );
}

export default GetParcelsOfCourierComponent;