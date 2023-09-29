import React, { useEffect, useState } from 'react';
import { Form, useFormik } from 'formik';
import { Box, Button, Chip, Divider, FormControlLabel, FormLabel, InputLabel, MenuItem, Radio, RadioGroup, Select, TextField, Typography } from '@mui/material';
import * as Yup from 'yup';
import ParcelService from '../Service/ParcelService';
import image15 from '../image15.png';
import image16 from '../image16.png';
import FollowParcelStepperComponent from './FollowParcelStepperComponent';




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
      },
        (error) => {

        })

    }
  });


  return (
    <Box>
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

            {
              parcel.message === null && (
                <FollowParcelStepperComponent parcel={parcel}></FollowParcelStepperComponent>
              )
            }


            {parcel.message === "notFound" && (
              <Box>
                <Typography>A megadott azonosítóval nem található csomag</Typography>
              </Box>
            )}

            {parcel.message === null && (
              <Box>
                <Divider><Chip label="Feladó" /></Divider>
                <Typography>Feladó neve: {parcel.senderName}</Typography>
                <Typography>Feladó email címe: {parcel.senderEmailAddress}</Typography>
              </Box>
            )}


            {parcel.message === null && parcel.sendingExpirationDate != null && (
              <Box className='mt-2'>
                <Divider><Chip label="Előzetesen feladva" /></Divider>
                <Typography>Csomag még nincs elhelyezve, csak előzetesen feladva</Typography>
                <Typography>Feladási automata: {
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
                <Divider><Chip label="Csomag feladva" /></Divider>
                <Typography>Csomag feladva {parcel.sendingDate + " " + parcel.sendingTime + "-kor"}</Typography>
                <Typography>Feladási automata: {
                  parcel.shippingFromPostCode + " " + parcel.shippingFromCity + " " + parcel.shippingFromStreet
                }</Typography>
              </Box>
            )}

            {parcel.message === null && parcel.handingDateToFirstStoreByCourier != null && (
              <Box className='mt-2'>
                <Divider><Chip label="Feladási megyei raktárban" /></Divider>
                <Typography>Csomag leszállítva {parcel.handingDateToFirstStoreByCourier + " " +
                  parcel.handingTimeToFirstStoreByCourier + "-kor"}</Typography>
              </Box>
            )}

            {parcel.message === null && parcel.pickingUpDateFromSecondStoreByCourier != null && (
              <Box className='mt-2'>
                <Divider><Chip label="Átvételi megyei raktárból elindult" /></Divider>
                <Typography>Csomag elindult {parcel.pickingUpDateFromSecondStoreByCourier + " " +
                  parcel.pickingUpTimeFromSecondStoreByCourier + "-kor"}</Typography>
              </Box>
            )}

            {parcel.message === null && parcel.shippingDate != null && (
              <Box>
                <Divider><Chip label="Csomag megérkezett" /></Divider>
                <Typography>Csomag leszállítva {parcel.shippingDate + " " + parcel.shippingTime + "-kor"}</Typography>
                <Typography>Itt tudod átvenni {
                  parcel.shippingToPostCode + " " + parcel.shippingToCity + " " + parcel.shippingToStreet
                }</Typography>
                <Typography>Eddig tudod átvenni: {
                  parcel.pickingUpExpirationDate + " " + parcel.pickingUpExpirationTime
                }
                </Typography>
              </Box>
            )}

            {
              parcel.message === null && parcel.pickingUpExpired === true && (
                <Box>
                  <Divider><Chip label="Csomag átvételi ideje lejárt" /></Divider>
                  <Typography>A csomagot a futár visszaszállította az átvételi megyei raktárba,
                    <Typography>mert lejárt az átvételi ideje.</Typography>
                    <Typography>Ha szeretnéd újraindítani a csomagot az átvételi automatához,</Typography>
                    <Typography>akkor hívd fel az ügyfélszolgálatot.</Typography>
                  </Typography>
                </Box>
              )
            }

            {parcel.message === null && parcel.pickingUpDate != null && (
              <Box>
                <Divider><Chip label="Csomag átvéve" /></Divider>
                <Typography>Csomag átvéve {parcel.pickingUpDate + " " + parcel.pickingUpTime + "-kor"}</Typography>
              </Box>
            )}

          </Box>

        </Box>

      </form>

      <Box sx={{ height: '100px' }}>

      </Box>

      <Box className="d-flex justify-content-center">
        <Box>
          <Typography sx={{ fontSize: 40 }}>Csomagátvétel</Typography>
        </Box>
      </Box>


      <Box className="d-flex justify-content-center">
        <Box>
          <img src={image15} alt="Logo" height="470" />
        </Box>
      </Box>

      <Box className="d-flex justify-content-center">
        <Box>
          <img src={image16} alt="Logo" height="470" />
        </Box>
      </Box>



    </Box>
  );
}

export default FollowParcelComponent;