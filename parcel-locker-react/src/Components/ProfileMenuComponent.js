import * as React from 'react';
import Box from '@mui/material/Box';
import Avatar from '@mui/material/Avatar';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import Settings from '@mui/icons-material/Settings';
import Logout from '@mui/icons-material/Logout';
import { Tab } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import postlogo from '../postlogo.png'
import AuthService from '../Service/AuthService';

const ProfileMenuComponent = () => {

    const [anchorEl, setAnchorEl] = React.useState(null);
    const navigate = useNavigate();



    const open = Boolean(anchorEl);
    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };

    const myProfile = () => {
        navigate('/myparcels');
    }

    return (
        <React.Fragment>
            <Box sx={{ display: 'flex', alignItems: 'center', textAlign: 'center', color: 'white', backgroundColor: 'blue' }}>
                <Link to={"/"} className='nav-link'>
                    <img src={postlogo} alt="Logo" height="40" />
                </Link>
                {
                    !AuthService.getCurrentUser() && (
                        <Tab label={<Link to={"/login"} className='nav-link'>Bejelentkezés</Link>} />
                    )
                }
                {
                    !AuthService.getCurrentUser() && (
                        <Tab label={<Link to={"/signup"} className='nav-link'>Regisztráció</Link>} />
                    )
                }
                {
                    AuthService.getCurrentUser() && (
                        <Tooltip title="Felhasználói fiók">
                            <IconButton
                                onClick={handleClick}
                                size="medium"
                                sx={{ ml: 2 }}
                                aria-controls={open ? 'account-menu' : undefined}
                                aria-haspopup="true"
                                aria-expanded={open ? 'true' : undefined}
                            >
                                <Avatar sx={{ width: 32, height: 32 }}>{AuthService.getCurrentUser().emailAddress[0]}</Avatar>
                            </IconButton>
                        </Tooltip>
                    )
                }
            </Box>
            {
                AuthService.getCurrentUser() && (
                    <Menu
                        anchorEl={anchorEl}
                        id="account-menu"
                        open={open}
                        onClose={handleClose}
                        onClick={handleClose}
                        PaperProps={{
                            elevation: 0,
                            sx: {
                                overflow: 'visible',
                                filter: 'drop-shadow(0px 2px 8px rgba(0,0,0,0.32))',
                                mt: 1.5,
                                '& .MuiAvatar-root': {
                                    width: 32,
                                    height: 32,
                                    ml: -0.5,
                                    mr: 1,
                                },
                                '&:before': {
                                    content: '""',
                                    display: 'block',
                                    position: 'absolute',
                                    top: 0,
                                    right: 14,
                                    width: 10,
                                    height: 10,
                                    bgcolor: 'background.paper',
                                    transform: 'translateY(-50%) rotate(45deg)',
                                    zIndex: 0,
                                },
                            },
                        }}
                        transformOrigin={{ horizontal: 'right', vertical: 'top' }}
                        anchorOrigin={{ horizontal: 'right', vertical: 'bottom' }}
                    >
                        <MenuItem onClick={myProfile}>
                            <Avatar /> Profilom
                        </MenuItem>
                        <Divider />
                        <MenuItem onClick={handleClose}>
                            <ListItemIcon>
                                <Settings fontSize="small" />
                            </ListItemIcon>
                            Beállítások
                        </MenuItem>
                        <MenuItem onClick={handleClose}>
                            <ListItemIcon>
                                <Logout fontSize="small" />
                            </ListItemIcon>
                            <Link to={"/login"} onClick={AuthService.logOut} className='nav-link'>Kijelentkezés</Link>
                        </MenuItem>
                    </Menu>

                )
            }
        </React.Fragment>
    );
}

export default ProfileMenuComponent;