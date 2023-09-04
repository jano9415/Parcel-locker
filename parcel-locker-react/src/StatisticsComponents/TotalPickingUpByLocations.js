import { Box } from '@mui/material';
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
  const dataset = [
    {
      seoul: 21,
      month: 'Várpalota Veszprémi út 5',
    },
    {
        seoul: 21,
        month: 'Várpalota Veszprémi út 7',
    },
    {
      seoul: 41,
      month: 'Mar',
    },
    {
      seoul: 73,
      month: 'Apr',
    }
  ];
  
  const valueFormatter = (value) => `${value}darab`;
  


const TotalPickingUpByLocations = () => {

    useEffect(() => {

        //Csomagátvételek száma automaták szerint
        ParcelStatistcsService.totalPickingUpByLocations().then(
          (response) => {
            console.log(response.data);
          },
          (error) => {
    
          }
        )
    
      }, [])


    return (
        <Box>
            <BarChart
                dataset={dataset}
                yAxis={[{ scaleType: 'band', dataKey: 'month' }]}
                series={[{ dataKey: 'seoul', label: 'Feladott csomagok száma', valueFormatter }]}
                layout="horizontal"
                {...chartSetting}
                />

        </Box>
    );
}

export default TotalPickingUpByLocations;