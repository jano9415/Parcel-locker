import { Box, Typography } from "@mui/material"
import { useEffect, useState } from "react";
import ParcelStatistcsService from "../Service/ParcelStatistcsService";

const AverageParcelValue = () => {

    const [parcelValue, setParcelValue] = useState("");

    useEffect(() => {

        //Csomagok értékének átlaga forintban
        ParcelStatistcsService.averageParcelValue().then(
            (response) => {
                setParcelValue(response.data.message);
            },
            (error) => {

            }
        )

    }, [])


    return (
        <Box>
            <Typography>{parcelValue} Ft</Typography>

        </Box>
    )
}

export default AverageParcelValue;