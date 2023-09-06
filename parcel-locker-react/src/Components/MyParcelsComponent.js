import { Box, Button, InputLabel, MenuItem, Paper, Select, Typography } from "@mui/material";
import MyProfileMenuComponent from "./MyProfileMenuComponent";
import { useEffect, useState } from "react";
import ParcelService from "../Service/ParcelService";
import ParcelDetailsModalComponent from "./ParcelDetailsModalComponent";

const MyParcelsComponent = () => {

    const [requestType, setRequestType] = useState("");
    const [parcels, setParcels] = useState([]);


    useEffect(() => {



    }, [])

    const selectType = (event) => {


        const selectedValue = event.target.value;
        setRequestType(selectedValue);

        //Felhasználó csomagjainak lekérése
        ParcelService.getParcelsOfUser(selectedValue).then(
            (response) => {
                setParcels(response.data);
                console.log(response.data);
            },
            (error) => {

            }
        )
    }


    return (
        <Box>

            <Box className="d-flex justify-content-center">
                <Box>
                    <MyProfileMenuComponent></MyProfileMenuComponent>
                    <InputLabel>Csomagtípus kiválasztása</InputLabel>
                    <Select
                        id='parcelLockerFromId'
                        name='parcelLockerFromId'
                        onChange={selectType}
                        value={requestType}
                        fullWidth
                    >

                        <MenuItem value={"reserved"}>Online feladott, még nem elhelyezett csomagok</MenuItem>
                        <MenuItem value={"notPickedUp"}>Szállítás alatti csomagok</MenuItem>
                        <MenuItem value={"pickedUp"}>Lezárt, átvett csomagok</MenuItem>

                    </Select>
                </Box>
            </Box>

            <Box sx={{
                display: 'flex',
                flexWrap: 'wrap',
                '& > :not(style)': {
                    m: 1,
                    width: 128,
                    height: 128,
                },
            }}>
                {
                    parcels.map((parcel) => (
                        <Paper
                            sx={{ textAlign: 'center' }} elevation={5} key={parcel.id}
                        >
                            <Typography>{parcel.pickingUpCode}</Typography>
                            {
                                requestType === "reserved" && (
                                    <Button>Törlés</Button>
                                )
                            }

                            <ParcelDetailsModalComponent parcel={parcel}></ParcelDetailsModalComponent>

                        </Paper>
                    ))
                }
            </Box>

        </Box>
    );
}

export default MyParcelsComponent;