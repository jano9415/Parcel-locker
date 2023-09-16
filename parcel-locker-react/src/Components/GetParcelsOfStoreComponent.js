import { Box, Button, InputLabel, MenuItem, Paper, Select, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
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

            <Box className="d-flex justify-content-center">
                <Box>
                    <InputLabel>Csomagtípus kiválasztása</InputLabel>
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
                            <TableCell>Id</TableCell>
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
                                <TableCell component="th" scope="row">
                                    {parcel.id}
                                </TableCell>
                                <TableCell align="right">{parcel.uniqueParcelId}</TableCell>
                                <TableCell align="right">{parcel.uniqueParcelId}</TableCell>
                                <TableCell align="right">{parcel.uniqueParcelId}</TableCell>
                                <TableCell align="right">{parcel.pickingUpExpired + ""}</TableCell>
                                <TableCell align="right">
                                    <Button onClick={() => updatePickingUpExpired(parcel.id)}>Módosítás</Button>
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