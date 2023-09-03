import { Box, Typography } from "@mui/material"
import { useEffect, useState } from "react";
import ParcelStatistcsService from "../Service/ParcelStatistcsService";

const NumberOfParcelsBySize = () => {

    const [smallSize, setSmallSize] = useState("");
    const [mediumSize, setMediumSize] = useState("");
    const [largeSize, setLargeSize] = useState("");

    useEffect(() => {

        //Csomagok száma méret szerint
        //Lista első eleme: kicsi csomagok száma
        //Lista második eleme: közepes csomagok száma
        //Lista harmadik eleme: nagy csomagok száma
        ParcelStatistcsService.numberOfParcelsBySize().then(
            (response) => {
                setSmallSize(response.data[0].message);
                setMediumSize(response.data[1].message);
                setLargeSize(response.data[2].message);
            },
            (error) => {

            }
        )

    }, [])


    return (
        <Box>
            <Typography>Kicsi csomagok száma: {smallSize}</Typography>
            <Typography>Közepes csomagok száma: {mediumSize}</Typography>
            <Typography>Nagy csomagok száma: {largeSize}</Typography>

        </Box>
    )
}

export default NumberOfParcelsBySize;