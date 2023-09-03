import { Box, Typography } from "@mui/material"
import { useEffect, useState } from "react";
import ParcelStatistcsService from "../Service/ParcelStatistcsService";

const MostCommonSendingLocation = () => {

    const [address, setAddress] = useState("");


    useEffect(() => {

        //Honnan adják fel a legtöbb csomagot?
        ParcelStatistcsService.mostCommonSendingLocation().then(
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

export default MostCommonSendingLocation;