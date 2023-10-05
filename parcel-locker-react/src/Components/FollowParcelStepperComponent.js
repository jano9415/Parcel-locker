import { Box, Step, StepLabel, Stepper, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';


const FollowParcelStepperComponent = (props) => {

    const [actualStep, setActualStep] = useState();

    //1. verzió
    const stepsWithPreSending = [
        'Előzetesen feladva',
        'Csomag feladva',
        'Csomag a feladási megyei raktárban',
        'Csomag az átvételi megyei raktárból elindult',
        'Csomag megérkezett',
        'Csomag átvéve'
    ];

    //2. verzió
    const stepsWithOutPreSending = [
        'Csomag feladva',
        'Csomag a feladási megyei raktárban',
        'Csomag az átvételi megyei raktárból elindult',
        'Csomag megérkezett',
        'Csomag átvéve'
    ];

    //3. verzió
    const stepsWithPreSendingAndExpired = [
        'Előzetesen feladva',
        'Csomag feladva',
        'Csomag a feladási megyei raktárban',
        'Csomag az átvételi megyei raktárból elindult',
        'Csomag megérkezett',
        'Csomag átvételi ideje lejárt',
        'Csomag átvéve'
    ];

    //4. verzió
    const stepsWithOutPreSendingAndExpired = [
        'Csomag feladva',
        'Csomag a feladási megyei raktárban',
        'Csomag az átvételi megyei raktárból elindult',
        'Csomag megérkezett',
        'Csomag átvételi ideje lejárt',
        'Csomag átvéve'
    ];

    useEffect(() => {


        //1. verzió
        if (props.parcel.sendingExpirationDate != null && props.parcel.pickingUpExpired === false) {

            setActualStep(0);
            if (props.parcel.sendingDate != null) {
                setActualStep(1);
            }
            if (props.parcel.handingDateToFirstStoreByCourier != null) {
                setActualStep(2);
            }
            if (props.parcel.pickingUpDateFromSecondStoreByCourier != null) {
                setActualStep(3);
            }
            if (props.parcel.shippingDate != null) {
                setActualStep(4);
            }
            if (props.parcel.pickingUpDate != null) {
                setActualStep(5);
            }

        }

        //2. verzió
        if (props.parcel.sendingExpirationDate == null && props.parcel.pickingUpExpired === false) {

            if (props.parcel.sendingDate != null) {
                setActualStep(0);
            }
            if (props.parcel.handingDateToFirstStoreByCourier != null) {
                setActualStep(1);
            }
            if (props.parcel.pickingUpDateFromSecondStoreByCourier != null) {
                setActualStep(2);
            }
            if (props.parcel.shippingDate != null) {
                setActualStep(3);
            }
            if (props.parcel.pickingUpDate != null) {
                setActualStep(4);
            }

        }

        //3. verzió
        if (props.parcel.sendingExpirationDate != null && props.parcel.pickingUpExpired) {

            setActualStep(5);
            if (props.parcel.pickingUpDate != null) {
                setActualStep(6);
            }

        }

        //4. verzió
        if (props.parcel.sendingExpirationDate == null && props.parcel.pickingUpExpired) {

            setActualStep(4);
            if (props.parcel.pickingUpDate != null) {
                setActualStep(5);
            }

        }

    }, [])





    return (
        <Box>

            {
                props.parcel.sendingExpirationDate != null && props.parcel.pickingUpExpired === false && (
                    <Box sx={{ width: '100%' }}>
                        <Stepper activeStep={actualStep} alternativeLabel>
                            {stepsWithPreSending.map((label) => (
                                <Step key={label}>
                                    <StepLabel>{label}</StepLabel>
                                </Step>
                            ))}
                        </Stepper>
                    </Box>
                )
            }

            {
                props.parcel.sendingExpirationDate == null && props.parcel.pickingUpExpired === false && (
                    <Box sx={{ width: '100%' }}>
                        <Stepper activeStep={actualStep} alternativeLabel>
                            {stepsWithOutPreSending.map((label) => (
                                <Step key={label}>
                                    <StepLabel>{label}</StepLabel>
                                </Step>
                            ))}
                        </Stepper>
                    </Box>
                )
            }

            {
                props.parcel.sendingExpirationDate != null && props.parcel.pickingUpExpired && (
                    <Box sx={{ width: '100%' }}>
                        <Stepper activeStep={actualStep} alternativeLabel>
                            {stepsWithPreSendingAndExpired.map((label) => (
                                <Step key={label}>
                                    <StepLabel>{label}</StepLabel>
                                </Step>
                            ))}
                        </Stepper>
                    </Box>
                )
            }

            {
                props.parcel.sendingExpirationDate == null && props.parcel.pickingUpExpired && (
                    <Box sx={{ width: '100%' }}>
                        <Stepper activeStep={actualStep} alternativeLabel>
                            {stepsWithOutPreSendingAndExpired.map((label) => (
                                <Step key={label}>
                                    <StepLabel>{label}</StepLabel>
                                </Step>
                            ))}
                        </Stepper>
                    </Box>
                )
            }


        </Box>

    );


}

export default FollowParcelStepperComponent;