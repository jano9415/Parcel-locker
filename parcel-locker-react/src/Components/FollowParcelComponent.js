import React, { useEffect, useState } from 'react';
import { Form, useFormik } from 'formik';
import { Box, Button, FormControlLabel, FormLabel, InputLabel, MenuItem, Radio, RadioGroup, Select, TextField, Typography } from '@mui/material';
import * as Yup from 'yup';
import ParcelService from '../Service/ParcelService';




const FollowParcelComponent = () => {

  const [parcel, setParcel] = useState({});

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

        setParcel(response.data);
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

            {parcel.message === "notFound" && (
              <Box>
                <Typography>A megadott azonosítóval nem található csomag</Typography>
              </Box>
            )}

            {parcel.message === null && parcel.sendingExpirationDate != null && (
              <Box>
                <Typography>Csomag még nincs elhelyezve, csak előzetesen feladva</Typography>
                <Typography>Itt tudod feladni: {
                  parcel.shippingFromPostCode + " " + parcel.shippingFromCity + " " + parcel.shippingFromStreet
                }</Typography>
                <Typography>Eddig tudod feladni: {
                  parcel.sendingExpirationDate + " " + parcel.sendingExpirationTime
                }
                </Typography>
              </Box>
            )}

            {parcel.message === null && parcel.sendingDate != null && (
              <Box className='mt-2'>
                <Typography>Csomag feladva {parcel.sendingDate + " " + parcel.sendingTime + "-kor"}</Typography>
                <Typography>Feladási automata: {
                  parcel.shippingFromPostCode + " " + parcel.shippingFromCity + " " + parcel.shippingFromStreet
                }</Typography>
              </Box>
            )}

            {parcel.message === null && parcel.shippingDate != null && (
              <Box>
                <Typography>Csomag leszállítva {parcel.shippingDate + " " + parcel.shippingTime + "-kor"}</Typography>
                <Typography>Érkezési automata {
                  parcel.shippingToPostCode + " " + parcel.shippingToCity + " " + parcel.shippingToStreet
                }</Typography>
                <Typography>Eddig tudod átvenni: {
                  parcel.pickingUpExpirationDate + " " + parcel.pickingUpExpirationTime
                }
                </Typography>
              </Box>
            )}

            {parcel.message === null && parcel.pickingUpDate != null && (
              <Box>
                <Typography>Csomag átvéve {parcel.pickingUpDate + " " + parcel.pickingUpTime + "-kor"}</Typography>
                <Typography>Érkezési automata {
                  parcel.shippingToPostCode + " " + parcel.shippingToCity + " " + parcel.shippingToStreet
                }</Typography>
              </Box>
            )}

          </Box>

        </Box>

      </form>



    </div>
  );
}

export default FollowParcelComponent;