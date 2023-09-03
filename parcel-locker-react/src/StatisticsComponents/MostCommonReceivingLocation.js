import { Box, Typography } from "@mui/material"
import { useEffect, useState } from "react";
import ParcelStatistcsService from "../Service/ParcelStatistcsService";

const MostCommonReceivingLocation = () => {

    const [address, setAddress] = useState("");


    useEffect(() => {

        //Hova érkezik a legtöbb csomag?
        ParcelStatistcsService.mostCommonReceivingLocation().then(
            (response) => {
                setAddress(response.data.message);
            },
            (error) => {

            }
        )

    }, [])


    return (
        <Box>
            <Typography>{address}</Typography>

        </Box>
    )
}

export default MostCommonReceivingLocation;