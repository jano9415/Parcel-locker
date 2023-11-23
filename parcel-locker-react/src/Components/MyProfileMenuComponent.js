import { Box, Tab, Tabs } from "@mui/material";
import { useState } from "react";
import { Link } from "react-router-dom";

const MyProfileMenuComponent = () => {

    const [value, setValue] = useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    return (
        <Box sx={{ width: '100%', bgcolor: 'background.paper' }}>
            <Tabs value={value} onChange={handleChange} centered>
                <Tab label={<Link to={"/myparcels"} className='nav-link'>Csomagjaim</Link>} />
                <Tab label={<Link to={"/personaldata"} className='nav-link'>Személyes adatok</Link>} />
                <Tab label={<Link to={"/updatepassword"} className='nav-link'>Jelszó módosítása</Link>} />
            </Tabs>
        </Box>
    );

}

export default MyProfileMenuComponent;