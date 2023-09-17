import { Box, Button, Modal, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import UpdateSendingExpirationDate from "../ModalComponents/UpdateSendingExpirationDate";
import UpdatePickingUpExpirationDate from "../ModalComponents/UpdatePickingUpExpirationDate";

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

const UpdateParcelOptionsModalComponent = (props) => {

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

            <Button onClick={showDatas}>Módosítás</Button>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description">
                <Box sx={style}>
                    <Typography>Csomagfeladási dátum módosításához</Typography>
                    {
                        props.parcel.sendingExpirationDate != null && (
                            <UpdateSendingExpirationDate parcel={props.parcel}></UpdateSendingExpirationDate>
                        )
                    }

                    <Typography>Csomagátvételi dátum módosításásához</Typography>
                    {
                        props.parcel.pickingUpExpirationDate != null && (
                            <UpdatePickingUpExpirationDate parcel={props.parcel}></UpdatePickingUpExpirationDate>
                        )
                    }

                </Box>
            </Modal>

        </Box>
    );
}

export default UpdateParcelOptionsModalComponent;