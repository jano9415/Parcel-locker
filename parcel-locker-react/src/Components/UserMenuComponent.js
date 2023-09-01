import React, { useState } from 'react';
import Box from '@mui/material/Box';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import { Link } from 'react-router-dom';
import AuthService from '../Service/AuthService';

const UserMenuComponent = () => {

    const [value, setValue] = useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    return (
        <Box sx={{ width: '100%', bgcolor: 'background.paper' }}>
            <Tabs value={value} onChange={handleChange} centered>

                {
                    AuthService.getCurrentUser() && AuthService.getCurrentUser().roles.includes("user") && (
                        <Tab label={<Link to={"/parcelsending"} className='nav-link'>Csomagküldés</Link>} />
                    )
                }
                <Tab label={<Link to={"/followparcel"} className='nav-link'>Csomagkövetés</Link>} />
                <Tab label={<Link to={"/parcellockers"} className='nav-link'>Csomagautomaták</Link>} />
                <Tab label={<Link to={"/prices"} className='nav-link'>Áraink</Link>} />
                <Tab label={<Link to={"/"} className='nav-link'>Ügyfélszolgálat</Link>} />

            </Tabs>
        </Box>
    );
}

export default UserMenuComponent;