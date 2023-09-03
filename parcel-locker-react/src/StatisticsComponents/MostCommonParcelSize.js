import { Box, Typography } from "@mui/material"
import { useEffect, useState } from "react";
import ParcelStatistcsService from "../Service/ParcelStatistcsService";

const MostCommonParcelSize = () => {

    const [size, setSize] = useState("");

    useEffect(() => {

        //Leggyakoribb méretű csomagok: kicsi, közepes vagy nagy
        ParcelStatistcsService.mostCommonParcelSize().then(
            (response) => {
                setSize(response.data.message);
            },
            (error) => {

            }
        )

    }, [])


    return (
        <Box>
            <Typography>{size}</Typography>
        </Box>
    )
}

export default MostCommonParcelSize;