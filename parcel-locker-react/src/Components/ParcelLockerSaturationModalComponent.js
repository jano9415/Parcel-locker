import { Box, Button, Modal, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import ParcelLockerService from '../Service/ParcelLockerService';

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


const ParcelLockerSaturationModalComponent = (props) => {

    const [open, setOpen] = useState(false);
    const [saturationData, setSaturationData] = useState({});


    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    useEffect(() => {


    }, [])

    const showDatas = () => {
        setOpen(true);

        //A kiválasztott automata telítettségi adatainak lekérése
        ParcelLockerService.getSaturationDatas(props.parcelLockerId).then(
            (response) => {
                setSaturationData(response.data);
            },
            (error) => {

            }
        )
    }



    return (
        <div>
            <Button onClick={showDatas}>Részletek</Button>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description">
                <Box sx={style}>
                    <Typography>Telítettségi adatok</Typography>
                    <Typography>
                        Szabad kicsi rekeszek száma: {saturationData.amountOfSmallBoxes - saturationData.amountOfFullSmallBoxes}
                    </Typography>
                    <Typography>
                        Szabad közepes rekeszek száma: {saturationData.amountOfMediumBoxes - saturationData.amountOfFullMediumBoxes}
                    </Typography>
                    <Typography>
                        Szabad nagy rekeszek száma: {saturationData.amountOfLargeBoxes - saturationData.amountOfFullLargeBoxes}
                    </Typography>
                </Box>
            </Modal>

        </div>
    );
}

export default ParcelLockerSaturationModalComponent;
