import { Box, Step, StepLabel, Stepper, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';


const FollowParcelStepperComponent = (props) => {

    const [actualStep, setActualStep] = useState();

    const stepsWithPreSending = [
        'Előzetesen feladva',
        'Csomag feladva',
        'Csomag a feladási megyei raktárban',
        'Csomag az átvételi megyei raktárból elindult',
        'Csomag megérkezett',
        'Csomag átvéve'
    ];

    const stepsWithOutPreSending = [
        'Csomag feladva',
        'Csomag a feladási megyei raktárban',
        'Csomag az átvételi megyei raktárból elindult',
        'Csomag megérkezett',
        'Csomag átvéve'
    ];

    const stepsWithPreSendingAndExpired = [
        'Előzetesen feladva',
        'Csomag feladva',
        'Csomag a feladási megyei raktárban',
        'Csomag az átvételi megyei raktárból elindult',
        'Csomag megérkezett',
        'Csomag átvételi ideje lejárt',
        'Csomag átvéve'
    ];

    const stepsWithOutPreSendingAndExpired = [
        'Csomag feladva',
        'Csomag a feladási megyei raktárban',
        'Csomag az átvételi megyei raktárból elindult',
        'Csomag megérkezett',
        'Csomag átvételi ideje lejárt',
        'Csomag átvéve'
    ];

    useEffect(() => {

        if (props.parcel.sendingExpirationDate != null) {
            setActualStep(0);
        }
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

        //Ha lejárt az átvételi idő, akkor vissza az átvételi megyei raktárba
        if (props.pickingUpExpired) {
            setActualStep(4);
        }

    }, [])





    return (
        <Box>

            {
                props.parcel.sendingExpirationDate != null && (
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
                props.parcel.sendingExpirationDate == null && (
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
                props.parcel.sendingExpirationDate != null && props.pickingUpExpired && (
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
                props.parcel.sendingExpirationDate == null && props.pickingUpExpired && (
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