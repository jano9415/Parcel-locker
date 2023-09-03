import { Box, Typography } from "@mui/material"
import { useEffect, useState } from "react";
import ParcelStatistcsService from "../Service/ParcelStatistcsService";

const AmountOfParcelsFromOnlineAndParcelLocker = () => {

    const [onlineNumber, setOnlineNumber] = useState("");
    const [fromParcelLockerNumber, setFromParcelLockerNumber] = useState("");

    useEffect(() => {

        //Feladott csomagok száma aszerint, hogy automatából vagy online adják fel
        //Lista első eleme: automatából
        //Lista második eleme: online
        ParcelStatistcsService.amountOfParcelsFromOnlineAndParcelLocker().then(
            (response) => {
                setOnlineNumber(response.data[0].message);
                setFromParcelLockerNumber(response.data[1].message);
            },
            (error) => {

            }
        )

    }, [])


    return (
        <Box>
            <Typography>Online {onlineNumber} darab csomagot adtak fel.</Typography>
            <Typography>Automatából {fromParcelLockerNumber} darab csomagot adtak fel.</Typography>
        </Box>
    )
}

export default AmountOfParcelsFromOnlineAndParcelLocker;