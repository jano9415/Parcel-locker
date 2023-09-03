import { Box, Typography } from "@mui/material"
import { useEffect, useState } from "react";
import ParcelStatistcsService from "../Service/ParcelStatistcsService";

const NumberOfParcels = () => {

    const [numberOfParcels, setNumberOfParcels] = useState("");

    useEffect(() => {

        //Összes kézbesített csomagok száma
        ParcelStatistcsService.numberOfParcels().then(
            (response) => {
                setNumberOfParcels(response.data.message);
            },
            (error) => {

            }
        )

    }, [])


    return (
        <Box>
            <Typography>{numberOfParcels} darab</Typography>
        </Box>
    )
}

export default NumberOfParcels;