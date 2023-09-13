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

      <Box className="d-flex justify-content-center">
        <Box className="m-2" sx={{ p: 2, border: '1px dashed grey' }}>
          {dataSet1.map((data) => (
            <Typography key={data.id}>{data.id}:   {data.location}</Typography>
          ))}
        </Box>
        <Box className="m-2" sx={{ p: 2, border: '1px dashed grey' }}>
          {dataSet2.map((data) => (
            <Typography key={data.id}>{data.id}:   {data.location}</Typography>
          ))}
        </Box>
      </Box>

      <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
        <Box className="m-2" >
          <BarChart
            dataset={dataSet1}
            xAxis={[{ scaleType: 'band', dataKey: 'id', label: 'Automaták' }]}
            series={[{ dataKey: 'amount', label: 'Átvett csomagok száma', valueFormatter }]}
            yAxis={[{ scaleType: 'linear', max: 60 }]}
            width={500}
            height={300}
          />
        </Box>
        <Box className="m-2">
          <BarChart
            dataset={dataSet2}
            xAxis={[{ scaleType: 'band', dataKey: 'id', label: 'Automaták' }]}
            series={[{ dataKey: 'amount', label: 'Átvett csomagok száma', valueFormatter }]}
            yAxis={[{ scaleType: 'linear', max: 60 }]}
            width={500}
            height={300}
          />
        </Box>
      </Box>

    </Box>
  );
}

export default TotalPickingUpByLocations;

/*
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

    </Box>*/