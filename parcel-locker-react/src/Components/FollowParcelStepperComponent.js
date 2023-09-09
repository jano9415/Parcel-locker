import { Box, Step, StepLabel, Stepper, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';


const FollowParcelStepperComponent = (props) => {

    const [actualStep, setActualStep] = useState();

    const steps = [
        'Előzetesen feladva',
        'Csomag feladva',
        'Csomag megérkezett',
        'Csomag átvéve'
    ];

    useEffect(() => {

        if(props.parcel.sendingExpirationDate != null){
            setActualStep(0);
        }
        if(props.parcel.sendingDate != null){
            setActualStep(1);
        }
        if(props.parcel.shippingDate != null){
            setActualStep(2);
        }
        if(props.parcel.pickingUpDate != null){
            setActualStep(3);
        }

    }, [])

    



    return (
        <Box>

            <Box sx={{ width: '100%' }}>
                <Stepper activeStep={actualStep} alternativeLabel>
                    {steps.map((label) => (
                        <Step key={label}>
                            <StepLabel>{label}</StepLabel>
                        </Step>
                    ))}
                </Stepper>
            </Box>

        </Box>

    );


}

export default FollowParcelStepperComponent;