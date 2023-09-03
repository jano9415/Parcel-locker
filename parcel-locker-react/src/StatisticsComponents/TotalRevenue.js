import { Box, Typography } from "@mui/material"
import { useEffect, useState } from "react";
import ParcelStatistcsService from "../Service/ParcelStatistcsService";

const TotalRevenue = () => {

    const [totalRevenue, setTotalRevenue] = useState("");

    useEffect(() => {

        //Összes bevétel a kézbesített csomagokból
        ParcelStatistcsService.totalRevenue().then(
            (response) => {
                setTotalRevenue(response.data.message);
            },
            (error) => {

            }
        )

    }, [])


    return (
        <Box>
            <Typography>{totalRevenue} Ft</Typography>

        </Box>
    )
}

export default TotalRevenue;