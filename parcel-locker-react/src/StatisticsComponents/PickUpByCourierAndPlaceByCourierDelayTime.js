import { Box, Typography } from "@mui/material"
import { useEffect, useState } from "react";
import ParcelStatistcsService from "../Service/ParcelStatistcsService";

const PickUpByCourierAndPlaceByCourierDelayTime = () => {

    const [parcelNumber, setParcelNumber] = useState("");

    useEffect(() => {

        //Szállítási késések
        ParcelStatistcsService.pickUpByCourierAndPlaceByCourierDelayTime().then(
            (response) => {
                setParcelNumber(response.data.message);

                if(response.data.message === null){
                    setParcelNumber("0");
                }
            },
            (error) => {

            }
        )

    }, [])


    return (
        <Box>
            <Typography>{parcelNumber} darab csomagot szállítottak le több, mint 72 óra alatt.</Typography>
        </Box>
    )
}

export default PickUpByCourierAndPlaceByCourierDelayTime;