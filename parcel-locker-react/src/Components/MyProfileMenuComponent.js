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
                <Tab label={<Link to={"/followparcel"} className='nav-link'>Csomagjaim</Link>} />
            </Tabs>
        </Box>
    );

}

export default MyProfileMenuComponent;