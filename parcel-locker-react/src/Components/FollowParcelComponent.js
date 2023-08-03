import React, { useEffect, useState } from 'react';
import { Form, useFormik } from 'formik';
import { Box, Button, FormControlLabel, FormLabel, InputLabel, MenuItem, Radio, RadioGroup, Select, TextField, Typography } from '@mui/material';
import * as Yup from 'yup';
import ParcelService from '../Service/ParcelService';




const FollowParcelComponent = () => {

  useEffect(() => {

  }, [])

  const formik = useFormik({
    initialValues: {
      uniqueParcelId: ''

    },
    validationSchema: Yup.object({
      uniqueParcelId: Yup.string()
        .required("Add meg a csomagazonosítót")

    }),
    onSubmit: (values) => {

      //Csomag követése
      ParcelService.followParcel(values.uniqueParcelId).then((response) => {

        console.log(response.data);

      },
        (error) => {

        })

    }
  });




  return (
    <div>
      <form onSubmit={formik.handleSubmit}>
        <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
          <Box>
            <InputLabel>Írd be az email-ben kapott csomagazonosítód</InputLabel>
            <Box className='mt-2'>
              <TextField
                id='uniqueParcelId'
                name='uniqueParcelId'
                type="text"
                label="Csomagazonosító"
                value={formik.uniqueParcelId}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
              />
            </Box>
            {formik.touched.uniqueParcelId && formik.errors.uniqueParcelId && (
              <Typography sx={{ color: 'red' }}>{formik.errors.uniqueParcelId}</Typography>
            )
            }
            <Box>
              <Button disabled={!formik.isValid} type='submit'>Küldés</Button>
            </Box>

          </Box>

        </Box>

      </form>



    </div>
  );
}

export default FollowParcelComponent;