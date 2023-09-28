import { Box, FormControlLabel, Paper, Typography } from "@mui/material";
import NumberOfParcels from "../StatisticsComponents/NumberOfParcels";
import MostCommonParcelSize from "../StatisticsComponents/MostCommonParcelSize";
import NumberOfParcelsBySize from "../StatisticsComponents/NumberOfParcelsBySize";
import TotalRevenue from "../StatisticsComponents/TotalRevenue";
import AverageParcelValue from "../StatisticsComponents/AverageParcelValue";
import AmountOfParcelsFromOnlineAndParcelLocker from "../StatisticsComponents/AmountOfParcelsFromOnlineAndParcelLocker";
import MostCommonSendingLocation from "../StatisticsComponents/MostCommonSendingLocation";
import MostCommonReceivingLocation from "../StatisticsComponents/MostCommonReceivingLocation";
import PaymentDatas from "../StatisticsComponents/PaymentDatas";
import AverageMinMaxShippingTime from "../StatisticsComponents/AverageMinMaxShippingTime";
import TotalSendingByLocations from "../StatisticsComponents/TotalSendingByLocations";
import TotalPickingUpByLocations from "../StatisticsComponents/TotalPickingUpByLocations";
import Checkbox from '@mui/material/Checkbox';
import { useState } from "react";
import PlaceByCustomerAndPickUpByCustomerTime from "../StatisticsComponents/PlaceByCustomerAndPickUpByCustomerTime";
import PlaceByCustomerAndPickUpByCourierTime from "../StatisticsComponents/PlaceByCustomerAndPickUpByCourierTime";
import PickUpByCourierAndPlaceByCourierTime from "../StatisticsComponents/PickUpByCourierAndPlaceByCourierTime";
import PlaceByCourierAndPickUpByCustomerTime from "../StatisticsComponents/PlaceByCourierAndPickUpByCustomerTime";



const StatisticsComponent = () => {

    const [checked, setChecked] = useState();
    const [checked2, setChecked2] = useState();
    const [checked3, setChecked3] = useState();
    const [checked4, setChecked4] = useState();
    const [checked5, setChecked5] = useState();
    const [checked6, setChecked6] = useState();
    const [checked7, setChecked7] = useState();

    const handleChange = (event) => {
        setChecked(event.target.checked);
    };

    const handleChange2 = (event) => {
        setChecked2(event.target.checked);
    };

    const handleChange3 = (event) => {
        setChecked3(event.target.checked);
    };

    const handleChange4 = (event) => {
        setChecked4(event.target.checked);
    };

    const handleChange5 = (event) => {
        setChecked5(event.target.checked);
    };

    const handleChange6 = (event) => {
        setChecked6(event.target.checked);
    };

    const handleChange7 = (event) => {
        setChecked7(event.target.checked);
    };

    return (
        <Box>
            <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                <Box>
                    <Typography sx={{ fontSize: 40 }}>Statisztikai adatok</Typography>
                    <Typography>Válaszd ki azokat az adatokat, amiket meg szeretnél jeleníteni</Typography>
                </Box>
            </Box>

            <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                <Box className="m-2">
                    <Box>
                        <FormControlLabel
                            value="top"
                            control={<Checkbox
                                checked={checked}
                                onChange={handleChange} />}
                            inputProps={{ 'aria-label': 'controlled' }}
                            label="Kézbesített csomagok száma és mérete"
                            labelPlacement="start"
                        />
                    </Box>
                    <Box>
                        <FormControlLabel
                            value="top"
                            control={<Checkbox
                                checked={checked2}
                                onChange={handleChange2} />}
                            inputProps={{ 'aria-label': 'controlled' }}
                            label="Pénzügyi, fizetési adatok"
                            labelPlacement="start"
                        />
                    </Box>
                    <Box>
                        <FormControlLabel
                            value="top"
                            control={<Checkbox
                                checked={checked3}
                                onChange={handleChange3} />}
                            inputProps={{ 'aria-label': 'controlled' }}
                            label="Csomagfeladási forgalom diagram"
                            labelPlacement="start"
                        />
                    </Box>
                </Box>

                <Box className="m-2">
                    <Box>
                        <FormControlLabel
                            value="top"
                            control={<Checkbox
                                checked={checked4}
                                onChange={handleChange4} />}
                            inputProps={{ 'aria-label': 'controlled' }}
                            label="Csomagátvételi forgalom diagram"
                            labelPlacement="start"
                        />
                    </Box>
                    <Box>
                        <FormControlLabel
                            value="top"
                            control={<Checkbox
                                checked={checked5}
                                onChange={handleChange5} />}
                            inputProps={{ 'aria-label': 'controlled' }}
                            label="Legforgalmasabb automták"
                            labelPlacement="start"
                        />
                    </Box>
                    <Box>
                        <FormControlLabel
                            value="top"
                            control={<Checkbox
                                checked={checked6}
                                onChange={handleChange6} />}
                            inputProps={{ 'aria-label': 'controlled' }}
                            label="Szállítási és átvételi idők"
                            labelPlacement="start"
                        />
                    </Box>
                </Box>

                <Box className="m-2">
                    <Box>
                        <FormControlLabel
                            value="top"
                            control={<Checkbox
                                checked={checked7}
                                onChange={handleChange7} />}
                            inputProps={{ 'aria-label': 'controlled' }}
                            label="Raktárak forgalma diagram"
                            labelPlacement="start"
                        />
                    </Box>
                </Box>
            </Box>

            {
                checked && (
                    <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                        <Paper elevation={5} className="m-2">
                            <Typography sx={{ fontSize: 20 }}>Összes kézbesített csomag</Typography>
                            <NumberOfParcels></NumberOfParcels>
                        </Paper>
                        <Paper elevation={5} className="m-2">
                            <Typography sx={{ fontSize: 20 }}>Leggyakoribb méretű csomag</Typography>
                            <MostCommonParcelSize></MostCommonParcelSize>
                        </Paper>
                        <Paper elevation={5} className="m-2">
                            <Typography sx={{ fontSize: 20 }}>Csomagok száma méret szerint</Typography>
                            <NumberOfParcelsBySize></NumberOfParcelsBySize>
                        </Paper>
                    </Box>
                )
            }

            {
                checked && (
                    <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                        <Paper elevation={5} className="m-2">
                            <Typography sx={{ fontSize: 20 }}>Feladott csomagok száma aszerint, hogy automatából vagy online adják fel</Typography>
                            <AmountOfParcelsFromOnlineAndParcelLocker></AmountOfParcelsFromOnlineAndParcelLocker>
                        </Paper>
                    </Box>
                )
            }

            {
                checked2 && (
                    <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                        <Paper elevation={5} className="m-2">
                            <Typography sx={{ fontSize: 20 }}>Összes bevétel a kézbesített csomagokból</Typography>
                            <TotalRevenue></TotalRevenue>
                        </Paper>
                        <Paper elevation={5} className="m-2">
                            <Typography sx={{ fontSize: 20 }}>Csomagok értékének átlaga</Typography>
                            <Typography>Az ügyfelek átlagban ilyen értékű csomagokat küldenek</Typography>
                            <AverageParcelValue></AverageParcelValue>
                        </Paper>
                        <Paper elevation={5} className="m-2">
                            <Typography sx={{ fontSize: 20 }}>Fizetések száma</Typography>
                            <PaymentDatas></PaymentDatas>
                        </Paper>
                    </Box>
                )
            }


            {
                checked3 && (
                    <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                        <Paper elevation={5} className="m-2">
                            <Typography sx={{ fontSize: 20 }}>Csomagfeladási forgalmi adatok</Typography>
                            <Typography >A diagram megmutatja, hogy melyik automatából hány darab csomagot adtak fel.</Typography>
                            <TotalSendingByLocations></TotalSendingByLocations>
                        </Paper>
                    </Box>
                )
            }

            {
                checked4 && (
                    <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                        <Paper elevation={5} className="m-2">
                            <Typography sx={{ fontSize: 20 }}>Csomagátvételi forgalmi adatok</Typography>
                            <Typography >A diagram megmutatja, hogy melyik automatából hány darab csomagot vettek át.</Typography>
                            <TotalPickingUpByLocations></TotalPickingUpByLocations>
                        </Paper>
                    </Box>
                )
            }


            {
                checked5 && (
                    <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                        <Paper elevation={5} className="m-2">
                            <Typography sx={{ fontSize: 20 }}>Innen adják fel a legtöbb csomagot</Typography>
                            <Typography>Csomagfeladási szempontból ez a legforgalmasabb automata.</Typography>
                            <MostCommonSendingLocation></MostCommonSendingLocation>
                        </Paper>
                        <Paper elevation={5} className="m-2">
                            <Typography sx={{ fontSize: 20 }}>Ide érkezik a legtöbb csomag</Typography>
                            <Typography>Csomagátvételi szempontból ez a legforgalmasabb automata.</Typography>
                            <MostCommonReceivingLocation></MostCommonReceivingLocation>
                        </Paper>
                        <Paper elevation={5} className="m-2">
                        </Paper>
                    </Box>
                )
            }

            {
                checked6 && (
                    <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                        <Paper elevation={5} className="m-2">
                            <Typography sx={{ fontSize: 20 }}>Átlag, min, max</Typography>
                            <Typography>Ügyfél elhelyezi a csomagot a feladási automatában időpont.</Typography>
                            <Typography>Futár elhelyezi a csomagot az érkezési automatában időpont.</Typography>
                            <Typography>Az adatok ezeknek az időkülönbségeknek az átlagát, minimumát és maximimát fejezik ki.</Typography>
                            <AverageMinMaxShippingTime></AverageMinMaxShippingTime>
                        </Paper>
                        <Paper elevation={5} className="m-2">
                            <Typography sx={{ fontSize: 20 }}>Átlag, min, max</Typography>
                            <Typography>Ügyfél elhelyezi a csomagot a feladási automatában időpont.</Typography>
                            <Typography>Ügyfél átveszi a csomagot az érkezési automatából időpont</Typography>
                            <Typography>Az adatok ezeknek az időkülönbségeknek az átlagát, minimumát és maximimát fejezik ki.</Typography>
                            <PlaceByCustomerAndPickUpByCustomerTime></PlaceByCustomerAndPickUpByCustomerTime>
                        </Paper>
                    </Box>
                )
            }

            {
                checked6 && (
                    <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                        <Paper elevation={5} className="m-2">
                            <Typography sx={{ fontSize: 20 }}>Átlag, min, max</Typography>
                            <Typography>Ügyfél elhelyezi a csomagot a feladási automatában időpont.</Typography>
                            <Typography>Futár kiveszi a csomagot a feladási automatából időpont.</Typography>
                            <Typography>Az adatok ezeknek az időkülönbségeknek az átlagát, minimumát és maximimát fejezik ki.</Typography>
                            <PlaceByCustomerAndPickUpByCourierTime></PlaceByCustomerAndPickUpByCourierTime>
                        </Paper>
                        <Paper elevation={5} className="m-2">
                            <Typography sx={{ fontSize: 20 }}>Átlag, min, max</Typography>
                            <Typography>Futár kiveszi a csomagot a feladási automatából időpont.</Typography>
                            <Typography>Futár elhelyezi a csomagot az érkezési automatában időpont.</Typography>
                            <Typography>Az adatok ezeknek az időkülönbségeknek az átlagát, minimumát és maximimát fejezik ki.</Typography>
                            <PickUpByCourierAndPlaceByCourierTime></PickUpByCourierAndPlaceByCourierTime>
                        </Paper>
                    </Box>
                )
            }

            {
                checked6 && (
                    <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                        <Paper elevation={5} className="m-2">
                            <Typography sx={{ fontSize: 20 }}>Átlag, min, max</Typography>
                            <Typography>Futár elhelyezi a csomagot az érkezési automatában időpont.</Typography>
                            <Typography>Ügyfél átveszi a csomagot az érkezési automatából időpont.</Typography>
                            <Typography>Az adatok ezeknek az időkülönbségeknek az átlagát, minimumát és maximimát fejezik ki.</Typography>
                            <PlaceByCourierAndPickUpByCustomerTime></PlaceByCourierAndPickUpByCustomerTime>
                        </Paper>
                        <Paper elevation={5} className="m-2">

                        </Paper>
                    </Box>
                )
            }




        </Box>

    );


}

export default StatisticsComponent;