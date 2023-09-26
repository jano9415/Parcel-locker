import { Box, Button, Modal, Typography } from "@mui/material";
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


const DeleteParcelModalComponent = (props) => {

    const [open, setOpen] = useState(false);

    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);


    useEffect(() => {


    }, [])

    const showDatas = () => {
        setOpen(true);
    }

    const deleteParcel = (value) =>{

        if(value === "no"){
            setOpen(false);
        }

        //Csomag törlése
        if(value === "yes"){
            ParcelService.deleteMyParcel(props.parcel.id).then((response) => {

                if(response.data.message === "notFound"){
                    setOpen(false);
                }
                if(response.data.message === "successfulDeleting"){
                    setOpen(false);
                    window.location.reload();
                }

              },
                (error) => {
        
                })
        }
        
    }


    return (
        <Box>
            <Button onClick={showDatas}>Törlés</Button>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description">
                <Box sx={style}>
                    <Typography>Biztosan törölni szeretnéd a(z) {props.parcel.uniqueParcelId} számú csomagod?</Typography>
                    <Button onClick={() => deleteParcel("yes")}>Igen</Button>
                    <Button onClick={() => deleteParcel("no")}>Nem</Button>
                    <Button onClick={() => { setOpen(false) }}>Vissza</Button>
                </Box>
            </Modal>

        </Box>
    );
}

export default DeleteParcelModalComponent;