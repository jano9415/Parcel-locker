import { Box, Typography } from "@mui/material"
import { useEffect, useState } from "react";
import ParcelStatistcsService from "../Service/ParcelStatistcsService";

const PickUpByCourierAndPlaceByCourierTime = () => {

    const [averageTime, setAverageTime] = useState("");
    const [minTime, setMinTime] = useState("");
    const [maxTime, setMaxTime] = useState("");

    useEffect(() => {

        ParcelStatistcsService.pickUpByCourierAndPlaceByCourierTime().then(
            (response) => {
                setAverageTime(response.data[0].message);
                setMaxTime(response.data[1].message);
                setMinTime(response.data[2].message);
            },
            (error) => {

            }
        )

    }, [])


    return (
        <Box className="m-2">
            <Typography>Átlagos idő: {averageTime} óra</Typography>
            <Typography>Leggyorsabb idő: {minTime} óra</Typography>
            <Typography>Leglassabb idő: {maxTime} óra</Typography>
        </Box>
    )
}

export default PickUpByCourierAndPlaceByCourierTime;