import { Box, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import swiftpostLogo from '../swifpostLogo.jpg';


const FooterMenuComponent = () => {


    return (
        <Box>
            <Box>
                <img src={swiftpostLogo} alt="Logo" width={200} height={150} />
            </Box>

            <Box className="d-flex justify-content-center" sx={{ backgroundColor: 'blue' }}>
                <Box className='m-3'>
                    <Typography sx={{ color: 'white' }}>Elérhetőségek</Typography>
                </Box>
                <Box className='m-3'>
                    <Typography sx={{ color: 'white' }}>Adatvédelem</Typography>
                </Box>
                <Box className='m-3'>
                    <Typography sx={{ color: 'white' }}>GYIK</Typography>
                </Box>
                <Box className='m-3'>
                    <Typography sx={{ color: 'white' }}>Karrier</Typography>
                </Box>
            </Box>
        </Box>

    );


}

export default FooterMenuComponent;