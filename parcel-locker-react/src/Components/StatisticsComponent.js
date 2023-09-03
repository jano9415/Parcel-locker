import { Box, Paper, Typography } from "@mui/material";
import NumberOfParcels from "../StatisticsComponents/NumberOfParcels";
import MostCommonParcelSize from "../StatisticsComponents/MostCommonParcelSize";
import NumberOfParcelsBySize from "../StatisticsComponents/NumberOfParcelsBySize";
import TotalRevenue from "../StatisticsComponents/TotalRevenue";
import AverageParcelValue from "../StatisticsComponents/AverageParcelValue";
import AmountOfParcelsFromOnlineAndParcelLocker from "../StatisticsComponents/AmountOfParcelsFromOnlineAndParcelLocker";
import MostCommonSendingLocation from "../StatisticsComponents/MostCommonSendingLocation";
import MostCommonReceivingLocation from "../StatisticsComponents/MostCommonReceivingLocation";

const StatisticsComponent = () => {

    return (
        <Box>
            <Box className="d-flex justify-content-center">
                <Box>
                    <Typography sx={{ fontSize: 40 }}>Statisztikai adatok</Typography>
                </Box>
            </Box>

            <Box sx={{textAlign: 'center'}} className="d-flex justify-content-center">
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

            <Box sx={{textAlign: 'center'}} className="d-flex justify-content-center">
                <Paper elevation={5} className="m-2">
                    <Typography sx={{ fontSize: 20 }}>Összes bevétel a kézbesített csomagokból</Typography>
                    <TotalRevenue></TotalRevenue>
                </Paper>
                <Paper elevation={5} className="m-2">
                    <Typography sx={{ fontSize: 20 }}>Csomagok értékének átlaga</Typography>
                    <AverageParcelValue></AverageParcelValue>
                </Paper>
                <Paper elevation={5} className="m-2">
                    <Typography sx={{ fontSize: 20 }}>Feladott csomagok száma aszerint, hogy automatából vagy online adják fel</Typography>
                    <AmountOfParcelsFromOnlineAndParcelLocker></AmountOfParcelsFromOnlineAndParcelLocker>
                </Paper>
            </Box>

            <Box sx={{textAlign: 'center'}} className="d-flex justify-content-center">
                <Paper elevation={5} className="m-2">
                    <Typography sx={{ fontSize: 20 }}>Innen adják fel a legtöbb csomagot</Typography>
                    <MostCommonSendingLocation></MostCommonSendingLocation>
                </Paper>
                <Paper elevation={5} className="m-2">
                    <Typography sx={{ fontSize: 20 }}>Ide érkezik a legtöbb csomag</Typography>
                    <MostCommonReceivingLocation></MostCommonReceivingLocation>
                </Paper>
                <Paper elevation={5} className="m-2">
                    <Typography sx={{ fontSize: 20 }}>Fizetések száma</Typography>
                </Paper>
            </Box>

        </Box>

    );


}

export default StatisticsComponent;