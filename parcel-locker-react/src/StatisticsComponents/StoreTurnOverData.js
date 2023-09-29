import { Box, Typography } from '@mui/material';
import { BarChart } from '@mui/x-charts';
import { useEffect, useState } from "react";
import ParcelStatistcsService from '../Service/ParcelStatistcsService';

const chartSetting = {
    xAxis: [
        {
            label: 'darabszám',
        },
    ],
    width: 500,
    height: 400,
};


const valueFormatter = (value) => `${value} darab`;



const StoreTurnOverData = () => {

    const [dataSet, setDataSet] = useState([{}]);

    useEffect(() => {

        //Raktárak forgalmi adatai
        ParcelStatistcsService.storeTurnOverData().then(
            (response) => {

                setDataSet(response.data);

            },
            (error) => {

            }
        )

    }, [])


    return (
        <Box>

            <Box className="d-flex justify-content-center">
                <Box className="m-2" sx={{ p: 2, border: '1px dashed grey' }}>
                    {dataSet.map((data) => (
                        <Typography key={data.id}>{data.id}:   {data.county + " megye"}</Typography>
                    ))}
                </Box>
            </Box>

            <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                <Box className="m-2" >
                    <BarChart
                        dataset={dataSet}
                        xAxis={[{ scaleType: 'band', dataKey: 'id', label: 'Központi raktárak' }]}
                        series={[{ dataKey: 'amount', label: 'Csomagok száma', color: 'green', valueFormatter }]}
                        yAxis={[{ scaleType: 'linear', max: 60 }]}
                        width={500}
                        height={300}
                    />
                </Box>
            </Box>

        </Box>
    );
}

export default StoreTurnOverData;
