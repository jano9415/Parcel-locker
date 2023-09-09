import { Box, Button, Divider, InputLabel, MenuItem, Paper, Select, Typography } from "@mui/material";
import MyProfileMenuComponent from "./MyProfileMenuComponent";
import { useEffect, useState } from "react";
import ParcelService from "../Service/ParcelService";
import ParcelDetailsModalComponent from "./ParcelDetailsModalComponent";
import image19 from '../image19.png';
import DeleteParcelModalComponent from "./DeleteParcelModalComponent";

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
            },
            (error) => {

            }
        )
    }


    return (
        <Box>
            <MyProfileMenuComponent></MyProfileMenuComponent>


            <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                <Box elevation={5} className="m-2">
                    <Typography sx={{ fontSize: 20 }}>Online feladott, még nem elhelyezett csomagok</Typography>
                    <Typography>Itt tudod megnézni azokat a csomagokat, amiket feladtál a webes</Typography>
                    <Typography>vagy mobilos alkalmazásból, de még nem vitted el és helyezted el a feladási automatában.</Typography>
                    <Typography>Ezeket az előzetes feladásokat itt még ki tudod törölni.</Typography>
                    <Typography>Ha nem viszed el a csomagod a feladási határidőig,</Typography>
                    <Typography>akkor a rendszer automatikusan törölni fogja azokat a lejárati idő után.</Typography>
                </Box>
                <Box elevation={5} className="m-2">
                    <Typography sx={{ fontSize: 20 }}>Szállítás alatti csomagok</Typography>
                    <Typography>Itt tudod megnézni azokat a csomagokat,</Typography>
                    <Typography>amiket már feladtál és azok a feladási automatában, szállítás alatt</Typography>
                    <Typography>vagy az érkezési automatában vannak.</Typography>
                    <Typography>Ezeket a csomagokat még nem vették át.</Typography>
                </Box>
                <Box elevation={5} className="m-2">
                    <Typography sx={{ fontSize: 20 }}>Lezárt, átvett csomagok</Typography>
                    <Typography>Itt tudod megnézni azokat a csomagokat, amiket feladtál</Typography>
                    <Typography>és a csomag címzettje már átvette.</Typography>
                </Box>
            </Box>

            <Box className="d-flex justify-content-center">
                <Box>
                    <InputLabel>Csomagtípus kiválasztása</InputLabel>
                    <Select
                        id='requestTypeId'
                        name='requestTypeId'
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
                    height: 180,
                },
            }}>
                {
                    parcels.map((parcel) => (
                        <Paper
                            sx={{ textAlign: 'center' }} elevation={5} key={parcel.id}
                        >
                            <img src={image19} alt="Logo" height="60" width="60" />
                            <Typography>{parcel.uniqueParcelId}</Typography>
                            {
                                requestType === "reserved" && (
                                    <DeleteParcelModalComponent parcel={parcel}></DeleteParcelModalComponent>
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