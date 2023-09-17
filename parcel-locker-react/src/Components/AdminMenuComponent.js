import React, { useState } from 'react';
import Box from '@mui/material/Box';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import { Link, useNavigate } from 'react-router-dom';
import { Menu, MenuItem } from '@mui/material';

const AdminMenuComponent = () => {

    let navigate = useNavigate();


    const [value, setValue] = useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const [anchorEl, setAnchorEl] = useState(null);
    const open = Boolean(anchorEl);
    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };

    //Link a központi raktárak csomagjai oldalra
    const getParcelsOfStore = () => {
        setAnchorEl(null);
        navigate("/getparcelsofstore");

    }

    //Link az automaták csomagjai oldalra
    const getParcelsOfParcelLocker = () => {
        setAnchorEl(null);
        navigate("/getparcelsofparcellocker");

    }

    //Link a futárok csomagjai oldalra
    const getParcelsOfCourier = () => {
        setAnchorEl(null);
        navigate("/getparcelsofcourier");

    }


    return (
        <Box sx={{ width: '100%', bgcolor: 'background.paper' }}>
            <Tabs value={value} onChange={handleChange} centered>

                <Tab label={<Link to={"/createcourier"} className='nav-link'>Futár hozzáadása</Link>} />
                <Tab label={<Link to={"/createadmin"} className='nav-link'>Admin hozzáadása</Link>} />
                <Tab label={<Link to={"/getcouriers"} className='nav-link'>Futárok</Link>} />
                <Tab label={<Link to={"/statistics"} className='nav-link'>Statisztikák</Link>} />

                <Tab id="basic-button"
                    aria-controls={open ? 'basic-menu' : undefined}
                    aria-haspopup="true"
                    aria-expanded={open ? 'true' : undefined}
                    onClick={handleClick} label="csomagok" />
                <Menu
                    id="basic-menu"
                    anchorEl={anchorEl}
                    open={open}
                    onClose={handleClose}
                    MenuListProps={{
                        'aria-labelledby': 'basic-button',
                    }}
                >
                    <MenuItem onClick={getParcelsOfParcelLocker}>Automaták csomagjai</MenuItem>
                    <MenuItem onClick={getParcelsOfCourier}>Futárok csomagjai</MenuItem>
                    <MenuItem onClick={getParcelsOfStore}>Központi raktárak csomagjai</MenuItem>
                </Menu>
            </Tabs>

        </Box>
    );
}

export default AdminMenuComponent;