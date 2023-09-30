import { Box, Button, InputLabel, MenuItem, Paper, Select, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import StoreService from "../Service/StoreService";
import ParcelService from "../Service/ParcelService";
import { Link } from "react-router-dom";

const GetParcelsOfStoreComponent = () => {

    const [parcels, setParcels] = useState([]);
    const [stores, setStores] = useState([]);
    const [selectedStoreId, setSelectedStoreId] = useState();


    useEffect(() => {

        //Központi raktárak lekérése
        StoreService.getStores().then((response) => {
            setStores(response.data);
        },
            (error) => {

            })



    }, [])

    //Központi raktár kiválasztása
    const selectStore = (event) => {


        const selectedValue = event.target.value;
        setSelectedStoreId(selectedValue);

        //Központi raktár csomagjainak lekérése
        ParcelService.getParcelsOfStore(selectedValue).then((response) => {
            setParcels(response.data);
            console.log(response.data);
        },
            (error) => {

            })

    }

    //Átvételi lejárat frissítése
    const updatePickingUpExpired = (parcelId) => {


        ParcelService.updatePickingUpExpired(parcelId).then((response) => {
            if (response.data.message === "notFound") {

            }
            if (response.data.message === "successfulUpdating") {
                //Csomagok megjelenítésének frissítése
                ParcelService.getParcelsOfStore(selectedStoreId).then((response) => {
                    setParcels(response.data);
                },
                    (error) => {

                    })
            }
        },
            (error) => {

            })

    }

    return (
        <Box>

            <Box className="d-flex justify-content-center m-2">
                <Box sx={{ p: 2, border: '1px dashed grey' }}>
                    <Typography>Itt tudod megtekinteni a központi raktárakban megtalálható csomagokat.</Typography>
                    <Typography>A legördülő listából válaszd ki a megyei központi raktárt.</Typography>
                    <Typography>Ha a csomag azért van a központi raktárban, mert lejárt az átvételi ideje,</Typography>
                    <Typography>akkor itt tudod azt beállítani, hogy a futár újra elszállítsa az átvételi automatához.</Typography>
                    <Typography>Ehhez kattints a csomaglejárat módosítása oszlopban az újraindítás gombra, ha az látható.</Typography>
                </Box>
            </Box>

            <Box className="d-flex justify-content-center">
                <Box>
                    <InputLabel>Központi raktárak</InputLabel>
                    <Select
                        id='storeid'
                        name='storeid'
                        onChange={selectStore}
                        value={selectedStoreId}
                        fullWidth
                    >

                        {
                            stores.map((store) => (
                                <MenuItem key={store.id} value={store.id}>{store.county}</MenuItem>
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
                            <TableCell align="right">Átvétel lejárt?</TableCell>
                            <TableCell align="right">Csomaglejárat módosítása</TableCell>
                            <TableCell align="right"></TableCell>
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
                                <TableCell align="right">
                                    {
                                        parcel.pickingUpExpired ? (
                                            "Igen"
                                        ) :
                                            (
                                                "Nem"
                                            )
                                    }
                                </TableCell>
                                <TableCell align="right">
                                    {
                                        parcel.pickingUpExpired && (
                                            <Button onClick={() => updatePickingUpExpired(parcel.id)}>Újraindítás</Button>
                                        )
                                    }
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

        </Box>
    );
}

export default GetParcelsOfStoreComponent;