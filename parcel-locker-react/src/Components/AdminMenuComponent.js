import React, { useState } from 'react';
import Box from '@mui/material/Box';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import { Link } from 'react-router-dom';

const AdminMenuComponent = () => {


    const [value, setValue] = useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };


    return (
        <Box sx={{ width: '100%', bgcolor: 'background.paper' }}>
            <Tabs value={value} onChange={handleChange} centered>

                <Tab label={<Link to={"/createcourier"} className='nav-link'>Futár hozzáadása</Link>} />
                <Tab label={<Link to={"/createadmin"} className='nav-link'>Admin hozzáadása</Link>} />
                <Tab label={<Link to={"/getcouriers"} className='nav-link'>Futárok</Link>} />
                <Tab label={<Link to={"/statistics"} className='nav-link'>Statisztikák</Link>} />
                <Tab label={<Link to={"/createadmin"} className='nav-link'>Csomagok</Link>} />
            </Tabs>
            
        </Box>
    );
}

export default AdminMenuComponent;