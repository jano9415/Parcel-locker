import { Box, Typography } from "@mui/material"
import { useEffect, useState } from "react";
import ParcelStatistcsService from "../Service/ParcelStatistcsService";

const PaymentDatas = () => {

    const [onlinePayment, setOnlinePayment] = useState("");
    const [paymentAtParcelLocker, setPaymentAtParcelLocker] = useState("");

    useEffect(() => {

        //Mennyi csomagot fizetnek ki előre? Mennyit fizetnek ki az automatánál?
        ParcelStatistcsService.paymentDatas().then(
            (response) => {
                setOnlinePayment(response.data[0].message);
                setPaymentAtParcelLocker(response.data[1].message);
            },
            (error) => {

            }
        )

    }, [])


    return (
        <Box>
            <Typography>{onlinePayment} darab csomagot fizettek ki előre átutalással</Typography>
            <Typography>{paymentAtParcelLocker} darab csomagot fizettek ki az automatánál</Typography>
        </Box>
    )
}

export default PaymentDatas;