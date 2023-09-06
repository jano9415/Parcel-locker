import { Box, Button, InputLabel, MenuItem, Modal, Paper, Select, Typography } from "@mui/material";
import MyProfileMenuComponent from "./MyProfileMenuComponent";
import { useEffect, useState } from "react";
import ParcelService from "../Service/ParcelService";

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 64,
    p: 4,
};

const ParcelDetailsModalComponent = (props) => {

    const [open, setOpen] = useState(false);

    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);


    useEffect(() => {


    }, [])

    const showDatas = () => {
        setOpen(true);
    }

    return (
        <Box>
            <Button onClick={showDatas}>Részletek</Button>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description">
                <Box sx={style}>
                    <Typography>Csomagadatok</Typography>
                    <Typography>{props.parcel.pickingUpCode}</Typography>
                </Box>
            </Modal>

        </Box>
    );
}

export default ParcelDetailsModalComponent;