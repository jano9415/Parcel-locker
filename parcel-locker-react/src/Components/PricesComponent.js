import React, { useState } from 'react';
import image17 from '../image17.png';
import { Box, Typography } from '@mui/material';
import image18 from '../image18.png';


const PricesComponent = () => {


    return (
        <Box>
            <Box className="d-flex justify-content-center">
                <Box>
                    <Typography sx={{ fontSize: 40 }}>Csomagok maximális mérete és súlya</Typography>
                </Box>
            </Box>

            <Box className="d-flex justify-content-center">
                <Box>
                    <img src={image17} alt="Logo" height="470" />
                </Box>
            </Box>

            <Box className="d-flex justify-content-center">
                <Box>
                    <Typography sx={{ fontSize: 40 }}>Árak</Typography>
                </Box>
            </Box>

            <Box className="d-flex justify-content-center">
                <Box>
                    <img src={image18} alt="Logo" height="470" />
                </Box>
            </Box>

        </Box>

    );
}

export default PricesComponent;