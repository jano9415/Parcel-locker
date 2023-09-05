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


const valueFormatter = (value) => `${value}darab`;



const TotalPickingUpByLocations = () => {

  const [dataSet1, setDataSet1] = useState([{}]);
  const [dataSet2, setDataSet2] = useState([{}]);

  useEffect(() => {

    //Csomagátvételek száma automaták szerint
    ParcelStatistcsService.totalPickingUpByLocations().then(
      (response) => {
        const datas = [];
        const datas2 = [];

        for (let i = 0; i <= 9; i++) {
          datas.push(response.data[i]);
        }
        setDataSet1(datas);

        for (let i = 10; i <= 19; i++) {
          datas2.push(response.data[i]);
        }
        setDataSet2(datas2);

      },
      (error) => {

      }
    )

  }, [])


  return (
    <Box>
      <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
        <Box className="m-2" >
          <BarChart
            dataset={dataSet1}
            yAxis={[{ scaleType: 'band', dataKey: 'location' }]}
            series={[{ dataKey: 'amount', label: 'Átvett csomagok száma', valueFormatter }]}
            layout="horizontal"
            {...chartSetting}
          />
        </Box>
        <Box className="m-2">
          <BarChart
            dataset={dataSet2}
            yAxis={[{ scaleType: 'band', dataKey: 'location' }]}
            series={[{ dataKey: 'amount', label: 'Átvett csomagok száma', valueFormatter }]}
            layout="horizontal"
            {...chartSetting}
          />
        </Box>
      </Box>

    </Box>
  );
}

export default TotalPickingUpByLocations;