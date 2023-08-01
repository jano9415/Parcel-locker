import { Box, Button, Modal, Paper, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import ParcelLockerSaturationModalComponent from './ParcelLockerSaturationModalComponent';
import ParcelLockerService from '../Service/ParcelLockerService';




const ParcelLockersComponent = () => {

  const [parcelLockers, setParcelLockers] = useState([{}]);



  useEffect(() => {

    //Csomag automaták lekérése
    ParcelLockerService.getParcelLockersForChoice().then(
      (response) => {
        setParcelLockers(response.data);
      },
      (error) => {

      }
    )

  }, [])






  return (
    <div>
      <Box className="d-flex justify-content-center">
        <Box sx={{
          display: 'flex',
          flexWrap: 'wrap',
          '& > :not(style)': {
            m: 1,
            width: 128,
            height: 128,
          },
        }}>
          {
            parcelLockers.map((parcelLocker) => (
              <Paper
                sx={{ textAlign: 'center' }} elevation={5} key={parcelLocker.id}
              >
                <Typography>{parcelLocker.postCode}</Typography>
                <Typography>{parcelLocker.city}</Typography>
                <Typography>{parcelLocker.street}</Typography>
                <ParcelLockerSaturationModalComponent parcelLockerId={parcelLocker.id}></ParcelLockerSaturationModalComponent>

              </Paper>
            ))
          }
        </Box>
      </Box>


    </div>
  );
}

export default ParcelLockersComponent;